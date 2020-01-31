package com.admfactory.javaapps;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetrieveResults {

    private String queryUrl;
    private Document html;
    private ArrayList <String> urls;

    public RetrieveResults(String rawQuery) throws IOException {

        //https://www.google.com/search?q=donald+trump
        //String prepend = "https://www.google.com/search?q=";
        //url = String.join("+",url.split(" +");
        String query = rawQuery.replace(" ", "+");
        this.queryUrl = "https://www.google.com/search?q="+query;
        this.urls = new ArrayList<>();

        html = Jsoup.connect(this.queryUrl).get();

        //System.out.println(html);
    }

    public int getResults () throws IOException {

        Element main = html.getElementById("main");
        Element cnt = main.getElementById("cnt");
        //System.out.println(cnt);
        Elements mw = cnt.getElementsByClass("mw");
        Element rcnt = mw.get(1);
        Element search = rcnt.getElementById("search");
        Elements bkWMgds = search.getElementsByClass("bkWMgd");
        for (Element bkWMgd : bkWMgds){
            /*System.out.println(bkWMgd);
            System.out.println("\n\n\nNEXT!!!\n\n\n");*/
            //String bkWMgdText = bkWMgd.text();
            Elements links = bkWMgd.select("a");
            for (Element link:links){
                    String linkText = link.attr("href");
                    if (linkText.matches("https://(.+)")) {
                        if (!linkText.matches("(.+)webcache(.+)google(.+)")) {
                            if (!urls.contains(linkText)){
                                urls.add(linkText);
                            }
                            /*System.out.println(linkText);
                            System.out.println("\n\n\nNEXT!!!\n\n\n");*/
                        }
                    }
            }
        }
        String html = this.html.html();
        Pattern pattern = Pattern.compile("href\\\\x3d\\\\x22https://(.+?)\\\\x22");
        Matcher matcher = pattern.matcher(html);
            /*if (matcher.find()){
                System.out.println(matcher.group());
                System.out.println("\n\n\nNEXT!!!\n\n\n");
            }*/
        while (matcher.find()) {
            //System.out.println("Full match: " + matcher.group(0));
            //System.out.println("\n\n\nNEXT!!!\n\n\n");
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (!urls.contains("https://"+matcher.group(i))) {
                    urls.add("https://"+matcher.group(i));
                }
                //System.out.println("Group " + i + ": " + matcher.group(i));
                //System.out.println("\n\n\nNEXT!!!!!!!!!\n\n\n");
            }
        }
        /*Elements webList = search.select("div.bkWMgd");
        Elements web = webList.select("div:contains(Web results)");
        for (Element singleWeb : web){
            System.out.println(singleWeb);
            System.out.println("Next Single Web");
        }
        if (web.size() == 0) {
            System.out.println(web.size());
            System.out.println("SOMETHING IS WRONG");
            return -1;
        }*/
        /*for (Element webResults : web) {
            Elements rs = webResults.select("div.r");
            for (Element r : rs) {
                Elements websiteLines = r.select("a:has(div.ellip)");
                for (Element websiteLine : websiteLines) {
                    String url = websiteLine.attr("href");
                    System.out.println(url);
                    System.out.println("NEXT!");
                    websites.add(url);
                }
            }
        }*/

        return 0;

    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public Document getHtml() {
        return html;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public void setHtml(Document html) {
        this.html = html;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public static void main (String[] args) throws IOException {
        RetrieveResults test = new RetrieveResults("according to wikipedia, who is bill gates");
        test.getResults();
        for (String url : test.urls){
            System.out.println(url);
        }

    }

}