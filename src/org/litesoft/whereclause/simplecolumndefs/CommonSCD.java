package org.litesoft.whereclause.simplecolumndefs;

import org.litesoft.whereclause.SimpleColumnDefinition;

public abstract class CommonSCD implements SimpleColumnDefinition {
    private final String mName, mColumnName;

    protected CommonSCD( String pName, String pColumnName ) {
        mName = significant( "Name", pName );
        mColumnName = significant( "ColumnName", pColumnName );
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getColumnName() {
        return mColumnName;
    }

    @Override
    public boolean hasSearchColumn() {
        return false;
    }

    @Override
    public String getSearchColumnName() {
        return null;
    }

    static String significant( String pReferenceLabel, String pString ) {
        if ( pString != null ) {
            pString = pString.trim();
            if ( pString.length() != 0 ) {
                return pString;
            }
        }
        throw new IllegalArgumentException( pReferenceLabel + " Must be Significant (Not be null, empty or just spaces" );
    }
}
