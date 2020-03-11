package cn.xpbootcamp.legacy_code.exception;

public class RedisLockException extends RuntimeException{

    public RedisLockException(String message){
        super(message);
    }
}
