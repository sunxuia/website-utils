package net.sunxu.website.test.dbunit;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import net.sunxu.website.test.dbunit.annotation.DbUnitClearTable;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbUnitClearTableTest {

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

        jdbcTemplate.execute("delete from test_table");
        jdbcTemplate.execute("insert into test_table(id) values(1)");
    }

    @Test
    @DbUnitClearTable("test_table")
    public void testDeleteAll() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from test_table", Integer.class);
        Assert.assertEquals(Integer.valueOf(0), count);
    }
}
