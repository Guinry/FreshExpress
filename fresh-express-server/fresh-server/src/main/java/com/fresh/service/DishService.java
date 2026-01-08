package com.fresh.service;

import com.fresh.dto.DishDTO;

public interface DishService {
    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}
