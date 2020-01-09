import com.admfactory.javaapps.RetrieveResults;

import java.io.IOException;
import java.util.ArrayList;

public class WikipediaResults{

    private String wikiRawQuery;
    private String wikiQuery;
    private RetrieveResults wikiResults;
    private ArrayList <String> wikiUrls;

    public WikipediaResults (String wikiRawQuery) throws IOException {
        this.wikiRawQuery = wikiRawQuery;
        this.wikiQuery = wikiRawQuery + " site:en.wikipedia.org";
        this.wikiUrls = new ArrayList<>();
        wikiResults = new RetrieveResults(wikiQuery);
    }

    public void setWikiUrls () throws IOException {
        int urlResults = wikiResults.getResults(wikiUrls);
        if (urlResults != 0){
            System.out.println("SOMETHING IS WRONG");
            return;
        }
        return;
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
        WikipediaResults test = new WikipediaResults("donald trump");
        test.setWikiUrls();
        test.getWikiUrls();
    }
}
