package com.admfactory.javaapps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

    public class RetrieveResultsHtml {

        private String queryUrl;
        private Document html;
        private ArrayList<String> urls;

        public RetrieveResultsHtml(String rawQuery) throws IOException {

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
                /*for (int i = 1; i <= matcher.groupCount(); i++) {
                    System.out.println("Group " + i + ": " + matcher.group(i));
                    System.out.println("\n\n\nNEXT!!!!!!!!!\n\n\n");
                }*/
            }
            pattern.compile("https://(.+)");
            matcher = pattern.matcher(html);
            while(matcher.find()){
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    System.out.println("Group " + i + ": " + matcher.group(i));
                    System.out.println("\n\n\nNEXT!!!!!!!!!\n\n\n");
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
        public static void main (String[] args) throws IOException {
            RetrieveResultsHtml test = new RetrieveResultsHtml ("donald trump");
            test.getResults(test.urls);
        }

    }

//why wont reformatcontent open?
// why what?