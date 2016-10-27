package com.github.gin.agama.core;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class ForverThreadPool extends ThreadPool {
    public ForverThreadPool(int threadNum) {
        super(threadNum);
    }

    @Override
    public void execute(Runnable runnable) {

    }
}
