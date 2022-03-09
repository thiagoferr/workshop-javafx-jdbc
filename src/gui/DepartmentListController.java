package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/DepartmentForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
	}
	
//Este é um metodo padrão do javaFX para iniciar o comportamento das colunas
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//O comando abaixo serve para que o tableView ocupe todo o espaço da janela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView () {
		if(service==null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
//O metodo abaixo recebe uma referencia para um Stage que criou a janela de dialogo
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
//É necessario informar o nome da View que o metodo ira carregar (absoluteName) passada por parametro
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
//Quando vamos carregar uma janela de dialogo modal na frente da janela existente, é preciso instanciar um 
//novo Stage (um palco na frente do outro)
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");//configura o titulo da janela
			dialogStage.setScene(new Scene(pane));//cria uma nova cena com elemento raiz 'pane'
			dialogStage.setResizable(false);//desativa a possibilidade de redimensionar a janela
			dialogStage.initOwner(parentStage);//associa esta janela com o 'pararentStage' (janela pai)
			dialogStage.initModality(Modality.WINDOW_MODAL);//configura se a janela sera modal ou tera outro comportamento (no caso será modal sendo travada enquanto não fechar ela não é possivel acessar a janela anterior)			
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
