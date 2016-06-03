import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSSReader {
	private StringBuilder contentBuilder;
	private String content;
	
	public CSSReader(String html) {
		contentBuilder = new StringBuilder(html);
		ArrayList<String> paths = getFiles();
		if(paths.size() > 0) {
			String style = getStyle(paths);
			insertStyle(style);
		}
		content = contentBuilder.toString();		
	}
	
	public ArrayList<String> getFiles() {
		ArrayList<String> paths = new ArrayList<String>();
		boolean done = false;
		int position = 0, hrefS = 0, hrefE = 0;
		while(!done) {
			if((position = contentBuilder.indexOf("<link", position)) == -1) {
				done = true;
			} else {
				hrefS = contentBuilder.indexOf("href=\"", position);
				hrefE = contentBuilder.indexOf("\"", hrefS+6);
				paths.add(contentBuilder.substring(hrefS+6, hrefE));
				int tag1 = contentBuilder.indexOf("/>", hrefE);
				int tag2 = contentBuilder.indexOf("</link>", hrefE);
				if(tag2 == -1 || tag1 < tag2) {
					// remove <link to />
					contentBuilder.delete(position, tag1 + 2);
				} else {
					// remove <link to </link>
					contentBuilder.delete(position, tag2 + 7);
				}
			}
		}
		return paths;
	}
	
	public String getStyle(ArrayList<String> paths) {
		String str, style;
		StringBuilder styleBuilder = new StringBuilder();
		styleBuilder.append("<style>");
		for(int i = 0; i < paths.size(); i++) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(paths.get(i)));
				while((str = in.readLine()) != null) {
					styleBuilder.append(str);
				}
				in.close();
			} catch (IOException e) {}
		}
		styleBuilder.append("</style>");
		style = styleBuilder.toString();
		return style;
	}

	// insert style under head
	public void insertStyle(String style) {	
		int insert = contentBuilder.indexOf("<head>") + 6;
		contentBuilder.insert(insert, style);
	}
	
	public String getContent() {
		return content;
	}
}
