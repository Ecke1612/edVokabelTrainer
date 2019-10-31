package edVokabelTrainer.objects;

import java.util.ArrayList;
import java.util.Collections;

public class Tools {

    public ArrayList<Integer> getRandomIntArray(int length) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

}
