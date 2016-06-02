/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Miguel
 */
public class FileTypeChecker {
    private static final FileTypeChecker instance = new FileTypeChecker();
    
    private FileTypeChecker(){
        
    }
    
    public static FileTypeChecker getInstance(){
        return instance;
    }
    
    public String getFileType(String path){
        String type = "";
        String ext = FilenameUtils.getExtension(path);
        return type;
    }
}
