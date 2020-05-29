package com.csk.cloud.service.impl;

import com.csk.cloud.model.User;
import com.csk.cloud.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/28 17:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(Long id) {
        return new User(id, "张三");
    }
}
