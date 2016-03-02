// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.SimpleColumnDefinition;
import org.litesoft.whereclause.SingleColumnSelect;
import org.litesoft.whereclause.WhereClause;
import org.litesoft.whereclause.WhereClauseType;

/**
 * An abstract representation of a SQL <b>IN</b> <i>WHERE</i> clause.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
 * <p/>
 * This form of <b>WhereClause</b> is used to support <i>Inner</i> Selects.
 * It is currently implemented for the IS_IN Type.<p>
 * <p/>
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

public abstract class AbstractWhereClauseColumnIsIn extends AbstractWhereClauseColumnReference {
    private SingleColumnSelect mSingleColumnSelect;

    /**
     * Constructor that supports the SQL <b>IN</b> <i>WHERE</i> clause.<p>
     * <p/>
     * Note: the Column Types of the ColumnDefinition and selected column of
     * the SingleColumnSelect MUST be equal, otherwise an IllegalArgumentException
     * is thrown.<p>
     *
     * @param pType               The Type (IS_IN) for this WhereClause.
     * @param pColumnDefinition   Column Definition (!null).
     * @param pSingleColumnSelect A single column mini-SELECT command (!null).
     */
    protected AbstractWhereClauseColumnIsIn( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition, SingleColumnSelect pSingleColumnSelect ) {
        super( pType, pColumnDefinition );

        IllegalArgument.ifNull( "SingleColumnSelect", mSingleColumnSelect = pSingleColumnSelect );

        if ( !pColumnDefinition.getColumnType().equals( pSingleColumnSelect.getColumnDefinition().getColumnType() ) ) {
            throw IllegalArgument.exception( "Column Types", "Incompatible" );
        }
    }

    /**
     * Accessor for the Is In (Inner) Single Column Select.<p>
     *
     * @return The Inner Single Column Select.
     */
    public final SingleColumnSelect getSingleColumnSelect() {
        return mSingleColumnSelect;
    }

    /**
     * Helper method for <b>toString()</b> that provides a more efficient
     * mechanism for the recursive decent of a WhereClause <i>tree</i>.<p>
     *
     * @param pSB the StringBuilder to build the WhereClause into.<p>
     *
     * @see WhereClause#toString()
     */
    @Override
    protected void toStringHelper( StringBuilder pSB ) {
        toStringColumnReference( pSB );
        pSB.append( ' ' );
        pSB.append( isNot() ? getType().getToNotStr() : getType().getToStr() );
        pSB.append( mSingleColumnSelect.toString() );
        pSB.append( ')' );
    }

    /**
     * Helper method for <b>toSQL()</b> that provides a more efficient
     * mechanism for the recursive decent of a WhereClause <i>tree</i>.<p>
     * <p/>
     * Note: May need to override to get proper result for SQL.<p>
     *
     * @param pWCtoSqlHelper helper called on each toSQLHelper()
     * @param pSB            the StringBuilder to build the WhereClause into.<p>
     *
     * @see WhereClause#toSQL()
     */
    @Override
    protected final void toSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB ) {
        if ( !pWCtoSqlHelper.preRender( this, pSB ) ) {
            toSqlColumnReference( pSB );
            pSB.append( ' ' );
            pSB.append( isNot() ? getType().getToNotSql() : getType().getToSql() );
            mSingleColumnSelect.toSqlHelper( pWCtoSqlHelper, pSB );
            pSB.append( ')' );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }
}
