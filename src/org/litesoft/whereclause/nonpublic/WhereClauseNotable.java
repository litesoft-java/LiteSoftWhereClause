// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

/**
 * An interface that a WhereClause implements if it supports Notability.<p>
 * <a href="../../../Licence.txt">Licence</a><br>
 * <p>
 * A WhereClause is Notable, if it can adjust its internal representation
 * to support both a <i>regular</i> and a <i>NOT</i> mode.  When the
 * WhereClauseFactory is asked to <i>NOT</i> a WhereClause it does one
 * of two things:
 * <p>
 * <ul>             Wrap the WhereClause in a <b>NOT</b> expression, or</ul>
 * <p>
 * <ul>             Tell the WhereClause to toggle (switchNot) it's NOT mode.</ul>
 * <p>
 * The determining factor is <i>if</i> the WhereClause implements this
 * interface.<p>
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

public interface WhereClauseNotable {
    /**
     * Accessor for the <b>NOT</b> flag.<p>
     *
     * @return if this WhereClause is currently in the <b>NOT</b> mode.
     */
    boolean isNot();

    /**
     * Toggle (or switch) this WhereClause's <b>NOT</b> mode.<p>
     */
    void switchNot();
}
