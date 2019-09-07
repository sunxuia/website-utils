package net.sunxu.website.test.dbunit;

public class DbUnitTestException extends RuntimeException {

    private DbUnitTestException() {}

    private DbUnitTestException(String msg) {
        super(msg);
    }

    public static DbUnitTestException wrapException(Throwable cause) {
        var newInst = new DbUnitTestException();
        newInst.initCause(cause);
        return newInst;
    }

    public static DbUnitTestException newException(String message, Object... paras) {
        if (paras.length > 0) {
            message = String.format(message, paras);
        }
        return new DbUnitTestException(message);
    }

    public static DbUnitTestException wrapException(Throwable throwable, String message, Object... paras) {
        var newInst = newException(message, paras);
        newInst.initCause(throwable);
        return newInst;
    }
}
