package top.camsyn.store.commons.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.redis.util.RedisLockRegistry;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.exception.NotFoundException;
import top.camsyn.store.commons.helper.LockHelper;
import top.camsyn.store.commons.service.ISuperService;

import java.io.Serializable;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;


public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements ISuperService<T> {

    protected RedisLockRegistry lockRegistry;

    @Autowired
    public final void setLogRepository(RedisLockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }


//    public void setLockRegistry(RedisLockRegistry lockRegistry) {
//        SuperServiceImpl.lockRegistry = lockRegistry;
//    }

    @Override
    public boolean exist(String column, Supplier<Object> supplier) {
        return !(count(new QueryWrapper<T>().eq(column, supplier.get())) == 0);
    }


    @Override
    public boolean saveIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        save(entity);

        return false;
    }

    @Override
    public boolean saveIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper) throws Exception {
        save(entity);
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        save(entity);

        return false;
    }


    @Override
    public boolean saveOrUpdateIdempotency(T entity, Lock locker, String lockKey, Wrapper<T> countWrapper) throws Exception {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                final Lock lock = lockRegistry.obtain(entity.toString());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, "");
                } else {
                    try {
                        LockHelper.tryLock(lock);
                        return updateById(entity);
                    } finally {
                        LockHelper.unlock(lock);
                    }
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        throw new BusinessException("不能更新 null 值");
    }

    @SneakyThrows
    @Override
    public boolean updateById(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    throw new BusinessException("主键缺失, 无法更新");
                } else {
                    final Lock lock = lockRegistry.obtain(entity.toString());
                    try {
                        LockHelper.tryLock(lock);
                        return super.updateById(entity);
                    } finally {
                        LockHelper.unlock(lock);
                    }
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        throw new BusinessException("不能更新 null 值");
    }

    @SneakyThrows
    @Override
    public T getById(Serializable id) {
        T entity = super.getById(id);
        if (entity == null) {
            throw new NotFoundException("查无此元素");
        }
        return entity;
    }
}
