import com.admfactory.javaapps.ScrapeWikipedia;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReformatContent {
    private static final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };
    private static final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };
    private String content;

    public ReformatContent(){
        return;
    }
    public String setContent (String content){
        this.content = content;
        return this.content;
    }
    public String deleteSymbols (){
//        String stringWithSymbols = content.replaceAll("((?<=[^a-zA-Z])[^a-zA-Z0-9 ](?=[ [^a-zA-Z0-9]]))", "");
//        stringWithSymbols = stringWithSymbols.replaceAll("((?<=[^a-zA-Z])[^a-zA-Z0-9 ](?=[a-zA-Z0-9]))", " ");
        return content;
    }

    public String spaceBeforeNumber (String stringWithNoSpace){
        String stringWithSpace = stringWithNoSpace.replaceAll("[a-zA-Z](?=[0-9])", "$0 ");
        return stringWithSpace;
    }

    public String convertNumbers (String withNumbers) throws ParseException {
        String withoutNumbers = withNumbers;
        ArrayList<Long> longs = convertWordsIntoNumbers(withNumbers);
        for (int i =0; i < longs.size(); i++){
            //System.out.println(longs.get(i));
            String numberWord = convert(longs.get(i));
            //System.out.println(numberWord);
            withoutNumbers = withoutNumbers.replaceFirst(longs.get(i).toString(), numberWord);
            //System.out.println(withoutNumbers);
        }
        return withoutNumbers;
    }
    private ArrayList<Long> convertWordsIntoNumbers (String withWords) throws ParseException {

        ArrayList<Long> longs = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(withWords);
        while(m.find()) {
//            if (m.group().compareTo(String.valueOf(Long.MAX_VALUE)) == -1  && m.group().compareTo((String.valueOf(Long.MIN_VALUE))) == 1) {
//                longs.add(Long.parseLong(m.group()));
//            }
            longs.add((Long) NumberFormat.getNumberInstance(Locale.US).parse(m.group()));
        }
        return longs;
    }
    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }

    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "zero"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
        }
        result =  result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1 :
                tradHundredThousands = "one thousand ";
                break;
            default :
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result =  result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }


//    public static void main(String[] args) {
//            System.out.println("*** " + EnglishNumberToWords.convert(0));
//            System.out.println("*** " + EnglishNumberToWords.convert(1));
//            System.out.println("*** " + EnglishNumberToWords.convert(16));
//            System.out.println("*** " + EnglishNumberToWords.convert(100));
//            System.out.println("*** " + EnglishNumberToWords.convert(118));
//            System.out.println("*** " + EnglishNumberToWords.convert(200));
//            System.out.println("*** " + EnglishNumberToWords.convert(219));
//            System.out.println("*** " + EnglishNumberToWords.convert(800));
//            System.out.println("*** " + EnglishNumberToWords.convert(801));
//            System.out.println("*** " + EnglishNumberToWords.convert(1316));
//            System.out.println("*** " + EnglishNumberToWords.convert(1000000));
//            System.out.println("*** " + EnglishNumberToWords.convert(2000000));
//            System.out.println("*** " + EnglishNumberToWords.convert(3000200));
//            System.out.println("*** " + EnglishNumberToWords.convert(700000));
//            System.out.println("*** " + EnglishNumberToWords.convert(9000000));
//            System.out.println("*** " + EnglishNumberToWords.convert(9001000));
//            System.out.println("*** " + EnglishNumberToWords.convert(123456789));
//            System.out.println("*** " + EnglishNumberToWords.convert(2147483647));
//            System.out.println("*** " + EnglishNumberToWords.convert(3000000010L));
//
//            /*
//             *** zero
//             *** one
//             *** sixteen
//             *** one hundred
//             *** one hundred eighteen
//             *** two hundred
//             *** two hundred nineteen
//             *** eight hundred
//             *** eight hundred one
//             *** one thousand three hundred sixteen
//             *** one million
//             *** two millions
//             *** three millions two hundred
//             *** seven hundred thousand
//             *** nine millions
//             *** nine millions one thousand
//             *** one hundred twenty three millions four hundred
//             **      fifty six thousand seven hundred eighty nine
//             *** two billion one hundred forty seven millions
//             **      four hundred eighty three thousand six hundred forty seven
//             *** three billion ten
//             **/
//        }
//    }
    public String reformatContent (String content) throws ParseException {
        setContent(content);
        String deleteSymbols = deleteSymbols();
        String stringWithSpace = spaceBeforeNumber(deleteSymbols);
        String changedNumbersToWords = convertNumbers(stringWithSpace);
        String lowerCase = changedNumbersToWords.toLowerCase();
        return lowerCase;
    }
    public static void main (String[] args) throws IOException, ParseException {
        ReformatContent reformatContent = new ReformatContent();
//        ScrapeWikipedia test = new ScrapeWikipedia("https://en.wikipedia.org/wiki/Hour");
//        System.out.println(test.scrapeContent());
//        System.out.println("\n\n\n");
//        String content = reformatContent.reformatContent(test.scrapeContent());
//        System.out.println(content);

        String content = "Mike Pence is a dick 1.11 Jun.4 7, 1958 June 7,18888 4/34th";
//        reformatContent.setContent(content);
//        String deleteSymbols = reformatContent.deleteSymbols();
//        System.out.println(deleteSymbols);
//        String stringWithSpace = reformatContent.spaceBeforeNumber(deleteSymbols);
//        String changedNumbersToWords = reformatContent.convertNumbers(stringWithSpace);
//        String lowerCase = changedNumbersToWords.toLowerCase();
        System.out.println(reformatContent.reformatContent(content));
//        System.out.println(reformatContent.convert(7,118,888));
//        System.out.println(reformatContent.convert((Long) NumberFormat.getNumberInstance(Locale.US).parse("35,634,646")));
    }
}
