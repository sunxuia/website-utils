package net.sunxu.website.test.dbunit.excution;

import lombok.extern.log4j.Log4j2;
import net.sunxu.website.test.dbunit.DbUnitTestException;
import net.sunxu.website.test.dbunit.annotation.DbUnitSetup;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.Description;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DbUnitSetupExecution implements Execution {

    @Override
    public boolean canExcute(Description description) throws Exception {
        return description.getAnnotation(DbUnitSetup.class) != null;
    }

    @Override
    public Object before(Description description, IDatabaseConnection con) throws Exception {
        DbUnitSetup setup = description.getAnnotation(DbUnitSetup.class);
        DatabaseOperation dbOperation;
        try {
            var field = DatabaseOperation.class.getDeclaredField(setup.operationType().name());
            dbOperation = (DatabaseOperation) field.get(null);
        } catch (Exception e) {
            throw DbUnitTestException.wrapException(e);
        }

        for (String location : setup.locations()) {
            var resource = HelpUtils.getXmlFile(location, description);
            log.debug("inserting " + location + " into database.");
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            var dataSet = builder.build(resource);
            dbOperation.execute(con, dataSet);
        }
        return null;
    }
}
