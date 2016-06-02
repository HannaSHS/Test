import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TXTReader
{
	File file;
	
	public String getContent(String filepath)
	{
		file = new File(filepath);
		FileReader fr = null;
		
		String content = null;
		
		try
		{
			fr = new FileReader (file);
			char[] chars = new char[(int) file.length()];
			fr.read(chars);
			
			content = new String(chars);
			fr.close();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(fr != null)
					fr.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return content;
	}
}
