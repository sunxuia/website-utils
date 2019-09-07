package net.sunxu.website.test.dbunit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbUnitExpect {

    /**
     * 期待结果的文件.
     */
    String[] locations();

    /**
     * 期待比较的表名. 默认是文件中的表名.
     */
    String[] tableName() default {};

    /**
     * 忽略比较的列名. 默认文件中有的列名都会比较.
     */
    String[] ignoreColumn() default {};
}
