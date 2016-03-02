// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.whereclause;

import org.litesoft.whereclause.nonpublic.AbstractColumnDefinition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WhereClauseFactoryTest extends TestCase {
    public static Test suite() {
        return new TestSuite( WhereClauseFactoryTest.class );
    }

    public WhereClauseFactoryTest( String name ) {
        super( name );
    }

    public static void main( String[] args ) {
        junit.textui.TestRunner.run( suite() );
    }

    static class WCtableID implements SimpleFromIdentifier {
        @Override
        public String getTableName() {
            return "TheTable";
        }

        @Override
        public String getIdentifierName() {
            return "zTable";
        }
    }

    static class TestingColumnDefinition extends AbstractColumnDefinition {
        protected TestingColumnDefinition( String pName, boolean pAddSearchColumn, Class pColumnType ) {
            super( pName + "Attr", pName + "Col", pAddSearchColumn ? pName + "Col_SC" : pName + "Col", pColumnType );
        }

        protected TestingColumnDefinition( String pName, Class pColumnType ) {
            super( pName + "Attr", pName + "Col", pColumnType );
        }
    }

    static class CDstring extends TestingColumnDefinition {
        CDstring( String pName, boolean pAddSearchColumn ) {
            super( pName, pAddSearchColumn, String.class );
        }

        CDstring( String pName ) {
            this( pName, false );
        }
    }

    static class CDinteger extends TestingColumnDefinition {
        CDinteger( String pName ) {
            super( pName, Integer.class );
        }
    }

    private WhereClauseFactory F = WhereClauseFactory.INSTANCE;

    private CDstring TheString = new CDstring( "TheStr" );
    private CDstring TheString_SC = new CDstring( "TheStr", true );
    private CDinteger TheInteger = new CDinteger( "TheInt" );
    private CDinteger TheInteger2 = new CDinteger( "TheInt2" );

    private WhereClause wcStrNull = F.isNull( TheString );
    private WhereClause wcStrNotNull = F.isNotNull( TheString );
    private WhereClause wcStrEqual = F.isEqual( TheString, "gas" );
    private WhereClause wcIntEqual = F.isEqual( TheInteger, 5 );
    private WhereClause wcIntEqual2 = F.isEqual( TheInteger2, 6 );
    private WhereClause wcStrNotEqual = F.isNotEqual( TheString, "gas" );
    private WhereClause wcintNotEqual = F.isNotEqual( TheInteger, 5 );

    private SingleColumnSelect zSingleColumnSelect = new SingleColumnSelect( TheInteger, new WCtableID(), wcStrNotNull );

    public void test_isNull() {
        WhereClause wc = F.isNull( TheString );

        assertEquals( "Where TheStrAttr == null", wc.toString() );
        assertEquals( "WHERE TheStrCol IS NULL", wc.toSQL() );

        wc = F.isNull( TheString_SC );

        assertEquals( "Where TheStrAttr == null", wc.toString() );
        assertEquals( "WHERE TheStrCol_SC IS NULL", wc.toSQL() );
    }

    public void test_isNotNull() {
        WhereClause wc = F.isNotNull( TheString );

        assertEquals( "Where TheStrAttr != null", wc.toString() );
        assertEquals( "WHERE TheStrCol IS NOT NULL", wc.toSQL() );
    }

    public void test_isEqual_Null() {
        WhereClause wc = F.isEqual( TheString, null );

        assertEquals( wcStrNull.toString(), wc.toString() );
        assertEquals( wcStrNull.toSQL(), wc.toSQL() );
    }

    public void test_isNotEqual_Null() {
        WhereClause wc = F.isNotEqual( TheString, null );

        assertEquals( wcStrNotNull.toString(), wc.toString() );
        assertEquals( wcStrNotNull.toSQL(), wc.toSQL() );
    }

    public void test_isEqual() {
        WhereClause wc = F.isEqual( TheString, "gas" );

        assertEquals( "Where TheStrAttr == \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol = 'gas'", wc.toSQL() );
    }

    public void test_isNotEqual() {
        WhereClause wc = F.isNotEqual( TheString, "gas" );

        assertEquals( "Where TheStrAttr != \"gas\"", wc.toString() );
        assertEquals( "WHERE (TheStrCol <> 'gas') OR (TheStrCol IS NULL)", wc.toSQL() );
    }

    public void test_isEqual_int() {
        WhereClause wc = F.isEqual( TheInteger, 5 );

        assertEquals( "Where TheIntAttr == 5", wc.toString() );
        assertEquals( "WHERE TheIntCol = 5", wc.toSQL() );
    }

    public void test_isNotEqual_int() {
        WhereClause wc = F.isNotEqual( TheInteger, 5 );

        assertEquals( "Where TheIntAttr != 5", wc.toString() );
        assertEquals( "WHERE (TheIntCol <> 5) OR (TheIntCol IS NULL)", wc.toSQL() );
    }

    public void test_isGreaterThan_Null() {
        expectIllegalArgumentException( () -> F.isGreaterThan( TheString, null ) );
    }

    public void test_isNotGreaterThan_Null() {
        expectIllegalArgumentException( () -> F.isNotGreaterThan( TheString, null ) );
    }

    public void test_isGreaterThan() {
        WhereClause wc = F.isGreaterThan( TheString, "gas" );

        assertEquals( "Where TheStrAttr > \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol > 'gas'", wc.toSQL() );
    }

    public void test_isNotGreaterThan() {
        WhereClause wc = F.isNotGreaterThan( TheString, "gas" );

        assertEquals( "Where TheStrAttr <= \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol <= 'gas'", wc.toSQL() );
    }

    public void test_isGreaterThan_int() {
        WhereClause wc = F.isGreaterThan( TheInteger, 5 );

        assertEquals( "Where TheIntAttr > 5", wc.toString() );
        assertEquals( "WHERE TheIntCol > 5", wc.toSQL() );
    }

    public void test_isNotGreaterThan_int() {
        WhereClause wc = F.isNotGreaterThan( TheInteger, 5 );

        assertEquals( "Where TheIntAttr <= 5", wc.toString() );
        assertEquals( "WHERE TheIntCol <= 5", wc.toSQL() );
    }

    public void test_isLessThan_Null() {
        expectIllegalArgumentException( () -> F.isLessThan( TheString, null ) );
    }

    public void test_isNotLessThan_Null() {
        expectIllegalArgumentException( () -> F.isNotLessThan( TheString, null ) );
    }

    public void test_isLessThan() {
        WhereClause wc = F.isLessThan( TheString, "gas" );

        assertEquals( "Where TheStrAttr < \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol < 'gas'", wc.toSQL() );
    }

    public void test_isNotLessThan() {
        WhereClause wc = F.isNotLessThan( TheString, "gas" );

        assertEquals( "Where TheStrAttr >= \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol >= 'gas'", wc.toSQL() );
    }

    public void test_isLessThan_int() {
        WhereClause wc = F.isLessThan( TheInteger, 5 );

        assertEquals( "Where TheIntAttr < 5", wc.toString() );
        assertEquals( "WHERE TheIntCol < 5", wc.toSQL() );
    }

    public void test_isNotLessThan_int() {
        WhereClause wc = F.isNotLessThan( TheInteger, 5 );

        assertEquals( "Where TheIntAttr >= 5", wc.toString() );
        assertEquals( "WHERE TheIntCol >= 5", wc.toSQL() );
    }

    public void test_isBetween_NullLeft() {
        expectIllegalArgumentException( () -> F.isBetween( TheString, null, "kls" ) );
    }

    public void test_isNotBetween_NullLeft() {
        expectIllegalArgumentException( () -> F.isNotBetween( TheString, null, "kls" ) );
    }

    public void test_isBetween_NullRight() {
        expectIllegalArgumentException( () -> F.isBetween( TheString, "gas", null ) );
    }

    public void test_isNotBetween_NullRight() {
        expectIllegalArgumentException( () -> F.isNotBetween( TheString, "gas", null ) );
    }

    public void test_isBetween_EqualNull() {
        WhereClause wc = F.isBetween( TheString, null, null );

        assertEquals( wcStrNull.toString(), wc.toString() );
        assertEquals( wcStrNull.toSQL(), wc.toSQL() );
    }

    public void test_isNotBetween_EqualNull() {
        WhereClause wc = F.isNotBetween( TheString, null, null );

        assertEquals( wcStrNotNull.toString(), wc.toString() );
        assertEquals( wcStrNotNull.toSQL(), wc.toSQL() );
    }

    public void test_isBetween_Equal() {
        WhereClause wc = F.isBetween( TheString, "gas", "g" + "a" + "s" );

        assertEquals( wcStrEqual.toString(), wc.toString() );
        assertEquals( wcStrEqual.toSQL(), wc.toSQL() );
    }

    public void test_isNotBetween_Equal() {
        WhereClause wc = F.isNotBetween( TheString, "gas", "g" + "a" + "s" );

        assertEquals( wcStrNotEqual.toString(), wc.toString() );
        assertEquals( wcStrNotEqual.toSQL(), wc.toSQL() );
    }

    public void test_isBetween_Equal_int() {
        WhereClause wc = F.isBetween( TheInteger, 5, 5 );

        assertEquals( wcIntEqual.toString(), wc.toString() );
        assertEquals( wcIntEqual.toSQL(), wc.toSQL() );
    }

    public void test_isNotBetween_Equal_int() {
        WhereClause wc = F.isNotBetween( TheInteger, 5, 5 );

        assertEquals( wcintNotEqual.toString(), wc.toString() );
        assertEquals( wcintNotEqual.toSQL(), wc.toSQL() );
    }

    public void test_isBetween() {
        WhereClause wc = F.isBetween( TheString, "gas", "kls" );

        assertEquals( "Where \"gas\" <= TheStrAttr <= \"kls\"", wc.toString() );
        assertEquals( "WHERE TheStrCol BETWEEN 'gas' AND 'kls'", wc.toSQL() );
    }

    public void test_isNotBetween() {
        WhereClause wc = F.isNotBetween( TheString, "gas", "kls" );

        assertEquals( "Where NOT (\"gas\" <= TheStrAttr <= \"kls\")", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT BETWEEN 'gas' AND 'kls'", wc.toSQL() );
    }

    public void test_isBetween_int() {
        WhereClause wc = F.isBetween( TheInteger, 5, 6 );

        assertEquals( "Where 5 <= TheIntAttr <= 6", wc.toString() );
        assertEquals( "WHERE TheIntCol BETWEEN 5 AND 6", wc.toSQL() );
    }

    public void test_isNotBetween_int() {
        WhereClause wc = F.isNotBetween( TheInteger, 5, 6 );

        assertEquals( "Where NOT (5 <= TheIntAttr <= 6)", wc.toString() );
        assertEquals( "WHERE TheIntCol NOT BETWEEN 5 AND 6", wc.toSQL() );
    }

    public void test_endsWith_Null() {
        expectIllegalArgumentException( () -> F.endsWith( TheString, null ) );
    }

    public void test_doesNotEndWith_Null() {
        expectIllegalArgumentException( () -> F.doesNotEndWith( TheString, null ) );
    }

    public void test_endsWith() {
        WhereClause wc = F.endsWith( TheString, "gas" );

        assertEquals( "Where TheStrAttr EndsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE '%gas'", wc.toSQL() );
    }

    public void test_doesNotEndWith() {
        WhereClause wc = F.doesNotEndWith( TheString, "gas" );

        assertEquals( "Where TheStrAttr Does NOT EndsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE '%gas'", wc.toSQL() );
    }

    public void test_startsWith_Null() {
        expectIllegalArgumentException( () -> F.startsWith( TheString, null ) );
    }

    public void test_doesNotStartWith_Null() {
        expectIllegalArgumentException( () -> F.doesNotStartWith( TheString, null ) );
    }

    public void test_startsWith() {
        WhereClause wc = F.startsWith( TheString, "gas" );

        assertEquals( "Where TheStrAttr StartsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'gas%'", wc.toSQL() );
    }

    public void test_doesNotStartWith() {
        WhereClause wc = F.doesNotStartWith( TheString, "gas" );

        assertEquals( "Where TheStrAttr Does NOT StartsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'gas%'", wc.toSQL() );
    }

    public void test_contains_Null() {
        expectIllegalArgumentException( () -> F.contains( TheString, null ) );
    }

    public void test_doesNotContain_Null() {
        expectIllegalArgumentException( () -> F.doesNotContain( TheString, null ) );
    }

    public void test_like_Null() {
        expectIllegalArgumentException( () -> F.like( TheString, (String[]) null ) );
    }

    public void test_isNotLike_Null() {
        expectIllegalArgumentException( () -> F.isNotLike( TheString, (String[]) null ) );
    }

    private void expectIllegalArgumentException( Runnable pRunnable ) {
        try {
            pRunnable.run();
            fail();
        }
        catch ( IllegalArgumentException expected ) {
            // expected
        }
    }

    public void test_like() {
        WhereClause wc = F.like( TheString, "gas" );

        assertEquals( "Where TheStrAttr == \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol = 'gas'", wc.toSQL() );

        wc = F.like( TheString, "gas", "" );

        assertEquals( "Where TheStrAttr StartsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'gas%'", wc.toSQL() );

        wc = F.like( TheString, "", "gas" );

        assertEquals( "Where TheStrAttr EndsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE '%gas'", wc.toSQL() );

        wc = F.like( TheString, "", "gas", "" );

        assertEquals( "Where TheStrAttr Contains \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE '%gas%'", wc.toSQL() );

        wc = F.like( TheString, "kls", "gas" );

        assertEquals( "Where TheStrAttr matches( \"kls\", \"gas\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'kls%gas'", wc.toSQL() );

        wc = F.like( TheString, "kls", "gas", "" );

        assertEquals( "Where TheStrAttr matches( \"kls\", \"gas\", \"\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'kls%gas%'", wc.toSQL() );

        wc = F.like( TheString, "", "gas", "kls" );

        assertEquals( "Where TheStrAttr matches( \"\", \"gas\", \"kls\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE '%gas%kls'", wc.toSQL() );

        wc = F.like( TheString, "as", "gas", "ds" );

        assertEquals( "Where TheStrAttr matches( \"as\", \"gas\", \"ds\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'as%gas%ds'", wc.toSQL() );

        wc = F.like( TheString, "as", "gas", "", "ds" );

        assertEquals( "Where TheStrAttr matches( \"as\", \"gas\", \"ds\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'as%gas%ds'", wc.toSQL() );

        wc = F.like( TheString, "gas", "as", "ds", "bs" );

        assertEquals( "Where TheStrAttr matches( \"gas\", \"as\", \"ds\", \"bs\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE 'gas%as%ds%bs'", wc.toSQL() );
    }

    public void test_isNotLike() {
        WhereClause wc = F.isNotLike( TheString, "gas" );

        assertEquals( "Where TheStrAttr != \"gas\"", wc.toString() );
        assertEquals( "WHERE (TheStrCol <> 'gas') OR (TheStrCol IS NULL)", wc.toSQL() );

        wc = F.isNotLike( TheString, "gas", "" );

        assertEquals( "Where TheStrAttr Does NOT StartsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'gas%'", wc.toSQL() );

        wc = F.isNotLike( TheString, "", "gas" );

        assertEquals( "Where TheStrAttr Does NOT EndsWith \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE '%gas'", wc.toSQL() );

        wc = F.isNotLike( TheString, "", "gas", "" );

        assertEquals( "Where TheStrAttr Does NOT Contains \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE '%gas%'", wc.toSQL() );

        wc = F.isNotLike( TheString, "kls", "gas" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"kls\", \"gas\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'kls%gas'", wc.toSQL() );

        wc = F.isNotLike( TheString, "kls", "gas", "" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"kls\", \"gas\", \"\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'kls%gas%'", wc.toSQL() );

        wc = F.isNotLike( TheString, "", "gas", "kls" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"\", \"gas\", \"kls\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE '%gas%kls'", wc.toSQL() );

        wc = F.isNotLike( TheString, "as", "gas", "ds" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"as\", \"gas\", \"ds\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'as%gas%ds'", wc.toSQL() );

        wc = F.isNotLike( TheString, "as", "gas", "", "ds" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"as\", \"gas\", \"ds\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'as%gas%ds'", wc.toSQL() );

        wc = F.isNotLike( TheString, "gas", "as", "ds", "bs" );

        assertEquals( "Where TheStrAttr Does NOT matches( \"gas\", \"as\", \"ds\", \"bs\" )", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE 'gas%as%ds%bs'", wc.toSQL() );
    }

    public void test_contains() {
        WhereClause wc = F.contains( TheString, "gas" );

        assertEquals( "Where TheStrAttr Contains \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol LIKE '%gas%'", wc.toSQL() );
    }

    public void test_doesNotContain() {
        WhereClause wc = F.doesNotContain( TheString, "gas" );

        assertEquals( "Where TheStrAttr Does NOT Contains \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol NOT LIKE '%gas%'", wc.toSQL() );
    }

    public void test_alwaysTrue() {
        WhereClause wc = F.alwaysTrue();

        assertEquals( "Where TRUE", wc.toString() );
        assertEquals( "WHERE TRUE", wc.toSQL() );
    }

    public void test_alwaysFalse() {
        WhereClause wc = F.alwaysFalse();

        assertEquals( "Where FALSE", wc.toString() );
        assertEquals( "WHERE FALSE", wc.toSQL() );
    }

    public void test_andTrue() {
        WhereClause wc = F.and( wcStrEqual, F.alwaysTrue() );

        assertEquals( "Where TheStrAttr == \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol = 'gas'", wc.toSQL() );
    }

    public void test_andFalse() {
        WhereClause wc = F.and( wcStrEqual, F.alwaysFalse() );

        assertEquals( "Where FALSE", wc.toString() );
        assertEquals( "WHERE FALSE", wc.toSQL() );
    }

    public void test_orTrue() {
        WhereClause wc = F.or( wcStrEqual, F.alwaysTrue() );

        assertEquals( "Where TRUE", wc.toString() );
        assertEquals( "WHERE TRUE", wc.toSQL() );
    }

    public void test_orFalse() {
        WhereClause wc = F.or( wcStrEqual, F.alwaysFalse() );

        assertEquals( "Where TheStrAttr == \"gas\"", wc.toString() );
        assertEquals( "WHERE TheStrCol = 'gas'", wc.toSQL() );
    }

    public void test_and() {
        WhereClause wc = F.and( wcStrEqual, wcIntEqual );

        assertEquals( "Where (TheStrAttr == \"gas\") AND (TheIntAttr == 5)", wc.toString() );
        assertEquals( "WHERE (TheStrCol = 'gas') AND (TheIntCol = 5)", wc.toSQL() );
    }

    public void test_and_and() {
        WhereClause wc = F.and( wcStrEqual, F.and( wcIntEqual, wcIntEqual2 ) );

        assertEquals( "Where (TheStrAttr == \"gas\") AND (TheIntAttr == 5) AND (TheInt2Attr == 6)", wc.toString() );
        assertEquals( "WHERE (TheStrCol = 'gas') AND (TheIntCol = 5) AND (TheInt2Col = 6)", wc.toSQL() );
    }

    public void test_not_and() {
        WhereClause wc = F.not( F.and( wcStrEqual, wcIntEqual ) );

        assertEquals( "Where NOT ((TheStrAttr == \"gas\") AND (TheIntAttr == 5))", wc.toString() );
        assertEquals( "WHERE NOT ((TheStrCol = 'gas') AND (TheIntCol = 5))", wc.toSQL() );
    }

    public void test_or() {
        WhereClause wc = F.or( wcStrEqual, wcIntEqual );

        assertEquals( "Where (TheStrAttr == \"gas\") OR (TheIntAttr == 5)", wc.toString() );
        assertEquals( "WHERE (TheStrCol = 'gas') OR (TheIntCol = 5)", wc.toSQL() );
    }

    public void test_or_or() {
        WhereClause wc = F.or( wcStrEqual, F.or( wcIntEqual, wcIntEqual2 ) );

        assertEquals( "Where (TheStrAttr == \"gas\") OR (TheIntAttr == 5) OR (TheInt2Attr == 6)", wc.toString() );
        assertEquals( "WHERE (TheStrCol = 'gas') OR (TheIntCol = 5) OR (TheInt2Col = 6)", wc.toSQL() );
    }

    public void test_and_or() {
        WhereClause wc = F.and( wcStrEqual, F.or( wcIntEqual, wcIntEqual2 ) );

        assertEquals( "Where (TheStrAttr == \"gas\") AND ((TheIntAttr == 5) OR (TheInt2Attr == 6))", wc.toString() );
        assertEquals( "WHERE (TheStrCol = 'gas') AND ((TheIntCol = 5) OR (TheInt2Col = 6))", wc.toSQL() );
    }

    public void test_isIn() {
        WhereClause wc = F.isIn( TheInteger2, zSingleColumnSelect );

        assertEquals( "Where TheInt2Attr IsIn (Select TheIntAttr From zTable Where TheStrAttr != null)", wc.toString() );
        assertEquals( "WHERE TheInt2Col IN (SELECT TheIntCol FROM TheTable WHERE TheStrCol IS NOT NULL)", wc.toSQL() );
    }

    public void test_isNotIn() {
        WhereClause wc = F.isNotIn( TheInteger2, zSingleColumnSelect );

        assertEquals( "Where TheInt2Attr IsNotIn (Select TheIntAttr From zTable Where TheStrAttr != null)", wc.toString() );
        assertEquals( "WHERE TheInt2Col NOT IN (SELECT TheIntCol FROM TheTable WHERE TheStrCol IS NOT NULL)", wc.toSQL() );
    }

    public void test_isAnyOf() {
        WhereClause wc = F.isAnyOf( TheInteger2 );

        assertEquals( "Where FALSE", wc.toString() );
        assertEquals( "WHERE FALSE", wc.toSQL() );

        wc = F.isAnyOf( TheInteger2, "gas" );

        assertEquals( "Where TheInt2Attr == gas", wc.toString() );
        assertEquals( "WHERE TheInt2Col = gas", wc.toSQL() );

        wc = F.isAnyOf( TheInteger2, "gas", "drs" );

        assertEquals( "Where TheInt2Attr isAnyOf (gas,drs)", wc.toString() );
        assertEquals( "WHERE TheInt2Col IN (gas,drs)", wc.toSQL() );
    }

    public void test_isNotAnyOf() {
        WhereClause wc = F.isNotAnyOf( TheInteger2 );

        assertEquals( "Where TRUE", wc.toString() );
        assertEquals( "WHERE TRUE", wc.toSQL() );

        wc = F.isNotAnyOf( TheInteger2, "gas" );

        assertEquals( "Where TheInt2Attr != gas", wc.toString() );
        assertEquals( "WHERE (TheInt2Col <> gas) OR (TheInt2Col IS NULL)", wc.toSQL() );

        wc = F.isNotAnyOf( TheInteger2, "gas", "drs" );

        assertEquals( "Where TheInt2Attr isNotAnyOf (gas,drs)", wc.toString() );
        assertEquals( "WHERE TheInt2Col NOT IN (gas,drs)", wc.toSQL() );
    }
}
