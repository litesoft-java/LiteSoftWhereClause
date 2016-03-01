// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

import org.litesoft.whereclause.*;

/**
 * <a href="../../../Licence.txt">Licence</a><br>
 */
public interface WCtoSqlHelper {
    /**
     * This method is called before the regular toSqlHelper() method executes.  It may either augment
     * the String being built and then return false, or return true in which case the regular toSqlHelper()
     * method is NOT executed!
     *
     * @param pWC the WhereClause to be 'Helped'
     * @param pSB the String being built
     *
     * @return true means do NOT continue processing this node and its children, false means continue normally
     */
    public boolean preRender( WhereClause pWC, StringBuilder pSB );

    /**
     * If the preRender() method returned false, then this method is called in case additional (post regular
     * toSqlHelper() method execution) augmentation is desired.
     *
     * @param pWC the WhereClause to be 'Helped'
     * @param pSB the String being built
     */
    public void postRender( WhereClause pWC, StringBuilder pSB );

    /**
     * This method is called before the regular toSqlHelper() method executes.  It may either augment
     * the String being built and then return false, or return true in which case the regular toSqlHelper()
     * method is NOT executed!
     *
     * @param pSCS the SingleColumnSelect to be 'Helped'
     * @param pSB  the String being built
     *
     * @return true means do NOT continue processing this node and its children, false means continue normally
     */
    public boolean preRender( SingleColumnSelect pSCS, StringBuilder pSB );

    /**
     * If the preRender() method returned false, then this method is called in case additional (post regular
     * toSqlHelper() method execution) augmentation is desired.
     *
     * @param pSCS the SingleColumnSelect to be 'Helped'
     * @param pSB  the String being built
     */
    public void postRender( SingleColumnSelect pSCS, StringBuilder pSB );

    public static final WCtoSqlHelper NULL = new WCtoSqlHelper() {
        @Override
        public boolean preRender( WhereClause pWC, StringBuilder pSB ) {
            return false;
        }

        @Override
        public void postRender( WhereClause pWC, StringBuilder pSB ) {
        }

        @Override
        public boolean preRender( SingleColumnSelect pSCS, StringBuilder pSB ) {
            return false;
        }

        @Override
        public void postRender( SingleColumnSelect pSCS, StringBuilder pSB ) {
        }
    };
}
