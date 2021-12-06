package top.camsyn.store.commons.func;

import lombok.SneakyThrows;
import top.camsyn.store.commons.exception.BusinessException;

public interface Supplier<T> {
    @SneakyThrows
    T get() throws BusinessException;
}
