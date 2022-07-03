package com.wgx.webflux.service;

import com.wgx.webflux.bean.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author:wgx
 * version:1.0
 */
public interface UserService {
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    Mono<User> getUserById(Integer id);

    /**
     * 查询所有用户
     * @return
     */
    Flux<User> getAllUser();

    /**
     * 保存用户
     * @param userMono
     * @return
     */
    Mono<Void> saveUser(Mono<User> userMono);
}
