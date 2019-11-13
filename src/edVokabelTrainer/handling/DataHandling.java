package edVokabelTrainer.handling;

import com.ed.filehandler.JsonHandler;
import com.ed.filehandler.ObjectSerializer;
import com.ed.filehandler.PlainHandler;
import edVokabelTrainer.Main;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.EntrySet;
import edVokabelTrainer.objects.StoreSettingsObject;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DataHandling {

    private PlainHandler plainHandler = new PlainHandler();
    private JsonHandler jsonHandler = new JsonHandler();
    private JsonDocHandler jsonDocHandler = new JsonDocHandler();
    private ArrayList<Dictonary> dictonaries = new ArrayList<>();
    private ObjectSerializer objectSerializer = new ObjectSerializer();
    private StoreSettingsObject storeSettingsObject = new StoreSettingsObject();

    public DataHandling() {

    }

    public void importDictonary() {
        FileChooser fileChooser = new FileChooser();
        File dicFile = fileChooser.showOpenDialog(null);
        if(dicFile != null) {
            storeSettingsObject.addDicStorePath(dicFile.getPath());
            loadJsonDictionary(dicFile);
        }
    }

    public void loadJsonDictionary(File dicFile) {
        JSONArray jarray = jsonHandler.readJsonArrayData(dicFile.getPath());
        Dictonary dictonary = new Dictonary(dicFile.getName());
        dictonary.setVokabelList(jsonDocHandler.convertJsonToVokList(jarray));
        dictonaries.add(dictonary);
    }

    public void loadDictionary(File dicFile) {
        ArrayList<String> stringlist = plainHandler.fileLoaderUTF(dicFile.getPath());
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
        System.out.println(dicFile.getName() + " wurde mit " + dictonary.getEntrySets().size() + " geladen");
    }


    public Dictonary getActiveDictionary() {
        if(dictonaries.size() > 0) {
            if(storeSettingsObject.getActiveDic() >= dictonaries.size()) {
                System.out.println("reset aciteve Dic to 0");
                //storeSettingsObject.setActiveDic(0);
            }
            //System.out.println("return activ dic: " + storeSettingsObject.getActiveDic());
            //System.out.println("dic Size: " + dictonaries.get(storeSettingsObject.getActiveDic()).getEntrySets().size());
            return dictonaries.get(storeSettingsObject.getActiveDic());
        }
        else return null;
    }

    public boolean dicLoaded() {
        if(getActiveDictionary() == null) return false;
        else return true;
    }

    public void save() {
        if(!plainHandler.fileExist(Main.parentPath + "saves")) {
            plainHandler.createDirs(Main.parentPath + "saves");
        }
        objectSerializer.writeObject(storeSettingsObject, Main.parentPath + "saves/save.dat");
        int counter = 0;
        for(String path : storeSettingsObject.getDictonaryStorePathes()) {
            //plainHandler.fileWriterNewLineUTF(path, dictonaries.get(counter).getListInSaveForm());
            jsonHandler.writeJsonArrayData(jsonDocHandler.convertVokListToJson(dictonaries.get(counter).getVokabelList()), path);
            counter++;
        }
        System.out.println("saved");
    }

    public boolean loadFromFile() {
        if(plainHandler.fileExist(Main.parentPath + "saves/save.dat")) {
            storeSettingsObject = (StoreSettingsObject) objectSerializer.loadObjects(Main.parentPath + "saves/save.dat");
            reload();
            return true;
        } else {
            System.out.println("no saves found.");
            return false;
        }
    }

    public void reload() {
        dictonaries.clear();
        for (String path : storeSettingsObject.getDictonaryStorePathes()) {
            File file = new File(path);
            loadJsonDictionary(file);
            System.out.println("loaded " + file.getName());
        }
    }

    public void resetDictionary() {
        for(EntrySet e : getActiveDictionary().getEntrySets()) {
            e.resetCorrectTranslated();
        }
        System.out.println("dictionary resetted");
    }

    public ArrayList<Dictonary> getDictonaries() {
        return dictonaries;
    }

    public StoreSettingsObject getStoreSettingsObject() {
        return storeSettingsObject;
    }
}
