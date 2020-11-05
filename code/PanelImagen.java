import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class PanelImagen extends JPanel {

	private BufferedImage imagen;

	public PanelImagen() {
		super();
		this.setBackground(Color.white);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(92, 112);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (imagen != null)
			g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), null);
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.drawString("No Imagen", 0, 0);
		}
	}

	public void setImagen(File f) {
		setImagen(ManejadorArchivos.leerImagen(f));
	}

	/**
	 * Este metodo se encarga de crear un buffer sobre el cual se pinta la
	 * imagen para posteriormente pintar el buffer sobre el canvas.
	 */
	public void setImagen(Imagen img) {
		if (img != null) {

			BufferedImage bufferImage = null;

			bufferImage = new BufferedImage(img.getColumns(), img.getRows(),
					BufferedImage.TYPE_INT_RGB);
			Graphics gbuff = bufferImage.getGraphics();
			double[] vector = img.getVectorF().getColumnPackedCopy();
			for (int i = 0; i < img.getRows(); i++) {
				for (int j = 0; j < img.getColumns(); j++) {
					int value = (int) vector[i * img.getColumns() + j];
					if (value > 255)
						value = 255;
					gbuff.setColor(new Color(value, value, value));
					gbuff.fillOval(j, i, 2, 2);// x y
				}
			}

			this.imagen = bufferImage;
		} else
			this.imagen = null;
	}
}
