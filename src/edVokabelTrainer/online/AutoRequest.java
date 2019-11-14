package edVokabelTrainer.online;

import com.ed.filehandler.PlainHandler;
import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.Vokabel;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class AutoRequest {

    private PlainHandler plainHandler = new PlainHandler();
    private HTMLRequest htmlRequest = new HTMLRequest();

    public void autoRequestPerFile(DataHandling dataHandling) {
        FileChooser fileChooser = new FileChooser();
        File textFile = fileChooser.showOpenDialog(null);
        ArrayList<String> list = new ArrayList<>();
        if(textFile != null) {
            list = plainHandler.fileLoaderUTF(textFile.getPath());
        }
        iterateList(list, dataHandling);
        dataHandling.save();
    }

    private void iterateList(ArrayList<String> list, DataHandling dataHandling) {
        for(String s : list) {
            Vokabel vokabel = htmlRequest.callHttp(s);
            dataHandling.getActiveDictionary().addVokabel(vokabel);
            System.out.println("german: " + vokabel.getGerman());
            System.out.println("singular: " + vokabel.getSingular());
            System.out.println("1. sin: " + vokabel.getErsteS());
            System.out.println("2. Sin: " + vokabel.getZweiteS());
            System.out.println("3. Sin: " + vokabel.getDritteS());
            System.out.println("4. Pl: " + vokabel.getVierteP());
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("");
        }
    }

}
