package edVokabelTrainer.handling;

import com.ed.filehandler.ObjectSerializer;
import com.ed.filehandler.PlainHandler;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.EntrySet;
import edVokabelTrainer.objects.StoreObject;
import edVokabelTrainer.objects.StoreSettingsObject;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DataHandling {

    private PlainHandler plainHandler = new PlainHandler();
    private ArrayList<Dictonary> dictonaries = new ArrayList<>();
    private ObjectSerializer objectSerializer = new ObjectSerializer();
    private StoreSettingsObject storeSettingsObject = new StoreSettingsObject();

    public DataHandling() {

    }

    public void loadDictonary() {

    }

    public void importDictonary() {
        FileChooser fileChooser = new FileChooser();
        File dicFile = fileChooser.showOpenDialog(null);
        if(dicFile != null) {
            storeSettingsObject.addDicStorePath(dicFile.getPath());
            loadDictionary(dicFile);
        }
    }

    private void loadDictionary(File dicFile) {
        ArrayList<String> stringlist = plainHandler.fileLoader(dicFile.getPath());
        Dictonary dictonary = new Dictonary(dicFile.getName());
        dictonaries.add(dictonary);
        for (String s : stringlist) {
            String[] str = s.split(";");
            if(str.length == 2) dictonary.addEntry(str[0], str[1]);
            else if(str.length == 3) {
                dictonary.addEntry(str[0], str[1], Integer.parseInt(str[2]), 5);
            }
            else {
                System.out.println("Import Parsing Error - WRONG ARRAY LENGTH: " + str.length);
                System.out.println(Arrays.toString(str));
            }
        }
    }



    public void updateDictonary1() {
        FileChooser fileChooser = new FileChooser();
        File dicFile = fileChooser.showOpenDialog(null);
        if(dicFile != null) {
            System.out.println("Wörterbuch update wird ausgeführt");
            ArrayList<String> stringlist = plainHandler.fileLoader(dicFile.getPath());
            for (String s : stringlist) {
                String[] str = s.split(";");
                if (str.length != 2) {
                    System.out.println("Import Parsing Error - WRONG ARRAY LENGTH: " + str.length);
                    System.out.println(Arrays.toString(str));
                }
                boolean entryAlreadyInList = false;
                for(EntrySet e : getActiveDictionary().getEntrySets()) {
                    if(e.getForeignWord().equals(str[0]) && e.getGermanWord().equals(str[1])) {
                        entryAlreadyInList = true;
                    }
                }
                if(!entryAlreadyInList) {
                    getActiveDictionary().addEntry(str[0], str[1]);
                    System.out.println(str[0] + "; " + str[1] + " - hinzugefügt");
                }
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
        objectSerializer.writeObject(storeSettingsObject, "saves/save.dat");
        int counter = 0;
        for(String path : storeSettingsObject.getDictonaryStorePathes()) {
            plainHandler.fileWriterNewLine(path, dictonaries.get(counter).getListInSaveForm());
            counter++;
        }
        System.out.println("saved");
    }

    public boolean load() {
        if(plainHandler.fileExist("saves/save.dat")) {
            storeSettingsObject = (StoreSettingsObject) objectSerializer.loadObjects("saves/save.dat");
            for(String pathe : storeSettingsObject.getDictonaryStorePathes()) {
                File file = new File(pathe);
                loadDictionary(file);
                System.out.println("loaded " + file.getName());
            }
            return true;
        } else {
            System.out.println("no saves found.");
            return false;
        }
    }

}
