package org.example.springmachinedemo.config;

import org.example.springmachinedemo.Events;
import org.example.springmachinedemo.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import static org.example.springmachinedemo.Status.*;

@Configuration
@EnableStateMachine
public class MachineConfig extends EnumStateMachineConfigurerAdapter<Status, Events> {

    // 启动配置
    @Override
    public void configure(StateMachineConfigurationConfigurer<Status, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true) // 自动启动
                .listener(listener());
//                .machineId("clearingTaskMachine"); // 指定机器ID

    }

    // 注册状态
    @Override
    public void configure(StateMachineStateConfigurer<Status, Events> states) throws Exception {
        states.withStates()
                .initial(UNPAID)
                .state(WAITING_FOR_RECEIVE)
                .state(WAITING_FOR_GOODSBACK)
                .end(DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Status, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(UNPAID).target(WAITING_FOR_RECEIVE).event(Events.PAY)
                .and().withExternal()
                .source(WAITING_FOR_RECEIVE).target(WAITING_FOR_GOODSBACK).event(Events.GOODS_REJECTED)
                .and().withExternal()
                .source(WAITING_FOR_GOODSBACK).target(DONE).event(Events.RECEIVE);
    }

    @Bean
    public StateMachineListener<Status, Events> listener() {
        return new StateMachineListenerAdapter<Status, Events>() {
            @Override
            public void stateChanged(State<Status, Events> from, State<Status, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
