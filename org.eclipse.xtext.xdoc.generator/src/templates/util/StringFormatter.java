package templates.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.xtext.xdoc.xdoc.Code;
import org.eclipse.xtext.xdoc.xdoc.CodeBlock;
import org.eclipse.xtext.xdoc.xdoc.LangDef;
import org.eclipse.xtext.xdoc.xdoc.Link;

public class StringFormatter {

	public static final String SL_COMMENT_DEFAULT = "//";

	@SuppressWarnings("unused")
	static private HashMap<String, Set<String>> languages = new HashMap<String, Set<String>>();

	static private Set<String> links = new HashSet<String>();

	private static String slComment = "//";
	private static String doubleQuote ="\"";
	private static String singleQuote ="&apos;";
	private static String mlCommentEnd = "*/";
	private static String mlCommentStart = "/*";

	static Map<String, Pattern> patterns;

	static {
		patterns = new HashMap<String, Pattern>();
		patterns.put(slComment, makeHighlightRegionPattern(slComment));
		patterns.put(mlCommentStart, makeHighlightRegionPattern(mlCommentStart));
		patterns.put(mlCommentEnd, makeHighlightRegionPattern(mlCommentEnd));
		patterns.put(singleQuote, makeHighlightRegionPattern(singleQuote));
		patterns.put(doubleQuote, makeHighlightRegionPattern(doubleQuote));
		patterns.put("\n", makeHighlightRegionPattern("\n"));
	}

	private static Pattern makeHighlightRegionPattern(String regionStarter) {
		return Pattern.compile("(?<![^\\\\]\\\\(\\\\\\\\){0," + (Short.MAX_VALUE) + "})\\Q" + regionStarter+"\\E");
	}

	static public final CodeBlock removeIndent(CodeBlock cb) {
		if(cb.getContents().size() > 0 && cb.getContents().get(0) instanceof Code){
			String code = ((Code)cb.getContents().get(0)).getContents();
			int indent = code.length();
			indent -= code.replaceAll("^(\n*)\\s*", "$1").length();
			String string = "\n\\s{"+indent+"}";
			for(int i = 0; i < cb.getContents().size(); i++) {
				if (cb.getContents().get(i) instanceof Code) {
					code = ((Code) cb.getContents().get(i)).getContents();
					if(i == 0) {
						code = code.replaceAll("^\n*", "").replaceAll("^\\s{"+indent+"}", "");
					}
					if(i == cb.getContents().size() - 1){
						code = code.replaceAll("(\\s|\n)*$", "");
					}
					code = code.replaceAll(string, "\n");
					((Code)cb.getContents().get(i)).setContents(code);
				}
			}
		}
		return cb;
	}

