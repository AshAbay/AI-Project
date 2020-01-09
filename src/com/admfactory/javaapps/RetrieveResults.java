package com.admfactory.javaapps;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public int getResults (ArrayList <String> websites) throws IOException {

        Element main = html.getElementById("main");
        Element search = main.getElementById("search");
        Elements webList = search.select("div.bkWMgd");
        Elements web = webList.select("div:contains(Web results)");
        for (Element singleWeb : web){
            /*System.out.println(singleWeb);
            System.out.println("Next Single Web");*/
        }
        if (web.size() == 0) {
            System.out.println(web.size());
            System.out.println("SOMETHING IS WRONG");
            return -1;
        }
        for (Element webResults : web) {
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
        }
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
    /*public static void main (String[] args) throws IOException {
        RetrieveResults test = new RetrieveResults("donald trump");
        test.getResults(test.urls);

    }*/

}