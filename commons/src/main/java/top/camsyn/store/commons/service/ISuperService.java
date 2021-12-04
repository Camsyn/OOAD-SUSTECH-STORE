package top.camsyn.store.commons.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import top.camsyn.store.commons.lock.DistributedLock;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public interface ISuperService<T> extends IService<T> {

    boolean exist(String column, Supplier<Object> supplier);

    /**
     * 幂等性新增记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception;

    boolean saveIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper) throws Exception;

    /**
     * 幂等性新增或更新记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param locker       锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception;

    boolean saveOrUpdateIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper) throws Exception;
}