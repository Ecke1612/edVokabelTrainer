package edVokabelTrainer;

import com.ed.filehandler.PlainHandler;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class DataHandling {

    private PlainHandler plainHandler = new PlainHandler();
    private ArrayList<Dictonary> dictonaries = new ArrayList<>();

    public DataHandling() {

    }

    public void loadDictonary() {

    }

    public void importDictonary() {
        FileChooser fileChooser = new FileChooser();
        File dicFile = fileChooser.showOpenDialog(null);
        if(dicFile != null) {
        ArrayList<String> stringlist = plainHandler.fileLoader(null);
            dictonaries.add(new Dictonary())
            for (String s : stringlist) {
                System.out.println(s);
            }
        }
    }

}
