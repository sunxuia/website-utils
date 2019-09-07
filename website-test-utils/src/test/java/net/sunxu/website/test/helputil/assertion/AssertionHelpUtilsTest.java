package net.sunxu.website.test.helputil.assertion;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class AssertionHelpUtilsTest {

    @Test
    public void testAssertCollectionEqualsEquals() {
        Collection<String> list1 = List.of(new String("test"), new String("test 2"));
        Collection<String> list2 = Set.of(new String("test"), new String("test 2"));
        AssertHelpUtils.assertCollectionEquals(list1, list2);
    }

    @Test(expected = AssertionError.class)
    public void testAssertCollectionEqualsNotEquals() {
        Collection<String> list1 = List.of(new String("test"), new String("test 2"));
        Collection<String> list2 = Set.of(new String("test"), new String("test 3"));
        AssertHelpUtils.assertCollectionEquals(list1, list2);
    }

}
