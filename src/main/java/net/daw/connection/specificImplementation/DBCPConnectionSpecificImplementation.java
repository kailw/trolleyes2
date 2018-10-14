/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicInterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author kevin
 */
public class DBCPConnectionSpecificImplementation implements ConnectionInterface {

    private BasicDataSource dataSource = null;
    private Connection oConnection = null;

    public Connection newConnection() throws Exception {
            dataSource = new BasicDataSource();
            dataSource.setUsername(ConnectionConstants.databaseLogin);
            dataSource.setPassword(ConnectionConstants.databasePassword);
            dataSource.setUrl(ConnectionConstants.getConnectionChain());
            dataSource.setValidationQuery("select 1");
            dataSource.setMaxActive(100);
            dataSource.setMaxWait(10000);
            dataSource.setMaxIdle(10);
        try{                
            oConnection = dataSource.getConnection();
        } catch (Exception ex) {
            String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
            throw new Exception(msg, ex);
        }
        return oConnection;
    }

    public void disposeConnection() throws Exception {
        try {
            if (oConnection != null) {
                oConnection.close();
            }
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
}
