import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpRequest {
  protected static String HTTP_GET = "GET";
  protected static String HTTP_HEAD = "HEAD";

  protected BufferedReader reader;
  protected HashMap<String, String> headers = new HashMap<String, String>();
  protected String method, uri;

  public HttpRequest(InputStream input) throws IOException {
    reader = new BufferedReader(new InputStreamReader(input));

    String intro = reader.readLine();
    System.out.println("> " + intro);

    String[] intro_split = intro.split(" ");
    method = intro_split[0];
    uri = intro_split[1];

    String header;
    int index;

    while ((header = reader.readLine()) != null) {
      System.out.println("> " + header);
      if (header.equals("")) break;

      index = header.indexOf(":");
      headers.put(header.substring(0, index), header.substring(index + 1));
    }
  }

  public boolean isGet() {
    return method.equals(HTTP_GET);
  }

  public boolean isHead() {
    return method.equals(HTTP_HEAD);
  }
}