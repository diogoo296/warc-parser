package edu.cmu.lemurproject;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class WarcHelper {

	//public static List<WarcDocument> readFile(String inputWarcFile) throws IOException {
	public static void readFile(String inputWarcFile) throws IOException {

//		List<WarcDocument> warcResponseList = new ArrayList<WarcDocument>();

		// open our gzip input stream
		GZIPInputStream gzInputStream = new GZIPInputStream(new FileInputStream(inputWarcFile));
		DataInputStream inStream = new DataInputStream(gzInputStream);

		System.out.println("Reading warc records...");
		System.out.println();

		WarcRecord thisWarcRecord;
		Database db = new Database();
		int count = 0;

		while ((thisWarcRecord = WarcRecord.readNextWarcRecord(inStream)) != null) {
			if (thisWarcRecord.getHeaderRecordType().equals("response")) {
				db.insert( new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)) );

				System.out.println("Register #" + (count+1) + " saved in DB!");
				if( ++count == 1000 ) break;
//				warcResponseList.add(new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)));
//				if( !record.isOrgBr() && !record.isYoutube() ) {
//				System.out.println(new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)));
//				}
			}
		}

		db.disconnect();

		System.out.println();
		System.out.println("All records read!");

		inStream.close();

//		return warcResponseList;
	}
}
