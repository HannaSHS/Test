import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class CSSrender extends Render {

	public CSSrender(){

	}

	public String getContent(String filepath){
		String body = "";

		BufferedReader br = null;

		try{
			String temp = "";

			br = new BufferedReader(new FileReader(filepath));

			while((temp = br.readLine()) != null){
				body += temp + "<br>";
			}

		}
		catch(IOException e){

		} finally{
			try{
				if( br != null) br.close();
			}catch(IOException e){

			}
		}



		return body;
	}
}