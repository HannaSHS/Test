import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSReader {
	private StringBuilder contentBuilder;
	private String content;
	private ArrayList<Integer> scriptPositions;
	
	public JSReader(String html) {
		
		content = html; // get HTML
		ArrayList<String> paths = getFiles(); // 
		if(paths.size() > 0) {
			ArrayList<String> js = getJS(paths);
			content = insertJS(js);
		}
	}
	
	public ArrayList<String> getFiles() {
		ArrayList<String> paths = new ArrayList<String>();
		int position = 0, srcS = 0, srcE = 0;
		boolean done = false;
		StringBuilder contentBuilder = new StringBuilder(content);
		while(!done) { // while end of html not reached
			if((position = contentBuilder.indexOf("<script", position)) == -1) { // find next script tag from starting position
				done = true; // if no more script tags, set done to true
			} else {
				// extract src link, add to paths, save position
				scriptPositions.add(position);
				srcS = content.indexOf("src=\"", position);
				srcE = content.indexOf("\"", srcE);
				paths.add(content.substring(srcS+4, srcE));
				// get end of script tag
				int tag1 = content.indexOf("/>", srcE), tag2 = content.indexOf("</script>", srcE);;
				if(tag2 == -1 || tag1 < tag2) {
					// remove <script to />
					content = content.substring(0, position) + content.substring(content.indexOf("/>", srcE)+1);
				} else {
					// remove <script to </script>
					content = content.substring(0, position) + content.substring(content.indexOf("</script>", srcE)+8);
				}
			}
		}
		return paths;
	}
	
	public ArrayList<String> getJS(ArrayList<String> paths) {
		String str;
		ArrayList<String> js = new ArrayList<String>();
		StringBuilder contentBuilder = new StringBuilder();
		for(int i = 0; i < paths.size(); i++) {
			try {
				contentBuilder.append("<script>");
				BufferedReader in = new BufferedReader(new FileReader(paths.get(i)));
				while((str = in.readLine()) != null) {
					contentBuilder.append(str);
				}
				in.close();
				contentBuilder.append("</script>");
			} catch (IOException e) {}
			js.add(contentBuilder.toString());
		}
		return js;
	}
	
	public String insertJS(ArrayList<String> js) {
		String newcontent = content;
		int added = 0;
		for(int i = 0; i < js.size(); i++) {
			newcontent = newcontent.substring(0,  scriptPositions.get(i) + added) + js.get(i) + newcontent.substring(scriptPositions.get(i) + added);
			added += js.get(i).length();
		}
		return newcontent;
	}
	
	public String getContent() {
		return content;
	}
	
	
}
