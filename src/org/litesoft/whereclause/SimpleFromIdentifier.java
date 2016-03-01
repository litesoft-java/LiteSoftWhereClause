// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

/**
 * An interface to provide a SQL Table Name for a SQL <i>WHERE</i> clause.<p>
 * <a href="../../Licence.txt">Licence</a><br>
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

public interface SimpleFromIdentifier {
    /**
     * Accessor for the SQL Table Name.<p>
     *
     * @return SQL Table Name (!null).
     */
    public String getTableName();

    /**
     * Accessor for the Identifier Name.<p>
     *
     * @return Identifier Name (!null).
     */
    public String getIdentifierName();
}
