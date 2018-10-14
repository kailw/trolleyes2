/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificImplementation;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicInterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;

/**
 *
 * @author kevin
 */
public class HikariConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection = null;
    private HikariDataSource oConnectionPool = null;

    @Override
    public Connection newConnection() throws Exception {

        HikariConfig config = new HikariConfig();        
        config.setJdbcUrl(ConnectionConstants.getConnectionChain());
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinimumIdle(ConnectionConstants.getDatabaseMinPoolSize);
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        try {
            oConnectionPool = new HikariDataSource(config);
            oConnection = oConnectionPool.getConnection();

        } catch (SQLException ex) {
            String msgError = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
            throw new Exception(msgError, ex);
        }

        return oConnection;
    }

    @Override
    public void disposeConnection() throws Exception {
        try {
            if (oConnection != null) {
                oConnection.close();
            }
            if (oConnectionPool != null) {
                oConnectionPool.close();
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

}
