package dad.javafx.mvc.ahorca_dos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CrearPalabras {

	public static void main(String[] args) {
		
		File archivo = new File("palabras.dat");
		RandomAccessFile random;
		try {
			random = new RandomAccessFile(archivo, "rw");
			
			random.setLength(0);
			random.writeUTF("palabra1");
			random.writeUTF("palabra2");
			random.writeUTF("palabra3");
			random.writeUTF("palabra4");
			random.writeUTF("palabra5");
			random.close();
		} catch (IOException e) {
		}

	}

}
