package com.csk.cloud.service.impl;

import com.csk.cloud.model.User;
import com.csk.cloud.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: caishengkai
 * @time: 2020/5/28 17:24
 */
@Service
public class UserServiceImpl implements UserService {

    private List<User> userList;

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "macro"));
        userList.add(new User(2L, "andy"));
        userList.add(new User(3L, "mark"));
    }

    @Override
    public User getUser(Long id) {
        return new User(id, "张三");
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        return userList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
    }


}