	static public String encode(String s){
		try {
			return URLEncoder.encode(s, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String urlDecode(String url) throws UnsupportedEncodingException {
		return URLDecoder.decode(url, "ISO-8859-1");
	}

	static public void storeLink(Link link){
		links.add(link.getUrl());
	}

	static public Set<String> getStoredLinks(){
		return links;
	}

	/**
	 * Highlight keywords in a text.
	 *
	 * @param text a piece of source code
	 * @param langDef the language
	 * @return the string with keywords highlighted
	 */
	static public String highlightKeywords(String text, final LangDef lang){
		if(lang != null && text != null){
			List<String> keywords = lang.getKeywords();
			StringBuilder result = new StringBuilder();
			String[] toks;
			do{
				toks = splitToNext(text);
				result.append(addHighlighting(keywords, toks[0]));
				switch(toks.length){
				case 3:
					text = toks[2];
				case 2:
					result.append(toks[1]);
					break;
				default: // do nothing
					;
				}
			} while(toks.length == 3);
			return result.toString();
		}
		return text;
	}

	public static String[] splitToNext(String input){
		int slC = -1, mlCStart = -1, doubleQuoteStart = -1, singleQuoteStart = -1;
		Matcher matcher;
		if(mlCommentEnd != null) {
			matcher = patterns.get(mlCommentStart).matcher(input);
			if(matcher.find(0))
				mlCStart = matcher.start();
		}
		matcher = patterns.get(slComment).matcher(input);
		if(matcher.find(0))
			slC = matcher.start();
		matcher = patterns.get(doubleQuote).matcher(input);
		if(matcher.find(0))
			doubleQuoteStart = matcher.start();
		matcher = patterns.get(singleQuote).matcher(input);
		if(matcher.find(0))
			singleQuoteStart = matcher.start();
		int min = Integer.MAX_VALUE;
		if(slComment != "" && slC >= 0 && slC < min){
			min = slC;
		}
		if(mlCommentStart != "" && mlCStart >= 0 && mlCStart < min){
			min = mlCStart;
		}
		if(doubleQuoteStart >= 0 && doubleQuoteStart < min){
			min = doubleQuoteStart;
		}
		if(singleQuoteStart >= 0 && singleQuoteStart < min){
			min = singleQuoteStart;
		}
		String[] res;
		if(min < Integer.MAX_VALUE){
			if(min == slC){
				res = breakApart(input, slC, slComment.length(), "\n");
				res[1] = "<span class=\"comment\" >" + res[1] + "</span>";
				return res;
			} else if(min == mlCStart){
				res = breakApart(input, min, mlCommentStart.length(), mlCommentEnd);
				res[1] = "<span class=\"comment\" >" + res[1] + "</span>";
				return res;
			} else if(min == doubleQuoteStart) {
				res = breakApart(input, min, doubleQuote.length(), doubleQuote);
				res[1] = "<span class=\"string\" >" + res[1] + "</span>";
				return res;
			} else if(min == singleQuoteStart) {
				res = breakApart(input, min, singleQuote.length(), singleQuote);
				res[1] = "<span class=\"string\" >" + res[1] + "</span>";
				return res;
			}
		}
		return new String[] {input};
	}

	public static String[] breakApart(String input, int startIndex, int len, String end) {
		String[] ret = { "", "", "" };
		ret[0] = input.substring(0, startIndex);
		ret[1] = input.substring(startIndex);
		Matcher matcher = patterns.get(end).matcher(ret[1]);
		if(matcher.find(len)){
			String rest = input.substring(startIndex);
			// split up and stuff
			ret[1] = rest.substring(0, matcher.end()); // rest.indexOf(end, len) + end.length());
			ret[2] = rest.substring(matcher.end()); //rest.indexOf(end, len) + end.length());
			return ret;
		}
		return new String[]{ret[0], ret[1]};
	}

	private static String addHighlighting(List<String> keywords, String text) {
		for (String keyword : keywords) {
			if(keyword.trim().equals("class")){
				text = text.replaceAll("(?<!<span )" + makeKeywordRegex(keyword.trim()) + "(?!\\=\"keyword\">)",
						"<span class=\"keyword\">" + keyword.trim() + "</span>");
			} else if(keyword.trim().equals("span")){
				text = text.replaceAll("((?<!<)" + makeKeywordRegex(keyword.trim()) + "(?!class\\=\"keyword\">)|(?<!</)span(?!>))",
						"<span class=\"keyword\">" + keyword.trim() + "</span>");
			} else if(keyword.trim().equals("keyword")){
				text = text.replaceAll("(?<!<span class=\")" + makeKeywordRegex(keyword.trim()) + "(?!\">)",
						"<span class=\"keyword\">" + keyword.trim() + "</span>");
			} else {
				text = text.replaceAll(makeKeywordRegex(keyword.trim()), "<span class=\"keyword\">" + keyword.trim() + "</span>");
			}
		}
		return text;
	}

	public static String percentToFloat(String percent){
		return (Float.parseFloat(percent.replaceAll("%", ""))/100) +"";
	}

	private static String makeKeywordRegex(String keyword) {
		return "(?<![\\w])"+keyword+"(?!\\w)";
	}

	public static void clearStoredLinks() {
		links.clear();
	}

}
