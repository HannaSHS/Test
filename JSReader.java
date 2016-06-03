import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSReader {
	private StringBuilder contentBuilder;
	private String content;
	private ArrayList<Integer> scriptPositions = new ArrayList<Integer>();
	
	public JSReader(String html) {
		contentBuilder = new StringBuilder(html); // get HTML
		ArrayList<String> paths = getFiles();
		if(paths.size() > 0) {
			ArrayList<String> js = getJS(paths);
			insertJS(js);
		}
		content = contentBuilder.toString();
	}
	
	public ArrayList<String> getFiles() {
		ArrayList<String> paths = new ArrayList<String>();
		boolean done = false;
		int position = 0, srcS = 0, srcE = 0;
		while(!done) {
			if((position = contentBuilder.indexOf("<script", position)) == -1) {
				done = true;
			} else {
				scriptPositions.add(position);
				srcS = contentBuilder.indexOf("src=\"", position);
				srcE = contentBuilder.indexOf("\"", srcS+5);
				System.out.println("JS file found: " + contentBuilder.substring(srcS+5, srcE));
				paths.add(contentBuilder.substring(srcS+5, srcE));
				int tag1 = contentBuilder.indexOf("/>", srcE);
				int tag2 = contentBuilder.indexOf("</script>", srcE);;
				if(tag2 == -1) {
					// remove <script to />
					contentBuilder.delete(position, tag1 + 2);
				} else {
					// remove <script to </script>
					contentBuilder.delete(position, tag2 + 9);
				}
			}
		}
		return paths;
	}
	
	public ArrayList<String> getJS(ArrayList<String> paths) {
		String str;
		ArrayList<String> js = new ArrayList<String>();
		for(int i = 0; i < paths.size(); i++) {
			StringBuilder jsBuilder = new StringBuilder();
			try {
				jsBuilder.append("<script>");
				BufferedReader in = new BufferedReader(new FileReader(paths.get(i)));
				while((str = in.readLine()) != null) {
					jsBuilder.append(str);
				}
				in.close();
				jsBuilder.append("</script>");
			} catch (IOException e) {}
			js.add(jsBuilder.toString());
		}
		return js;
	}
	
	public void insertJS(ArrayList<String> js) {
		for(int i = js.size()-1; i >= 0; i--) {
			contentBuilder.insert(scriptPositions.get(i).intValue(), js.get(i));
		}
	}
	
	public String getContent() {
		return content;
	}
	
	
}
