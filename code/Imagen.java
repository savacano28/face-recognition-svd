import Jama.Matrix;

/**
 * Esta clase contiene la información de una imagen
 * */
public class Imagen {

	private Matrix vectorF;
	private int m;
	private int n;

	public Imagen(int m, int n, double[] vectorPixeles) {
		this.m = m;// ancho, columnas
		this.n = n;// alto, filas
		this.vectorF = new Matrix(vectorPixeles, vectorPixeles.length);
	}

	public Imagen(int m, int n, Matrix vectorPixeles) {
		this.m = m;// ancho, columnas
		this.n = n;// alto, filas
		this.vectorF = vectorPixeles;
	}

	public Matrix getVectorF() {
		return vectorF;
	}

	public int getRows() {
		return n;
	}

	public int getColumns() {
		return m;
	}
}
