// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause.nonpublic;

/**
 * @author George Smith
 * @version 1.0 02/02/02 Initial Version
 */
public class IllegalArgument {
    public static void ifNotNull( String pReferenceLabel, Object pReference ) {
        if ( pReference != null ) {
            throw ofNotNull( pReferenceLabel );
        }
    }

    public static void ifNull( String pReferenceLabel, Object pReference ) {
        if ( pReference == null ) {
            throw ofNull( pReferenceLabel );
        }
    }

    public static IllegalArgumentException ofNotNull( String pReferenceLabel ) {
        return exception( pReferenceLabel, "MUST be 'null'" );
    }

    public static IllegalArgumentException ofNull( String pReferenceLabel ) {
        return exception( pReferenceLabel, "may NOT be 'null'" );
    }

    public static IllegalArgumentException exception( String pReferenceLabel, String pWhy ) {
        return new IllegalArgumentException( pReferenceLabel + " " + pWhy );
    }
}
