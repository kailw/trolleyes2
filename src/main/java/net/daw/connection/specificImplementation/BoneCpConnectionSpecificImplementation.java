/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificImplementation;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicInterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;


/**
 *
 * @author kevin
 */
public class BoneCpConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection = null;
    private BoneCP oConnectionPool = null;

    public Connection newConnection() throws Exception {

        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(ConnectionConstants.getConnectionChain()); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMinConnectionsPerPartition(ConnectionConstants.getDatabaseMinPoolSize);
        config.setMaxConnectionsPerPartition(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setPartitionCount(1);
        try {
            oConnectionPool = new BoneCP(config);
            oConnection =oConnectionPool.getConnection();
            
        } catch (SQLException ex) {
            String msgError = this.getClass().getName() + ":" +ex.getStackTrace()[1].getMethodName();
            throw new Exception(msgError,ex);
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
