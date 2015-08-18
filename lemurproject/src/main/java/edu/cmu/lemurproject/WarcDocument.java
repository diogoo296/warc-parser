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
		this.content = record.getContent();
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
		//doc += "[DOC HEADER] " + documentHeader + "\n";
		//		doc += "[WARC HEADER] " + warcHeader + "\n";
		//		doc += "[TITLE] " + title + "\n";
		doc += "[CONTENT LENGTH] " + getContentLength() + "\n";
		doc += "[CONTENT TYPE] " + getContentType() + "\n";
		doc += "[SERVER] " + getServer() + "\n";

		return doc;
	}

	public String getID() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public String getContent() {
		return content;
	}

	public String getDocHeader() {
		return documentHeader;
	}

	public String getWarcHeader() {
		return warcHeader;
	}

	public String getTitle() {
		return title;
	}

	private String strSubpart(String original, String delimiter) {
		String[] parts = original.split(delimiter);
		return parts[1].split("\r\n")[0];
	}

	public int getContentLength() {
		int length = 0;
		String delimiter = "Content-Length: ";

		if (documentHeader != null && documentHeader.contains(delimiter)) {
			length = Integer.parseInt( strSubpart(documentHeader, delimiter) );
		}

		return length;
	}

	public String getContentType() {
		String type;
		String delimiter = "Content-Type: ";

		if (documentHeader != null && documentHeader.contains(delimiter)) {
			type = strSubpart(documentHeader, delimiter);

			if (type.contains(";")) {
				type = type.split(";")[0];
			}
		}
		else {
			type = "Unknown";
		}

		return type;
	}

	public String getServer() {
		String server;
		String delimiter = "Server: ";

		if (documentHeader != null && documentHeader.contains(delimiter)) {
			server = strSubpart(documentHeader, delimiter);

			if (server.contains("/")) {
				server = server.split("/")[0];
			}
		}
		else {
			server = "Unknown";
		}

		return server;
	}
}
