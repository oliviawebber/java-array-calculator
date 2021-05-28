import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ArrayClient {
    static int count = 0;
    static int size = Integer.MAX_VALUE / 10;
    static int samples = 1000;
    static Random rng = new Random();

    public static void main(String[] args) throws IOException {
        int[] array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = rng.nextInt();
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(Inet4Address.getLocalHost().getHostAddress(),8888),0);
        server.createContext("/metric", new MetricHandler());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        server.setExecutor(threadPoolExecutor);
        server.start();
        while(true) {
            int result = 0;
            for(int i = 0; i < samples; i++) {
                result += array[rng.nextInt(size)];
            }
            count++;
        }
    }
}


class MetricHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("GET".equals(exchange.getRequestMethod())) {
            String htmlResponse = "<html><body>" + ArrayClient.count + "</body></html>";
            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(200,htmlResponse.length());
            os.write(htmlResponse.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
    }
}