package edVokabelTrainer.online;

import edVokabelTrainer.objects.Vokabel;
import edVokabelTrainer.objects.VokabelBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLRequest {

    private VokabelBuilder vokabelBuilder = new VokabelBuilder();

    public Vokabel callHttp(String word) {
        try {
            Document doc = Jsoup.connect(buildURL(word)).get();
            //System.out.println(doc.toString());
            //System.out.println("--------------------------------------------------------------------------------------------------------");
            Elements e1 = doc.select("body > div.container > div.col-md-12.search-content > div:nth-child(4) > div:nth-child(2)");
            String sel = e1.text();
            System.out.println(sel);
            System.out.print(word + " hat: ");
            Vokabel vokabel = vokabelBuilder.buildVokabel(sel, word);
            return vokabel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private String buildURL(String word) {
        String strUrl = "https://www.platt-wb.de/hoch-platt/?term=" + word;
        return strUrl;
    }

}
