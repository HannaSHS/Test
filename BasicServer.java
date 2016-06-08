import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicServer implements Runnable {
  protected String BASE_PATH = "www";
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

  private void doGet(HttpRequest request, HttpResponse response) throws IOException {
    if (request.uri.equals("/")) {
 	 String body = "<h1>Directory</h1><hr>";

    response.status = "200 OK";

    File folder = new File(BASE_PATH);
	File[] listOfFiles = folder.listFiles();
	body+= "<ul>";
	for (File file : listOfFiles) {
	    if (file.isFile()) {
	        System.out.println(file.getName());
	        body+= "<li><a href= '"+ file.getName() +"'>";
	        body+= file.getName();
	        body+= "</li></a>";
	    }
	}
	body+= "</ul>";

    response.headers.put("Content-Length", String.valueOf(body.length()));
    response.headers.put("Content-Type", "text/html");
    response.sendHeaders();

    response.writer.write(body);
    response.writer.close();
      // show index page
    } else {
      File file = new File(BASE_PATH + request.uri);

      if (!file.exists()) {
        not_found(response);
        return;
      }

      response.status = "200 OK";
      response.headers.put("Content-Length", String.valueOf(file.length()));

      if (request.uri.endsWith(".css")) {
        response.headers.put("Content-Type", "text/css");

        //not_found(response);
        //return;
      } else if (request.uri.endsWith(".html")) {
        response.headers.put("Content-Type", "text/html");
      }else if (request.uri.endsWith(".json")) {
        response.headers.put("Content-Type", "application/json");
      } else if (request.uri.endsWith(".xml")) {
        response.headers.put("Content-Type", "application/xml");
      } else if (request.uri.endsWith(".js")) {
        response.headers.put("Content-Type", "text/javascript");
        //not_found(response);
        //return;
      } else {
        response.headers.put("Content-Type", "text/plain");
      }


      String s;
      BufferedReader reader = new BufferedReader(new FileReader(file));
      while ((s = reader.readLine()) != null) {
        response.writer.write(s + "\n");
        System.out.println(s);
      }
      response.sendHeaders();
      response.writer.close();
    }
  }

  private void not_found(HttpResponse response) throws IOException {
    response.status = "404 Not Found";

    response.headers.put("Content-Length", "0");
    response.sendHeaders();
    response.writer.close();
  }

  private void process_request(HttpRequest request, HttpResponse response) throws IOException {
    if (request.isGet()) {
      doGet(request, response);
    } else if (request.isHead()) {
      // doHead(request, response);
    }

    System.out.println();
  }

  public static void main(String[] args) throws IOException {
    BasicServer server = new BasicServer();
    Thread t = new Thread(server);
    t.run();
  }
}