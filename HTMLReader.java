
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

public class HTMLReader {
	private String content;
	
	public HTMLReader(String path) {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			while((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e){
			
		}
		content = contentBuilder.toString();
	}
	
	public String getContent() {
		return content;
	}
}
