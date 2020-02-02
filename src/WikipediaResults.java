import com.admfactory.javaapps.RetrieveResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class WikipediaResults{

    private String wikiRawQuery;
    private String wikiQuery;
    private RetrieveResults wikiResults;
    private ArrayList <String> wikiUrls;

    public WikipediaResults (String wikiRawQuery) throws IOException {
        this.wikiRawQuery = wikiRawQuery;
        this.wikiQuery = "according to wikipedia, " + wikiRawQuery;
        this.wikiUrls = new ArrayList<>();
        wikiResults = new RetrieveResults(wikiQuery);
        setWikiUrls();
    }

    public int setWikiUrls () throws IOException {
        int urlResults = wikiResults.getResults();
        if (urlResults != 0 || wikiResults.getUrls().isEmpty()){
            System.out.println("SOMETHING IS WRONG");
            return -1;
        }
        wikiUrls = wikiResults.getUrls();
        Iterator<String> iterator = wikiUrls.iterator();

        while (iterator.hasNext()) {
            String s = iterator.next();
            if (!s.contains("wikipedia.org") || s.contains("#")) {
                iterator.remove();
            }
        }
        if (!wikiUrls.get(wikiUrls.size()-1).contains("wikipedia.org") || wikiUrls.get(wikiUrls.size()-1).contains("#")){
            wikiUrls.remove(wikiUrls.size()-1);
        }
        if (wikiUrls.isEmpty()){
            return -1;
        }
        return 0;
    }

    public ArrayList<String> getWikiUrls() {
        return wikiUrls;
    }

    public String getWikiRawQuery() {
        return wikiRawQuery;
    }

    public String getWikiQuery() {
        return wikiQuery;
    }

    public static void main (String[] args) throws IOException {
        WikipediaResults test = new WikipediaResults("what is stupidity");
//        test.setWikiUrls();
        test.getWikiUrls();
        for (String url : test.wikiUrls){
            System.out.println(url);
        }
    }
}
