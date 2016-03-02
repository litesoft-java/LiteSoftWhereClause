// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.SimpleColumnDefinition;
import org.litesoft.whereclause.WhereClause;
import org.litesoft.whereclause.WhereClauseType;

/**
 * An abstract representation of a SQL <i>WHERE</i> clause that supports a
 * column and comparison to a type constant.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
 * <p/>
 * This form of <b>WhereClause</b> is currently used to support:
 * IS_NULL.<p>
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
 * @version 1.0 08/08/08
 */

public abstract class AbstractWhereClauseColumnAndTypeTo extends AbstractWhereClauseColumnReference {
    /**
     * Constructor that supports a Column for a SQL <i>WHERE</i> clause.<p>
     * <p/>
     * Note: Both the type of the Column and the type of the Value should be
     * comparable.  For the SQL generation, the Value object's toString()
     * method is used for <i>conversion</i>.<p>
     *
     * @param pType             The Type (EQUALS, LESSTHAN, and GREATERTHAN) for this WhereClause.
     * @param pColumnDefinition Column Definition (!null).
     */
    protected AbstractWhereClauseColumnAndTypeTo( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition ) {
        super( pType, pColumnDefinition );
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
    }

    @Override
    protected final void toSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB ) {
        if ( !pWCtoSqlHelper.preRender( this, pSB ) ) {
            toSqlColumnReference( pSB );
            pSB.append( ' ' );
            pSB.append( isNot() ? getType().getToNotSql() : getType().getToSql() );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }
}
