package com.tec.speedpercent.config;

import com.tec.speedpercent.util.BeanConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class ExecutorAsyncVirtualConfig {

    @Bean(name = BeanConstants.ASYNC_VIRTUAL_SAVE_CALL_HISTORY)
    public Executor asyncVirtualSaveCallHistory() {
        return Executors.newThreadPerTaskExecutor(new ThreadFactory() {
            private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = defaultFactory.newThread(r);
                thread.setName(STR."virtual-thread-\{counter++}");
                return thread;
            }
        });
    }
}