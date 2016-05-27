import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
  protected BufferedWriter writer;
  protected HashMap<String, String> headers = new HashMap<String, String>();
  protected String status;

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

  private void sendHeader(String message) throws IOException {
    System.out.println("< " + message);
    writer.write(message + "\r\n");
  }
}