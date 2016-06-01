import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.shape.Path;

public class TXTrender {
	public TXTrender() {
		
	}
	
	public String getContent(String filepath) {
		String body = null;
		
		File file = new File(filepath);
		FileReader fr = null; 
		
		try{
			fr = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			fr.read(chars);
			
			body = new String(chars);
			fr.close();
		}
		catch(IOException e){
			
		} finally{
			try{
				if( fr != null) fr.close();
			}catch(IOException e){

			}
		}
		return body;
	}
}
