package it.polito.tdp.exam;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.exam.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Model model;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDettagli;

    @FXML
    private Button btnPercorso;

    @FXML
    private ComboBox<?> cmbAnno;

    @FXML
    private ComboBox<?> cmbSquadra;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtSquadre;

    @FXML
    void handleAnno(ActionEvent event) {

    }

    @FXML
    void handleCreaGrafo(ActionEvent event) {

    }

    @FXML
    void handleDettagli(ActionEvent event) {

    }

    @FXML
    void handlePercorso(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDettagli != null : "fx:id=\"btnDettagli\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSquadre != null : "fx:id=\"txtSquadre\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
		
	}

}
