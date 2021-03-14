import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
        int depth = 0; // обнуляет глубину и количество потоков
        int numThreads = 0;
        if (args.length != 3){ // проверяет правильность введенных данных
            System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads>");
            System.exit(1);
        }
        try{
            depth = Integer.parseInt(args[1]); // переводит в число аргументы командной строки
            numThreads = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException nfe){
            System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads>");
            System.exit(1);
        }
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);
        URLPool pool = new URLPool(depth);
        pool.put(currentDepthPair);
        int initialActiveThreads = Thread.activeCount(); // задает начальное количество активных потоков
        while (pool.getWaitThreads() != numThreads){ // пока количество ожидающих потоков не равно общему количеству
            if (Thread.activeCount() - initialActiveThreads < numThreads){ // если количество запущенных меньше общих
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            else{
                try{
                    Thread.sleep(500);
                }
                catch (InterruptedException ie){
                    System.out.println("Caught unexpected InterruptedException," + " ignoring...");
                }
            }
        }
        Iterator<URLDepthPair> iter = pool.processedURLs.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
        System.exit(0);
    }
}
