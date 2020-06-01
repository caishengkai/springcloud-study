package com.csk.cloud.service;

import com.csk.cloud.common.CommonResult;
import com.csk.cloud.model.User;
import com.csk.cloud.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/31 10:43
 */
@FeignClient(value = "user-service",fallback = UserFallbackService.class)
public interface UserService {

    @GetMapping("/user/{id}")
    CommonResult<User> getUser(@PathVariable Long id);

}
