package edVokabelTrainer.handling;

import com.ed.filehandler.ObjectSerializer;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.StoreObject;
import javafx.stage.FileChooser;
import com.ed.filehandler.PlainFileHandler;

import java.io.File;
import java.util.ArrayList;

public class DataHandling {

    private PlainFileHandler plainHandler = new PlainFileHandler();
    private ArrayList<Dictonary> dictonaries = new ArrayList<>();
    private ObjectSerializer objectSerializer = new ObjectSerializer();

    public DataHandling() {

    }

    public void loadDictonary() {

    }

    public void importDictonary() {
        FileChooser fileChooser = new FileChooser();
        File dicFile = fileChooser.showOpenDialog(null);
        if(dicFile != null) {
            ArrayList<String> stringlist = plainHandler.fileLoader(dicFile.getPath());
            Dictonary dictonary = new Dictonary(dicFile.getName());
            dictonaries.add(dictonary);
            for (String s : stringlist) {
                String[] str = s.split(";");
                dictonary.addEntry(str[0], str[1]);
            }
        }
    }

    public Dictonary getActiveDictionary() {
        if(dictonaries.size() > 0) {
            return dictonaries.get(0);
        }
        else return null;
    }

    public void save() {
        if(!plainHandler.fileExist("saves")) {
            plainHandler.createDir("saves");
        }
        StoreObject storeObject = new StoreObject(dictonaries);
        objectSerializer.writeObject(storeObject, "saves/save.dat");
        System.out.println("saved");
    }

    public void load() {
        StoreObject storeObject = (StoreObject) objectSerializer.loadObjects("saves/save.dat");
        dictonaries = storeObject.getDictonaries();
        System.out.println("loaded");
    }

}
