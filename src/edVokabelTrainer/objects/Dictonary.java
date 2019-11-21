package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<Vokabel> vokabelList = new ArrayList<>();
    private ArrayList<Vokabel> learndVokabelList = new ArrayList<>();
    private String name;
    private DicMetaData dicMetaData = new DicMetaData();

    public Dictonary(String name) {
        this.name = name;
    }

    public void addVokabel(Vokabel vokabel) {
        vokabelList.add(vokabel);
    }

    public void addLearndVokabel(Vokabel vokabel) {
        learndVokabelList.add(vokabel);
    }

    public ArrayList<Vokabel> getVokabelList() {
        return vokabelList;
    }

    public void moveToLearnd(Vokabel vokabel) {
        vokabelList.remove(vokabel);
        learndVokabelList.add(vokabel);
    }

    public void moveToEntrySet(Vokabel vokabel) {
        learndVokabelList.remove(vokabel);
        vokabelList.add(vokabel);
    }

    public ArrayList<Vokabel> getLearndVokabelList() {
        return learndVokabelList;
    }

    public void setLearndVokabelList(ArrayList<Vokabel> learndVokabelList) {
        this.learndVokabelList = learndVokabelList;
    }

    public DicMetaData getDicMetaData() {
        return dicMetaData;
    }

    public void setDicMetaData(DicMetaData dicMetaData) {
        this.dicMetaData = dicMetaData;
    }

    public void setVokabelList(ArrayList<Vokabel> vokabelList) {
        this.vokabelList = vokabelList;
    }

    public String getName() {
        return name;
    }
}
