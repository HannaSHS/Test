import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TXTReader {
	
	private String content = "";
	
	public TXTReader(String path) throws FileNotFoundException {
		StringBuilder contentBuilder = new StringBuilder();

		BufferedReader in = new BufferedReader(new FileReader(path));
		try {
			String str;
			contentBuilder.append("<p>");
			while((str = in.readLine()) != null) {
				System.out.println("Output: " + str);
				contentBuilder.append(str);
				//contentBuilder.append("<br>");
			}
			contentBuilder.append("</p>");
			in.close();
		} catch (IOException e){
			
		}
		
		System.out.println("contentBuilder: " + contentBuilder.toString()); // DEBUG
		
		content = contentBuilder.toString();
		
		System.out.println("Content: " + content); // DEBUG
	}
	
	public String getContent() {
		return content;
	}
}
