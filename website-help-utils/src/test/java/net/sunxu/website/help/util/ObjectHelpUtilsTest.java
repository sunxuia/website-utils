package net.sunxu.website.help.util;

import static net.sunxu.website.help.util.ObjectHelpUtils.chainInvoke;
import static net.sunxu.website.help.util.ObjectHelpUtils.mapValue;
import static net.sunxu.website.help.util.ObjectHelpUtils.nvl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.Supplier;
import org.junit.Test;

public class ObjectHelpUtilsTest {

    @Test
    public void testNvl2() {
        assertEquals("b", nvl(null, "b"));
        assertEquals("a", nvl("a", "b"));
        assertEquals("a", nvl("a", null));
        assertNull(nvl(null, (String) null));
    }

    @Test
    public void testNvl3() {
        assertEquals("c", nvl(null, null, "c"));
        assertEquals("b", nvl(null, "b", "c"));
        assertEquals("a", nvl("a", "b", "c"));
        assertNull(nvl(null, null, null));
    }

    @Test
    public void testNvl4() {
        assertEquals("d", nvl(null, null, null, "d"));
        assertEquals("a", nvl("a", "b", "c", "d"));
        assertNull(nvl(null, null, null, null));
    }

    @Test
    public void testNvlSupplier() {
        assertEquals("b", nvl(null, () -> "b"));
        assertNull(nvl(null, (Supplier<String>) null));

        StringBuilder sb = new StringBuilder();
        assertEquals("a", nvl("a", () -> sb.append("b").toString()));
        assertEquals(0, sb.length());
    }

    @Test
    public void testChainInvoke() {
        assertEquals("a", chainInvoke(() -> "a"));
        assertNull(chainInvoke(null));
        final var strings = new String[1];
        assertEquals(null, chainInvoke(() -> strings[0].length()));
    }

    @Test
    public void testMapValue() {
        assertEquals("ab", mapValue("a", a -> a + "b"));
    }
}
