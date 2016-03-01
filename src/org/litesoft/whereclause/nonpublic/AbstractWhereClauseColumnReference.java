// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.*;
import org.litesoft.whereclause.nonpublic.*;

/**
 * An abstract helper class for a SQL <i>WHERE</i> clause that carries a Column Definition.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p/>
 * This <b>WhereClause</b> is <a href="WhereClauseNotable.html"><b>NOT</b><i>able</i></a>.<p>
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

public abstract class AbstractWhereClauseColumnReference extends WhereClause implements WhereClauseNotable {
    private SimpleColumnDefinition mColumnDefinition;

    /**
     * Constructor that supports a Type and Column Definition.<p>
     *
     * @param pType             The Type for this WhereClause.
     * @param pColumnDefinition Column Definition (!null).
     */
    protected AbstractWhereClauseColumnReference( WhereClauseType pType, SimpleColumnDefinition pColumnDefinition ) {
        super( pType );

        IllegalArgument.ifNull( "ColumnDefinition", mColumnDefinition = pColumnDefinition );
    }

    /**
     * Accessor for the Column Definition.<p>
     *
     * @return Column Definition (!null).
     */
    public final SimpleColumnDefinition getColumnDefinition() {
        return mColumnDefinition;
    }

    private boolean zNot = false;

    /**
     * Accessor for the <b>NOT</b> flag.<p>
     *
     * @return if this WhereClause is currently in the <b>NOT</b> mode.
     */
    @Override
    public final boolean isNot() {
        return zNot;
    }

    /**
     * Toggle (or switch) this WhereClause's <b>NOT</b> mode.<p>
     */
    @Override
    public final void switchNot() {
        zNot = !zNot;
    }

    /**
     * Helper method for <b>toStringHelper()</b> that adds the ColumnDefinition's Name.<p>
     *
     * @param pSB the StringBuilder to build the WhereClause into.
     *
     * @see #toStringHelper(StringBuilder)
     */
    protected final void toStringColumnReference( StringBuilder pSB ) {
        pSB.append( mColumnDefinition.getName() );
    }

    /**
     * Helper method for <b>toSqlHelper()</b> that adds the ColumnDefinition's Column Name.<p>
     *
     * @param pSB the StringBuilder to build the WhereClause into.
     *
     * @see WhereClause#toSqlHelper(WCtoSqlHelper, StringBuilder)
     */
    protected final void toSqlColumnReference( StringBuilder pSB ) {
        pSB.append( mColumnDefinition.getSearchColumnName() );
    }
}
