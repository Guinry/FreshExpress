package com.fresh.service;

import com.fresh.dto.CategoryDTO;
import com.fresh.dto.CategoryPageQueryDTO;
import com.fresh.result.PageResult;

public interface CategoryService {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);
}
