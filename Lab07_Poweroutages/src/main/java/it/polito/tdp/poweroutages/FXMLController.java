/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class FXMLController {

	Model model;
	
	@FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<Nerc> comboBox;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnAnalysis;

    @FXML
    void doAnalysis(ActionEvent event) {
    	txtResult.clear();
    	String inputYears = txtYears.getText();
    	String inputHours = txtHours.getText();
    	if(!this.checkInputNumber(inputYears)) {
    		txtResult.setText("Errore! Inserire un valore numerico intero per il campo 'Years'\n");
    		return;
    	}
    	if(!this.checkInputNumber(inputHours)) {
    		txtResult.setText("Errore! Inserire un valore numerico intero per il campo 'Hours'\n");
    		return;
    	}
    	if(comboBox.getValue() == null) {
    		txtResult.setText("Errore! Selezionare un NERC tra quelli elencati\n");
    		return;
    	}
    	Integer years = Integer.parseInt(inputYears);
    	Integer hours= Integer.parseInt(inputHours);
    	
    	List<PowerOutage> powerOutages = this.model.getWorstCase(comboBox.getValue(), years, hours);
    	if(powerOutages.isEmpty()) {
    		txtResult.setText("Nessuna soluzione trovata\n");
    		return;
    	}
    	txtResult.setText("Il NERC selezionato, " + comboBox.getValue() + ", in un range massimo di "
    			+ years + " anni e con un massimo di ore di blackout pari a " + hours +
    			", ha coinvolto, nel caso peggiore, " + this.model.getCustomersAffected(powerOutages) +
    			" persone, per un totale di " + this.model.getHoursOutage(powerOutages) + " ore di blackout.\n");
    	txtResult.appendText("I guasti considerati sono i seguenti:\n");
    	for(PowerOutage po : powerOutages) {
    		txtResult.appendText(po.toString() + "\n");
    	}
    }
    
    private boolean checkInputNumber(String input) {
    	try {
    		Integer.parseInt(input);
    	} catch(NumberFormatException e) {
    		return false;
    	}
    	if((Integer.parseInt(input) % 1) != 0)
    		return false;
    	return true;
    }
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

	public void setModel(Model model) {
		this.model = model;		
		comboBox.getItems().addAll(model.getNercList());
	}
}
