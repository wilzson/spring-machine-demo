package org.example.springmachinedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 测试类
 *
 * CommandLineRunner Spring Boot 提供的一个接口，
 * 用于在 Spring Boot 应用程序启动后执行一些特定的任务或逻辑。
 * 当应用程序启动时，`CommandLineRunner` 接口的实现类中的 `run` 方法会被调用，
 * 可以在这个方法中添加需要在应用程序启动后立即执行的逻辑。
 */

@Component
public class test implements CommandLineRunner {
    @Resource
    private StateMachine<Status, Events> stateMachine;


    @Override
    public void run(String... args) throws Exception {
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.GOODS_REJECTED);
    }
}
