package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.EntrySet;
import edVokabelTrainer.objects.Tools;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Controller {

    public Button btn_ok;
    public Label label_out;
    public Label label_vok;
    public TextField textfield;
    public BorderPane borderPane;
    public Label info_label;
    public Menu menu_dictionaries;
    public Label label_title;
    public Label label_feedback;
    public Button btn_toggleTraining;

    private DataHandling datahandler = new DataHandling();
    private AddVokWindow addVokWindow = new AddVokWindow();
    private int globalRIndex = 0;
    private int learndIndex = 5;
    private Random random = new Random();
    private int pickEntryCounter = 0;
    private boolean vocableActive = false;
    private ArrayList<EntrySet> activeEntrySets;
    private boolean repeatState = false;
    private int countDics = 0;
    private boolean stateTrainingRunning = false;
    private boolean dicLoaded = false;

    public Controller() {

    }

    public void initialize() {
        if(datahandler.loadFromFile()) {
            if(datahandler.dicLoaded()) {
                info_label.setText(datahandler.getActiveDictionary().getName() + " geladen");
            }
            setTitle();
            changeUIActiveState();
        }
        initMenuDictionaries();
        setGlobalEventHandler(borderPane);
        changeUIActiveState();
    }

    private void setTitle() {
        if(datahandler.dicLoaded()) {
            String[] strArray = datahandler.getActiveDictionary().getName().split("\\.");
            String title = strArray[0];
            label_title.setText(title);
        } else {
            label_title.setText("ED Vokabeltrainer");
        }
    }

    public void importDic() {
        datahandler.importDictonary();
        initMenuDictionaries();
        setTitle();
        changeUIActiveState();
        datahandler.save();
    }

    public void startTraining() {
        if(stateTrainingRunning) {
            stopTraining();
        } else {
            btn_toggleTraining.setText("\uE103");
            System.out.println("start training");
            activeEntrySets = new ArrayList<>(datahandler.getActiveDictionary().getEntrySets());
            info_label.setText(activeEntrySets.size() + " Vokablen geladen");
            repeatState = false;
            stateTrainingRunning = true;
            changeUIActiveState();
            nextEntry();
        }
    }

    public void startRepetition() {
        System.out.println("start Repetition");
        activeEntrySets = new ArrayList<>(datahandler.getActiveDictionary().getLearndSets());
        info_label.setText(activeEntrySets.size() + " Vokablen geladen");
        repeatState = true;
        stateTrainingRunning = true;
        changeUIActiveState();
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

    private void stopTraining() {
        if(activeEntrySets != null) {
            activeEntrySets.clear();
            label_vok.setText("");
            info_label.setText("Übungen wurde gestoppt");
            stateTrainingRunning = false;
            btn_toggleTraining.setText("\uE102");
            changeUIActiveState();
        }
    }

    private void changeUIActiveState() {
        System.out.println("stateTR: " + stateTrainingRunning + "; dicLoaded: " + datahandler.dicLoaded());
        if(datahandler.dicLoaded()) {
            btn_toggleTraining.setDisable(false);
            if (stateTrainingRunning) {
                btn_ok.setDisable(false);
                textfield.setDisable(false);
            } else {
                System.out.println("deactivate UI");
                btn_ok.setDisable(true);
                textfield.setDisable(true);
            }
        } else {
            btn_toggleTraining.setDisable(true);
        }
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
        if(stateTrainingRunning) {
            if (vocableActive) {
                EntrySet entrySet = activeEntrySets.get(globalRIndex);
                String correctAnswer = entrySet.getForeignWord();
                if (textfield.getText().equals(correctAnswer) && !textfield.getText().equals("")) {
                    if (!repeatState) {
                        entrySet.countCorrectTranslatedUp();
                        if (entrySet.getCorrectTranslated() >= learndIndex) {
                            datahandler.getActiveDictionary().moveToLearnd(entrySet);
                            activeEntrySets.remove(entrySet);
                            info_label.setText(entrySet.getGermanWord() + " wurde als gelernt eingestuft. noch " + activeEntrySets.size() + " Vokablen zu lernen.");
                            System.out.println("Vokabel wurde als gelernt eingestuft");
                        }
                    }
                    label_out.setTextFill(Color.WHITE);
                    label_out.setText("Super! " + entrySet.getForeignWord() + " ist korrekt. (" + entrySet.getCorrectTranslated() + ")");
                    label_feedback.setText("\uE001");
                    label_feedback.setTextFill(Color.rgb(147,255,130));
                } else {
                    label_out.setTextFill(Color.rgb(136, 0, 21));
                    label_out.setText("Leider falsch. " + entrySet.getForeignWord() + " wäre richtig gewesen. (" + entrySet.getCorrectTranslated() + ")");
                    label_feedback.setText("\uE106");
                    label_feedback.setTextFill(Color.rgb(136,0,21));
                    entrySet.countCorrectTranslatedDown();
                    if (repeatState) {
                        datahandler.getActiveDictionary().moveToEntrySet(entrySet);
                        activeEntrySets.remove(entrySet);
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
        } else {

        }
    }

    public void initMenuDictionaries() {
        System.out.println("show dics");
        menu_dictionaries.getItems().clear();
        countDics = 0;
        System.out.println("dic size: " + datahandler.getDictonaries().size());
        for(Dictonary d : datahandler.getDictonaries()) {
            int locDic = countDics;
            MenuItem menuItem = new MenuItem(d.getName());
            menuItem.setOnAction(event -> {
                changeDictionarySelection(locDic);
            });
            menu_dictionaries.getItems().add(menuItem);
            countDics++;
        }
    }

    private void changeDictionarySelection(int locDic) {
        stopTraining();
        datahandler.getStoreSettingsObject().setActiveDic(locDic);
        System.out.println("active Dic: " + locDic);
        info_label.setText(datahandler.getActiveDictionary().getName() + " wurde geladen");
        setTitle();
    }

    public void deleteDic() {
        stopTraining();
        datahandler.getStoreSettingsObject().deleteActiveDic();
        datahandler.reload();
        initMenuDictionaries();
        setTitle();
        changeUIActiveState();
        datahandler.save();
    }

    public void addVokabel() {
        addVokWindow.drawWindow(datahandler);
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
