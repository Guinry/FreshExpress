package com.fresh.service;

import com.fresh.dto.OrdersSubmitDTO;
import com.fresh.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
