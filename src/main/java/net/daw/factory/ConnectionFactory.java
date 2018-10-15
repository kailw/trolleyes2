/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.factory;

import net.daw.connection.publicInterface.ConnectionInterface;
import net.daw.connection.specificImplementation.BoneCpConnectionSpecificImplementation;
import net.daw.connection.specificImplementation.C3p0ConnectionSpecificImplementation;
import net.daw.connection.specificImplementation.DBCPConnectionSpecificImplementation;
import net.daw.connection.specificImplementation.HikariConnectionSpecificImplementation;
import net.daw.constant.ConnectionConstants;
import net.daw.constant.ConnectionConstants.EnumConstans;

/**
 *
 * @author kevin
 */
public class ConnectionFactory {

    public static ConnectionInterface getConnection(EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface = null;
        switch (enumConnection) {
            case HIKARI:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
            case C3P0:
                oConnectionInterface = new C3p0ConnectionSpecificImplementation();
                break;                      
            case BONECP:
                oConnectionInterface = new BoneCpConnectionSpecificImplementation();
                break;                              
            case DBCP:
                oConnectionInterface = new DBCPConnectionSpecificImplementation();
                break;                
            default:                
                break;
        }
        return oConnectionInterface;

    }
}
