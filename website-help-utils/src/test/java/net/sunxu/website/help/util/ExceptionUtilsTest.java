package net.sunxu.website.help.util;

import static net.sunxu.website.help.util.ExceptionUtils.wrapException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import net.sunxu.website.help.util.ExceptionUtils.WrappedException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExceptionUtilsTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testWrapException() {
        assertEquals("a", wrapException(() -> "a"));
        assertNull(wrapException(null));

        exceptionRule.expect(WrappedException.class);
        exceptionRule.expectCause(IsInstanceOf.instanceOf(NullPointerException.class));
        final var strings = new String[1];
        assertEquals(null, wrapException(() -> strings[0].length()));
    }
}
