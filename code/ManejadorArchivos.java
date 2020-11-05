import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ManejadorArchivos {

	public static Imagen leerImagen(File file) {
		try {
			BufferedReader d = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			boolean desc = true;
			String data = "";

			String codMagic = d.readLine();

			while (desc) {
				data = d.readLine();
				if (data.contains("#")) {
				} else {
					desc = false;
				}
			}

			StringTokenizer token = new StringTokenizer(data);

			int column = Integer.parseInt(token.nextToken()); // columna
			int row = Integer.parseInt(token.nextToken()); // row
			double[] vector = new double[column * row];

			int intensidad = Integer.parseInt(d.readLine());// intensidad

			if (codMagic.equals("P2")) {
				int i = 0;
				while ((data = d.readLine()) != null) {
					token = new StringTokenizer(data);
					while (token.hasMoreElements()) {
						vector[i] = Integer.parseInt(token.nextToken());
						i++;
					}
				}
			}

			if (codMagic.equals("P5")) {
				DataInputStream dis = new DataInputStream(new FileInputStream(
						file));
				for (int j = 0; j < (column * row); j++)
					vector[j] = (char) (dis.readUnsignedByte());
				dis.close();
			}

			d.close();

			return new Imagen(column, row, vector);

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Retorna el vector M x 1 que contiene todos los pixeles de la imagen. M =
	 * m x n = 92 x 112 = 10304
	 * */
	public static Imagen leerImagen(String file) {

		return leerImagen(new File(file));
	}
}
