package edu.cmu.lemurproject;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main( String[] args ) {
		try {
			WarcHelper.readFile( "/var/scratch/diogomarques/crawler-govbr/sample.warc.gz" );

			/*
			List<WarcDocument> docs = WarcHelper.readFile( "/var/scratch/diogomarques/crawler-govbr/sample.warc.gz" );

			for( WarcDocument doc : docs ) {
				System.out.println( doc );
			}
			*/
		}
		catch( IOException e ) {
			System.out.println( "Problem reading file!" );
		}
	}
}
