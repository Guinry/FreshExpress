package com.fresh.controller.user;

import com.fresh.entity.Category;
import com.fresh.result.Result;
import com.fresh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController( "userCategoryController")
@RequestMapping ("/user/category")
@Api(tags = "C端-分类接口")
@Slf4j
public class CategoryController {
     @Autowired
     private CategoryService categoryService;
     /**
     * 根据类型查询
     * @param type
     * @return
     */
     @GetMapping("/list")
     @ApiOperation("根据类型查询")
     public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询");
        System .out.println(type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
