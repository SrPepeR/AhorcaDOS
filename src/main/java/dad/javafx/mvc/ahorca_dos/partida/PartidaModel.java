package dad.javafx.mvc.ahorca_dos.partida;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PartidaModel {
	
	private IntegerProperty puntuacionObtenida = new SimpleIntegerProperty();
	private StringProperty palabraOculta = new SimpleStringProperty();
	private StringProperty introducido = new SimpleStringProperty();
	
	
	public final IntegerProperty puntuacionObtenidaProperty() {
		return this.puntuacionObtenida;
	}
	
	public final int getPuntuacionObtenida() {
		return this.puntuacionObtenidaProperty().get();
	}
	
	public final void setPuntuacionObtenida(final int puntuacionObtenida) {
		this.puntuacionObtenidaProperty().set(puntuacionObtenida);
	}
	
	public final StringProperty introducidoProperty() {
		return this.introducido;
	}
	
	public final String getIntroducido() {
		return this.introducidoProperty().get();
	}
	
	public final void setIntroducido(final String introducido) {
		this.introducidoProperty().set(introducido);
	}

	public final StringProperty palabraOcultaProperty() {
		return this.palabraOculta;
	}
	

	public final String getPalabraOculta() {
		return this.palabraOcultaProperty().get();
	}
	

	public final void setPalabraOculta(final String palabraOculta) {
		this.palabraOcultaProperty().set(palabraOculta);
	}
	
	
}
