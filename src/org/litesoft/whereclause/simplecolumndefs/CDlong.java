package org.litesoft.whereclause.simplecolumndefs;

public class CDlong extends CommonSCD {
    public CDlong( String pName, String pColumnName ) {
        super( pName, pColumnName );
    }

    @Override
    public Class getColumnType() {
        return Long.class;
    }

    public static CDlong of( String pName, String pColumnName ) {
        return new CDlong( pName, pColumnName );
    }

    public static CDlong of( String pName ) {
        return of( pName, pName );
    }
}
