package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreSettingsObject implements Serializable {

    private ArrayList<String> dictonaryStorePathes = new ArrayList<>();

    public void addDicStorePath(String path) {
        dictonaryStorePathes.add(path);
    }

    public ArrayList<String> getDictonaryStorePathes() {
        return dictonaryStorePathes;
    }
}
