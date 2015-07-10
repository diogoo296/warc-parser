package edu.cmu.lemurproject;

public class WarcDocument {
	private String id;
	private String url;
	private String content;
	private String documentHeader;
	private String warcHeader;
	private String title;

	public WarcDocument(CustomWarcHTMLResponseRecord record) {
		this.id = record.getTargetTrecID();
		this.url = record.getTargetURI();
//		this.content = record.getContent();
		this.documentHeader = record.getDocumentHeader();
		this.warcHeader = record.getWarcHeader();
		this.title = record.getTitle();
	}

	@Override
	public String toString() {
		String doc = "";

//		doc += "[ID] " + id + "\n";
		doc += "[URL] "	+ url + "\n";
//		doc += "[CONTENT] " + content + "\n";
//		doc += "[DOC HEADER] " + documentHeader + "\n";
//		doc += "[WARC HEADER] " + warcHeader + "\n";
		doc += "[TITLE] " + title + "\n";

		return doc;
	}
}
