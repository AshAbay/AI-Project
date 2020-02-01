import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FindRightAnswer {
    private ArrayList <String> wikiUrls;
    private String assistantTranscription;
    private String watsonTranscription;
    private String searchQuery;
    private String rightUrl;

    public FindRightAnswer (String searchQuery) throws IOException {
        this.searchQuery = searchQuery;
        WikipediaResults test = new WikipediaResults(this.searchQuery);
        wikiUrls = test.getWikiUrls();
        assistantTranscription = "";
        watsonTranscription = "";
        rightUrl = "";
        GetAssistantTranscription assistant = new GetAssistantTranscription();
        assistant.getTranscription(searchQuery);
        String line;
        BufferedReader br1 = new BufferedReader(new FileReader("/home/arsha/IdeaProjects/AI-Project/assistantPipe"));
        while ((line = br1.readLine()) != null) {
            assistantTranscription+= line;
        }
        String lowerAssistant = assistantTranscription.toLowerCase();
        assistantTranscription = lowerAssistant;
        GetWatsonTranscription watson = new GetWatsonTranscription();
        watson.getTranscription();
        BufferedReader br2 = new BufferedReader(new FileReader("/home/arsha/IdeaProjects/AI-Project/assistantPipe"));
        while ((line = br2.readLine()) != null) {
            watsonTranscription+= line;
        }
        String lowerWatson = watsonTranscription.toLowerCase();
        watsonTranscription = lowerWatson;
    }

    public void getRightAnswer () throws IOException, ParseException {
        DecisionMaker decisionMaker = new DecisionMaker(this.wikiUrls, this.searchQuery);
        rightUrl = decisionMaker.findRightUrl();
        System.out.println(rightUrl);
    }
}
