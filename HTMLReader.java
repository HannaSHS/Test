import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTMLReader {
    private String filePath;
    private StringBuilder contentBuilder;
    private FileReader fileReader;
    private BufferedReader lineReader;
    
    public HTMLReader(String path){
        this.init(path);
    }
    
    public void init(String path){
        try {
            this.filePath = path;
            this.contentBuilder = new StringBuilder("");
            this.fileReader = new FileReader(this.filePath);
            this.lineReader = new BufferedReader(this.fileReader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HTMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String readFile(){
            String temp;
            String content = "";
            
        try {
            while ((temp = this.lineReader.readLine()) != null) {
                this.contentBuilder.append(temp);
            }
            
            content = this.contentBuilder.toString();
            this.lineReader.close();
            this.fileReader.close();
        } catch (IOException ex) {
            Logger.getLogger(HTMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return content;
    }
}
