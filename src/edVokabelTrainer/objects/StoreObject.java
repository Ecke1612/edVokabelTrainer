package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreObject implements Serializable {

    private ArrayList<Dictonary> dictonaries;

    public StoreObject(ArrayList<Dictonary> dictonaries) {
        this.dictonaries = dictonaries;
    }

    public ArrayList<Dictonary> getDictonaries() {
        return dictonaries;
    }
}
