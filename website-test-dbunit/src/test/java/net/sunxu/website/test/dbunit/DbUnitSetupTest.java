package net.sunxu.website.test.dbunit;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import net.sunxu.website.test.dbunit.annotation.DbUnitSetup;
import net.sunxu.website.test.dbunit.annotation.DbUnitSetup.OperationType;
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
public class DbUnitSetupTest {

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
    @DbUnitSetup(locations = "/net/sunxu/website/test/dbunit/sampleData.xml", operationType = OperationType.CLEAN_INSERT)
    public void testNormal() {
        List<Map<String, Object>> res = jdbcTemplate.queryForList("select * from test_table order by id");
        Assert.assertEquals(2, res.size());
        Assert.assertEquals(100L, res.get(0).get("id"));
        Assert.assertEquals("text", res.get(0).get("text"));
        Assert.assertEquals(1, res.get(0).get("is_active"));

        res = jdbcTemplate.queryForList("select * from test_table2");
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(101L, res.get(0).get("id"));
    }

    @Test
    @DbUnitSetup(locations = "sampleData.xml", operationType = OperationType.DELETE_ALL)
    public void testDeleteAll() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from test_table", Integer.class);
        Assert.assertEquals(Integer.valueOf(0), count);

        count = jdbcTemplate.queryForObject("select count(*) from test_table2", Integer.class);
        Assert.assertEquals(Integer.valueOf(0), count);
    }
}
