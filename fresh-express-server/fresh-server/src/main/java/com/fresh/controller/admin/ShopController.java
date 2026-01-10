package com.fresh.controller.admin;

import com.fresh.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺营业状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     *
     * @return
     */
    @RequestMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus() {
        log.info("获取店铺营业状态");
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        log.info("店铺营业状态：{}", shopStatus == 1 ? "营业中" : "打烊中");
        return Result.success(shopStatus);
    }
}
