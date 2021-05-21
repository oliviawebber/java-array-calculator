import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

public class SleepClient {
    static int count = 0;
    static int size = Integer.MAX_VALUE;
    static Random rng = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
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
            sleep(rng.nextInt(500) + 500);
            int result = 0;
            for(int i = 0; i < 100; i++) {
                result += array[rng.nextInt(size)];
            }
            count++;
        }
    }
}