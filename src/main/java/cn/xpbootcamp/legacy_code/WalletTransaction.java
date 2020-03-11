package cn.xpbootcamp.legacy_code;

import cn.xpbootcamp.legacy_code.entity.Order;
import cn.xpbootcamp.legacy_code.enums.STATUS;
import cn.xpbootcamp.legacy_code.exception.ExpiredException;
import cn.xpbootcamp.legacy_code.service.WalletService;
import cn.xpbootcamp.legacy_code.service.WalletServiceImpl;
import cn.xpbootcamp.legacy_code.utils.IdGenerator;
import cn.xpbootcamp.legacy_code.utils.RedisDistributedLock;
import cn.xpbootcamp.legacy_code.exception.RedisLockException;

public class WalletTransaction {
    private String id;
    private Long createdTimestamp;
    private STATUS status;
    private Order order;
    private String walletTransactionId;

    public STATUS getStatus() {
        return status;
    }

    public boolean isExecuted(){
        return this.status == STATUS.EXECUTED;
    }

    public WalletTransaction(Order order) {
        this.id = IdGenerator.generateTransactionId();
        this.order = order;
        this.status = STATUS.TO_BE_EXECUTED;
        this.createdTimestamp = System.currentTimeMillis();
    }

    public void execute() throws RuntimeException{
        if (status == STATUS.EXECUTED) return;
        boolean isLocked = false;
        try {
            isLocked = RedisDistributedLock.getSingletonInstance().lock(id);
            if (status == STATUS.EXECUTED) return; // double check
            checkExpired();
            WalletService walletService = new WalletServiceImpl();
            this.walletTransactionId = walletService.moveMoney(id, order.getBuyer().getId(), order.getSeller().getId(), order.getProduct().getPrice());
            this.status = (this.walletTransactionId == null) ? STATUS.FAILED : STATUS.EXECUTED;
        } catch (RuntimeException e){
            throw e;
        }finally {
            if (isLocked) {
                RedisDistributedLock.getSingletonInstance().unlock(id);
            }
        }
    }

    private void checkExpired(){
        if ( System.currentTimeMillis() - createdTimestamp > 1728000000) {
            this.status = STATUS.EXPIRED;
            throw new ExpiredException("transaction is expired");
        }
    }




}