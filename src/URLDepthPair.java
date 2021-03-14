import java.net.*;
public class URLDepthPair {

    private final String currentURL;
    private final int currentDepth;
    public URLDepthPair(String URL, int depth) {
        currentURL = URL;
        currentDepth = depth;
    }
    public String getURL() { // возвращает текущий адрес
        return currentURL;
    }
    public int getDepth() { // возвращает текущую глубину
        return currentDepth;
    }
    public String toString() { // переводит глубину в строчное значение и возвращает с адресом
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }

    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }
}