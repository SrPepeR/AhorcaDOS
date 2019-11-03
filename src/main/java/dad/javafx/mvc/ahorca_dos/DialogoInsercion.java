package dad.javafx.mvc.ahorca_dos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogoInsercion {
	
	Stage dialog;
	
	Label palabraLabel;
	TextField palabraText;
	Button enviar;
	
	Long posicion;
	
	public DialogoInsercion(Long pos) {
		
		posicion = pos;
		
		dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		
		palabraLabel = new Label("Inserte la palabra");
		
		palabraText = new TextField();
		palabraText.setPromptText("Palabra");
		
		enviar = new Button("ENVIAR");
		enviar.setDefaultButton(true);
		enviar.setOnAction(e -> onEnviarAction());
		
		HBox contenidoBox = new HBox(palabraLabel, palabraText, enviar);
		
		Scene scene = new Scene(new Group(contenidoBox));
		dialog.setScene(scene);
		dialog.showAndWait();
		
	}
	
	private void onEnviarAction() {
		
		String palabra = palabraText.getText();
		
		if (palabra.length() > 0) {
			File palabras = new File("palabras.dat");
			RandomAccessFile random;
			try {
				random = new RandomAccessFile(palabras, "rw");
				random.seek(posicion);
				
				random.writeUTF(palabra);
				
				dialog.close();
			} catch (IOException e) {
			}
		}
	}
	
}
