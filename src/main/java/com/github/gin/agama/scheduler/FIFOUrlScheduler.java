package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by FSTMP on 2017/2/20.
 */
public class FIFOUrlScheduler implements Scheduler {

    private BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();

    @Override
    public void push(Request request) {
        if (AgamaUtils.isNotBlank(request.getUrl())) {
            requestQueue.offer(request);
        }
    }

    @Override
    public Request poll() {
        return requestQueue.poll();
    }
}
