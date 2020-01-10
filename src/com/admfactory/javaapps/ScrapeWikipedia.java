package com.admfactory.javaapps;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScrapeWikipedia {

    private String wikipediaUrl;
    private Document wikipediaHtml;

    public ScrapeWikipedia (String wikipediaUrl) throws IOException {
        this.wikipediaUrl = wikipediaUrl;
        wikipediaHtml = Jsoup.connect(this.wikipediaUrl).get();

    }
    public String scrapeContent (){

       // String content = wikipediaHtml.text();
        Elements pTexts = wikipediaHtml.getElementsByTag("p");
        String content = "";
        for (Element pText: pTexts){
            content = content.concat(pText.text());
            content = content.concat("\n");
        }
        return content;
    }

    public static void main (String[] args) throws IOException {
        ScrapeWikipedia test = new ScrapeWikipedia("https://en.wikipedia.org/wiki/Mike_Pence");
        String content = test.scrapeContent();
        System.out.println(content);

        //String content = test.scrapeContent();
        //System.out.println(content);
    }
}
