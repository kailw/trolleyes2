/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificImplementation;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicInterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;

/**
 *
 * @author kevin
 */
public class C3p0ConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;
    private ComboPooledDataSource oConnectionPool = null;

    @Override
    public Connection newConnection() throws Exception {
                
        oConnectionPool = new ComboPooledDataSource();
        //oConnectionPool.setDriverClass("com.mysql.jdbc.Driver");
        oConnectionPool.setJdbcUrl(ConnectionConstants.getConnectionChain());
        oConnectionPool.setUser(ConnectionConstants.databaseLogin);
        oConnectionPool.setPassword(ConnectionConstants.databasePassword);
        oConnectionPool.setMaxStatements(180);
        
        oConnectionPool.setMinPoolSize(ConnectionConstants.getDatabaseMinPoolSize);
        oConnectionPool.setMaxPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
        oConnectionPool.setAcquireIncrement(5);        
        

        try {
            //ComboPooledDataSource dataSource = oConnectionPool;
            oConnection =  oConnectionPool.getConnection();              

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
