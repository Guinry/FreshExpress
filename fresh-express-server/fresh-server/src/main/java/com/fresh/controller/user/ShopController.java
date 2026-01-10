package com.fresh.controller.user;

import com.fresh.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
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
