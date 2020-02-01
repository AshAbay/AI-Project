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
        System.out.println("Getting translation from assistant");
        assistant.getTranscription(searchQuery);
        System.out.println("done");
        String line;
        BufferedReader br1 = new BufferedReader(new FileReader("/home/arsha/IdeaProjects/AI-Project/assistantPipe"));
        System.out.println("Reading assistant Pipe");
        while ((line = br1.readLine()) != null) {
            assistantTranscription += line;
        }
        System.out.println("Done ");
        assistantTranscription = assistantTranscription.toLowerCase();
        System.out.println(assistantTranscription);
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

    public void getRightAnswer () throws IOException, ParseException {
        DecisionMaker decisionMaker = new DecisionMaker(this.wikiUrls, this.watsonTranscription);
        rightUrl = decisionMaker.findRightUrl();
        System.out.println(rightUrl);
        decisionMaker.setSearchString(this.assistantTranscription);
        rightUrl = decisionMaker.findRightUrl();
        System.out.println(rightUrl);
    }
    public static void main (String[] args) throws IOException, ParseException {
        FindRightAnswer findRightAnswer = new FindRightAnswer("According to wikipedia, who is bill gates?");
        findRightAnswer.getRightAnswer();
    }
}
