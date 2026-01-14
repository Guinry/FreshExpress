package com.fresh.mapper;

import com.fresh.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    /**
     * 新增
     * @param orders
     */
    void insert(Orders orders);
}
