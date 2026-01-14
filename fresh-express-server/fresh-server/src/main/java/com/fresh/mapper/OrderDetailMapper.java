package com.fresh.mapper;

import com.fresh.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单详情
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);
}
