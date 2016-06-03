import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

public class TXTReader {
	
	private String content = "";
	
	public TXTReader(String path) {
		StringBuilder contentBuilder = new StringBuilder("");

		String str;
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			contentBuilder.append("<p>");
			while((str = in.readLine()) != null) {
				contentBuilder.append(str);
				contentBuilder.append("<br>");
			}
			contentBuilder.append("</p>");
			in.close();
		} catch (IOException e) {}
			
		content = contentBuilder.toString();
	}
	
	public String getContent() {
		return content;
	}
}
