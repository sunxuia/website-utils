package net.sunxu.website.test.dbunit;

import static net.sunxu.website.help.util.ObjectHelpUtils.nvl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import lombok.extern.log4j.Log4j2;
import net.sunxu.website.test.dbunit.excution.Execution;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.db2.Db2Connection;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.dbunit.ext.mckoi.MckoiConnection;
import org.dbunit.ext.mssql.MsSqlConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.ext.oracle.OracleConnection;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

@Log4j2
public class DbUnitStatement extends Statement {

    private final Statement statement;

    private final Connection connection;

    private final Description description;

    private Execution[] executions;

    public DbUnitStatement(Statement statement, Description description, Connection connection,
                           Collection<Execution> executions) {
        this.statement = statement;
        this.connection = connection;
        this.description = description;
        this.executions = executions.toArray(new Execution[executions.size()]);
    }

    @Override
    public void evaluate() throws Throwable {
        IDatabaseConnection con = getDatabaseConnection();

        final var excutionLength = executions.length;
        boolean[] canExcute = new boolean[excutionLength];
        boolean[] excuted = new boolean[excutionLength];
        Object[] beforeValues = new Object[excutionLength];

        for (int i = 0; i < excutionLength; i++) {
            canExcute[i] = executions[i].canExcute(description);
        }
        try {
            for (int i = 0; i < excutionLength; i++) {
                if (canExcute[i]) {
                    excuted[i] = true;
                    beforeValues[i] = executions[i].before(description, con);
                }
            }

            statement.evaluate();

            for (int i = 0; i < excutionLength; i++) {
                if (canExcute[i]) {
                    executions[i].verify(description, con, beforeValues[i]);
                }
            }
        } finally {
            for (int i = 0; i < excutionLength; i++) {
                if (canExcute[i] && excuted[i]) {
                    try {
                        executions[i].cleanUp(description, con);
                    } catch (Exception err) {
                        if (log.isDebugEnabled()) {
                            log.debug(String.format("error in clean up %s: %s: %s",
                                    executions[i].getClass(), err.getClass(), err.getMessage()));
                            err.printStackTrace();
                        }
                    }
                }
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private IDatabaseConnection getDatabaseConnection() throws Exception {
        Class<?> klass = getConnectionType();
        IDatabaseConnection instance = (IDatabaseConnection) klass
                .getConstructor(Connection.class, String.class)
                .newInstance(connection, nvl(connection.getSchema(), connection.getCatalog()));

        var config = instance.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        return instance;
    }

    private Class<? extends DatabaseConnection> getConnectionType() throws SQLException {
        String dbName = connection.getMetaData().getDatabaseProductName().toLowerCase();
        Class<? extends DatabaseConnection> klass = DatabaseConnection.class;
        if (dbName.contains("mysql")) {
            klass = MySqlConnection.class;
        } else if (dbName.contains("oracle")) {
            klass = OracleConnection.class;
        } else if (dbName.contains("h2")) {
            klass = H2Connection.class;
        } else if (dbName.contains("sql server")) {
            klass = MsSqlConnection.class;
        } else if (dbName.contains("db2")) {
            klass = Db2Connection.class;
        } else if (dbName.contains("mockoi")) {
            klass = MckoiConnection.class;
        } else if (dbName.contains("hsql")) {
            klass = HsqldbConnection.class;
        }
        return klass;
    }

}
