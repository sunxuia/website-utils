package net.sunxu.website.test.dbunit.excution;

import net.sunxu.website.test.dbunit.annotation.DbUnitClearTable;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.Description;
import org.springframework.stereotype.Component;

@Component
public class DbUnitClearTableExecution implements Execution {

    @Override
    public boolean canExcute(Description description) throws Exception {
        return description.getAnnotation(DbUnitClearTable.class) != null;
    }

    @Override
    public Object before(Description description, IDatabaseConnection con) throws Exception {
        var anno = description.getAnnotation(DbUnitClearTable.class);
        var dataSet = new DefaultDataSet();
        for (String tableName : anno.value()) {
            var table = new DefaultTable(tableName);
            dataSet.addTable(table);
        }
        DatabaseOperation.DELETE_ALL.execute(con, dataSet);
        return null;
    }
}
