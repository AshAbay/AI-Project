import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FindWatsonAnswer {
    private ArrayList <String> wikiUrls;
    private String watsonTranscription;
    private String searchQuery;
    private String rightUrl;

    public FindWatsonAnswer (String searchQuery) throws IOException {
        this.searchQuery = searchQuery;
        WikipediaResults test = new WikipediaResults(this.searchQuery);
        wikiUrls = test.getWikiUrls();
        watsonTranscription = "";
        rightUrl = "";

    }

    public String getRightAnswer () throws IOException, ParseException {
        setWatsonTranscription();
        DecisionMaker decisionMaker = new DecisionMaker(this.wikiUrls, this.watsonTranscription);
        rightUrl = decisionMaker.findRightUrl();
        System.out.println(rightUrl);
        return rightUrl;
    }

    public String getWatsonTranscription() {
        return watsonTranscription;
    }

    public void setWatsonTranscription () throws IOException {
        String line;
        System.out.println("Getting translation from Watson");
        GetWatsonTranscription watson = new GetWatsonTranscription();
        System.out.println("done");
        watson.getTranscription();
        System.out.println("Reading watson Pipe");
        BufferedReader br2 = new BufferedReader(new FileReader("/home/arsha/IdeaProjects/AI-Project/watsonPipe"));
        while ((line = br2.readLine()) != null) {
            watsonTranscription += line;
        }
        System.out.println("done");
        watsonTranscription = watsonTranscription.toLowerCase();
        System.out.println(watsonTranscription);
    }

    public static void main (String[] args) throws IOException, ParseException {
        FindWatsonAnswer FindWatsonAnswer = new FindWatsonAnswer("According to wikipedia, who is bill gates?");
        FindWatsonAnswer.getRightAnswer();
    }
}
