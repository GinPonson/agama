package com.github.gin.agama.scheduler;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.site.Request;
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Created by FSTMP on 2017/2/20.
 */
public class RedisUrlScheduler implements Scheduler , Closeable {

    private RedissonClient redisson ;

    private RScoredSortedSet<Request> requestRScoredSortedSet;

    public RedisUrlScheduler(String address) {
        Config config = new Config();
        config.useSingleServer().setAddress(address);
        redisson = Redisson.create(config);
        requestRScoredSortedSet = redisson.getScoredSortedSet("agama:url:scheduler");
    }

    @Override
    public void push(Request request) {
        requestRScoredSortedSet.add(System.nanoTime(),request);
    }

    @Override
    public Request poll() {
        return requestRScoredSortedSet.pollFirst();
    }

    @Override
    public void close() {
        redisson.shutdown();
    }
}