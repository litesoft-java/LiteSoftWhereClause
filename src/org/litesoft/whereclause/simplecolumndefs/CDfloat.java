package org.litesoft.whereclause.simplecolumndefs;

public class CDfloat extends CommonSCD {
    public CDfloat( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return Float.class;
    }

    public static CDfloat of( String pName, String pColumnName ) {
        return new CDfloat( pName, pColumnName );
    }

    public static CDfloat of( String pName ) {
        return of( pName, pName );
    }
}
