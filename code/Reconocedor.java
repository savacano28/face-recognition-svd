import java.io.File;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class Reconocedor {

	private int cantidadPersonas = 20;
	private int cantidadCarasPorPersona = 8;
	private double umbral = 3400;

	private int cantidadImagenes;
	private String carpetaFaces;

	private Matrix fmean, UrT;
	private Matrix[] coordinateVectors;
	private Imagen[] imagenes;

	public Reconocedor() {
		cantidadImagenes = cantidadCarasPorPersona * cantidadPersonas;
		/*
		 * personas = new Persona[cantidadPersonas]; for (int i = 1; i <=
		 * cantidadPersonas; i++) { Persona p = new Persona(i); personas[i - 1]
		 * = p; // p.calcularMatrizMagica(); }
		 */
		// buscar una cara/*
		/*
		 * for (int i = 0; i < personas.length; i++) { Persona persona =
		 * personas[i]; Matrix[] vectoresX =
		 * persona.getVectoresCaracteristicos(); double valorEi =
		 * Double.MAX_VALUE; for (int j = 0; j < vectoresX.length; j++) { //
		 * valorEi = }
		 * 
		 * }
		 */
	}

	public void init() {
		crearMatrizAUnica();
	}

	public void crearMatrizAUnica() {
		Matrix A;

		double[][] matrizA = new double[cantidadImagenes][];
		imagenes = new Imagen[cantidadImagenes];
		Matrix[] vectoresFi = new Matrix[cantidadImagenes];
		coordinateVectors = new Matrix[cantidadImagenes];

		int posImagen = 0;
		for (int i = 0; i < cantidadPersonas; i++) {
			int numero = i + 1;
			String carpetaPersona = "s" + numero + "_ascii";
			for (int j = 0; j < cantidadCarasPorPersona; j++) {

				Imagen img = ManejadorArchivos.leerImagen(carpetaFaces + "/"
						+ carpetaPersona + "/output_" + (j + 1) + ".pgm");

				imagenes[posImagen] = img;
				// vector F de 10304 puntos
				vectoresFi[posImagen] = img.getVectorF();
				matrizA[posImagen] = vectoresFi[posImagen]
						.getColumnPackedCopy();

				posImagen++;
			}
		}

		Matrix sumaFi = vectoresFi[0].copy();
		for (int i = 1; i < vectoresFi.length; i++)
			sumaFi.plusEquals(vectoresFi[i]);

		this.fmean = sumaFi.times(1.0 / ((double) cantidadImagenes));

		for (int i = 0; i < vectoresFi.length; i++) {
			System.out.print(".");
			matrizA[i] = vectoresFi[i].minus(this.fmean).getRowPackedCopy();
		}

		A = new Matrix(matrizA);
		A = A.transpose();
		System.out.println("svd iniciando");
		SingularValueDecomposition svd = A.svd();
		System.out.println("svd done");
		// crearVectoresCaracteristicos
		int r = A.rank();

		Matrix U = svd.getU();
		// submatrix Ur, se resta 1, porque en jama se empieza desde la fila y
		// columna 0, pero en la teoría se empieza desde la fila y columna 1
		Matrix Ur = U.getMatrix(0, U.getRowDimension() - 1, 0, r - 1);
		Matrix UrT = Ur.transpose();
		// this.Ur = Ur;
		this.UrT = UrT;

		for (int i = 0; i < vectoresFi.length; i++) {
			System.out.print(".");
			this.coordinateVectors[i] = UrT.times(vectoresFi[i]
					.minus(this.fmean));
		}

	}

	public Imagen reconocer(File f) {
		Imagen img = ManejadorArchivos.leerImagen(f);
		// calculo el vector caracteristico X de la imagen de entrada
		Matrix x = UrT.times(img.getVectorF().minus(fmean));

		double normaActual = Double.MAX_VALUE;
		int imagenSeleccionada = -1;
		for (int j = 0; j < coordinateVectors.length; j++) {
			Matrix xi = coordinateVectors[j];
			double normaNueva = x.minus(xi).norm2();
			//System.out.println("Norma " + j + " = " + normaNueva);

			if (normaNueva < normaActual && normaNueva < umbral) {
				normaActual = normaNueva;
				System.out.println("Norma Final" + j + " = " + normaActual);
				imagenSeleccionada = j;

				}
		}
		if (imagenSeleccionada != -1) {
			return imagenes[imagenSeleccionada];
		} else {
			return ManejadorArchivos.leerImagen("no_se_encontro.pgm");
		}
	}

	public void setCarpeta(String carpeta) {
		carpetaFaces = carpeta;
		init();
	}

	public void setUmbral(double u) {
		this.umbral = u;
	}
}
