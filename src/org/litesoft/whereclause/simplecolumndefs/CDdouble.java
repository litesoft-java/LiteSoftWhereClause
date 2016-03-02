package org.litesoft.whereclause.simplecolumndefs;

public class CDdouble extends CommonSCD {
    public CDdouble( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return Double.class;
    }

    public static CDdouble of( String pName, String pColumnName ) {
        return new CDdouble( pName, pColumnName );
    }

    public static CDdouble of( String pName ) {
        return of( pName, pName );
    }
}
