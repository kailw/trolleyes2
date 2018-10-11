/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.conexiones;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author kevin
 */
public class BoneCp implements ConnectionInterface {

    Connection connection;
    private BoneCP connectionPool;

    public Connection newConnection() throws Exception {

        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(Parameters.url); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
        config.setUsername(Parameters.user);
        config.setPassword(Parameters.pass);
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        try {
            connectionPool = new BoneCP(config);
            Connection connection = connectionPool.getConnection();

        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return connection;
    }

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
