import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse {
  protected BufferedWriter writer;
  protected HashMap<String, String> headers = new HashMap<String, String>();
  protected String status;
  protected String body;
  protected BufferedReader reader;
  protected String content;
  public HttpResponse(OutputStream output) throws IOException {
    writer = new BufferedWriter(new OutputStreamWriter(output));
  }

  public void sendHeaders() throws IOException {
    sendHeader("HTTP/1.1 " + status);

    for (Map.Entry<String, String> entry : headers.entrySet()) {
      sendHeader(entry.getKey() + ": " + entry.getValue());
    }

    sendHeader("");
  }

  public String sendBody(String fileName) throws IOException{
    content = "";
    try{
      reader = new BufferedReader(new FileReader(fileName));
      String str = reader.readLine(); 
      while(str != null){
        System.out.println("str is: " +str);
        content += str;
        str = reader.readLine();
      }
      reader.close();
    }
    catch(IOException e){
      System.out.println("file does not exist");
    }

    return content;
  }

  private void sendHeader(String message) throws IOException {
    System.out.println("< " + message);
    writer.write(message + "\r\n");
  }
}