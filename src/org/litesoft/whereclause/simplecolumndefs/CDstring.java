package org.litesoft.whereclause.simplecolumndefs;

public class CDstring extends CommonSCD {
    public CDstring( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return String.class;
    }

    public static CDstring of( String pName, String pColumnName ) {
        return new CDstring( pName, pColumnName );
    }

    public static CDstring of( String pName ) {
        return of( pName, pName );
    }
}
