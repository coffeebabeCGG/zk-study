package com.cgg.zkstudy.service;

import com.cgg.zkstudy.dao.ZkStudyRepository;
import com.cgg.zkstudy.entity.ZkStudy;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ZkLock {

    @Autowired
    private ZkStudyRepository zkStudyRepository;

    @Autowired
    private CuratorFramework curatorFramework;

    private static final String lock_path = "/lock01";

    @Scheduled(cron = "0/30 * * * * ?")
    private void updateUserStatus() {
        boolean flag = false;
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lock_path);
        try {
            log.info("try get lock  --------->");
            flag = lock.acquire(0, TimeUnit.SECONDS);
            if (flag) {
                log.info("get lock --------->");
                List<ZkStudy> list = zkStudyRepository.findAll();
                for (ZkStudy zkStudy : list) {
                    timeWait();
                    if (Integer.valueOf(zkStudy.getStatus()) < 0) {
                        break;
                    }
                    int count = Integer.valueOf(zkStudy.getStatus());
                    zkStudy.setStatus(String.valueOf(count - 1));
                    zkStudyRepository.save(zkStudy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (flag) {
                    lock.release();
                    log.info("release lock --------->");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("service end  --------->");
    }

    private void timeWait() {

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
