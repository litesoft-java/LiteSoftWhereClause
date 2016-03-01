// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

import org.litesoft.whereclause.nonpublic.*;

/**
 * A class that can represent a SQL Single Column Select statement.<p>
 * <a href="../../Licence.txt">Licence</a><br>
 * <p/>
 * This form of SQL Select statement exists primarily to support the SQL IN
 * <b>WhereClause</b>.<p>
 * <p/>
 * See <a href="AbstractWhereClauseColumnIsIn.html">AbstractWhereClauseColumnIsIn</a><br>
 * See <a href="WhereClause.html">WhereClause</a><br>
 * See <a href="WhereClauseFactory.html">WhereClauseFactory</a><p>
 * <p/>
 * Exceptions: All problems caught when the parameter(s) are checked (as
 * indicated/implied in the @param tags) will generate an IllegalArgumentException,
 * and means the API user has a problem.  If a NullPointerException (or some
 * others, like: ClassCastException or ArrayIndexOutOfBoundsException) is thrown,
 * it means the API developer has a problem.  Any Exception that is explicitly
 * thrown in the API, but unrelated to a parameter, will be listed in the throws
 * clause (and hopefully in the tag @throws).  These may (but probably won't) be
 * checked Exceptions.
 *
 * @author George Smith
 * @version 1.0 10/07/01
 */

public class SingleColumnSelect {
    private SimpleColumnDefinition zColumnDefinition;
    private SimpleFromIdentifier zFromIdentifier;
    private WhereClause zWhereClause;

    /**
     * Constructor that supports the generation of a SQL Select statment that
     * selects a single column for all rows.<p>
     *
     * @param pColumnDefinition Column Definition (!null).
     * @param pFromTable        Table Identifier (supports getTableName()) (!null).
     */
    public SingleColumnSelect( SimpleColumnDefinition pColumnDefinition, SimpleFromIdentifier pFromTable ) {
        this( pColumnDefinition, pFromTable, null );
    }

    /**
     * Constructor that supports the generation of a SQL Select statment that
     * selects a single column for the specified rows.<p>
     *
     * @param pColumnDefinition Column Definition (!null).
     * @param pFromIdentifier   Table Identifier (supports getTableName()) (!null).
     * @param pWhereClause      A Where Clause to select the appropriate rows (null ok).
     */
    public SingleColumnSelect( SimpleColumnDefinition pColumnDefinition, SimpleFromIdentifier pFromIdentifier, WhereClause pWhereClause ) {
        IllegalArgument.ifNull( "ColumnDefinition", zColumnDefinition = pColumnDefinition );
        IllegalArgument.ifNull( "FromIdentifier", zFromIdentifier = pFromIdentifier );
        zWhereClause = pWhereClause; // Null OK!
    }

    /**
     * Accessor for the Column Definition of <b>the</b> selected column.<p>
     *
     * @return The selected Column Definition (!null).
     */
    public final SimpleColumnDefinition getColumnDefinition() {
        return zColumnDefinition;
    }

    /**
     * Accessor for the Table Name.<p>
     *
     * @return The Table Name (!null).
     */
    public final SimpleFromIdentifier getFromIdentifier() {
        return zFromIdentifier;
    }

    /**
     * Accessor for the WhereClause to limit the select the rows.<p>
     *
     * @return Select's WhereClause (null == all rows).
     */
    public final WhereClause getWhereClause() {
        return zWhereClause;
    }

    /**
     * Generate a Debug friendly representation.<p>
     *
     * @return a String for Debuging.
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder( "Select " );
        sb.append( zColumnDefinition.getName() );
        sb.append( " From " );
        sb.append( zFromIdentifier.getIdentifierName() );
        if ( zWhereClause != null ) {
            sb.append( ' ' );
            sb.append( zWhereClause.toString() );
        }
        return sb.toString();
    }

    /**
     * Generate a String that may be used as a SQL statement that selects a
     * single column.<p>
     * <p/>
     * Note: This String can get quite large.<p>
     *
     * @return a single column SQL SELECT statement.
     */
    public final String toSQL() {
        return toSQL( WCtoSqlHelper.NULL );
    }

    /**
     * Generate a String that may be used as a SQL statement that selects a
     * single column.<p>
     * <p/>
     * Note: This String can get quite large.<p>
     *
     * @return a single column SQL SELECT statement.
     */
    public final String toSQL( WCtoSqlHelper pWCtoSqlHelper ) {
        StringBuilder sb = new StringBuilder();
        toSqlHelper( (pWCtoSqlHelper != null) ? pWCtoSqlHelper : WCtoSqlHelper.NULL, sb );
        return sb.toString();
    }

    /**
     * Helper method for <b>toSQL()</b> that provides a more efficient
     * mechanism for the recursive decent of a WhereClause <i>tree</i>.<p>
     *
     * @param pWCtoSqlHelper helper called on each toSQLHelper()
     * @param pSB            the StringBuilder to build the WhereClause into.<p>
     *
     * @see #toSQL()
     */
    public void toSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB ) {
        if ( !pWCtoSqlHelper.preRender( this, pSB ) ) {
            LLtoSqlHelper( pWCtoSqlHelper, pSB, zWhereClause );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }

    public void LLtoSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB, WhereClause pWhereClause ) {
        pSB.append( "SELECT " );
        pSB.append( zColumnDefinition.getSearchColumnName() );
        pSB.append( " FROM " );
        pSB.append( zFromIdentifier.getTableName() );
        if ( pWhereClause != null ) {
            pSB.append( " WHERE " );
            pWhereClause.toSqlHelper( pWCtoSqlHelper, pSB );
        }
    }
}
