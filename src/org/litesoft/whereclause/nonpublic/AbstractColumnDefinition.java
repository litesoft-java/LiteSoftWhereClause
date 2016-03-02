// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.SimpleColumnDefinition;
import org.litesoft.whereclause.WhereClause;

/**
 * An abstract helper class to make implementing SimpleColumnDefinition simpler.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
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

public abstract class AbstractColumnDefinition implements SimpleColumnDefinition {
    private String mName;
    private String mColumnName, mSearchColumnName;
    private boolean mHasSearchColumn;
    private Class mColumnType;

    /**
     * Constructor that simply sets the attributes to support the
     * SimpleColumnDefinition interface.<p>
     *
     * @param pName             Logical Column Name (!null).
     * @param pColumnName       SQL Column Name (!null).
     * @param pSearchColumnName Search SQL Column Name (!null).
     * @param pColumnType       Java Class type for the SQL Column Type (!null).
     */
    protected AbstractColumnDefinition( String pName, String pColumnName, String pSearchColumnName, Class pColumnType ) {
        mName = pName;
        mColumnName = pColumnName;
        mSearchColumnName = pSearchColumnName;
        mColumnType = pColumnType;
        mHasSearchColumn = (mColumnName != null) && !mColumnName.equals( mSearchColumnName );
    }

    /**
     * Constructor that simply sets the attributes to support the
     * SimpleColumnDefinition interface.<p>
     *
     * @param pName       Logical Column Name (!null).
     * @param pColumnName SQL Column Name (!null).
     * @param pColumnType Java Class type for the SQL Column Type (!null).
     */
    protected AbstractColumnDefinition( String pName, String pColumnName, Class pColumnType ) {
        mName = pName;
        mColumnName = pColumnName;
        mSearchColumnName = pColumnName;
        mColumnType = pColumnType;
    }

    /**
     * Accessor for the logical name of this Column.<p>
     * <p/>
     * While this name can be equal to the ColumnName, it does not need
     * to be, it could be the name of an Object's attribute that is mapped
     * to this column by an OR mapper.  In any case, this name is what
     * is used/displayed by the specific <b>WhereClause</b>'s toString()
     * method.<p>
     *
     * @return logical name of this Column.<p>
     *
     * @see WhereClause#toString()
     */
    @Override
    public String getName() {
        return mName;
    }

    /**
     * Accessor for the <b>actual</b> SQL Column Name.<p>
     * <p/>
     * This ColumnName MUST match that declared in the SQL Table Meta-data,
     * as it is used/displayed by the specific <b>WhereClause</b>'s toSQL()
     * method.<p>
     *
     * @return SQL Column Name.<p>
     *
     * @see WhereClause#toSQL()
     */
    @Override
    public String getColumnName() {
        return mColumnName;
    }

    /**
     * Return <code>true</code> if the SearchColumn should be a seperate Column.<p>
     * <p/>
     *
     * @see WhereClause#toSQL()
     */
    @Override
    public boolean hasSearchColumn() {
        return mHasSearchColumn;
    }

    /**
     * Accessor for the SQL Column Name that should <b>actually</b> be used for Searching.<p>
     * <p/>
     * Normally this has the same value as <code>getColumnName</code>.  If it doesn't, then it
     * indicates that there is a secondary column that needs to be maintained just for searching.<p>
     * <p/>
     * This ColumnName MUST match that declared in the SQL Table Meta-data,
     * as it is used/displayed by the specific <b>WhereClause</b>'s toSQL()
     * method.<p>
     *
     * @return SQL Column Name.<p>
     *
     * @see WhereClause#toSQL()
     */
    @Override
    public String getSearchColumnName() {
        return mSearchColumnName;
    }

    /**
     * Accessor for the <i>Java</i> Class <i>type</i> of the SQL Column.<p>
     * <p/>
     * The primary purpose of this method (within the <b>WhereClause</b>
     * infrastructure) is to indicate (to the toString() and toSQL() methods)
     * how the values should be formatted/displayed.  (e.g. that <b>String</b>s
     * must be quote safe and non-<b>String</b>s must use thier toString()
     * method to format)<p>
     *
     * @return <i>Java</i> Class <i>type</i> of the SQL Column.<p>
     *
     * @see WhereClause#toSQL()
     * @see WhereClause#toString()
     */
    @Override
    public Class getColumnType() {
        return mColumnType;
    }
}
