package com.wgx.webflux;

import com.wgx.webflux.handler.UserHandler;
import com.wgx.webflux.service.impl.UserServiceImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * author:wgx
 * version:1.0
 */
public class Server {
    //创建路由
    public RouterFunction<ServerResponse> routerFunction() {
        //创建handler对象
        UserServiceImpl userService = new UserServiceImpl();
        UserHandler userHandler = new UserHandler(userService);
        //设置路由
        return RouterFunctions.route(GET("/user/{id}").and(accept(APPLICATION_JSON)), userHandler :: getUserById)
                .andRoute(GET("/user").and(accept(APPLICATION_JSON)), userHandler :: getAllUser)
                .andRoute(POST("/user").and(accept(APPLICATION_JSON)), userHandler :: saveUser);
    }

    //创建服务器完成适配
    public void createReactorServer() {
        //路由和handler适配
        RouterFunction<ServerResponse> router = routerFunction();
        HttpHandler httpHandler = toHttpHandler(router);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        //创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorServer();
        System.in.read();
    }
}
