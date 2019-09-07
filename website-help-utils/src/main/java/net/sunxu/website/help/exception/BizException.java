package net.sunxu.website.help.exception;

public class BizException extends ServiceException {

    protected BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BizException newException(String message, Object... paras) {
        return new BizException(format(message, paras), null);
    }

    public static BizException wrapException(Throwable cause) {
        return new BizException(null, cause);
    }

    public static BizException wrapException(Throwable cause, String message, Object... paras) {
        return new BizException(format(message, paras), cause);
    }
}
