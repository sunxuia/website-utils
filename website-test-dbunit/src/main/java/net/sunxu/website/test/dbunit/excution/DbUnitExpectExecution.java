package net.sunxu.website.test.dbunit.excution;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.sunxu.website.test.dbunit.annotation.DbUnitExpect;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.dbunit.Assertion;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.runner.Description;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DbUnitExpectExecution implements Execution {

    @Override
    public boolean canExcute(Description description) throws Exception {
        return description.getAnnotation(DbUnitExpect.class) != null;
    }

    @Override
    public void verify(Description description, IDatabaseConnection con, Object beforeValue) throws Exception {
        DbUnitExpect expect = description.getAnnotation(DbUnitExpect.class);
        List<IDataSet> dataSets = new ArrayList<>(expect.locations().length);
        for (String location : expect.locations()) {
            var resource = HelpUtils.getXmlFile(location, description);
            log.debug("verify file " + location + ".");
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            var dataSet = builder.build(resource);
            dataSets.add(dataSet);
        }
        IDataSet totalDataSet = new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));

        String[] tableNames = expect.tableName().length == 0 ? totalDataSet.getTableNames() : expect.tableName();

        MultiValuedMap<String, String> ignoreColumns = new ArrayListValuedHashMap<>();
        for (String column : expect.ignoreColumn()) {
            String[] args = column.split("\\.");
            Assert.assertEquals("ignoreColumn " + column + " should be in form of \"table_name.column_name\"",
                    2, args.length);
            ignoreColumns.put(args[0], args[1]);
        }

        for (String tableName : tableNames) {
            ITable actualTable = con.createTable(tableName);
            ITable expectTable = totalDataSet.getTable(tableName);
            var columns = ignoreColumns.get(tableName);
            Assertion.assertEqualsIgnoreCols(expectTable, actualTable, columns.toArray(new String[0]));
        }
    }
}
