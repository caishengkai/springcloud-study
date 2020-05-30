package com.csk.cloud.service.impl;

import com.csk.cloud.common.CommonResult;
import com.csk.cloud.model.User;
import com.csk.cloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/29 10:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    /**
     * 指定降级方法为getDefaultUser
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getDefaultUser")
    @Override
    public CommonResult getUser(Long id) {
        /*CommonResult result = new CommonResult();
        try {
            result = restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }*/
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
}
