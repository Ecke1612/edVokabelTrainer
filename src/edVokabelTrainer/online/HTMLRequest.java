package edVokabelTrainer.online;

import edVokabelTrainer.objects.Vokabel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLRequest {

    public Vokabel callHttp(String word) {
        try {
            Document doc = Jsoup.connect(buildURL(word)).get();
            //System.out.println(doc.toString());
            //System.out.println("--------------------------------------------------------------------------------------------------------");
            Elements e1 = doc.select("body > div.container > div.col-md-12.search-content > div:nth-child(4) > div:nth-child(2)");
            String sel = e1.text();
            System.out.println(sel);
            System.out.print(word + " hat: ");
            Vokabel vokabel = buildVokabel(sel, word);
            return vokabel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Vokabel buildVokabel(String selected, String german) {
        Vokabel vokabel = new Vokabel(german);
        String[] splitted = selected.split(",");
        System.out.println(splitted.length + " Elemente");
        if(splitted.length == 1) {
            vokabel.setSingular(splitted[0].trim());
        }
        else if(splitted.length == 2) {
            vokabel.setSingular(splitted[0].trim());
            String[] plural = splitted[1].split(":");
            if(plural.length != 2) {
                System.out.println("Fehler bei Plural");
            } else vokabel.setPlural(plural[1].trim());
        }
        else if(splitted.length == 3) {
            String[] firstSplit = splitted[0].split(" ");
            vokabel.setSingular(firstSplit[0].trim());

            String[] secondSplitt = splitted[2].split(" ");
            int count = 0;
            for(String s : secondSplitt) {
                switch(s) {
                    case "ik":
                        vokabel.setErsteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "du":
                        vokabel.setZweiteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "he/se/dat":
                        vokabel.setDritteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "wi/ji/Se/se":
                        vokabel.setVierteP(selectSplittedWord(secondSplitt, count));
                        break;
                }
                count++;
            }
        }
        int emptyCounter = 0;
        for(String s : vokabel.getWordsAsList()) {
            if(s.equals("")) emptyCounter++;
        }
        if(emptyCounter == vokabel.getWordsAsList().size()) return null;
        else return vokabel;
    }

    private String selectSplittedWord(String[] str, int count) {
        String select = str[count + 1];
        String[] array = select.split("/");
        return array[0].trim();
    }

    private String buildURL(String word) {
        String strUrl = "https://www.platt-wb.de/hoch-platt/?term=" + word;
        return strUrl;
    }

}
