/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Adrian Bachini
 */
public class ReadFile {
    
    String file;
    
    public ReadFile(String file){
        this.file = file;
        read();
    }
    
    public String read(){
        StringBuilder content = new StringBuilder();
        String line;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while((line = br.readLine()) != null){
                if(!line.isEmpty())
                    content.append(line);
                else{
                    break;
                }
            }

            
        } 
       catch(Exception e){
            e.getMessage();
        }
        
        return content.toString();
    }
    
    
}
