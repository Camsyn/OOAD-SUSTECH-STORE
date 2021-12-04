package top.camsyn.store.commons.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface DTO {
}
