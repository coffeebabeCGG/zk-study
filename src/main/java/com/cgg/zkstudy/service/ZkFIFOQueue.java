package com.cgg.zkstudy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cgg
 *
 * zk 实现FIFO分布式队列
 *
 * *zk Node并不适合做消息队列，Node数据量过大会拖慢zk同步和启动效率，默认Node限制1M.
 * *生产推荐 MQ中间件
 */
@Component
@Slf4j
public class ZkFIFOQueue {

    private static final String FIFO_QUEUE_PATH = "/fqueue01";

    @Autowired
    private CuratorFramework curatorFramework;

    /**
     * 模拟 生产者 服务（8881端口启动）
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 30000)
    private void prdouctMessage() {

        //建立消费者 匿名接口 内部实现消费主体方法
        QueueConsumer<String> consumer = createConsumer();

        DistributedQueue<String> queue = QueueBuilder.builder(curatorFramework, consumer,
                createQueueSerializer(), FIFO_QUEUE_PATH).buildQueue();
        try {
            queue.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //模拟生产消息
        for (int i = 0; i < 10; i++) {
            try {
                log.info("product ticket " + i);
                queue.put("ticket" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 模拟 消费者服务 （8882端口/和8883端口启动）
     */
//    @Scheduled(cron = "0/10 * * * * ?")
    private void consumer() {

        //建立消费者 匿名接口 内部实现消费主体方法
        QueueConsumer<String> consumer = createConsumer();


        QueueBuilder.builder(curatorFramework, consumer,
                createQueueSerializer(), FIFO_QUEUE_PATH).buildQueue();


    }

    private QueueSerializer<String> createQueueSerializer() {
        return new QueueSerializer<String>() {
            @Override
            public byte[] serialize(String s) {
                return s.getBytes();
            }

            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes);
            }
        };
    }

    private QueueConsumer<String> createConsumer() {
        return new QueueConsumer<String>() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                log.info("ConnectionState change, state is "+ connectionState.name());
            }

            @Override
            public void consumeMessage(String s) throws Exception {
                //consumer message service
                log.info("consumer message, message is " + s);
            }
        };
    }

}
