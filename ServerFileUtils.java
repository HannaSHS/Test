import java.io.File;
import java.io.IOException;

public class ServerFileUtils {
	FileTypeChecker fileTypeChecker = FileTypeChecker.getInstance();
	DisplayDirectory displayDirectory = new DisplayDirectory();
	
	public String getPath(File file) throws IOException{
        return displayDirectory.getPath(file);
	}

	public void displayDirectory() throws IOException  {
		displayDirectory.displayDirectory();
	}
	
	public String getFileType(String path){
        return fileTypeChecker.getFileType(path);
    }
	
	public 
}
