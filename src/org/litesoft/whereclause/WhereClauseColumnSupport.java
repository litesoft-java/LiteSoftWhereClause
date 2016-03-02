// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

import org.litesoft.whereclause.nonpublic.WhereClauseToSQLable;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Utility class with a number of methods to help with the formatting of <i>WhereClause</i>s.<p>
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

public class WhereClauseColumnSupport {
    private static final Class STRING_CLASS = String.class;
    private static final Class DATE_CLASS = java.util.Date.class;
    private static final Class SQLDATE_CLASS = Date.class;
    private static final Class TIMESTAMP_CLASS = Timestamp.class;

    private static final Class[] QUOTEDS = {STRING_CLASS, DATE_CLASS, SQLDATE_CLASS, TIMESTAMP_CLASS,};

    private static final char SQL_ANY_STRING = '%';
    private static final char SQL_ANY_CHAR = '_';
    private static final char SQL_QUOTE = '\'';
    // Note: the String below does NOT include the three values above!
    private static final String SQL_POSSIBLE_ESCAPE_CHARS = "|~^#!@$&*+-)(}{][;:?><.,abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private WhereClauseColumnSupport() {
    }

    /**
     * Method to add a Quote Safe String, for a SQL WHERE clause, to a StringBuilder.<p>
     *
     * @param pSB    Appending to buffer (!null).
     * @param pValue String to process (append) (!null).
     */
    public static void makeSqlQuoteSafe( StringBuilder pSB, String pValue ) {
        for ( int i = 0; i < pValue.length(); i++ ) {
            char c = pValue.charAt( i );
            if ( (c == SQL_QUOTE) || (c == '\\') ) {
                pSB.append( c ); // Double It Up
            }
            pSB.append( c );
        }
    }

    /**
     * Method to add a Value (Object), for a SQL value.<p>
     *
     * @param pColumnDefinition Column Definition to deterine Column Type (!null).
     * @param pSearching        Should adjust value for Search?
     * @param pValue            Object to process (append) (!null).
     */
    public static String makeSqlValue( SimpleColumnDefinition pColumnDefinition, boolean pSearching, Object pValue ) {
        StringBuilder sb = new StringBuilder();
        makeSqlValue( sb, pColumnDefinition, pSearching, pValue );
        return sb.toString();
    }

    /**
     * Method to add a Value (Object), for a SQL WHERE clause, to a StringBuilder.<p>
     *
     * @param pSB               Appending to buffer (!null).
     * @param pColumnDefinition Column Definition to deterine Column Type (!null).
     * @param pSearching        Should adjust value for Search?
     * @param pValue            Object to process (append) (!null).
     */
    public static void makeSqlValue( StringBuilder pSB, SimpleColumnDefinition pColumnDefinition, boolean pSearching, Object pValue ) {
        if ( pValue == null ) {
            pSB.append( "null" );
        } else {
            if ( pValue instanceof WhereClauseToSQLable ) {
                pValue = ((WhereClauseToSQLable) pValue).toSqlValueForEquals();
            } else if ( pValue instanceof SQLvalueable ) {
                pValue = ((SQLvalueable) pValue).toSQLvalue();
            }
            String strValue = adjustType( pColumnDefinition, pValue ).toString();
            if ( pSearching && pColumnDefinition.hasSearchColumn() && (strValue != null) ) {
                strValue = strValue.toLowerCase();
            }
            if ( shouldQuote( pColumnDefinition, strValue ) ) {
                pSB.append( "'" );
                makeSqlQuoteSafe( pSB, strValue );
                pSB.append( "'" );
            } else {
                pSB.append( strValue );
            }
        }
    }

    private static Object adjustType( SimpleColumnDefinition pColumnDefinition, Object pValue ) {
        if ( pValue.getClass() == java.util.Date.class ) {
            Class zColumnType = pColumnDefinition.getColumnType();
            if ( zColumnType != null ) {
                java.util.Date zDate = (java.util.Date) pValue;
                if ( java.sql.Timestamp.class.isAssignableFrom( zColumnType ) || SQLtimestampable.class.isAssignableFrom( zColumnType ) ) {
                    return new java.sql.Timestamp( zDate.getTime() );
                }
                if ( java.sql.Date.class.isAssignableFrom( zColumnType ) || SQLdateable.class.isAssignableFrom( zColumnType ) ) {
                    return new java.sql.Date( zDate.getTime() );
                }
                if ( java.sql.Time.class.isAssignableFrom( zColumnType ) || SQLtimeable.class.isAssignableFrom( zColumnType ) ) {
                    return new java.sql.Time( zDate.getTime() );
                }
            }
        }
        return pValue;
    }

    /**
     * Method to add a Value (Object), for a WhereClause's toString() method, to a StringBuilder.<p>
     *
     * @param pSB               Appending to buffer (!null).
     * @param pColumnDefinition Column Definition to deterine Column Type (!null).
     * @param pValue            Object to process (append) (!null).
     */
    public static void makeStringValue( StringBuilder pSB, SimpleColumnDefinition pColumnDefinition, Object pValue ) {
        if ( pValue == null ) {
            pSB.append( "null" );
        } else {
            String strValue = adjustType( pColumnDefinition, pValue ).toString();
            if ( shouldQuote( pColumnDefinition, strValue ) ) {
                pSB.append( '"' );
                makeStringQuoteSafe( pSB, strValue );
                pSB.append( '"' );
            } else {
                pSB.append( strValue );
            }
        }
    }

    private static boolean shouldQuote( SimpleColumnDefinition pColumnDefinition, String pValue ) {
        Class type = pColumnDefinition.getColumnType();
        for ( Class quoted : QUOTEDS ) {
            if ( quoted == type ) {
                return true;
            }
        }
        return !isUnquotedSafe( pValue, ((pValue.length() > 1) && (pValue.charAt( 1 ) == '-')) ? 1 : 0 );
    }

    private static boolean isUnquotedSafe( String pValue, int pfrom ) {
        while ( pfrom < pValue.length() ) {
            if ( !isUnquotedSafe( pValue.charAt( pfrom++ ) ) ) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUnquotedSafe( char c ) {
        return (('0' <= c) && (c <= '9')) || (c == '.') || (('A' <= c) && (c <= 'Z')) || (('a' <= c) && (c <= 'z'));
    }

    /**
     * Method to add a Quote Safe String, for a WhereClause's toString() method, to a StringBuilder.<p>
     *
     * @param pSB    Appending to buffer (!null).
     * @param pValue String to process (append) (!null).
     */
    public static void makeStringQuoteSafe( StringBuilder pSB, String pValue ) {
        for ( int i = 0; i < pValue.length(); i++ ) {
            char c = pValue.charAt( i );
            if ( (c == '"') || (c == '\\') ) {
                pSB.append( '\\' );
            }

            pSB.append( c );
        }
    }

    /**
     * Method to add a String, for a SQL WHERE LIKE clause, to a StringBuilder.<p>
     *
     * @param pQuoteIt         Quote the result? (and double it up if found).
     * @param pLowerCaseValues true indicates that all the pValues should be lowercased before being added to the Like object
     * @param pValues          Strings to process (append) (!null).
     *
     * @throws IllegalStateException if NO acceptable SQL ESCAPE character can be determined.
     */
    public static Like makeSqlLikeValue( boolean pQuoteIt, boolean pLowerCaseValues, String... pValues )
            throws IllegalStateException {
        return new Like( pLowerCaseValues, pValues, getEscapeCode( pValues ), pQuoteIt ? SQL_QUOTE : 0 );
    }

    /**
     * Method to determine a <i>safe</i> SQL ESCAPE character to use with a
     * SQL WHERE LIKE clause.<p>
     *
     * @param pValues Strings to process (append) (!null).<p>
     *
     * @return Character acceptable as an SQL ESCAPE character.<p>
     *
     * @throws IllegalStateException if NO acceptable character can be determined.
     */
    protected static char getEscapeCode( String[] pValues )
            throws IllegalStateException {
        if ( !containsChar( pValues, SQL_ANY_CHAR ) && !containsChar( pValues, SQL_ANY_STRING ) ) {
            return 0;
        }

        String possibles = SQL_POSSIBLE_ESCAPE_CHARS;
        for ( int i = 0; i < possibles.length(); i++ ) {
            char c = possibles.charAt( i );
            if ( !containsChar( pValues, c ) ) {
                return c;
            }
        }
        throw new IllegalStateException( "Unable to determine SQL Escape Code" );
    }

    private static boolean containsChar( String[] pValues, char pChar ) {
        for ( String value : pValues ) {
            if ( value.indexOf( pChar ) != -1 ) {
                return true;
            }
        }
        return false;
    }

    public static class Like {
        private StringBuilder zSB = new StringBuilder();
        private char zEscapeCode;
        private char zQuoteWith;

        public Like( boolean pLowerCaseValues, String[] pValues, char pEscapeCode, char pQuoteWith ) {
            zEscapeCode = pEscapeCode;
            zQuoteWith = pQuoteWith;
            appendValues( pLowerCaseValues, pValues );
        }

        public String getLikeString() {
            return zSB.toString();
        }

        public boolean hasEscapeCode() {
            return (zEscapeCode != 0);
        }

        public char getEscapeCode() {
            return zEscapeCode;
        }

        public String getEscapeClause() {
            return !hasEscapeCode() ? "" : " ESCAPE '" + getEscapeCode() + "'";
        }

        @Override
        public String toString() {
            return getLikeString() + getEscapeClause();
        }

        private void appendValues( boolean pLowerCaseValues, String[] pValues ) {
            quoteIt();
            int ndx = 0;
            appendSql( pLowerCaseValues, pValues[ndx++] );
            zSB.append( SQL_ANY_STRING );
            while ( ndx < (pValues.length - 1) ) {
                String value = pValues[ndx++];
                if ( (value != null) && (value.length() != 0) ) {
                    appendSql( pLowerCaseValues, value );
                    zSB.append( SQL_ANY_STRING );
                }
            }
            appendSql( pLowerCaseValues, pValues[ndx] );
            quoteIt();
        }

        private void appendSql( boolean pLowerCaseValue, String pValue ) {
            if ( pLowerCaseValue ) {
                pValue = pValue.toLowerCase();
            }
            for ( int i = 0; i < pValue.length(); i++ ) {
                char c = pValue.charAt( i );

                if ( (c == SQL_ANY_STRING) || (c == SQL_ANY_CHAR) ) {
                    zSB.append( zEscapeCode );
                } else if ( c == zQuoteWith ) {
                    zSB.append( c ); // Double It Up
                }

                zSB.append( c );
            }
        }

        private void quoteIt() {
            if ( zQuoteWith != 0 ) {
                zSB.append( zQuoteWith );
            }
        }
    }
}
