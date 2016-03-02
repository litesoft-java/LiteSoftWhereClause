// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.WhereClause;
import org.litesoft.whereclause.WhereClauseType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract representation of an associative list of SQL <i>WHERE</i> clauses.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This form of <b>WhereClause</b> is primarily used with a <i>list</i> of
 * <b>WhereClause</b>s and something to tie/relate these <b>WhereClause</b>s
 * togther, such as AND or OR.<p>
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

public abstract class AbstractWhereClauseAssociativeList extends WhereClause {
    private List<WhereClause> mWhereClauseList;

    /**
     * Constructor that associates two <b>WhereClause</b>s with the appropriate
     * relationship.<p>
     * <p/>
     * Note: If either (or both) of the two <b>WhereClause</b>s are them selves
     * instances of this class AND it (they) has the same relationship, then
     * the <b>WhereClause</b>(s) are <i>merged</i> (collapsed to this level)
     * with this.<p>
     *
     * @param pType         the Type (AND or OR) (also know as the relationship) for this WhereClause.
     * @param pWhereClause1 1st WhereClause of the two to relate (!null).
     * @param pWhereClause2 2nd WhereClause of the two to relate (!null).
     */
    protected AbstractWhereClauseAssociativeList( WhereClauseType pType, WhereClause pWhereClause1, WhereClause pWhereClause2 ) {
        super( pType );

        List<WhereClause> list = new ArrayList<>();
        addWhereClause( list, pWhereClause1 );
        addWhereClause( list, pWhereClause2 );
        mWhereClauseList = Collections.unmodifiableList( list );
    }

    private void addWhereClause( List<WhereClause> pList, WhereClause pWhereClause ) {
        if ( pWhereClause.getType() == this.getType() ) {
            pList.addAll( ((AbstractWhereClauseAssociativeList) pWhereClause).mWhereClauseList );
        } else {
            pList.add( pWhereClause );
        }
    }

    /**
     * Get Count of the contained <b>WhereClause</b>s.<p>
     * <p/>
     * Note: There will NEVER be less than two, but because of potential
     * merging there might be more than two.<p>
     *
     * @return count of of contained <b>WhereClause</b>s.
     */
    public final int getWhereClausesCount() {
        return mWhereClauseList.size();
    }

    /**
     * List accessor for the contained <b>WhereClause</b>s.<p>
     * <p/>
     * Note: There will NEVER be less than two, but because of potential
     * merging there might be more than two.<p>
     *
     * @return List of contained <b>WhereClause</b>s.
     */
    public final List<WhereClause> getWhereClauseList() {
        return mWhereClauseList;
    }

    /**
     * Iterator accessor for the contained <b>WhereClause</b>s.<p>
     * <p/>
     * Note: There will NEVER be less than two, but because of potential
     * merging there might be more than two.<p>
     *
     * @return iteration of contained <b>WhereClause</b>s.
     */
    public final Iterator<WhereClause> getWhereClauses() {
        return mWhereClauseList.iterator();
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
        Iterator<WhereClause> it = getWhereClauses();

        toStringHelperParenthesizer( pSB, it.next() );
        do {
            pSB.append( ' ' );
            toStringHelperParenthesizer( pSB, getType().getToStr(), it.next() );
        }
        while ( it.hasNext() );
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
            Iterator<WhereClause> it = getWhereClauses();

            toSqlHelperParenthesizer( pWCtoSqlHelper, pSB, it.next() );
            do {
                pSB.append( ' ' );
                toSqlHelperParenthesizer( pWCtoSqlHelper, pSB, getType().getToSql(), it.next() );
            }
            while ( it.hasNext() );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }
}
