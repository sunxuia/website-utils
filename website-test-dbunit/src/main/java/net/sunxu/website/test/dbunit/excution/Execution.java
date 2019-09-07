package net.sunxu.website.test.dbunit.excution;

import org.dbunit.database.IDatabaseConnection;
import org.junit.runner.Description;

public interface Execution<T> {

    boolean canExcute(Description description) throws Exception;

    default T before(Description description, IDatabaseConnection con) throws Exception {
        return null;
    }

    default void verify(Description description, IDatabaseConnection con, T beforeValue) throws Exception {

    }

    default void cleanUp(Description description, IDatabaseConnection con) throws Exception {

    }
}
