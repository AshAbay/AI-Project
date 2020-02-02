import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FindAssistantAnswer {
    private ArrayList<String> wikiUrls;
    private String assistantTranscription;
    private String searchQuery;
    private String rightUrl;

    public FindAssistantAnswer (String searchQuery) throws IOException {
        this.searchQuery = searchQuery;
        WikipediaResults test = new WikipediaResults(this.searchQuery);
        wikiUrls = test.getWikiUrls();
        System.out.println("URLS");
        System.out.println(wikiUrls);
        System.out.println("\n\n\n");
        assistantTranscription = "";
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
    }

    public String getAssistantTranscription () {
        return this.assistantTranscription;
    }
    public String getRightAnswer () throws IOException, ParseException {
        DecisionMaker decisionMaker = new DecisionMaker(this.wikiUrls, this.assistantTranscription);
        rightUrl = decisionMaker.findRightUrl();
        System.out.println(rightUrl);
        return rightUrl;

    }
    public static void main (String[] args) throws IOException, ParseException {
        FindAssistantAnswer FindAssistantAnswer = new FindAssistantAnswer("According to Wikipedia, what is stupidity");
        FindAssistantAnswer.getRightAnswer();
    }
}
