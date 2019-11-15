package edVokabelTrainer.handling;

import edVokabelTrainer.objects.DicMetaData;
import edVokabelTrainer.objects.Vokabel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JsonDocHandler {

    public JsonDocHandler() {

    }

    public JSONObject convertMetaToJson(DicMetaData dicMetaData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sin", dicMetaData.getSin());
        jsonObject.put("pl", dicMetaData.getPl());
        jsonObject.put("ersteS", dicMetaData.getErsteS());
        jsonObject.put("zweiteS", dicMetaData.getZweiteS());
        jsonObject.put("dritteS", dicMetaData.getDritteS());
        jsonObject.put("vierteP", dicMetaData.getVierteP());
        return jsonObject;
    }

    public JSONArray convertVokListToJson(ArrayList<Vokabel> vokabels) {
        JSONArray jsonArray = new JSONArray();
        for(Vokabel v : vokabels) {
            jsonArray.add(convertVokToJson(v));
        }
        return jsonArray;
    }

    private JSONObject convertVokToJson(Vokabel vokabel) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("german", vokabel.getGerman());
        jsonObject.put("foreign", vokabel.getSingular());
        jsonObject.put("plural", vokabel.getPlural());
        jsonObject.put("ersteS", vokabel.getErsteS());
        jsonObject.put("zweiteS", vokabel.getZweiteS());
        jsonObject.put("dritteS", vokabel.getDritteS());
        jsonObject.put("vierteP", vokabel.getVierteP());
        jsonObject.put("sCount", vokabel.getSuccessCount());
        return jsonObject;
    }


    public ArrayList<Vokabel> convertJsonToVokList(JSONArray jsonArray) {
        ArrayList<Vokabel> vokabels = new ArrayList<>();
        for(Object o : jsonArray) {
            vokabels.add(convertJsonToVok((JSONObject) o));
        }
        return vokabels;
    }

    public DicMetaData readMeta(JSONObject jo) {
        DicMetaData dicMetaData = new DicMetaData();
        dicMetaData.setSin(jo.get("sin").toString());
        dicMetaData.setPl(jo.get("pl").toString());
        dicMetaData.setErsteS(jo.get("ersteS").toString());
        dicMetaData.setZweiteS(jo.get("zweiteS").toString());
        dicMetaData.setDritteS(jo.get("dritteS").toString());
        dicMetaData.setVierteP(jo.get("vierteP").toString());
        return dicMetaData;
    }

    private Vokabel convertJsonToVok(JSONObject jsonObject) {
        Vokabel vokabel = new Vokabel(jsonObject.get("german").toString());
        vokabel.setSingular(jsonObject.get("foreign").toString());
        vokabel.setPlural(jsonObject.get("plural").toString());
        vokabel.setErsteS(jsonObject.get("ersteS").toString());
        vokabel.setZweiteS(jsonObject.get("zweiteS").toString());
        vokabel.setDritteS(jsonObject.get("dritteS").toString());
        vokabel.setVierteP(jsonObject.get("vierteP").toString());
        vokabel.setSuccessCount(Integer.parseInt(jsonObject.get("sCount").toString()));
        return vokabel;
    }

}
