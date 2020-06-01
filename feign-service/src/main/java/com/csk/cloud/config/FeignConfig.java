package com.csk.cloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Feign日志配置类
 * 日志级别
 * NONE：默认的，不显示任何日志；
 * BASIC：仅记录请求方法、URL、响应状态码及执行时间；
 * HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息；
 * FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。
 * @author: caishengkai
 * @time: 2020/5/31 10:50
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}