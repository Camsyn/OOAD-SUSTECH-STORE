package top.camsyn.store.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.camsyn.store.commons.lock.DistributedLock;
import top.camsyn.store.commons.service.ISuperService;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements ISuperService<T> {

    @Override
    public boolean exist(String column, Supplier<Object> supplier) {
        return !(count(new QueryWrapper<T>().eq(column, supplier.get()))==0);
    }



    @Override
    public boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        save(entity);

        return false;
    }

    @Override
    public boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper) throws Exception {
        save(entity);
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        save(entity);

        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper) throws Exception {
        save(entity);
        return false;
    }
}
