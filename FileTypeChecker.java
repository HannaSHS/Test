import java.io.File;

public class FileTypeChecker {

	public FileTypeChecker(){
		
	}
	
	public String checker(File file){
		String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1);
	}
}
