package edVokabelTrainer.online;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLRequest {

    public void callHttp(String word) {
        try {
            Document doc = Jsoup.connect(buildURL(word)).get();
            //System.out.println(doc.toString());
            System.out.println("");
            Elements e1 = doc.select("body > div.container > div.col-md-12.search-content > div:nth-child(4) > div:nth-child(2)");
            //Elements sel = e1.after()
            System.out.println("result:::");
            System.out.println(sel.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildURL(String word) {
        String strUrl = "https://www.platt-wb.de/hoch-platt/?term=" + word;
        return strUrl;
    }

}
