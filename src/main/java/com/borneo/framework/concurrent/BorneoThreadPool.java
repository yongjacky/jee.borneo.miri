package com.borneo.framework.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: seven_shi@qq.com Date: 13-10-2 Time: 上午10:26
 */
public class BorneoThreadPool {

    private static ExecutorService instance;

    private BorneoThreadPool() {
    }

    public static ExecutorService getInstance() {
        if (instance == null) {
            synchronized (BorneoThreadPool.class) {
                instance = Executors.newFixedThreadPool(10);
            }
        }
        return instance;
    }
}
