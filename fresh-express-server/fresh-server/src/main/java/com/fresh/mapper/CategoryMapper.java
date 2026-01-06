package com.fresh.mapper;

import com.fresh.annotation.AutoFill;
import com.fresh.dto.CategoryPageQueryDTO;
import com.fresh.entity.Category;
import com.fresh.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 插入数据
     * @param category
     */
    @Insert("insert into category (type, name, sort, status ,create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category getById(Long id);

    /**
     * 修改分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);
}
