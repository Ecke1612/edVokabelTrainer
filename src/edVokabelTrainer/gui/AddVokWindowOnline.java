package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.Vokabel;
import edVokabelTrainer.online.HTMLRequest;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddVokWindowOnline {

    private ArrayList<TextField> textFields = new ArrayList<>();
    private HTMLRequest htmlRequest = new HTMLRequest();

    public void drawWindow(DataHandling datahandler) {
        textFields.clear();

        Stage addVokStage = new Stage();
        addVokStage.setTitle("Vokabel hinzufügen");
        VBox mainVbox = new VBox(10);
        Scene scene = new Scene(mainVbox);

        VBox vBoxContent = new VBox(10);
        HBox table = new HBox(10);
        VBox leftSide = new VBox(10);
        VBox rightSide = new VBox(10);
        HBox hboxBtn = new HBox(10);


        mainVbox.setPadding(new Insets(15));
        hboxBtn.setAlignment(Pos.BOTTOM_RIGHT);

        Label labelForeign = new Label("Fremdwort");
        Label labelGerman = new Label("Deutsch");

        TextField txtGerman = new TextField();

        Button btn_search = new Button("Suchen");
        Button btn_abort = new Button("Abbrechen");
        Button btn_save = new Button("Speichern");


        Label labelOutput = new Label();

        btn_search.setOnAction(event -> {
            labelOutput.setText("Suche...");
            searchVokabel(txtGerman.getText());
            labelOutput.setText("Suche abgeschlossen");
        });

        btn_save.setOnAction(event -> {
            btn_search.setText("Speichern");
            saveVokabel(datahandler, getVokabelByFields(labelOutput, txtGerman.getText()));
            btn_search.setText("Suchen");
            labelOutput.setText("Vokabel gespeichert");
        });

        btn_abort.setOnAction(event -> addVokStage.close());


        rightSide.getChildren().addAll(labelForeign, getLabel_Field("Singular"), getLabel_Field("Plural"), getLabel_Field("ik"),
                getLabel_Field("du"), getLabel_Field("he/se/dat"), getLabel_Field("wi/ji/Se/se"));
        leftSide.getChildren().addAll(labelGerman, txtGerman);
        table.getChildren().addAll(leftSide, rightSide);
        hboxBtn.getChildren().addAll(btn_abort, btn_search, btn_save);
        vBoxContent.getChildren().addAll(table, hboxBtn, labelOutput);
        mainVbox.getChildren().addAll(vBoxContent);

        mainVbox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_search.fire();
                ev.consume();
            }
        });

        addVokStage.setScene(scene);
        addVokStage.setAlwaysOnTop(true);
        addVokStage.showAndWait();
    }

    private HBox getLabel_Field(String text) {
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        Label label = new Label(text + ":");
        TextField textField = new TextField();
        HBox hboxSpaceing = new HBox();
        HBox.setHgrow(hboxSpaceing, Priority.ALWAYS);
        hbox.getChildren().addAll(label, hboxSpaceing, textField);
        textFields.add(textField);
        return hbox;
    }

    private void searchVokabel(String germanWord) {
        Vokabel vokabel = htmlRequest.callHttp(germanWord);
        textFields.get(0).setText(vokabel.getSingular());
        textFields.get(1).setText(vokabel.getPlural());
        textFields.get(2).setText(vokabel.getErsteS());
        textFields.get(3).setText(vokabel.getZweiteS());
        textFields.get(4).setText(vokabel.getDritteS());
        textFields.get(5).setText(vokabel.getVierteP());
    }

    private Vokabel getVokabelByFields(Label labelOut, String german) {
        int emptyCounter = 0;
        int count = 0;
        for(TextField t : textFields) {
            if(t.getText().equals("")) emptyCounter++;
            System.out.println(count + "/ " + textFields.size());
            count++;
        }
        if(emptyCounter == textFields.size()) {
            labelOut.setText("mindestens ein Feld muss ausgefüllt sein");
        } else {
            Vokabel vokabel = new Vokabel(german);
            vokabel.setSingular(textFields.get(0).getText());
            vokabel.setPlural(textFields.get(1).getText());
            vokabel.setErsteS(textFields.get(2).getText());
            vokabel.setZweiteS(textFields.get(3).getText());
            vokabel.setDritteS(textFields.get(4).getText());
            vokabel.setVierteP(textFields.get(5).getText());
            return vokabel;
        }
        return null;
    }

    private void saveVokabel(DataHandling dataHandling, Vokabel vokabel) {
        dataHandling.getActiveDictionary().addVokabel(vokabel);
        dataHandling.save();
    }

}
