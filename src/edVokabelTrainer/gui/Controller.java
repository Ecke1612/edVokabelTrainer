package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.EntrySet;
import edVokabelTrainer.objects.Tools;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    public Button btn_ok;
    public Label label_out;
    public Label label_vok;
    public TextField textfield;
    public BorderPane borderPane;
    public Label info_label;

    private DataHandling datahandler = new DataHandling();
    private Tools tools = new Tools();
    private int globalRIndex = 0;
    private int learndIndex = 5;
    private Random random = new Random();
    private int pickEntryCounter = 0;
    private boolean vocableActive = false;
    private ArrayList<EntrySet> activeEntrySets;
    private boolean repeatState = false;

    public Controller() {

    }

    public void initialize() {
        if(datahandler.load()) {
            info_label.setText(datahandler.getActiveDictionary().getName() + " geladen");
        }
        setGlobalEventHandler(borderPane);
    }

    public void importDic() {
        datahandler.importDictonary();
    }

    public void startTraining() {
        System.out.println("start training");
        //Collections.shuffle(datahandler.getActiveDictionary().getEntrySets());
        activeEntrySets = datahandler.getActiveDictionary().getEntrySets();
        info_label.setText(activeEntrySets.size() + " Vokablen geladen");
        repeatState = false;
        nextEntry();
    }

    public void startRepetition() {
        System.out.println("start Repetition");
        //Collections.shuffle(datahandler.getActiveDictionary().getEntrySets());
        activeEntrySets = datahandler.getActiveDictionary().getLearndSets();
        info_label.setText(activeEntrySets.size() + " Vokablen geladen");
        repeatState = true;
        nextEntry();
    }

    private void nextEntry() {
        System.out.println("next Entry");
        if(activeEntrySets.size() != 0) {
            EntrySet entrySet = pickEntrySet();
            label_vok.setText(entrySet.getGermanWord());
            vocableActive = true;
        } else {
            label_out.setText("Glückwunsch, alle Vokabeln gelernt");
        }
    }

    private EntrySet pickEntrySet() {
        System.out.println("entrySets size: " + activeEntrySets.size() + "; pickCounter: " + pickEntryCounter);
        int rIndex = random.nextInt(activeEntrySets.size());
        globalRIndex = rIndex;
        return activeEntrySets.get(rIndex);
    }

    private EntrySet pickEntrySet1() {
        ArrayList<EntrySet> entrySets = datahandler.getActiveDictionary().getEntrySets();
        System.out.println("entrySets size: " + entrySets.size() + "; pickCounter: " + pickEntryCounter);
        int rIndex = random.nextInt(entrySets.size());
        if(pickEntryCounter == entrySets.size() - 1) {
            pickEntryCounter = 0;
            return entrySets.get(rIndex);
        } else {
            double rDouble = random.nextDouble();
            double propability = (double) entrySets.get(rIndex).getCorrectTranslated() / (double) learndIndex;
            System.out.println("rDouble = " + rDouble + "; prop: " + propability);
            if (rDouble > propability) {
                globalRIndex = rIndex;
                return entrySets.get(rIndex);
            } else {
                pickEntrySet();
            }
        }
        return null;
    }


    public void check_vok() {
        if(vocableActive) {
            EntrySet entrySet = activeEntrySets.get(globalRIndex);
            String correctAnswer = entrySet.getForeignWord();
            if (textfield.getText().equals(correctAnswer) && !textfield.getText().equals("")) {
                if(!repeatState) {
                    entrySet.countCorrectTranslatedUp();
                    if (entrySet.getCorrectTranslated() >= learndIndex) {
                        datahandler.getActiveDictionary().moveToLearnd(entrySet);
                        info_label.setText(entrySet.getGermanWord() + " wurde als gelernt eingestuft. noch " + activeEntrySets.size() + " Vokablen zu lernen.");
                        System.out.println("Vokabel wurde als gelernt eingestuft");
                    }
                }
                label_out.setTextFill(Color.WHITE);
                label_out.setText("Super! " + entrySet.getForeignWord() + " ist korrekt. (" + entrySet.getCorrectTranslated() + ")");
            } else {
                label_out.setTextFill(Color.rgb(136,0, 21));
                label_out.setText("Leider falsch. " + entrySet.getForeignWord() + " wäre richtig gewesen. (" + entrySet.getCorrectTranslated() + ")");
                entrySet.countCorrectTranslatedDown();
                if(repeatState) {
                    datahandler.getActiveDictionary().moveToEntrySet(entrySet);
                    info_label.setText(entrySet.getGermanWord() + " wurde zurück zu lernen verschoben. noch " + activeEntrySets.size() + " Vokablen zu wiederholen");
                }
            }
            textfield.setText("");
            datahandler.save();
            btn_ok.setText("Nächste");
            vocableActive = false;
        } else {
            btn_ok.setText("Prüfen");
            nextEntry();
            label_out.setText("");
        }
    }


    private void setGlobalEventHandler(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_ok.fire();
                ev.consume();
            }
        });
    }

    public void close() {

    }

}
