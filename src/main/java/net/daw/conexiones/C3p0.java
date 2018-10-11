/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.conexiones;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kevin
 */
public class C3p0 implements ConnectionInterface {

    
    Connection connection;
    ComboPooledDataSource cpds;
    
    @Override
    public Connection newConnection() throws Exception {        
        cpds = new ComboPooledDataSource();        

        cpds.setJdbcUrl(Parameters.url);
        cpds.setUser(Parameters.user);
        cpds.setPassword(Parameters.pass);

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(180);

        try {
            ComboPooledDataSource dataSource = cpds;
            connection = dataSource.getConnection();                        
            return connection;

        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @Override
    public void disposeConnection() throws Exception {
        try {
            if (connection != null) {
                connection.close();
            }
            if (cpds != null) {
                cpds.close();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
