// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

/**
 * <a href="../../Licence.txt">Licence</a><br>
 */
public enum WhereClauseType {
    TRUE( "TRUE" ), //
    FALSE( "FALSE" ), //
    IS_NULL( "== null", "IS NULL", // . . . . .  Is Null . . . . . . . . Reference
             "!= null", "IS NOT NULL" ),
    EQUALS( "==", "=", // . . . . . . . . . . .  Equals: . . . . . . . . Reference _And Value
            "!=", "<>", "Equals" ),
    LESSTHAN( "<", "<", // . . . . . . . . . . . Less Than:. . . . . . . Reference _And Value
              ">=", ">=", "LessThan" ),
    GREATERTHAN( ">", ">", // .. . . . . . . . . Greater Than: . . . . . Reference _And Value
                 "<=", "<=", "GreaterThan" ),
    BETWEEN( "Between", "BETWEEN", // . . . . .  Between:. . . . . . . . Reference _And Two Values
             "", "NOT BETWEEN" ),
    IS_ANY_OF( "isAnyOf (", "IN (", // . . . . . Is Any Of:. . . . . . . Reference _And N Values
               "isNotAnyOf (", "NOT IN (" ),
    OR( "OR" ), // . . . . . . . . . . . . . . . Or: . . . . . . . . . . Associative List
    AND( "AND" ), // . . . . . . . . . . . . . . And:. . . . . . . . . . Associative List
    NOT( "NOT" ), // . . . . . . . . . . . . . . Not:. . . . . . . . . . 'Not' Wrapper
    CONTAINS( "Does NOT ", "Contains" ), // . .  Contains (String):. . . Reference _And LikeValue
    STARTS_WITH( "Does NOT ", "StartsWith" ), // Starts With (String): . Reference _And LikeValue
    ENDS_WITH( "Does NOT ", "EndsWith" ), // . . Ends With (String): . . Reference _And LikeValue
    LIKE( "Does NOT ", "matches(", "Like" ), //  Like (String[]):. . . . Reference _And LikeValues
    IS_IN( "IsIn (", "IN (", // .. . . . . . . . Is In:. . . . . Reference IsIn (Single Reference Sub-Select)
           "IsNotIn (", "NOT IN (" );

    private String mToStr, mToSql, mToNotStr, mToNotSql, mInputStr;

    WhereClauseType( String pToStr, String pToSql, String pToNotStr, String pToNotSql, String pInputStr ) {
        mToStr = pToStr;
        mToSql = pToSql;
        mToNotStr = pToNotStr;
        mToNotSql = pToNotSql;
        mInputStr = pInputStr;
    }

    WhereClauseType( String pToStr, String pToSql, String pToNotStr, String pToNotSql ) {
        this( pToStr, pToSql, pToNotStr, pToNotSql, pToStr );
    }

    WhereClauseType( String pCommonForm ) {
        this( pCommonForm, pCommonForm, null, null, pCommonForm );
    }

    WhereClauseType( String pNotPrefix, String pToStr, String pToNotSql ) {
        this( pToStr, null, pNotPrefix + pToStr, null, pToNotSql );
    }

    WhereClauseType( String pNotPrefix, String pToStr ) {
        this( pNotPrefix, pToStr, pToStr );
    }

    public String getToStr() {
        return mToStr;
    }

    public String getToSql() {
        return mToSql;
    }

    public String getToNotStr() {
        return mToNotStr;
    }

    public String getToNotSql() {
        return mToNotSql;
    }

    public String getInputStr() {
        return mInputStr;
    }
}
