package com.csk.cloud.controller;

import com.csk.cloud.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description: springcloud-ribbon负载均衡
 * @author: caishengkai
 * @time: 2020/5/28 17:35
 */
@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/{id}")
    public CommonResult getUser(@PathVariable Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

}
