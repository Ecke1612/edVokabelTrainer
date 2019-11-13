package edVokabelTrainer.handling;

import edVokabelTrainer.objects.Vokabel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JsonDocHandler {

    public JsonDocHandler() {

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
