package net.sunxu.website.test.dbunit;

import java.sql.Connection;
import java.util.Collection;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.sunxu.website.test.dbunit.excution.Execution;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbUnitRuleFactory {

    public class DbUnitRuleBuilder {

        @Setter
        @Accessors(fluent = true)
        private Connection connection;

        public DbUnitRule build() {
            Assert.assertNotNull("Must specific connection!", connection);
            DbUnitRule rule = new DbUnitRule(connection, executions);
            return rule;
        }
    }

    public DbUnitRuleBuilder builder() {

        return new DbUnitRuleBuilder();
    }

    private Collection<Execution> executions;

    @Autowired
    public void initial(ApplicationContext applicationContext) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.scan(Execution.class.getPackageName());
        ctx.setParent(applicationContext);
        ctx.refresh();
        executions = ctx.getBeansOfType(Execution.class).values();
    }
}
