import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {
    static int count = 0;
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost",8888),0);
        server.createContext("/metric", new MetricHandler());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        server.setExecutor(threadPoolExecutor);
        server.start();
        while(true) {
            Matrix m1 = Matrix.generateMatrix(1000);
            Matrix m2 = Matrix.generateMatrix(1000);
            m1.slowMultiply(m2);
            count++;
        }
    }
}

class MetricHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("GET".equals(exchange.getRequestMethod())) {
            String htmlResponse = "<html><body>" + Client.count + "</body></html>";
            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(200,htmlResponse.length());
            os.write(htmlResponse.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }
}