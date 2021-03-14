import java.util.*;
public class URLPool {
    private final LinkedList<URLDepthPair> pendingURLs;
    public LinkedList<URLDepthPair> processedURLs;
    private final ArrayList<String> seenURLs = new ArrayList<>();
    public int waitingThreads;
    int maxDepth;

    public URLPool(int maxDepthPair){ // конструктор хранилища с заданной глубиной, количеством потоков и списками адресов
        maxDepth = maxDepthPair;
        waitingThreads = 0;
        pendingURLs = new LinkedList<>();
        processedURLs = new LinkedList<>();
    }

    public synchronized int getWaitThreads(){ // возвращает количество ожидающих потоков
        return waitingThreads;
    }

    public synchronized void put(URLDepthPair depthPair){ // если глубина меньше максимальной, адрес добавляется в ожидающие
        if (waitingThreads != 0){
            --waitingThreads;
            this.notify();
        }
        if (!seenURLs.contains(depthPair.getURL()) & !pendingURLs.contains(depthPair)){
            if (depthPair.getDepth() < maxDepth){
                pendingURLs.add(depthPair);
            }
            else{
                processedURLs.add(depthPair);
                seenURLs.add(depthPair.getURL());
            }
        }
    }
    public synchronized URLDepthPair get(){ // если список ожидающих пуст, увеличивает количество потоков
        URLDepthPair myDepthPair;
        while (pendingURLs.isEmpty()){
            waitingThreads++;
            try{
                this.wait();
            }
            catch (InterruptedException e){
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }
        myDepthPair = pendingURLs.pop();
        while (seenURLs.contains(myDepthPair.getURL())){
            myDepthPair = pendingURLs.pop();
        }
        processedURLs.add(myDepthPair);
        seenURLs.add(myDepthPair.getURL());
        return myDepthPair;
    }
}