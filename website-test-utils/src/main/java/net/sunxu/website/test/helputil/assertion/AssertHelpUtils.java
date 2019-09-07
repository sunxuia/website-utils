package net.sunxu.website.test.helputil.assertion;

import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Collection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssertHelpUtils {

    public static <T> void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        assertCollectionEquals("", expected, actual);
    }

    public static <T> void assertCollectionEquals(String message, Collection<T> expected, Collection<T> actual) {
        if (assertObjectEqual(message, expected, actual)) {
            return;
        }
        if (expected.size() != actual.size()) {
            fail(message, "expect size is %d and actual size is %d", expected.size(), actual.size());
        }
        final int size = expected.size();
        Object[] expectedArray = expected.toArray(new Object[size]);
        Object[] actualArray = actual.toArray(new Object[size]);
        boolean[] checked = new boolean[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!checked[j]) {
                    checked[j] = actualArray[i] == expectedArray[j];
                    checked[j] = checked[j] || (actualArray[i] != null
                            && expectedArray[j] != null && actualArray[i].equals(expectedArray[j]));
                    if (checked[j]) {
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (!checked[i]) {
                fail(message, "collection elements not equal");
            }
        }
    }

    private boolean assertObjectEqual(String message, Object expected, Object actual) {
        if (expected == actual) {
            return true;
        }
        if (expected == null) {
            fail(message, "expect is null, while actual is not");
        }
        if (actual == null) {
            fail(message, "expect is not null, while actual is null");
        }
        return false;
    }

    private static void fail(String customMessage, String message, Object... paras) {
        if (paras.length > 0) {
            message = String.format(message, paras);
        }
        AssertionError error = new AssertionError(
                Strings.isNullOrEmpty(customMessage) ? message : customMessage + " : " + message);
        var stackTraces = error.getStackTrace();
        int i = 0;
        while (stackTraces[i].getClassName().equals(AssertHelpUtils.class.getName())) {
            i++;
        }
        error.setStackTrace(Arrays.copyOfRange(stackTraces, i, stackTraces.length));
        throw error;
    }

}
