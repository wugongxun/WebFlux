package com.wgx.webflux.handler;

import com.alibaba.fastjson.JSON;
import com.wgx.webflux.bean.User;
import com.wgx.webflux.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author:wgx
 * version:1.0
 */
public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        //获取路径中的id值
        Integer id = Integer.valueOf(request.pathVariable("id"));
        //调用service获取数据
        Mono<User> userMono = this.userService.getUserById(id);
        //userMono转换
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class).switchIfEmpty(Mono.empty());
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        Flux<User> users = this.userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.saveUser(userMono));
    }
}
