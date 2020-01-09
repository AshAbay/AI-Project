/*package com.admfactory.javaapps;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TabsResults {
    private String url;
    private Document html;

    public TabsResults() throws IOException {
        this.url= "https://www.google.com.au/search?q=who+invented+facebook";
        this.html = Jsoup.connect(this.url).get();
    }

    public int getResults (ArrayList <String> websites) throws IOException{

        Element main = html.getElementById("main");
        System
    }

    public static void printHtml () throws IOException {


    }




}*/
