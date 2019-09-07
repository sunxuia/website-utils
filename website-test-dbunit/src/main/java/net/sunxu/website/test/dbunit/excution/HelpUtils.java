package net.sunxu.website.test.dbunit.excution;


import java.net.URL;
import lombok.experimental.UtilityClass;
import org.junit.Assert;
import org.junit.runner.Description;

@UtilityClass
public class HelpUtils {

    public static URL getXmlFile(String path, Description description) {
        if (!path.startsWith("/")) {
            String packageName = description.getTestClass().getPackageName();
            path = "/" + packageName.replaceAll("\\.", "/") + "/" + path;
        }

        var resource = HelpUtils.class.getResource(path);
        Assert.assertNotNull(String.format("Xml file [%s] not found.", path), resource);
        return resource;
    }
}
