package com.csk.cloud.service;

import com.csk.cloud.model.User;

import java.util.List;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/28 17:23
 */
public interface UserService {
    User getUser(Long id);

    List<User> getUserByIds(List<Long> ids);
}
