package com.fresh.controller.user;

import com.fresh.constant.StatusConstant;
import com.fresh.entity.Setmeal;
import com.fresh.result.Result;
import com.fresh.service.SetmealService;
import com.fresh.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController( "userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐接口")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
     /**
     * 根据分类id条件查询套餐
     * @param categoryId
     * @return
     */
    @GetMapping ("/list")
    @ApiOperation("根据分类id条件查询套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("根据分类id条件查询套餐：{}", categoryId);
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.getByCategoryId(setmeal);
        return Result.success(list);
    }
    /**
     * 根据套餐id查询包含的菜品列表
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据id查询包含的菜品列表")
    @Cacheable(cacheNames = "setmealDishCache",key = "#id")
    public Result<List<DishItemVO>> listWithDish(@PathVariable  Long id) {
        log.info("查询套餐下的菜品：{}", id);
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }

}
