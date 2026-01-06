package com.fresh.service;

import com.fresh.dto.CategoryDTO;
import com.fresh.dto.CategoryPageQueryDTO;
import com.fresh.entity.Category;
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

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    Category getById(Long id);

    /**
     * 修改分类
     * @param category
     */
    void update(Category category);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除分类
     * @param id
     */
    void delete(Long id);
}
