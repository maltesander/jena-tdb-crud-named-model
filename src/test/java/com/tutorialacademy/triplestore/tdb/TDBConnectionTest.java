package com.tutorialacademy.triplestore.tdb;

import java.util.List;

import junit.framework.TestCase;

import com.hp.hpl.jena.rdf.model.Statement;

/**
 * Unit test for TDBConnection
 */
public class TDBConnectionTest extends TestCase 
{
	protected TDBConnection tdb = null;
	
	protected String URI = "http://tutorial-academy.com/2015/tdb#";
	
	protected String namedModel1 = "Model_German_Cars";
	protected String namedModel2 = "Model_US_Cars";
	
	protected String john = URI + "John";
	protected String mike = URI + "Mike";
	protected String bill = URI + "Bill";
	protected String owns = URI + "owns";
	
	
	protected void setUp()
	{
		tdb = new TDBConnection("tdb");
	}
	
	public void testAll()
	{
		// named Model 1
		tdb.addStatement( namedModel1, john, owns, URI + "Porsche" );
		tdb.addStatement( namedModel1, john, owns, URI + "BMW" );
		tdb.addStatement( namedModel1, mike, owns, URI + "BMW" );
		tdb.addStatement( namedModel1, bill, owns, URI + "Audi" );
		tdb.addStatement( namedModel1, bill, owns, URI + "BMW" );
		
		// named Model 2
		tdb.addStatement( namedModel2, john, owns, URI + "Chrysler" );
		tdb.addStatement( namedModel2, john, owns, URI + "Ford" );
		tdb.addStatement( namedModel2, bill, owns, URI + "Chevrolet" );
		
		// null = wildcard search. Matches everything with BMW as object!
		List<Statement> result = tdb.getStatements( namedModel1, null, null, URI + "BMW");
		System.out.println( namedModel1 + " size: " + result.size() + "\n\t" + result );
		assertTrue( result.size() > 0);
		
		// null = wildcard search. Matches everything with john as subject!
		result = tdb.getStatements( namedModel2, john, null, null);
		System.out.println( namedModel2 + " size: " + result.size() + "\n\t" + result );
		assertTrue( result.size() == 2 );
		
		// remove all statements from namedModel1
		tdb.removeStatement( namedModel1, john, owns, URI + "Porsche" );
		tdb.removeStatement( namedModel1, john, owns, URI + "BMW" );
		tdb.removeStatement( namedModel1, mike, owns, URI + "BMW" );
		tdb.removeStatement( namedModel1, bill, owns, URI + "Audi" );
		tdb.removeStatement( namedModel1, bill, owns, URI + "BMW" );
		
		result = tdb.getStatements( namedModel1, URI + "John Doe", null, null);
		assertTrue( result.size() == 0);
		
		tdb.close();
	}
}
