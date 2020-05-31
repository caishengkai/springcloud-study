package com.csk.cloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.csk.cloud.common.CommonResult;
import com.csk.cloud.model.User;
import com.csk.cloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/29 10:16
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    /**
     * 降级服务之降级方法
     * 指定降级方法为getDefaultUser
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getDefaultUser")
    @Override
    public CommonResult getUser(Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);

    }

    /**
     * getUser的降级方法
     * @param id
     * @return
     */
    public CommonResult getDefaultUser(@PathVariable Long id) {
        User defaultUser = new User(id, "李四");
        return CommonResult.success(defaultUser);
    }

    /**
     * 降级服务之忽略某些异常降级
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getDefaultUser2", ignoreExceptions = {NullPointerException.class})
    @Override
    public CommonResult getUserException(Long id) {
        if (id == 1) {
            throw new NullPointerException();
        } else if(id == 2) {
            throw new IndexOutOfBoundsException();
        }
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    /**
     * getUserException的降级方法
     * @param id
     * @return
     */
    public CommonResult getDefaultUser2(@PathVariable Long id, Throwable e) {
        log.error("getDefaultUser2 id:{},throwable class:{}", id, e.getClass());
        User defaultUser = new User(id, "李四");
        return CommonResult.success(defaultUser);
    }

    /**
     * 开启缓存
     * @CacheResult开启缓存
     * cacheKeyMethod指定返回key的方法名
     * @param id
     * @return
     */
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "getCachedUser")
    @Override
    public CommonResult getCachedUser(Long id) {
        log.info("getUserCache id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @CacheRemove(commandKey = "getCachedUser", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    @Override
    public CommonResult removeCachedUser(Long id) {
        log.info("removeUserCache id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    /**
     * 缓存key生成方法
     * @param id
     * @return
     */
    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }

    /**
     * 请服务降级之求合并
     * 当请求在100毫秒以内，将合并请求，并调用batchMethod指定的方法
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    @Override
    public Future<User> getUserFuture(Long id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
                Map data = (Map) commonResult.getData();
                User user = BeanUtil.mapToBean(data,User.class,true);
                log.info("getUserById username:{}", user.getUsername());
                return user;
            }
        };
    }

    /**
     *  请求合并后所调用的方法
     * @param ids
     * @return
     */
    @HystrixCommand
    public List<User> getUserByIds(List<Long> ids) {
        log.info("getUserByIds:{}", ids);
        CommonResult commonResult = restTemplate.getForObject(userServiceUrl + "/user/getUserByIds?ids={1}", CommonResult.class, CollUtil.join(ids,","));
        return (List<User>) commonResult.getData();
    }
}
