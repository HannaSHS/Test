import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TXTFileReader {

	private BufferedReader bufferedReader;
	private StringBuilder stringBuilder;
	private String content;
	
	public TXTFileReader(){
		
	}
	
	public String retrieveContents(String path){
		String wholeContent =null;
		
		try{
			bufferedReader = new BufferedReader(new FileReader(new File(path)));
			stringBuilder = new StringBuilder();
			content = bufferedReader.readLine();
			
			while(content != null){
				stringBuilder.append(content);
				stringBuilder.append("\n");
				content = bufferedReader.readLine();
			}
			
			wholeContent = stringBuilder.toString();
		}
		catch(Exception e){
			
		}
		
		return wholeContent;
	}
}
