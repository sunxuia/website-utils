package net.sunxu.website.help.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadPoolHelpUtils {

    private static class CustomThreadFactory implements ThreadFactory {

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String threadNamePrefix;

        @Getter
        @Setter
        private boolean isSingleThread = false;

        public CustomThreadFactory(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName =
                    isSingleThread ? threadNamePrefix : (threadNamePrefix + "-" + threadNumber.getAndIncrement());
            Thread t = new Thread(group, r, threadName, 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    public static ThreadPoolExecutor newSingleThreadExecutor(String threadName) {
        var factory = new CustomThreadFactory(threadName);
        factory.setSingleThread(true);
        return new ThreadPoolExecutor(1, 1,
                0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2048),
                factory);
    }

    public static ThreadPoolExecutor newFixedThreadExecutor(String threadName, int count) {
        return new ThreadPoolExecutor(count, count, 0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2048), new CustomThreadFactory(threadName));
    }

}
