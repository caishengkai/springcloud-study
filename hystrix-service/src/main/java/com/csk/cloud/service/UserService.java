package com.csk.cloud.service;

import com.csk.cloud.common.CommonResult;
import com.csk.cloud.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

import java.util.concurrent.Future;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/29 10:16
 */
public interface UserService {
    CommonResult getUser(Long id);

    CommonResult getUserException(Long id);

    CommonResult getCachedUser(Long id);

    CommonResult removeCachedUser(Long id);

    Future<User> getUserFuture(Long id);
}
