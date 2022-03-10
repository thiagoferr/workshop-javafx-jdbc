package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

		@FXML
		private TextField txtId;
		
		@FXML
		private TextField txtName;
		
		@FXML
		private Label labelErrorName;
		
		@FXML
		private Button btCancel;
		
		@FXML 
		private Button btSave;
		
		public void onBtSaveAction() {
			System.out.println("onBtSaveAction");
		}
		
		public void onBtCancelAction() {
			System.out.println("onBtCancelAction");
		}
		

		@Override
		public void initialize(URL url, ResourceBundle rb) {
			initializeNodes();
		}
		
		private void initializeNodes() {
			Constraints.setTextFieldInteger(txtId);//s� aceita numero inteiros
			Constraints.setTextFieldMaxLength(txtName, 30);//maximo 30 caracteres
		}

}
