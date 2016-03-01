// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.*;

/**
 * An abstract representation of a SQL <i>WHERE</i> clause that supports a
 * column and two or more "equal" values.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
 * <p/>
 * This form of <b>WhereClause</b> primarily exists to support AnyOf.<p>
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
 * @version 1.0 07/07/08
 */

public abstract class AbstractWhereClauseColumnAnd_N_Values extends AbstractWhereClauseColumnReference {
    private Object[] mValues;

    /**
     * Constructor that associates two or more !null "equal" values with
     * a column.<p>
     * <p/>
     * Note: Both the type of the Column and the types of the values should
     * be comparable.  For the SQL generation, the object's toString()
     * method is used for <i>conversion</i>.<p>
     *
     * @param pType             the Type for this WhereClause.
     * @param pColumnDefinition Column Definition (!null).
     * @param pValues           Values (two or more !null).
     */
    protected AbstractWhereClauseColumnAnd_N_Values( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition, Object... pValues ) {
        super( pType, pColumnDefinition );

        if ( (pValues == null) || (pValues.length == 0) ) {
            throw new IllegalArgumentException( "No Values" );
        }

        if ( pValues.length < 2 ) {
            throw new IllegalArgumentException( "Not Two Values" );
        }

        for ( int i = 0; i < pValues.length; i++ ) {
            if ( pValues[i] == null ) {
                throw new IllegalArgumentException( "Values[" + i + "] was null" );
            }
        }
        mValues = pValues;
    }

    /**
     * Accessor for the two or more "equal" values (!null).<p>
     *
     * @return the two or more "equal" values (!null).
     */
    public final Object[] getValues() {
        return mValues;
    }

    @Override
    protected void toStringHelper( StringBuilder pSB ) {
        toStringColumnReference( pSB );
        pSB.append( ' ' );
        pSB.append( isNot() ? getType().getToNotStr() : getType().getToStr() );
        Object[] zValues = getValues();
        WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), zValues[0] );
        for ( int i = 1; i < zValues.length; i++ ) {
            pSB.append( ',' );
            WhereClauseColumnSupport.makeStringValue( pSB, getColumnDefinition(), zValues[i] );
        }
        pSB.append( ')' );
    }

    @Override
    protected void toSqlHelper( WCtoSqlHelper pWCtoSqlHelper, StringBuilder pSB ) {
        if ( !pWCtoSqlHelper.preRender( this, pSB ) ) {
            toSqlColumnReference( pSB );
            pSB.append( ' ' );
            pSB.append( isNot() ? getType().getToNotSql() : getType().getToSql() );
            Object[] zValues = getValues();
            WhereClauseColumnSupport.makeSqlValue( pSB, getColumnDefinition(), true, zValues[0] );
            for ( int i = 1; i < zValues.length; i++ ) {
                pSB.append( ',' );
                WhereClauseColumnSupport.makeSqlValue( pSB, getColumnDefinition(), true, zValues[i] );
            }
            pSB.append( ')' );

            pWCtoSqlHelper.postRender( this, pSB );
        }
    }
}
