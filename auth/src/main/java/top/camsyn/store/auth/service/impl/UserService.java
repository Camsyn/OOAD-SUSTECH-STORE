package top.camsyn.store.auth.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;
import top.camsyn.store.auth.service.IUserService;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.exception.LockException;
import top.camsyn.store.commons.mapper.UserMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Service
public class UserService extends SuperServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    RedisLockRegistry lockRegistry;

    @Override
    public User getOne(int sid) {
        return lambdaQuery().eq(User::getSid, sid).one();
    }

    public List<String> getAvatarBatch(List<Integer> sid) {
        return lambdaQuery().in(User::getSid, sid).list().stream().map(User::getHeadImage).collect(Collectors.toList());
    }

    public List<User> getRandomUsers(int size) {
        return query().orderByDesc("rand()").last("limit " + size).list()
                .stream().map(User::dePrivacy).collect(Collectors.toList());
    }


    @SneakyThrows
    public User changeLiyuan(Integer sid, Double delta) {
        User one = null;
        final Lock lock = lockRegistry.obtain(sid.toString());
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                one = getOne(sid);
                final Double liyuan = one.getLiyuan();
                if (liyuan + delta < 0) {
                    throw new BusinessException("荔元余额不足以消费");
                }
                one.setLiyuan(liyuan + delta);
                updateById(one);
            } else {
                throw new LockException("分布式锁获取失败");
            }
        } catch (InterruptedException | LockException e) {
            throw new LockException("分布式锁获取失败");
        } finally {
            lock.unlock();
        }
        return one;
    }

    @SneakyThrows
    public boolean changeLiyuan(Integer adder, Integer subscriber, Double delta) {
        User one = null;
        final Lock lock = lockRegistry.obtain(adder.toString());
        final Lock lock1 = lockRegistry.obtain(subscriber.toString());
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                one = getOne(adder);
                final Double liyuan = one.getLiyuan();
                one.setLiyuan(liyuan + delta);
                updateById(one);
            } else {
                throw new LockException("分布式锁获取失败");
            }
            if (lock1.tryLock(10, TimeUnit.SECONDS)) {
                one = getOne(subscriber);
                final Double liyuan = one.getLiyuan();
                if (liyuan + delta < 0) {
                    throw new BusinessException("荔元余额不足以消费");
                }
                one.setLiyuan(liyuan + delta);
                updateById(one);
            } else {
                throw new LockException("分布式锁获取失败");
            }

        } catch (InterruptedException | LockException e) {
            throw new LockException("分布式锁获取失败");
        } finally {
            lock.unlock();
        }
        return true;
    }

    @SneakyThrows
    public User changeCredit(Integer sid, Integer delta) {
        User one = null;
        final Lock lock = lockRegistry.obtain(sid.toString());
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                one = getOne(sid);
                one.setCredit(one.getCredit() + delta);
                updateById(one);
            } else {
                throw new LockException("分布式锁获取失败");
            }
        } catch (InterruptedException | LockException e) {
            throw new LockException("分布式锁获取失败");
        } finally {
            lock.unlock();
        }
        return one;
    }
}
