package top.camsyn.store.commons;

import lombok.SneakyThrows;
import top.camsyn.store.commons.exception.NotFoundException;

public class Test {

    @SneakyThrows
    public static void test(){
        throw new NotFoundException("asd");
    }
    public static void main(String[] args) {
        try {
            test();
        }catch (RuntimeException e){
            System.out.println(e);
        }
    }
}
