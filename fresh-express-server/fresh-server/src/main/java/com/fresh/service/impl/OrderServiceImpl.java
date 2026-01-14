package com.fresh.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.fresh.constant.MessageConstant;
import com.fresh.context.BaseContext;
import com.fresh.dto.OrdersPaymentDTO;
import com.fresh.dto.OrdersSubmitDTO;
import com.fresh.entity.*;
import com.fresh.exception.AddressBookBusinessException;
import com.fresh.exception.ShoppingCartBusinessException;
import com.fresh.mapper.*;
import com.fresh.service.OrderService;
import com.fresh.vo.OrderPaymentVO;
import com.fresh.vo.OrderSubmitVO;
import org.apache.http.entity.ContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if(addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if(shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);

        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String redisKey = "order:" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Long seq = redisTemplate.opsForValue().increment(redisKey);
        if (seq == 1) {
            redisTemplate.expire(redisKey, 2, TimeUnit.SECONDS);
        }
        String seqStr = String.format("%04d", seq);
        String number = time + seqStr;
        orders.setNumber(number);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());

        orderMapper.insert(orders);

        List<OrderDetail> orderDetails = shoppingCartList.stream().map(x -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(x, orderDetail);
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).toList();

        orderDetailMapper.insertBatch(orderDetails);
        shoppingCartMapper.deleteByUserId(userId);

        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(number)
                .orderAmount(ordersSubmitDTO.getAmount())
                .orderTime(orders.getOrderTime())
                .build();

        return orderSubmitVO;
    }

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO
     * @return
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
        this.paySuccess(ordersPaymentDTO.getOrderNumber());
        return new OrderPaymentVO();
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param orderNumber
     */
    @Override
    public void paySuccess(String orderNumber) {
        Orders orders = Orders.builder()
                .number(orderNumber)
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();
        orderMapper.updateByNumber(orders);
    }
}
