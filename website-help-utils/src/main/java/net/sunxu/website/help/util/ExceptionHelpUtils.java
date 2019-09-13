package net.sunxu.website.help.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionHelpUtils {

    @FunctionalInterface
    public interface SupplierWithException<T> {

        T get() throws Exception;
    }

    public static <T> T wrapException(SupplierWithException<T> supplier) {
        if (supplier != null) {
            try {
                return supplier.get();
            } catch (Exception err) {
                throw new WrappedException(err);
            }
        }
        return null;
    }

    public static class WrappedException extends RuntimeException {

        private WrappedException(Throwable cause) {
            super(cause);
        }
    }

    @FunctionalInterface
    public interface RunnableWithException {

        void run() throws Exception;
    }

    public static void wrapException(RunnableWithException runnable) {
        if (runnable != null) {
            try {
                runnable.run();
            } catch (Exception err) {
                throw new WrappedException(err);
            }
        }
    }
}
