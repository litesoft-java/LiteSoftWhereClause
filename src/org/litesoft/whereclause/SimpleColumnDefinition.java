// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

/**
 * An interface that the <b>WhereClause</b>s use to support the formatting
 * generated in the toString() and toSQL() methods.<p>
 * <a href="../../Licence.txt">Licence</a><br>
 * <p>
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

public interface SimpleColumnDefinition {
    /**
     * Accessor for the logical name of this Column.<p>
     * <p>
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
    String getName();

    /**
     * Accessor for the <b>actual</b> SQL Column Name.<p>
     * <p>
     * This ColumnName MUST match that declared in the SQL Table Meta-data,
     * as it is used/displayed by the specific <b>WhereClause</b>'s toSQL()
     * method.<p>
     *
     * @return SQL Column Name.<p>
     *
     * @see WhereClause#toSQL()
     */
    String getColumnName();

    /**
     * Return <code>true</code> if the SearchColumn should be a seperate Column.<p>
     * <p>
     *
     * @see WhereClause#toSQL()
     */
    boolean hasSearchColumn();

    /**
     * Accessor for the SQL Column Name that should <b>actually</b> be used for Searching.<p>
     * <p>
     * Normally this has the same value as <code>getColumnName</code>.  If it doesn't, then it
     * indicates that there is a secondary column that needs to be maintained just for searching.<p>
     * <p>
     * This ColumnName MUST match that declared in the SQL Table Meta-data,
     * as it is used/displayed by the specific <b>WhereClause</b>'s toSQL()
     * method.<p>
     *
     * @return SQL Column Name.<p>
     *
     * @see WhereClause#toSQL()
     */
    String getSearchColumnName();

    /**
     * Accessor for the <i>Java</i> Class <i>type</i> of the SQL Column.<p>
     * <p>
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
    Class getColumnType();
}
