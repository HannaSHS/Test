import java.io.File;
import java.io.IOException;

public class DisplayDirectory {

    public String getPath(File file) throws IOException{
            String filePath = file.getCanonicalPath();
            return filePath;
    }

    public void displayDirectory() throws IOException  {

            File f = new File("."); // current directory

            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()){
                    System.out.println(getPath(file));
                } 	
            }
    }
}