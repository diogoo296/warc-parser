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

		System.out.println( "Reading warc records..." );
		System.out.println();

		WarcRecord thisWarcRecord;

		while ((thisWarcRecord = WarcRecord.readNextWarcRecord(inStream)) != null) {
			if (thisWarcRecord.getHeaderRecordType().equals("response")) {
//				warcResponseList.add(new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)));
				System.out.println( new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)) );
			}
		}

		System.out.println();
		System.out.println( "All records read!" );

		inStream.close();

//		return warcResponseList;
	}
}
