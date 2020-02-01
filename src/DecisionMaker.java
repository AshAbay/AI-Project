import com.admfactory.javaapps.ScrapeWikipedia;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class DecisionMaker {

    private ArrayList<String> wikiUrls;
    private String searchString;

    public DecisionMaker(ArrayList<String> wikiUrls, String searchString) throws IOException {
        this.wikiUrls = wikiUrls;
        this.searchString = searchString;
    }

    static int wordcount(String string)
    {
        int count=0;

        char ch[]= new char[string.length()];
        for(int i=0;i<string.length();i++)
        {
            ch[i]= string.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                count++;
        }
        return count;
    }

    public String setUrlString() throws IOException, ParseException {
        ReformatContent reformatContent = new ReformatContent();
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<String> longestSubstring = new ArrayList<>();
        for (int i = 0; i < wikiUrls.size();i++){
            ScrapeWikipedia scrapeWikipediaContents = new ScrapeWikipedia(wikiUrls.get(i));
            //System.out.println(reformatContent.reformatContent(scrapeWikipediaContents.scrapeContent()));
            //System.out.println("\n\n\n");
            if (searchRightContent (reformatContent.reformatContent(scrapeWikipediaContents.scrapeContent()))!= null){
                matches.add(wikiUrls.get(i));
                longestSubstring.add(searchRightContent (reformatContent.reformatContent(scrapeWikipediaContents.scrapeContent())));
           }
        }
        if (matches.size() == 0){
            return null;
        }
        int max = 0;
        String rightUrl = null;
        for (int j = 0; j < longestSubstring.size(); j++){
            int length = wordcount(longestSubstring.get(j));
            if (max <= length){
                max = longestSubstring.get(j).length();
                System.out.println("Right url: " + rightUrl);
                rightUrl = matches.get(j);
            }
        }
        return rightUrl;
    }

    public String searchRightContent (String content) throws ParseException {
        ReformatContent reformatContent = new ReformatContent();
        String X = content;

        String Y = reformatContent.reformatContent(searchString);
        //System.out.println(Y);
        int m = X.length();
        int n = Y.length();

        int[][] LCSuff = new int[m + 1][n + 1];
        int len = 0;
        int row = 0, col = 0;

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    LCSuff[i][j] = 0;

                else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    LCSuff[i][j] = LCSuff[i - 1][j - 1] + 1;
                    if (len < LCSuff[i][j]) {
                        len = LCSuff[i][j];
                        row = i;
                        col = j;
                    }
                }
                else
                    LCSuff[i][j] = 0;
            }
        }
        if (len == 0) {
            System.out.println("No Common Substring");
            return null;
        }
        String resultStr = "";
        while (LCSuff[row][col] != 0) {
            resultStr = X.charAt(row - 1) + resultStr; // or Y[col-1]
            --len;
            row--;
            col--;
        }
        System.out.println(resultStr);
        return resultStr;
    }

    public String findRightUrl () throws IOException, ParseException {
        String rightUrl = setUrlString();
        if (rightUrl != null){
            return rightUrl;
        }
        else {
            System.out.println("Got no right url\n");
            return null;
        }
    }

    public static void main (String[] args) throws IOException, ParseException {
        WikipediaResults test = new WikipediaResults("what is hour");
        test.getWikiUrls();
        for (String url : test.getWikiUrls()){
            System.out.println(url);
        }
        String searchString = "according to Wikipedia an hour is a unit of time conventionally reckon there's one 24th of a day and scientifically reckoned as 3599 to 3601 seconds depending on conditions";
        searchString = searchString.toLowerCase();
        DecisionMaker rightAnswer = new DecisionMaker(test.getWikiUrls(), searchString);
        System.out.println(rightAnswer.findRightUrl());
    }
}
