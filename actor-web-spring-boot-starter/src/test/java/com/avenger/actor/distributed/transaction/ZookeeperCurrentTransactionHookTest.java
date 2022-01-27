package com.avenger.actor.distributed.transaction;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionTypeEnum;
import com.avenger.actor.distributed.transaction.constant.ZookeeperTransactionPathEnum;
import com.avenger.actor.distributed.transaction.zookeeper.ZookeeperCoordinatorServiceImpl;
import com.avenger.actor.distributed.transaction.zookeeper.ZookeeperCurrentTransactionHook;
import com.avenger.actor.distributed.transaction.zookeeper.ZookeeperManagerServiceImpl;
import com.avenger.actor.exception.DistributedTransactionException;
import com.avenger.actor.kit.FastThreadLocalKit;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
@Slf4j
@RunWith(SpringRunner.class)
public class ZookeeperCurrentTransactionHookTest {


    //private String url = "172.26.59.108:2181";
    private String url = "127.0.0.1:2181";
    private int baseSleepTimeMs = 1000;
    private int maxRetries = 3;
    private int sessionTimeoutMs = 8000;
    private int connectionTimeoutMs = 8000;
    CuratorFramework client = null;


    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        client =
            CuratorFrameworkFactory.builder()
                .connectString(url)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                //.namespace("base")
                .build();
        client.start();
    }

    @Test
    public void watchParentPath() {
        //ZookeeperWholeTransactionHook hook = new ZookeeperWholeTransactionHook(client);
        //hook.watchParentPath("/okm/123");
        ThreadUtil.sleep(2, TimeUnit.MINUTES);
    }


    @Test
    public void transactionOne() throws Exception {
        DistributedTransactionEvent transactionEvent = new DistributedTransactionEvent();
        transactionEvent.setTransactionId("rfvtgbe");
        transactionEvent.setTransactionCount(3);
        transactionEvent.setCurrentTransactionIndex(0);
        transactionEvent.setTransactionTypeEnum(DistributedTransactionTypeEnum.SERIAL);
        transactionEvent.setTransactionTimeOut(10000L);
        transactionEvent.setCurrentTransactionTimeOut(1L);

        around(JSON.toJSONString(transactionEvent));
    }

    @Test
    public void transactionTwo() throws Exception {
        DistributedTransactionEvent transactionEvent = new DistributedTransactionEvent();
        transactionEvent.setTransactionId("rfvtgbe");
        transactionEvent.setTransactionCount(3);
        transactionEvent.setCurrentTransactionIndex(1);
        transactionEvent.setTransactionTypeEnum(DistributedTransactionTypeEnum.SERIAL);
        transactionEvent.setTransactionTimeOut(10000L);
        transactionEvent.setCurrentTransactionTimeOut(1L);

        around(JSON.toJSONString(transactionEvent));
    }

    @Test
    public void transactionThree() throws Exception {
        DistributedTransactionEvent transactionEvent = new DistributedTransactionEvent();
        transactionEvent.setTransactionId("rfvtgbe");
        transactionEvent.setTransactionCount(3);
        transactionEvent.setCurrentTransactionIndex(2);
        transactionEvent.setTransactionTypeEnum(DistributedTransactionTypeEnum.SERIAL);
        transactionEvent.setTransactionTimeOut(10000L);
        transactionEvent.setCurrentTransactionTimeOut(1L);

        around(JSON.toJSONString(transactionEvent));
    }

    private void around(String message) throws Exception {
        DistributedTransactionEvent distributedTransactionEvent = JSON.parseObject(message,
            DistributedTransactionEvent.class);
        distributedTransactionEvent.setCurrentTransactionTimeOut(15000L);
        FastThreadLocalKit.put("event", distributedTransactionEvent);

        ZookeeperCoordinatorServiceImpl zookeeperTCService = new ZookeeperCoordinatorServiceImpl(client);
        zookeeperTCService.begin();
        ZookeeperManagerServiceImpl transactionTMService = new ZookeeperManagerServiceImpl(client);
        if (transactionTMService.turnToMe()) {
            String path =
                ZookeeperTransactionPathEnum.FIXED_PATH.getValue() + StrPool.SLASH
                    + distributedTransactionEvent.getTransactionId() + StrPool.SLASH
                    + ZookeeperTransactionPathEnum.SERIAL_PATH_PREFIX.getValue();

            new ZookeeperCurrentTransactionHook(client, path).success();
        } else {
            throw new DistributedTransactionException(DistributedTransactionExceptionEnum.LOCAL_TIMEOUT);
        }
        zookeeperTCService.end();
    }
}