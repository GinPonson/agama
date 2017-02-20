package com.github.gin.agama.scheduler;

import com.github.gin.agama.site.Request;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by FSTMP on 2017/2/20.
 */
public class PriorityFIFOUrlScheduler implements Scheduler{

    private BlockingQueue<Request> requestQueue = new PriorityBlockingQueue(11, comparator());

    private Comparator<Request> comparator(){
        return (Request r1, Request r2) -> {
            if (r1.getPriority() < r2.getPriority()) return 1;
            else if (r1.getPriority() > r2.getPriority()) return -1;
            else return 0;
        };
    }

    private Scheduler scheduler;

    public PriorityFIFOUrlScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void push(Request request) {
        scheduler.push(scheduler.poll());
    }

    @Override
    public Request poll() {
        return requestQueue.poll();
    }
}
