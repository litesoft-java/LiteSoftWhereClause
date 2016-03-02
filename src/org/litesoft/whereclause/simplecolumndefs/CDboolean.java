package org.litesoft.whereclause.simplecolumndefs;

public class CDboolean extends CommonSCD {
    public CDboolean( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return Boolean.class;
    }

    public static CDboolean of( String pName, String pColumnName ) {
        return new CDboolean( pName, pColumnName );
    }

    public static CDboolean of( String pName ) {
        return of( pName, pName );
    }
}
