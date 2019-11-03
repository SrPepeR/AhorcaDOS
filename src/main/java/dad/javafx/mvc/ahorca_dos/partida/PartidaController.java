package dad.javafx.mvc.ahorca_dos.partida;

import java.io.IOException;
import java.util.ArrayList;

import dad.javafx.mvc.ahorca_dos.DialogoRegistro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PartidaController {
	
	//VIEW
	@FXML
	VBox contenedorBox;
	
		@FXML
		HBox encabezadoBox;
			@FXML
			ImageView ahorcadoImage;
			@FXML
			HBox puntuacionBox;
				@FXML
				Label puntuacionLabel;
				@FXML
				Label puntuacionObtenidaLabel;
				
		@FXML
		Label palabraLabel;
		
		@FXML
		HBox letrasBox;
			@FXML
			Label acertadasLabel;
			@FXML
			Label falladasLabel;
		
		@FXML
		HBox introducirBox;
			@FXML
			TextField introducidoText;
			@FXML
			Button letraButton;
			@FXML
			Button resolverButton;
	
	//MODEL
	PartidaModel model = new PartidaModel();
	
	
	Partida partida = new Partida();
	
	ArrayList<Character> palabra;
	ArrayList<Character> letras;
	
	String palabraAdivinar = "";
	String palabraOculta = "";
	int fallos = 0;
	
	String nombre = "";
	
	public PartidaController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PartidaView.fxml"));
 		loader.setController(this);
		loader.load();
		
		pedirNombre();
		
		nuevaRonda();
		
		//BINDEOS
		model.introducidoProperty().bind(introducidoText.textProperty());
		puntuacionObtenidaLabel.textProperty().bind(model.puntuacionObtenidaProperty().asString());
		model.palabraOcultaProperty().bindBidirectional(palabraLabel.textProperty());
		model.palabraOcultaProperty().addListener((o, ov, nv) -> comprobarVictoria(nv));
		
		//ACTIONS
		letraButton.setOnAction(e -> onLetraAction());
		resolverButton.setOnAction(e -> onResolverAction());
		
	}
	
	public void pistasInicialesC() {
		char pista = partida.generaConsonante();
		ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, pista);
		
		if (partida.noEstaRepetidaLetra(pista, letras)) {
			letras.add(pista);
			if (Integer.parseInt(comprobacion.get(0)) > 0) {
				palabraOculta = comprobacion.get(1);
				palabraLabel.setText(palabraOculta);
				acertadasLabel.setText(acertadasLabel.getText() + pista + " ");
			}else {
				falladasLabel.setText(falladasLabel.getText() + pista + " ");
			}
		}else {
			pistasInicialesC();
		}
	}
	
	public void pistasInicialesV() {
		char pista = partida.generaVocal();
		ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, pista);
		
		if (partida.noEstaRepetidaLetra(pista, letras)) {
			letras.add(pista);
			if (Integer.parseInt(comprobacion.get(0)) > 0) {
				palabraOculta = comprobacion.get(1);
				palabraLabel.setText(palabraOculta);
				acertadasLabel.setText(acertadasLabel.getText() + pista + " ");
			}else {
				falladasLabel.setText(falladasLabel.getText() + pista + " ");
			}
		}else {
			pistasInicialesV();
		}
	}
	
	public void cambiarImagen() {
		ahorcadoImage.setImage(new Image(getClass().getResource("/imagenes/" + (fallos+1) + ".png").toString()));
	}

	private void onLetraAction() {
		if (model.getIntroducido().length() == 1) {
			char introducido = model.getIntroducido().charAt(0);
			if (!comprobarLetras(introducido)) {
				letras.add(model.getIntroducido().charAt(0));
				ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, introducido);
				if (Integer.parseInt(comprobacion.get(0)) > 0) {
					palabraOculta = comprobacion.get(1);
					palabraLabel.setText(palabraOculta);
					acertadasLabel.setText(acertadasLabel.getText() + introducido + " ");
					model.setPuntuacionObtenida(model.getPuntuacionObtenida() + Integer.parseInt(comprobacion.get(0)));
				}else {
					falladasLabel.setText(falladasLabel.getText() + introducido + " ");
					fallos();
				}
			}
		}
		introducidoText.clear();
	}
	
	private boolean comprobarLetras(char letra) {
		for (int i = 0; i < letras.size(); i++) {
			if (letras.get(i) == letra) {
				return true;
			}
		}
		return false;
	}
	
	private void onResolverAction() {
		if (model.getIntroducido().length() > 1) {
			String introducido = model.getIntroducido();
			if (introducido.equals(palabraAdivinar)) {
				model.setPuntuacionObtenida(model.getPuntuacionObtenida() + 10);
				siguienteRonda();
			}else {
				falladasLabel.setText(falladasLabel.getText() + introducido + " ");
				fallos();
				fallos();
			}
		}
		introducidoText.clear();
	}
	
	private void fallos() {
		fallos++;
		cambiarImagen();
		if (fallos >= 8) {
			fin();
		}
	}
	
	private void fin() {
		Alert fin = new Alert(AlertType.ERROR);
		fin.setTitle("Ahorcado");
		fin.setHeaderText("Ha terminado la partida " + nombre);
		fin.setContentText("Tu puntuación es de " + model.getPuntuacionObtenida() + " vuelve a intentarlo.");
		fin.showAndWait();
		
		partida.guardarPuntuacion(model.getPuntuacionObtenida(), nombre);
		
		model.setPuntuacionObtenida(0);
		fallos = 0;
		nombre = "";
		cambiarImagen();
		pedirNombre();
		nuevaRonda();
	}
	
	private void comprobarVictoria(String nv) {
		if (nv.equals(palabraAdivinar)) {
			siguienteRonda();
		}
	}
	
	private void siguienteRonda() {
		
		Alert ganado = new Alert(AlertType.CONFIRMATION);
		ganado.setTitle("Ahorcado");
		ganado.setHeaderText("HAS ACERTADO!!");
		ganado.setContentText("Va a empezar otra ronda, sigue así!!");
		ganado.showAndWait();
		
		nuevaRonda();
		
	}
	
	private void pedirNombre() {
		DialogoRegistro registro = new DialogoRegistro();
		
		nombre = registro.getNombre();
	}
	
	private void nuevaRonda() {
		
		palabra = new ArrayList<Character>();
		letras = new ArrayList<Character>();
		palabraOculta = "";
		
		acertadasLabel.setText("Palabras acertadas" + '\n');
		falladasLabel.setText("Palabras falladas" + '\n');
		
		palabraAdivinar = partida.palabraAleatoria();
		
		for (int i = 0; i < palabraAdivinar.length(); i++) {
			palabra.add(palabraAdivinar.charAt(i));
		}
		
		for (int i = 0; i < palabraAdivinar.length(); i++) {
			if (palabraAdivinar.charAt(i) == ' ') {
				palabraOculta += " ";
			}else {
				palabraOculta += "_";
			}
		}
		
		palabraLabel.setText(palabraOculta);
		
		pistasInicialesC();
		pistasInicialesC();
		pistasInicialesC();
		pistasInicialesV();
		
	}

	public VBox getRoot() {
		return contenedorBox;
	}
	
}
