import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class FindRightAnswer {
    public static AssistantCache assistantCache;

    static {
        assistantCache = new AssistantCache();
    }

    public static WatsonCache watsonCache;

    static {
        try {
            watsonCache = new WatsonCache();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FindRightAnswer() {
//        this.assistantCache = new AssistantCache();
//        this.watsonCache = new WatsonCache();
    }

    public String findRightAnswer(String query) throws IOException, ParseException {
        FindAssistantAnswer assistantAnswer = new FindAssistantAnswer(query);
        FindWatsonAnswer watsonAnswer = new FindWatsonAnswer(query);
        String assistantTranscription = assistantAnswer.getAssistantTranscription();
        String assistantAndWatsonDifferent = "Assistant transcription is bringing up a different url to watson's transcription\n";
        String assistantWatsonCacheNotInclusive = "Assistant Cache and Watson Cache do not have inclusive urls, and assistant transcription and watson transcription no result";
        if (assistantTranscription == null || assistantTranscription.equals("") ) {
            String error = "Something is wrong, got no Assistant Transcription";
            return error;
        }
        if (assistantCache.getURL(assistantTranscription) != null && assistantCache.getURL(assistantTranscription).size() == 1) {
            return assistantCache.getURL(assistantTranscription).get(0);
        }
        if (assistantCache.getURL(assistantTranscription) == null) {
            System.out.println("No assistant cache results");
            watsonAnswer.setWatsonTranscription();
            String watsonTranscription = watsonAnswer.getWatsonTranscription();
            if (watsonCache.getURL(watsonTranscription) != null && watsonCache.getURL(watsonTranscription).size() == 1){
                assistantCache.put(assistantTranscription, watsonCache.getURL(watsonTranscription).get(0));
                return watsonCache.getURL(watsonTranscription).get(0);
            }
            if (watsonCache.getURL(watsonTranscription) == null){
                String assistantURL = assistantAnswer.getRightAnswer();
                if (!assistantURL.equals("")){
                    assistantCache.put(assistantTranscription, assistantURL);
                    watsonCache.put(watsonTranscription, assistantURL);
                    return assistantURL;
                }
                String watsonURL = watsonAnswer.getRightAnswer();
                if (!watsonURL.equals("")){
                    assistantCache.put(assistantTranscription, watsonURL);
                    watsonCache.put(watsonTranscription, watsonURL);
                    return watsonURL;
                }
                return "WatsonURL, assistantURL, watsoncache and AssistantCache are all empty";

            }
            if (watsonCache.getURL(watsonTranscription).size() > 1){
                String assistantURL = assistantAnswer.getRightAnswer();
                if (!assistantURL.equals("")){
                    if (!watsonCache.getURL(watsonTranscription).contains(assistantURL)){
                        watsonCache.put(watsonTranscription, assistantURL);
                    }
                    assistantCache.put(assistantTranscription, assistantURL);
                    return assistantURL;
                }
                String watsonURL = watsonAnswer.getRightAnswer();
                if (!watsonAnswer.equals("")){
                    if (!watsonCache.getURL(watsonTranscription).contains(watsonURL)){
                        watsonCache.put(watsonTranscription, watsonURL);
                    }
                    assistantCache.put(assistantTranscription, watsonURL);
                    return watsonURL;
                }
                return watsonCache.getURL(watsonTranscription).get(0);
            }
        }

        if (assistantCache.getURL(assistantTranscription).size() > 1) {
            System.out.println("Got more than one answer\n");
            watsonAnswer.setWatsonTranscription();
            String watsonTranscription = watsonAnswer.getWatsonTranscription();
            if (watsonCache.getURL(watsonTranscription) != null && watsonCache.getURL(watsonTranscription).size() == 1) {
                if (assistantCache.getURL(assistantTranscription).contains(watsonCache.getURL(watsonTranscription).get(0))) {
                    return watsonCache.getURL(watsonTranscription).get(0);
                }
                String assistantURL = assistantAnswer.getRightAnswer();
                if (!assistantURL.equals("")) {
                    if (assistantURL.equals(watsonCache.getURL(watsonTranscription).get(0))) {
                        assistantCache.put(assistantTranscription, assistantURL);
                    } else {
                        assistantCache.getURL(assistantTranscription).add(assistantURL);
                        watsonCache.put(watsonTranscription,assistantURL);
                    }
                    return assistantURL;
                }
                String watsonURL = watsonAnswer.getRightAnswer();
                if (!watsonURL.equals("")) {
                    if (watsonURL.equals(watsonCache.getURL(watsonTranscription).get(0))) {
                        assistantCache.put(assistantTranscription, watsonURL);
                    } else {
                        assistantCache.put(assistantTranscription, watsonURL);
                        watsonCache.put (watsonTranscription, watsonURL);
                    }
                    return watsonURL;
                }
                return assistantWatsonCacheNotInclusive;
            }
            String assistantURL = assistantAnswer.getRightAnswer();
            if (watsonCache.getURL(watsonTranscription) == null){
                if (!assistantURL.equals("")) {
                    assistantCache.put(assistantTranscription, assistantURL);
                    watsonCache.put(watsonTranscription, assistantURL);
                    return assistantURL;
                }
                String watsonURL = watsonAnswer.getRightAnswer();
                if (!watsonURL.equals("")) {
                    assistantCache.put(assistantTranscription, watsonURL);
                    watsonCache.put(watsonTranscription, watsonURL);
                    return watsonURL;
                }
                return assistantWatsonCacheNotInclusive;
            }
            if (watsonCache.getURL(watsonTranscription).size() > 1){
                List<String> common = new ArrayList<>(assistantCache.getURL(assistantTranscription));
                common.retainAll(watsonCache.getURL(watsonTranscription));
                if (!assistantURL.equals("")){
                    assistantCache.put(assistantTranscription, assistantURL);
                    watsonCache.put(watsonTranscription, assistantURL);
                    return assistantURL;
                }
                String watsonURL = watsonAnswer.getRightAnswer();
                if (!watsonURL.equals("")){
                    assistantCache.put(assistantTranscription, watsonURL);
                    watsonCache.put(watsonTranscription, watsonURL);
                    return watsonURL;
                }
                if (common.size() > 0){
                    return common.get(0);
                }
                return assistantWatsonCacheNotInclusive;
            }

        }
        return "Something is wrong, how did it hit none of them?\n";
    }

    public static void main (String [] args) throws IOException, ParseException {
        FindRightAnswer findRightAnswer = new FindRightAnswer();
        assistantCache.retrieveHashmaps();
        watsonCache.retrieveHashmaps();
        System.out.println(findRightAnswer.findRightAnswer("According to Wikipedia, who is bill gates"));
        watsonCache.saveHashmaps();
        assistantCache.saveHashmaps();

    }
}
