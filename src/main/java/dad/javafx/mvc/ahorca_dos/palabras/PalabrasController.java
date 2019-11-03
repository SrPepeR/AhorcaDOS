package dad.javafx.mvc.ahorca_dos.palabras;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import dad.javafx.mvc.ahorca_dos.DialogoInsercion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PalabrasController {

	@FXML
	HBox contenedorBox;

	@FXML
	ListView<String> palabrasList;
	@FXML
	VBox botonesBox;
	@FXML
	Button addButton;
	@FXML
	Button deleteButton;
	
	Long tamañoArchivo = (long) 0;
	
	DialogoInsercion dialogo;

	public PalabrasController() throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PalabrasView.fxml"));
		loader.setController(this);
		loader.load();

		rellenarPalabras();
		
		addButton.setOnAction(e -> onAddAction());
		deleteButton.setOnAction(e -> onDeleteAction());
	}

	private void onAddAction() {
		dialogo = new DialogoInsercion(tamañoArchivo);
		rellenarPalabras();
	}
	
	private void onDeleteAction() {
		if (!palabrasList.getSelectionModel().getSelectedItem().isEmpty()) {
			borrarPalabra();
			
			rellenarPalabras();
		}
	}

	private void rellenarPalabras() {

		File palabras = new File("palabras.dat");
		RandomAccessFile random;
		try {
			random = new RandomAccessFile(palabras, "r");
			tamañoArchivo = random.length();
			palabrasList.getItems().clear();
			while (true) {
				palabrasList.getItems().add(random.readUTF());
			}
		} catch (IOException e) {
		}
	}
	
	private void borrarPalabra() {

		File palabras = new File("palabras.dat");
		RandomAccessFile random;
		
		ArrayList<String> palabra = new ArrayList<String>();
		
		try {
			random = new RandomAccessFile(palabras, "rw");
			for (int i = 0; i < palabrasList.getItems().size(); i++) {
				if (!palabrasList.getItems().get(i).equals(palabrasList.getSelectionModel().getSelectedItem())) {
					palabra.add(palabrasList.getItems().get(i));
				}
			}
			random.setLength(0);
			for (int i = 0; i < palabra.size(); i++) {
				random.writeUTF(palabra.get(i));
			}
			tamañoArchivo = random.length();
		} catch (IOException e) {
		}
	}

	public HBox getRoot() {
		return this.contenedorBox;
	}

}
