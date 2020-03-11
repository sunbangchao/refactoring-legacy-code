package cn.xpbootcamp.legacy_code.utils;

import cn.xpbootcamp.legacy_code.exception.RedisLockException;

public class RedisDistributedLock {
    private static final RedisDistributedLock INSTANCE = new RedisDistributedLock();

    private RedisDistributedLock(){

    }

    public static RedisDistributedLock getSingletonInstance() {
        return INSTANCE;
    }

    public boolean lock(String transactionId) {
        // Here is connecting to redis server, please do not invoke directly
        throw new RedisLockException("Redis server is connecting......");
    }

    public void unlock(String transactionId) {
        // Here is connecting to redis server, please do not invoke directly
        throw new RedisLockException("Redis server is connecting......");
    }
}
