package net.sunxu.website.help.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {

    @FunctionalInterface
    public interface SupplierWithException<T> {

        T get() throws Exception;
    }

    public static <T> T wrapException(SupplierWithException<T> supplier) {
        if (supplier == null) {
            return null;
        }
        try {
            return supplier.get();
        } catch (Exception err) {
            throw new WrappedException(err);
        }
    }

    public static class WrappedException extends RuntimeException {

        private WrappedException(Throwable cause) {
            super(cause);
        }
    }
}
