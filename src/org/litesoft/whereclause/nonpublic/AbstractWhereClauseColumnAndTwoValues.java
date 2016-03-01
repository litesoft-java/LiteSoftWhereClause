// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.*;

/**
 * An abstract representation of a SQL <i>WHERE</i> clause that supports a
 * column and two (Left & Right) comparison values.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
 * <p/>
 * This form of <b>WhereClause</b> primarily exists to support BETWEEN.<p>
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

public abstract class AbstractWhereClauseColumnAndTwoValues extends AbstractWhereClauseColumnReference {
    private Object mLeftValue;
    private Object mRightValue;

    /**
     * Constructor that associates two (Left & Right) comparison values with
     * a column.<p>
     * <p/>
     * Note: Both the type of the Column and the types of the Left & Right
     * values should be comparable.  For the SQL generation, the Left &
     * Right value object's toString() method is used for <i>conversion</i>.<p>
     *
     * @param pType             the Type (BETWEEN) for this WhereClause.
     * @param pColumnDefinition Column Definition (!null).
     * @param pLeftValue        Left (or 1st) value (!null).
     * @param pRightValue       Right (or 2nd) value (!null).
     */
    protected AbstractWhereClauseColumnAndTwoValues( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition, Object pLeftValue, Object pRightValue ) {
        super( pType, pColumnDefinition );

        IllegalArgument.ifNull( "Left (GE) Value", mLeftValue = pLeftValue );
        IllegalArgument.ifNull( "Right (LE) Value", mRightValue = pRightValue );
    }

    /**
     * Accessor for the Left (or 1st) comparison value (!null).<p>
     *
     * @return the Left (or 1st) comparison value (!null).
     */
    public final Object getLeftValue() {
        return mLeftValue;
    }

    /**
     * Accessor for the Right (or 2nd) comparison value (!null).<p>
     *
     * @return the Right (or 2nd) comparison value (!null).
     */
    public final Object getRightValue() {
        return mRightValue;
    }
}
