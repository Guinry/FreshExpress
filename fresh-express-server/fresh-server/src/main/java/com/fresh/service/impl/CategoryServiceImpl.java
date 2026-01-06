package com.fresh.service.impl;

import com.fresh.constant.StatusConstant;
import com.fresh.context.BaseContext;
import com.fresh.dto.CategoryDTO;
import com.fresh.dto.CategoryPageQueryDTO;
import com.fresh.entity.Category;
import com.fresh.mapper.CategoryMapper;
import com.fresh.result.PageResult;
import com.fresh.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 新增分类
     *
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.DISABLE);

        category.setType(categoryDTO.getType());
        category.setSort(categoryDTO.getSort());
        category.setName(categoryDTO.getName());

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    @Override
    public Category getById(Long id) {
        return categoryMapper.getById(id);
    }
    /**
     * 修改分类
     *
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.update(category);
    }

    /**
     * 删除分类
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        categoryMapper.delete(id);
    }
}
