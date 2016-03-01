// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.*;
import org.litesoft.whereclause.nonpublic.*;

/**
 * An abstract representation of a wrapped SQL <i>WHERE</i> clause (like NOT).<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * The purpose of this class is to support the unary operations (like NOT)
 * against another <b>WhereClause</b>.<p>
 * <p/>
 * This form of <b>WhereClause</b> is currently used only to support:
 * NOT.<p>
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

public abstract class AbstractWhereClauseWrapper extends WhereClause {
    private WhereClause mWrappedWhereClause;

    /**
     * Constructor that supports a <i>wrapped</i> SQL <i>WHERE</i> clause.<p>
     *
     * @param pType               The Type for this WhereClause.
     * @param pWrappedWhereClause The WhereClause that is being wrapped (!null).
     */
    protected AbstractWhereClauseWrapper( WhereClauseType pType, WhereClause pWrappedWhereClause ) {
        super( pType );

        IllegalArgument.ifNull( "Wrapped WhereClause", mWrappedWhereClause = pWrappedWhereClause );
    }

    /**
     * Accessor for the Wrapped WhereClause.<p>
     *
     * @return Wrapped WhereClause (!null).
     */
    public final WhereClause getWrappedWhereClause() {
        return mWrappedWhereClause;
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
    protected final void toStringHelper( StringBuilder pSB ) {
        toStringHelperParenthesizer( pSB, getType().getToStr(), getWrappedWhereClause() );
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
            toSqlHelperParenthesizer( pWCtoSqlHelper, pSB, getType().getToSql(), getWrappedWhereClause() );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }
}
