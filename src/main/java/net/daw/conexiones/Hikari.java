/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.conexiones;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kevin
 */
public class Hikari implements ConnectionInterface {

    Connection connection;
    private HikariDataSource connectionPool;
    @Override
    public Connection newConnection() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Parameters.url);
        config.setUsername(Parameters.user);
        config.setPassword(Parameters.pass);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        try {
            connectionPool = new HikariDataSource(config);
            connection = (Connection) connectionPool.getConnection();
            return connection;
        } catch (SQLException ex) {
            throw new Exception(ex);
        }

    }

    @Override
    public void disposeConnection() throws Exception {
        try {
            if (connection != null) {
                connection.close();
            }
            if (connectionPool != null) {
                connectionPool.close();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
