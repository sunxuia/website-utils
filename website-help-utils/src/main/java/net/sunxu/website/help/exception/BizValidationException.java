package net.sunxu.website.help.exception;

import java.util.Objects;

public class BizValidationException extends BizException {

    protected BizValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BizValidationException newException(String message, Object... paras) {
        return new BizValidationException(format(message, paras), null);
    }

    public static BizValidationException wrapException(Throwable cause) {
        return new BizValidationException(null, cause);
    }

    public static BizValidationException wrapException(Throwable cause, String message, Object... paras) {
        return new BizValidationException(format(message, paras), cause);
    }

    public static void assertTrue(boolean success, String message, Object... paras) {
        if (!success) {
            throw newException(message, paras);
        }
    }

    public static void assertFalse(boolean success, String message, Object... paras) {
        assertTrue(!success, message, paras);
    }

    public static <T> void assertEquals(T a, T b, String message, Object... paras) {
        if (Objects.equals(a, b)) {
            throw newException(message, paras);
        }
    }

    public static void assertNull(Object resource, String message, Object... paras) {
        assertTrue(resource == null, message, paras);
    }

    public static void assertNotNull(Object resource, String message, Object... paras) {
        assertTrue(resource != null, message, paras);
    }
}
