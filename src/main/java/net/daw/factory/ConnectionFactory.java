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

/**
 *
 * @author kevin
 */
public class ConnectionFactory {

    public static ConnectionInterface getConnection(ConnectionConstants.EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface = null;
        switch (enumConnection) {
            case Hikari:
                oConnectionInterface = new HikariConnectionSpecificImplementation();
                break;
            case C3p0:
                oConnectionInterface = new C3p0ConnectionSpecificImplementation();
                break;                      
            case BoneCp:
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
