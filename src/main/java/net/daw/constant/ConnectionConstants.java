/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.constant;

/**
 *
 * @author kevin
 */
public class ConnectionConstants {

    public static enum EnumConstans {
        Hikari,
        BoneCp,
        C3p0,
        DBCP
    };

    public static final EnumConstans connectionPoolHikari = EnumConstans.Hikari;
    public static final EnumConstans connectionPoolBoneCp = EnumConstans.BoneCp;
    public static final EnumConstans connectionPoolC3p0 = EnumConstans.C3p0;
    public static final EnumConstans connectionPoolDBCP = EnumConstans.DBCP;

//    public static final String connectionName = "hikari";
    public static final String databaseName = "trolleyes";
    public static final String databaseLogin = "root";
    public static final String databasePassword = "bitnami";
    public static final String databasePort = "3306";
    public static final String databaseHost = "localhost";
    public static final int getDatabaseMaxPoolSize = 10;
    public static final int getDatabaseMinPoolSize = 5;

    public static String getConnectionChain() {
        return "jdbc:mysql://" + ConnectionConstants.databaseHost + ":" + ConnectionConstants.databasePort + "/"
                + ConnectionConstants.databaseName;
    }

}
