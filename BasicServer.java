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

        processRequest(request, response);
        socket.close();
      } catch (IOException ex) {
        ;
      }
    }
  }
  
  private boolean checkSupported(String file){
      String extension = "";
      int i = file.lastIndexOf('.');
      if (i > 0){
          extension = file.substring(i+1);
      }
      if(extension.equals(".html") || extension.equals(".js") || extension.equals(".xml")){
          return true;
      }
      else {
          return false;
      }
  }
  
  private void doPost(HttpRequest request, HttpResponse response) throws IOException{
      File f = new File(BASE_PATH + "/" + request.uri);
      
      if(checkSupported(request.uri))
      {
          if(f.exists()){
              //file already exists
              response.status = "409 Conflict";
              response.headers.put("Content-Length", "0");
              response.sendHeaders();
              response.writer.close();
          }
          else {
              //file is created
              f.createNewFile();
              response.status = "201 Created";
              response.headers.put("Content-Length", String.valueOf(f.getName().length()));
              response.sendHeaders();
              response.writer.close();
          }
          
      }
      else {
          //file is unsupported
              response.status = "415 Unsupported Media Type";
              response.headers.put("Content-Length", "0");
              response.sendHeaders();
              response.writer.close();
      }
      
  }

  private void doGet(HttpRequest request, HttpResponse response) throws IOException {
    String filename = request.uri.equals("/") ? "index.html" : request.uri;
    File file = new File(BASE_PATH + filename);

    if (!file.exists()) {
      if (request.uri.equals("/")) {
        listDirectory(response);
      } else {
        notFound(response);
      }

      return;
    }

    response.status = "200 OK";
    response.headers.put("Content-Length", String.valueOf(file.length()));

    if (request.uri.endsWith(".css")) {
      response.headers.put("Content-Type", "text/css");
    } else if (request.uri.endsWith(".html")) {
      response.headers.put("Content-Type", "text/html");
    } else if (request.uri.endsWith(".js")) {
      response.headers.put("Content-Type", "text/javascript");
    } else {
      response.headers.put("Content-Type", "text/plain");
    }

    response.sendHeaders();

    String s;
    BufferedReader reader = new BufferedReader(new FileReader(file));
    while ((s = reader.readLine()) != null) {
      response.writer.write(s + "\n");
    }

    response.writer.close();
  }

  private void listDirectory(HttpResponse response) throws IOException {
    StringBuffer body = new StringBuffer("<h1>Directory</h1><ul>");
    File folder = new File(System.getProperty("user.dir") + "/www");
    String filename, href;

    for (final File fileEntry : folder.listFiles()) {
      filename = fileEntry.getName();

      body.append("<li><a href='");
      body.append(filename.replaceAll(" ", "%20")); // handle spaces in files
      body.append("'>");
      body.append(filename);
      body.append("</a></li>");
    }

    body.append("</ul>");

    response.status = "200 OK";
    response.headers.put("Content-Length", String.valueOf(body.length()));
    response.sendHeaders();

    response.writer.write(body.toString());
    response.writer.close();
  }


  private void notFound(HttpResponse response) throws IOException {
    response.status = "404 Not Found";

    response.headers.put("Content-Length", "0");
    response.sendHeaders();
    response.writer.close();
  }

  private void processRequest(HttpRequest request, HttpResponse response) throws IOException {
    if (request.isGet()) {
      doGet(request, response);
    } else if (request.isHead()) {
      // doHead(request, response);
    } else if (request.isPost()) {
        doPost(request, response);
    }

    System.out.println();
  }

  public static void main(String[] args) throws IOException {
    BasicServer server = new BasicServer();
    Thread t = new Thread(server);
    t.run();
  }
}