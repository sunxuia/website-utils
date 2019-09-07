package net.sunxu.website.test.dbunit;

import java.sql.Connection;
import java.util.Collection;
import net.sunxu.website.test.dbunit.excution.Execution;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DbUnitRule implements TestRule {

    private final Connection connection;

    private final Collection<Execution> executions;

    public DbUnitRule(Connection connection, Collection<Execution> executions) {
        this.connection = connection;
        this.executions = executions;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new DbUnitStatement(base, description, connection, executions);
    }
}
