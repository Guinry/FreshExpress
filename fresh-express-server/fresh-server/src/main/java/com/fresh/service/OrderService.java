package com.fresh.service;

import com.fresh.dto.OrdersPaymentDTO;
import com.fresh.dto.OrdersSubmitDTO;
import com.fresh.vo.OrderPaymentVO;
import com.fresh.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 支付订单
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 支付成功，修改订单状态
     *
     * @param orderNumber
     */
    void paySuccess(String orderNumber);
}
