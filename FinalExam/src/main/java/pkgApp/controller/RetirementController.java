package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// DONE!
		
		hmTextFieldRegEx.put(txtYearsToWork, "\\d*?");
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "\\d*(\\.\\d*)?");
		hmTextFieldRegEx.put(txtYearsRetired, "\\d*?");
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "(\\.\\d*)?");
		hmTextFieldRegEx.put(txtRequiredIncome, "\\d*?");
		hmTextFieldRegEx.put(txtMonthlySSI, "\\d*(\\.\\d*)?");

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					// If newPropertyValue = true, then the field HAS FOCUS
					// If newPropertyValue = false, then field HAS LOST FOCUS
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}
	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");

		txtSaveEachMonth.setDisable(true);
		txtWhatYouNeedToSave.setDisable(true);

		txtYearsToWork.clear();
		txtYearsToWork.setDisable(false);

		// DONE!
		
		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			txtField.clear();
			txtField.setDisable(false);
		}
	}

	@FXML
	public void btnCalculate() {
		// DONE!
		
		String ARW = txtAnnualReturnWorking.getText();
		double ARWvalue = Double.parseDouble(ARW);
		String YTW = txtYearsToWork.getText();
		int YTWvalue = Integer.parseInt(YTW);
		String RI = txtRequiredIncome.getText();
		int RIvalue = Integer.parseInt(RI);
		String MSSI = txtMonthlySSI.getText();
		int MSSIvalue = Integer.parseInt(MSSI);
		String YR = txtYearsRetired.getText();
		int YRvalue = Integer.parseInt(YR);
		String ARR = txtAnnualReturnRetired.getText();
		double ARRvalue = Double.parseDouble(ARR);		
		Retirement r = new Retirement(YTWvalue, ARWvalue, YRvalue, ARRvalue, RIvalue, MSSIvalue);

		System.out.println("calculating");

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);
		
		txtWhatYouNeedToSave.setText("" + r.TotalAmountToSave());
		txtSaveEachMonth.setText("" + r.MonthlySavings());

	}
}
