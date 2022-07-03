package com.wgx.webflux.service.impl;

import com.wgx.webflux.bean.User;
import com.wgx.webflux.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class UserServiceImpl implements UserService {
    public final Map<Integer, User> users = new ConcurrentHashMap<>();

//    @PostConstruct
//    public void init() {
//        this.users.put(1, new User("wgx", "男", 23));
//        this.users.put(2, new User("mary", "女", 23));
//        this.users.put(3, new User("jack", "男", 23));
//    }

    public UserServiceImpl() {
        this.users.put(1, new User("wgx", "男", 23));
        this.users.put(2, new User("mary", "女", 23));
        this.users.put(3, new User("jack", "男", 23));
    }

    //根据id查询用户
    @Override
    public Mono<User> getUserById(Integer id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    //查询所有用户
    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    //保存用户
    @Override
    public Mono<Void> saveUser(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            this.users.put(this.users.size() + 1, user);
        }).then(Mono.empty());
    }
}
