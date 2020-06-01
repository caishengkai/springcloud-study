package com.csk.cloud.service.impl;

import com.csk.cloud.common.CommonResult;
import com.csk.cloud.model.User;
import com.csk.cloud.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/31 10:46
 */
@Component
public class UserFallbackService implements UserService {
    @Override
    public CommonResult<User> getUser(Long id) {
        return CommonResult.success(new User(1, "王五"));
    }
}
