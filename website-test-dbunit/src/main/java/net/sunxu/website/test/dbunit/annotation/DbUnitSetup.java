package net.sunxu.website.test.dbunit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbUnitSetup {

    /**
     * 要操作的文件名. 相对路径相对于当前包路径.
     */
    String[] locations();

    /**
     * 操作类型.
     */
    OperationType operationType();

    enum OperationType {
        NONE,
        UPDATE,
        INSERT,
        REFRESH,
        DELETE,
        DELETE_ALL,
        TRUNCATE_TABLE,
        CLEAN_INSERT
    }
}
