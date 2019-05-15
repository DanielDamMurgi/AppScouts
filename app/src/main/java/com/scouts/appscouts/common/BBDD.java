package com.scouts.appscouts.common;

public class BBDD {
    private static final String DIRECCION= "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7291737";
    private static  final String USUARIO= "sql7291737";
    private static final String CONTRASEÑA= "ZynRmi9UFj";

    public static String getDIRECCION() {
        return DIRECCION;
    }

    public static String getUSUARIO() {
        return USUARIO;
    }

    public static String getCONTRASEÑA() {
        return CONTRASEÑA;
    }
}
