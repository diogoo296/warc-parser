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
				CustomWarcHTMLResponseRecord record = new CustomWarcHTMLResponseRecord(thisWarcRecord); 
//				warcResponseList.add(new WarcDocument(new CustomWarcHTMLResponseRecord(thisWarcRecord)));
				if (!record.isDNS()) {
					WarcDocument doc = new WarcDocument(record);
					db.insert(doc);
					System.out.println("Webpage #" + (count+1) + ": " + doc.getURL());
					if (++count == 1000) break;
					//System.out.println(new WarcDocument(record));
				}
			}
		}

		db.disconnect();

		System.out.println();
		System.out.println("All records read!");

		inStream.close();

//		return warcResponseList;
	}
}
