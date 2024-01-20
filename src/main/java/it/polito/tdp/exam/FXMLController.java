package it.polito.tdp.exam;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.exam.model.Model;
import it.polito.tdp.exam.model.Team;
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
    private ComboBox<String> cmbAnno;

    @FXML
    private ComboBox<Team> cmbSquadra;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtSquadre;

    @FXML
    void handleAnno(ActionEvent event) {
    	
    	String anno = this.cmbAnno.getValue();
		int count = this.model.getNmumSquadrePerAnno(anno);
		this.txtSquadre.setText("Squadre per anno: " + anno + ": "+count+  "\n" );
		
		for (Team t: this.model.listaSquadre(anno)) {
			this.txtSquadre.appendText(t.toString());
		}
		this.cmbSquadra.getItems().setAll(this.model.listaSquadre(anno));
		
    }

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String anno = this.cmbAnno.getValue();
    	if (anno == null) {
    		this.txtResult.setText("Inserire prima un anno dal menù a tendina! \n");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	this.txtResult.setText("Grafo corretamante creato! \n");
    	this.txtResult.appendText("Il grafo ha: #vertici: " + this.model.numVertex());
    	this.txtResult.appendText(" e #archi: " + this.model.numEdge());
    	
    }

    @FXML
    void handleDettagli(ActionEvent event) {
    	Team squadra = this.cmbSquadra.getValue();
    	if (squadra == null) {
    		this.txtResult.setText("Inserire prima una squadra dal menù a tendina! \n");
    		return;
    	}
    	this.model.stampaAdiacenti(squadra);
    	//for (Pair<Team, Double> t: this.model.stampaAdiacenti(squadra)) {
    		this.txtResult.appendText("Adiacenti per la squadra " + squadra + " : " + this.model.stampaAdiacenti(squadra).toString() + "\n");
    	//}

    	

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
		this.cmbAnno.getItems().setAll(this.model.getAnno());
		
	}

}
