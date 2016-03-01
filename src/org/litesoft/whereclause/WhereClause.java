// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

import org.litesoft.whereclause.nonpublic.*;

/**
 * An abstract representation of a SQL <i>WHERE</i> clause.<p>
 * <a href="../../Licence.txt">Licence</a><br>
 * <p/>
 * Using the <b>WhereClauseFactory</b> a complex SQL <i>WHERE</i> clause
 * can be generated.  The concrete extensions of <b>WhereClause</b> generated
 * by the <b>WhereClauseFactory</b> can form a <i>tree</i> that represents any
 * arbitrarily complex expression.<p>
 * <p/>
 * Two methods are provided to translate a <i>tree</i> of <b>WhereClause</b>s
 * into <b>String</b>s:<p>
 * <pre>
 *      toString() ;
 *      toSQL() ;
 * </pre>
 * <p/>
 * The <i>toString()</i> is intended for debuging purposes.<p>
 * <p/>
 * If you need to create/support a specific SQL <i>WHERE</i> clause that
 * is not provided, you would need to extend the <b>WhereClauseFactory</b>,
 * and extend this class.  However, you will probably not extend this class
 * directly, but extend one of the following abstract helper classes:<p>
 * <ul>
 * <a href="AbstractWhereClauseAssociativeList.html">AbstractWhereClauseAssociativeList</a><br>
 * <a href="AbstractWhereClauseWrapper.html">AbstractWhereClauseWrapper</a><br>
 * <a href="AbstractWhereClauseColumnReference.html">AbstractWhereClauseColumnReference</a><br>
 * <a href="AbstractWhereClauseColumnAndValue.html">AbstractWhereClauseColumnAndValue</a><br>
 * <a href="AbstractWhereClauseColumnAndTwoValues.html">AbstractWhereClauseColumnAndTwoValues</a><br>
 * <a href="AbstractWhereClauseColumnAndLikeValue.html">AbstractWhereClauseColumnAndLikeValue</a><br>
 * and unlikely but possibly:<br>
 * <ul>
 * <a href="AbstractWhereClauseColumnIsIn.html">AbstractWhereClauseColumnIsIn</a><br>
 * </ul>
 * </ul><p>
 * <p/>
 * By using the <i>Type</i>s (<tt>public static final int</tt>s),
 * direct interpretation / alternate translation can be accomplished.<p>
 * <p/>
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

public abstract class WhereClause {
    private WhereClauseType mType;

    /**
     * Constructor that simply sets the <b>Type</b> of this WhereClause.<p>
     * <p/>
     * Please see the <tt>public static final int</tt>s for the currently
     * supported <b>Type</b>s.<p>
     *
     * @param pType the Type for this WhereClause.
     */
    protected WhereClause( WhereClauseType pType ) {
        mType = pType;
    }

    /**
     * Accessor for the <b>Type</b> of this WhereClause.<p>
     * <p/>
     * Please see the <tt>public static final int</tt>s for the currently
     * supported <b>Type</b>s.<p>
     *
     * @return the <b>Type</b>.
     */
    public final WhereClauseType getType() {
        return mType;
    }

    /**
     * Generate a Debug friendly representation.<p>
     *
     * @return a String for Debuging.
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder( "Where " );
        toStringHelper( sb );
        return sb.toString();
    }

    /**
     * Generate a String that may be used in a SQL statement, where the
     * <b>WHERE</b> <i>clause</i> would go.<p>
     * <p/>
     * Note: This String can get quite large.<p>
     *
     * @return the <b>WHERE</b> <i>clause</i> for SQL statement as a String.
     */
    public final String toSQL() {
        return toSQL( WCtoSqlHelper.NULL );
    }

    /**
     * Generate a String that may be used in a SQL statement, where the
     * <b>WHERE</b> <i>clause</i> would go.<p>
     * <p/>
     * Note: This String can get quite large.<p>
     *
     * @return the <b>WHERE</b> <i>clause</i> for SQL statement as a String.
     */
    public final String toSQL( WCtoSqlHelper pWCtoSqlHelper ) {
        StringBuilder sb = new StringBuilder( "WHERE " );
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
    protected abstract void toSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB );

    /**
     * Helper method for <b>toSqlHelper()</b> that adds a prefix and then
     * parenthesizes a nested WhereClause.<p>
     *
     * @param pWCtoSqlHelper helper called on each toSQLHelper()
     * @param pSB            the StringBuilder to build the WhereClause into.
     * @param pPreFix        the prefix to append.
     * @param pWhereClause   the nested WhereClause to wrap with parenthesizes
     *                       and append.<p>
     *
     * @see #toSqlHelper(WCtoSqlHelper, StringBuilder)
     */
    protected static void toSqlHelperParenthesizer( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB, String pPreFix, WhereClause pWhereClause ) {
        pSB.append( pPreFix );
        pSB.append( ' ' );
        toSqlHelperParenthesizer( pWCtoSqlHelper, pSB, pWhereClause );
    }

    /**
     * Helper method for <b>toSqlHelper()</b> that adds a parenthesized
     * nested WhereClause.<p>
     *
     * @param pWCtoSqlHelper helper called on each toSQLHelper()
     * @param pSB            the StringBuilder to build the WhereClause into.
     * @param pWhereClause   the nested WhereClause to wrap with parenthesizes
     *                       and append.<p> @see #toSqlHelper(StringBuilder,WCtoSqlHelper)
     */
    protected static void toSqlHelperParenthesizer( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB, WhereClause pWhereClause ) {
        pSB.append( '(' );
        pWhereClause.toSqlHelper( pWCtoSqlHelper, pSB );
        pSB.append( ')' );
    }

    /**
     * Helper method for <b>toString()</b> that provides a more efficient
     * mechanism for the recursive decent of a WhereClause <i>tree</i>.<p>
     *
     * @param pSB the StringBuilder to build the WhereClause into.<p>
     *
     * @see #toString()
     */
    protected abstract void toStringHelper( StringBuilder pSB );

    /**
     * Helper method for <b>toStringHelper()</b> that adds a parenthesized
     * nested WhereClause.<p>
     *
     * @param pSB          the StringBuilder to build the WhereClause into.
     * @param pWhereClause the nested WhereClause to wrap with parenthesizes
     *                     and append.<p>
     *
     * @see #toStringHelper(StringBuilder)
     */
    protected static void toStringHelperParenthesizer( StringBuilder pSB, WhereClause pWhereClause ) {
        pSB.append( '(' );
        pWhereClause.toStringHelper( pSB );
        pSB.append( ')' );
    }

    /**
     * Helper method for <b>toStringHelper()</b> that adds a prefix and then
     * parenthesizes a nested WhereClause.<p>
     *
     * @param pSB          the StringBuilder to build the WhereClause into.
     * @param pPreFix      the prefix to append.
     * @param pWhereClause the nested WhereClause to wrap with parenthesizes
     *                     and append.<p>
     *
     * @see #toStringHelper(StringBuilder)
     */
    protected static void toStringHelperParenthesizer( StringBuilder pSB, String pPreFix, WhereClause pWhereClause ) {
        pSB.append( pPreFix );
        pSB.append( ' ' );
        toStringHelperParenthesizer( pSB, pWhereClause );
    }
}
