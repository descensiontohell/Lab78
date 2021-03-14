import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.*;

public class CrawlerTask implements Runnable
{
    public URLDepthPair depthPair;
    public URLPool pool;
    public CrawlerTask (URLPool newPool) { // конструктор, создающий хранилище
        pool = newPool;
    }
    public void run() {
        depthPair = pool.get();
        int depth = depthPair.getDepth();
        LinkedList<String> linksList = null;
        try {
            linksList = Crawler.getAllLinks(depthPair);
        }
        catch (IOException ex) {
            Logger.getLogger(CrawlerTask.class.getName()).log(Level.SEVERE,null, ex);
        }
        for (int i = 0; i < linksList.size(); i++) { // создает новую пару
            String newURL = linksList.get(i);
            URLDepthPair newDepthPair = new URLDepthPair(newURL, depth + 1);
            pool.put(newDepthPair);
        }
    }
}