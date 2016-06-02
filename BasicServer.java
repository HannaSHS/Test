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
	 // body = file to be read
    String filepath = "/* file path here */"; 
    int filetype = 0;
    String body = "";
    switch(filetype) {
    	case 0: body = ((new HTMLReader(filepath)).getContent()); break;
    	case 1: body = ((new CSSReader(filepath)).getContent()); break;
    	case 2: body = ((new JSReader(filepath)).getContent()); break;
    	case 3: body = ((new TXTReader(filepath)).getContent()); break;
    }
    
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