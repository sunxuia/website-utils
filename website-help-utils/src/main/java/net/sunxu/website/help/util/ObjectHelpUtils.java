package net.sunxu.website.help.util;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectHelpUtils {

    public static <T> T nvl(T first, T second) {
        return first == null ? second : first;
    }

    public static <T> T nvl(T first, T second, T third) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        return third;
    }

    public static <T> T nvl(T... values) {
        for (T item : values) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    public static <T> T nvl(T first, Supplier<T> second) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second.get();
        }
        return null;
    }

    public static <T> T chainInvoke(Supplier<T> supplier) {
        if (supplier == null) {
            return null;
        }
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static <T, R> R mapValue(T value, @NonNull Function<T, R> mapper) {
        return mapper.apply(value);
    }

    public static <T> boolean inRange(T value, T... candidates) {
        for (T candidate : candidates) {
            if (Objects.equals(value, candidate)) {
                return true;
            }
        }
        return false;
    }

}
