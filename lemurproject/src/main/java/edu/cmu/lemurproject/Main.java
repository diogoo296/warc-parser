package edu.cmu.lemurproject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			File folder = new File(args[0]);
			File[] files = folder.listFiles();

			for (File file : files) {
				System.out.println("Reading file " + file.getCanonicalPath());
				WarcHelper.readFile(file.getCanonicalPath());

				/*
				List<WarcDocument> docs = WarcHelper.readFile("/var/scratch/diogomarques/crawler-govbr/sample.warc.gz");

				for(WarcDocument doc : docs) {
					System.out.println(doc);
				}
				*/
			}
		}
		catch (IOException e) {
			System.out.println("Problem reading file!");
		}
	}
}
