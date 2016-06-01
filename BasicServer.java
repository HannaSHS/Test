import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicServer implements Runnable {
  protected int PORT_NUMBER = 8765;

  private ServerSocket listener;

  public BasicServer() throws IOException {
    System.out.println("Starting server...");

    this.listener = new ServerSocket(PORT_NUMBER);
  }

  public void run() {
    Socket socket;

    System.out.println("Awaiting requests at port " + PORT_NUMBER + "...");
    while (true) {
      try {
        socket = listener.accept();
        HttpRequest request = new HttpRequest(socket.getInputStream());
        HttpResponse response = new HttpResponse(socket.getOutputStream());

        process_request(request, response);
        socket.close();
      } catch (IOException ex) {
        ;
      }
    }
  }
  


  private void process_request(HttpRequest request, HttpResponse response) throws IOException {
    String body = "<h1>Directory</h1>";
    body+="<ul>";
    String file;
    final File folder = new File(System.getProperty("user.dir")+"/www");
      for (final File fileEntry : folder.listFiles()) {
          //file directory
          file=System.getProperty("user.dir")+"/www/"+fileEntry.getName();
          //handle spaces in files
          file=file.replaceAll(" ", "%20");
          //list item
          body+="<li>"+"<a href="+file+">"+fileEntry.getName()+"</a>"+"</li>";
      }
      body+="</ul>";
    

    response.status = "200 OK";

    response.headers.put("Content-Length", String.valueOf(body.length()));
    response.headers.put("Content-Type", "text/html");
    response.sendHeaders();

    response.writer.write(body);
    response.writer.close();
  }

  public static void main(String[] args) throws IOException {
    BasicServer server = new BasicServer();
    Thread t = new Thread(server);
    t.run();
  }
}