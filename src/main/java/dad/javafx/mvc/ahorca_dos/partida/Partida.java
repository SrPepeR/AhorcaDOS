package dad.javafx.mvc.ahorca_dos.partida;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Partida {
	
	public ArrayList<String> comprobar(ArrayList<Character> palabra, String oculta, char letra) {
		
		ArrayList<String> devolver = new ArrayList<String>();
		int coincidencias = 0;
		String ocultaNueva = "";
		
		for(int i = 0; i < palabra.size(); i++) {
			if (palabra.get(i) == letra) {
				coincidencias++;
				ocultaNueva += letra;
			}else {
				ocultaNueva += oculta.charAt(i);
			}
		}
		
		devolver.add(String.valueOf(coincidencias));
		devolver.add(ocultaNueva);
		
		return devolver;
	}
	
	private int generaAleatorio(int num) {
		return (int) (Math.random()*num)+1;
	}
	
	public char generaConsonante() {
		return consonantes(generaAleatorio(21));
	}
	
	public char generaVocal() {
		return vocal(generaAleatorio(5));
	}
	
	private char consonantes(int num) {
		char devolver = '!';
		switch (num) {
		case 1:
			devolver = 'b';
			break;
		case 2:
			devolver = 'c';
			break;
		case 3:
			devolver = 'd';
			break;
		case 4:
			devolver = 'f';
			break;
		case 5:
			devolver = 'g';
			break;
		case 6:
			devolver = 'h';
			break;
		case 7:
			devolver = 'j';
			break;
		case 8:
			devolver = 'k';
			break;
		case 9:
			devolver = 'l';
			break;
		case 10:
			devolver = 'm';
			break;
		case 11:
			devolver = 'n';
			break;
		case 12:
			devolver = 'ñ';
			break;
		case 13:
			devolver = 'p';
			break;
		case 14:
			devolver = 'q';
			break;
		case 15:
			devolver = 'r';
			break;
		case 16:
			devolver = 's';
			break;
		case 17:
			devolver = 't';
			break;
		case 18:
			devolver = 'v';
			break;
		case 19:
			devolver = 'w';
			break;
		case 20:
			devolver = 'x';
			break;
		case 21:
			devolver = 'y';
			break;
		case 22:
			devolver = 'z';
			break;
		}
		return devolver;
	}
	
	private char vocal(int num) {
		char devolver = '!';
		switch (num) {
		case 1:
			devolver = 'a';
			break;
		case 2:
			devolver = 'e';
			break;
		case 3:
			devolver = 'i';
			break;
		case 4:
			devolver = 'o';
			break;
		case 5:
			devolver = 'u';
			break;
		}
		return devolver;
	}

	public String palabraAleatoria() {
		File palabras = new File("palabras.dat");
		RandomAccessFile random = null;
		
		ArrayList<String> palabra = new ArrayList<String>();
		
		try {
			random = new RandomAccessFile(palabras, "rw");
			while (true) {
				palabra.add(random.readUTF());
			}
		} catch (IOException e) {
		}finally {
			try {
				random.close();
			} catch (IOException e) {
			}
		}
		if (palabra.size() == 1) {
			return palabra.get(0);
		}else {
			return palabra.get(generaAleatorio(palabra.size())-1);
		}
	}

	public boolean noEstaRepetidaLetra(char pista, ArrayList<Character> letras) {
		for (int i = 0; i < letras.size(); i++) {
			if (letras.get(i) == pista) {
				return false;
			}
		}
		return true;
	}

	public void guardarPuntuacion(int puntuacionObtenida, String nombre) {
		File palabras = new File("puntuaciones.dat");
		RandomAccessFile random;
		
		try {
			random = new RandomAccessFile(palabras, "rw");
			random.seek(random.length());
			random.writeUTF(nombre);
			random.writeInt(puntuacionObtenida);
			random.close();
		} catch (IOException e) {
		}
	}
	
}
