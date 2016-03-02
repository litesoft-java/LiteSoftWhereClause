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

public abstract class AbstractWhereClauseColumnAndLikeValues extends AbstractWhereClauseStringColumnLike {
    private String[] mValues;

    /**
     * Constructor that supports the <b>LIKE</b> SQL <i>WHERE</i> clause.<p>
     *
     * @param pType             The Type (CONTAINS, STARTS_WITH, or ENDS_WITH) for this WhereClause.
     * @param pColumnDefinition Column Definition (Column Type must equal String) (!null).
     * @param pValues           Strings that are to be used for <i>contains</i> checking (!null).
     */
    protected AbstractWhereClauseColumnAndLikeValues( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition, String[] pValues ) {
        super( pType, pColumnDefinition );

        if ( countOfValues( mValues = pValues ) < 2 ) {
            throw IllegalArgument.exception( "Values", "At least two required" );
        }
    }

    private int countOfValues( String[] pStrings ) {
        int count = 0;
        if ( pStrings != null ) {
            for ( String s : pStrings ) {
                if ( !isEmpty( s ) ) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isEmpty( String pPart ) {
        return (pPart == null) || (pPart.length() == 0);
    }

    /**
     * Accessor for the value that the column is to be checked against.<p>
     *
     * @return column checked against value.
     */
    public final String[] getValues() {
        return mValues;
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
        int ndx = 0;
        WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), mValues[ndx++] );
        while ( ndx < (mValues.length - 1) ) {
            String value = mValues[ndx++];
            if ( !isEmpty( value ) ) {
                pSB.append( ", " );
                WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), value );
            }
        }
        pSB.append( ", " );
        WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), mValues[ndx] );
        pSB.append( " )" );
    }

    @Override
    protected String[] getSqlLikeValues() {
        return getValues();
    }
}
