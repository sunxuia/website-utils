package net.sunxu.website.test.dbunit;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import net.sunxu.website.test.dbunit.annotation.DbUnitExpect;
import net.sunxu.website.test.dbunit.annotation.DbUnitSetup;
import net.sunxu.website.test.dbunit.annotation.DbUnitSetup.OperationType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbUnitExpectTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DbUnitRuleFactory factory;

    @Autowired
    private DataSource dataSource;

    @Rule
    public DbUnitRule dbUnitRule;

    @PostConstruct
    public void initial() throws SQLException {
        dbUnitRule = factory.builder().connection(dataSource.getConnection()).build();
    }

    @Test
    @DbUnitSetup(locations = "sampleData.xml", operationType = OperationType.CLEAN_INSERT)
    @DbUnitExpect(locations = "expectedData.xml")
    public void testNormal() {
        jdbcTemplate.update("update test_table set is_active = 0");
    }

    @Test
    @DbUnitSetup(locations = "sampleData.xml", operationType = OperationType.CLEAN_INSERT)
    @DbUnitExpect(locations = "expectedData.xml", ignoreColumn = "test_table.is_active")
    public void testIgnoreColumn() {
        // do nothing
    }

    @Test
    @DbUnitSetup(locations = "sampleData.xml", operationType = OperationType.CLEAN_INSERT)
    @DbUnitExpect(locations = "expectedData.xml", tableName = "test_table2")
    public void testTableName() {
        // do nothing
    }
}
