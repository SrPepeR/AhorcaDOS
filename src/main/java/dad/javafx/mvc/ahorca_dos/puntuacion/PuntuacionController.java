package dad.javafx.mvc.ahorca_dos.puntuacion;

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

public class PuntuacionController {

	@FXML
	HBox contenedorBox;

	@FXML
	ListView<String> puntuacionList;
	@FXML
	VBox botonesBox;
		@FXML
		Button deleteButton;
		@FXML
		Button actualizarButton;

	Long tamañoArchivo = (long) 0;

	DialogoInsercion dialogo;

	public PuntuacionController() throws IOException {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PuntuacionView.fxml"));
			loader.setController(this);
			loader.load();

			rellenarPuntuaciones();
			
			deleteButton.setOnAction(e -> onDeleteAction());
			actualizarButton.setOnAction(e -> rellenarPuntuaciones());
			
		}

	private void onDeleteAction() {
		if (!puntuacionList.getSelectionModel().getSelectedItem().isEmpty()) {
			borrarPuntuacion();

			rellenarPuntuaciones();
		}
	}

	private void rellenarPuntuaciones() {

		File palabras = new File("puntuaciones.dat");
		RandomAccessFile random;
		
		String aux = "";
		
		try {
			random = new RandomAccessFile(palabras, "r");
			tamañoArchivo = random.length();
			puntuacionList.getItems().clear();
			while (true) {
				aux = random.readUTF() + " ";
				aux += String.valueOf(random.readInt());
				puntuacionList.getItems().add(aux);
			}
		} catch (IOException e) {
		}
	}

	private void borrarPuntuacion() {

		File palabras = new File("puntuaciones.dat");
		RandomAccessFile random;

		ArrayList<String> puntuacion = new ArrayList<String>();

		try {
			random = new RandomAccessFile(palabras, "rw");
			for (int i = 0; i < puntuacionList.getItems().size(); i++) {
				if (!puntuacionList.getItems().get(i).equals(puntuacionList.getSelectionModel().getSelectedItem())) {
					puntuacion.add(puntuacionList.getItems().get(i));
				}
			}
			random.setLength(0);
			for (int i = 0; i < puntuacion.size(); i++) {
				random.writeUTF(puntuacion.get(i));
			}
			tamañoArchivo = random.length();
		} catch (IOException e) {
		}
	}

	public HBox getRoot() {
		return this.contenedorBox;
	}

}
