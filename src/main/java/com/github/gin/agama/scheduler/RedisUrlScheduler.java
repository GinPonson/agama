package com.github.gin.agama.scheduler;

import com.github.gin.agama.Closeable;
import com.github.gin.agama.site.Request;
import org.apache.http.util.Asserts;
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.support.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by FSTMP on 2017/2/20.
 */
public class RedisUrlScheduler implements Scheduler , Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUrlScheduler.class);

    private RedissonClient redisson ;

    private RScoredSortedSet<Request> requestRScoredSortedSet;

    private Set<String> urls = setFromMap(new ConcurrentHashMap<String, Boolean>());

    private static <E>Set<E> setFromMap(Map<E,Boolean> map){
        return Collections.newSetFromMap(map);
    }

    public RedisUrlScheduler(String address) {
        Config config = new Config();

        config.useSingleServer().setAddress(address);
        redisson = Redisson.create(config);
        requestRScoredSortedSet = redisson.getScoredSortedSet("agama:url:scheduler");
    }

    private boolean isDuplicate(String url) {
        return !urls.add(url);
    }

    @Override
    public void push(Request request) {
        if(!isDuplicate(request.getUrl()) || request.isRetryRequest()){
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("push request to redis <= {}",request.getUrl());
            }
            requestRScoredSortedSet.add(System.nanoTime(),request);
        }
    }

    @Override
    public Request poll() {
        return requestRScoredSortedSet.pollFirst();
    }

    @Override
    public void close() {
        redisson.shutdown();
    }

    public static void main(String[] args){
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.153.131:6379");
        RedissonClient redisson = Redisson.create(config);
        RScoredSortedSet<Request> requestRScoredSortedSet = redisson.getScoredSortedSet("agama:url:scheduler");
        for(Request request : requestRScoredSortedSet){
            System.out.println(requestRScoredSortedSet.pollFirst().getUrl());
        }

    }
}
