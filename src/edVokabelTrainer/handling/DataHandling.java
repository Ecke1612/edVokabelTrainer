package edVokabelTrainer.handling;

import com.ed.filehandler.JsonHandler;
import com.ed.filehandler.ObjectSerializer;
import com.ed.filehandler.PlainHandler;
import edVokabelTrainer.Main;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.StoreSettingsObject;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
        Dictonary dictonary = new Dictonary(dicFile.getName());
        try {
            JSONObject jsonObject = jsonHandler.readJsonData(dicFile.getPath());

            JSONObject jsMetaObject = (JSONObject) jsonObject.get("meta");
            dictonary.setDicMetaData(jsonDocHandler.readMeta(jsMetaObject));

            try {
                JSONArray jarray = (JSONArray) jsonObject.get("vokabeln");
                dictonary.setVokabelList(jsonDocHandler.convertJsonToVokList(jarray));
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Fehler beim JSON Vokabeln parsen");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim Metadaten parsen");
        }
        dictonaries.add(dictonary);
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("meta", jsonDocHandler.convertMetaToJson(dictonaries.get(counter).getDicMetaData()));
            jsonObject.put("vokabeln", jsonDocHandler.convertVokListToJson(dictonaries.get(counter).getVokabelList()));
            jsonHandler.writeJsonData(jsonObject, path);
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

    public ArrayList<Dictonary> getDictonaries() {
        return dictonaries;
    }

    public StoreSettingsObject getStoreSettingsObject() {
        return storeSettingsObject;
    }
}
