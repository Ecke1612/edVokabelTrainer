package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddVokWindow {

    public void drawWindow(DataHandling datahandler) {
        Stage addVokStage = new Stage();
        addVokStage.setTitle("Vokabel hinzufügen");
        VBox mainVbox = new VBox(10);
        Scene scene = new Scene(mainVbox);

        VBox vBoxContent = new VBox(10);
        HBox hboxInput = new HBox(10);
        VBox vboxInput1 = new VBox(5);
        VBox vboxInput2 = new VBox(5);
        HBox hboxBtn = new HBox(10);

        mainVbox.setPadding(new Insets(15));
        hboxBtn.setAlignment(Pos.BOTTOM_RIGHT);

        Label labelForeign = new Label("Fremdwort");
        Label labelGerman = new Label("Deutsch");

        TextField txtForeign = new TextField();
        TextField txtGerman = new TextField();

        Button btn_okay = new Button("Hinzufügen");
        Button btn_abort = new Button("Abbrechen");

        Label labelOutput = new Label();

        btn_okay.setOnAction(event -> {
            addVokToList(datahandler, txtForeign.getText(), txtGerman.getText(), labelOutput);
            txtForeign.setText("");
            txtGerman.setText("");
            txtForeign.requestFocus();
        });
        btn_abort.setOnAction(event -> addVokStage.close());

        vboxInput1.getChildren().addAll(labelForeign, txtForeign);
        vboxInput2.getChildren().addAll(labelGerman, txtGerman);
        hboxInput.getChildren().addAll(vboxInput1, vboxInput2);
        hboxBtn.getChildren().addAll(btn_abort, btn_okay);
        vBoxContent.getChildren().addAll(hboxInput, hboxBtn, labelOutput);
        mainVbox.getChildren().addAll(vBoxContent);

        mainVbox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_okay.fire();
                ev.consume();
            }
        });

        addVokStage.setScene(scene);
        addVokStage.setAlwaysOnTop(true);
        addVokStage.showAndWait();
    }

    private void addVokToList(DataHandling datahandler, String foreign, String german, Label labelOutput) {
        if(!foreign.equals("") && !german.equals("")) {
            datahandler.getActiveDictionary().addEntry(foreign, german);
            labelOutput.setText(foreign + " hinzugefügt");
            datahandler.save();
        } else {
            labelOutput.setText("Vokabel konnte nicht hinzugefögt werden");
        }
    }

}
