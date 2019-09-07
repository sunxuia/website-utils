package net.sunxu.website.help.util;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

public class ThreadPoolHelpUtilsTest {

    @Test
    public void testNewSingleThreadExecutor() throws Exception {
        String expectThreadPoolName = "testNewSingleThreadExecutor";
        var threadPool = ThreadPoolHelpUtils.newSingleThreadExecutor(expectThreadPoolName);

        Assert.assertEquals(1, threadPool.getMaximumPoolSize());
        Future<String> threadPoolNameFuture = threadPool.submit(() -> Thread.currentThread().getName());
        threadPool.shutdown();
        String threadPoolName = threadPoolNameFuture.get(1, TimeUnit.SECONDS);
        Assert.assertEquals(expectThreadPoolName, threadPoolName);
    }
}
