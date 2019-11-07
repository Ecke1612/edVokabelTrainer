package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreSettingsObject implements Serializable {

    private ArrayList<String> dictonaryStorePathes = new ArrayList<>();
    private int activeDic = 0;

    public void addDicStorePath(String path) {
        dictonaryStorePathes.add(path);
    }

    public ArrayList<String> getDictonaryStorePathes() {
        return dictonaryStorePathes;
    }

    public void deleteActiveDic() {
        dictonaryStorePathes.remove(activeDic);
        if(activeDic > 0) {
            activeDic--;
        }
    }

    public int getActiveDic() {
        return activeDic;
    }

    public void setActiveDic(int activeDic) {
        System.out.println("active Dic set to " + activeDic);
        this.activeDic = activeDic;
    }
}
