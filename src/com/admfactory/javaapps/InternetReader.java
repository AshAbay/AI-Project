package com.admfactory.javaapps;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InternetReader {

    private String url;
    private Document html;

    public InternetReader () throws IOException {

        //https://www.google.com/search?q=donald+trump
        //String prepend = "https://www.google.com/search?q=";
        //url = String.join("+",url.split(" +");
        this.url = "https://www.google.com/search?q=donald+trump";


        html = Jsoup.connect(this.url).get();

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
                    /*System.out.println(url);
                    System.out.println("NEXT!");*/
                    websites.add(url);
                }
            }
        }
        return 0;

    }
    public static void main (String[] args) throws IOException {
        InternetReader test = new InternetReader();
        ArrayList<String> urls = new ArrayList<>();
        test.getResults(urls);

    }

}