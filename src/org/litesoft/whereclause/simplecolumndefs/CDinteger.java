package org.litesoft.whereclause.simplecolumndefs;

public class CDinteger extends CommonSCD {
    public CDinteger( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return Integer.class;
    }

    public static CDinteger of( String pName, String pColumnName ) {
        return new CDinteger( pName, pColumnName );
    }

    public static CDinteger of( String pName ) {
        return of( pName, pName );
    }
}
