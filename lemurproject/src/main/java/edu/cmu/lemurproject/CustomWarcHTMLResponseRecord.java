package edu.cmu.lemurproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomWarcHTMLResponseRecord extends WarcHTMLResponseRecord {

	private static Pattern HTML_TITLE = Pattern.compile("<title[>]*>(.*?)</title>");
	private static Pattern DOCUMENT_HEADER = Pattern.compile("^(.+?)(?:\\s+)?([\\s\\S]+?)\r?\n(?=\r?\n)", Pattern.UNIX_LINES);
	private static Pattern HTML_CONTENT = Pattern.compile("(?<=((?<!.)[\r\n]))(?s).*", Pattern.UNIX_LINES);

	private final int MAX_CONTENT_LENGTH = 30000000;

	public CustomWarcHTMLResponseRecord(WarcRecord o) {
		super(o);
	}

	public boolean isOrgBr() {
		Pattern pattern = Pattern.compile("(\\S)*.gov.br(\\S)*");
		Matcher matcher = pattern.matcher(getTargetURI());
		return matcher.find();
	}

	public boolean isYoutube() {
		Pattern pattern = Pattern.compile("(\\S)*.youtube.com(\\S)*");
		Matcher matcher = pattern.matcher(getTargetURI());
		return matcher.find();
	}

	public String getWarcHeader() {
		return getRawRecord().getHeaderString();
	}

	public String getTitle() {
		Pattern pattern = HTML_TITLE;
		Matcher matcher = pattern.matcher(getRawRecord().getContentUTF8());
		if (matcher.find()) {
			String title = matcher.group();
			return title.replaceAll("</?title>", " ").replaceAll("/s+/", " ").trim();
		}
		return null;
	}

	public String getDocumentHeader() {
		Pattern pattern = DOCUMENT_HEADER;
		Matcher matcher = pattern.matcher(getRawRecord().getContentUTF8());
		if (matcher.find())
			return matcher.group().trim();

		return null;
	}

	public String getContent() {
		Pattern pattern = HTML_CONTENT;
		Matcher matcher = pattern.matcher(getRawRecord().getContentUTF8());

		if (getRawRecord().getContentUTF8().length() <= MAX_CONTENT_LENGTH
				&& matcher.find()) {
			return matcher.group().trim();
		}
		else {
			return null;
		}
	}
}
