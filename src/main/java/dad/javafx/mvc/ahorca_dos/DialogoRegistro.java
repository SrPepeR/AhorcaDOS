package dad.javafx.mvc.ahorca_dos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogoRegistro {

	Stage dialog;

	Label nombreLabel;
	TextField nombreText;
	Button enviar;

	Long posicion;

	public DialogoRegistro() {

		dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);

		nombreLabel = new Label("Introduzca un nombre");

		nombreText = new TextField();
		nombreText.setPromptText("Nombre");

		enviar = new Button("ENVIAR");
		enviar.setDefaultButton(true);
		enviar.setOnAction(e -> onEnviarAction());

		HBox contenidoBox = new HBox(nombreLabel, nombreText, enviar);

		Scene scene = new Scene(new Group(contenidoBox));
		dialog.setScene(scene);
		dialog.setOnCloseRequest(e -> onCloseAction());
		dialog.showAndWait();

	}

	private void onCloseAction() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("DEBES INTRODUCIR UN NOMBRE");
		alert.showAndWait();
		dialog.showAndWait();
	}

	private void onEnviarAction() {
		dialog.close();
	}

	public String getNombre() {
		return nombreText.getText();
	}

}
