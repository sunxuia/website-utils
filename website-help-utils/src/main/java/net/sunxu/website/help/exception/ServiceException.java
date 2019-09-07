package net.sunxu.website.help.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ServiceException newException(String message, Object... paras) {
        return new ServiceException(format(message, paras), null);
    }

    protected static String format(String message, Object... paras) {
        if (paras.length > 0) {
            return String.format(message, paras);
        }
        return message;
    }

    public static ServiceException wrapException(Throwable cause) {
        return new ServiceException(null, cause);
    }

    public static ServiceException wrapException(Throwable cause, String message, Object... paras) {
        return new ServiceException(format(message, paras), cause);
    }
}
