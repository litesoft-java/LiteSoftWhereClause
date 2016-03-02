// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.SimpleColumnDefinition;
import org.litesoft.whereclause.WhereClause;
import org.litesoft.whereclause.WhereClauseColumnSupport;
import org.litesoft.whereclause.WhereClauseType;

/**
 * An abstract representation of a <b>LIKE</b> SQL <i>WHERE</i> clause.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
 * <p/>
 * This form of <b>WhereClause</b> is only used with Strings (both as
 * Columns and comparison values) to determine if the column <i>contains</i>
 * the comparison value.  The form of the <i>contains</i> currently is
 * limited to: CONTAINS, STARTS_WITH, ENDS_WITH.<p>
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

public abstract class AbstractWhereClauseColumnAndLikeValue extends AbstractWhereClauseStringColumnLike {
    private String mValue;

    /**
     * Constructor that supports the <b>LIKE</b> SQL <i>WHERE</i> clause.<p>
     *
     * @param pType             The Type (CONTAINS, STARTS_WITH, or ENDS_WITH) for this WhereClause.
     * @param pColumnDefinition Column Definition (Column Type must equal String) (!null).
     * @param pValue            String that is to be used for <i>contains</i> checking (!null).
     */
    protected AbstractWhereClauseColumnAndLikeValue( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition, String pValue ) {
        super( pType, pColumnDefinition );

        IllegalArgument.ifNull( "Value", mValue = pValue );
    }

    /**
     * Accessor for the value that the column is to be checked against.<p>
     *
     * @return column checked against value.
     */
    public final String getValue() {
        return mValue;
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
        pSB.append( ' ' );
        WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), getValue() );
    }
}
