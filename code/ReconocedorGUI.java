import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class ReconocedorGUI extends JFrame implements ActionListener {

	private JButton btnCargarBD, btnCargarImagen, btnClasificar;
	private PanelImagen imagenBuscar, imagenEncontrada;
	private PanelImagen[] imagenesEncontradas;
	private JMenuItem menuItemCargarBaseDatos, menuItemSalir,
			menuItemCargarImagen, menuItemAcercaDe;

	private Reconocedor objReconocedor;

	public ReconocedorGUI() {
		super("RECONOCIMIENTO ROSTROS UV-2014");
		this.inicializarComponentes();

		this.setLayout(new GridLayout(1, 2));
		JPanel panelIzquierdo = new JPanel();
		this.add(panelIzquierdo);

		panelIzquierdo.setLayout(new BorderLayout());
		JPanel panelCanvas = new JPanel();
		panelIzquierdo.add(panelCanvas, BorderLayout.CENTER);
		panelCanvas.setLayout(new GridBagLayout());
		panelCanvas.add(imagenBuscar);
		JPanel panelBotones = new JPanel();
		panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.add(btnCargarBD);
		panelBotones.add(btnCargarImagen);
//		panelBotones.add(btnClasificar);

		JPanel panelDerecho = new JPanel();
		this.add(panelDerecho);

		panelDerecho.setLayout(new BorderLayout());
		JPanel panelCanvas2 = new JPanel();
		panelDerecho.add(panelCanvas2, BorderLayout.CENTER);
		panelCanvas2.setLayout(new GridBagLayout());
		panelCanvas2.add(imagenEncontrada);
		/*
		 * panelDerecho.setLayout(new GridLayout(3, 3));
		 * 
		 * for (int i = 0; i < 9; i++) { JPanel panelCanvasTmp = new JPanel();
		 * panelCanvasTmp.setLayout(new GridBagLayout());
		 * panelCanvasTmp.add(imagenesEncontradas[i]);
		 * panelDerecho.add(panelCanvasTmp); }
		 */
		this.setSize(800, 450);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("coldface.jpg"));
		this.setVisible(true);
	}

	private void inicializarComponentes() {

		objReconocedor = new Reconocedor();

		imagenBuscar = new PanelImagen();
		imagenEncontrada = new PanelImagen();

		imagenesEncontradas = new PanelImagen[9];
		for (int i = 0; i < imagenesEncontradas.length; i++)
			imagenesEncontradas[i] = new PanelImagen();

		btnCargarBD = new JButton("Cargar Base de Datos");
		btnCargarBD.addActionListener(this);
		btnCargarImagen = new JButton("Cargar Imagen");
		btnCargarImagen.addActionListener(this);
		btnCargarImagen.setEnabled(false);
		btnClasificar = new JButton("Procesar");
		btnClasificar.addActionListener(this);
		JMenuBar menuBarra = new JMenuBar();

		JMenu menuArchivo = new JMenu("Archivo");

		menuItemCargarBaseDatos = new JMenuItem("Cargar Base de Datos");
		menuItemCargarBaseDatos.addActionListener(this);
		menuArchivo.add(menuItemCargarBaseDatos);

		menuItemCargarImagen = new JMenuItem("Cargar Imagen");
		menuItemCargarImagen.setAccelerator(KeyStroke.getKeyStroke('O',
				KeyEvent.CTRL_DOWN_MASK));
		menuItemCargarImagen.addActionListener(this);
		menuArchivo.add(menuItemCargarImagen);

		menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.setAccelerator(KeyStroke.getKeyStroke('Q',
				KeyEvent.CTRL_DOWN_MASK));
		menuItemSalir.addActionListener(this);
		menuArchivo.add(menuItemSalir);

		menuBarra.add(menuArchivo);

		JMenu menuAyuda = new JMenu("Ayuda");
		menuBarra.add(menuAyuda);
		menuItemAcercaDe = new JMenuItem("Acerca de");
		menuItemAcercaDe.addActionListener(this);
		menuAyuda.add(menuItemAcercaDe);

		this.setJMenuBar(menuBarra);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

		if (evento.getSource() == menuItemSalir) {
			System.exit(0);
		} else if ((evento.getSource() == btnCargarBD)
				|| (evento.getSource() == menuItemCargarBaseDatos)) {

			JFileChooser chooserDB = new JFileChooser(".");
			chooserDB.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int seleccion = chooserDB.showOpenDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File carpeta = chooserDB.getSelectedFile();
				objReconocedor.setCarpeta(carpeta.getAbsolutePath());
				btnCargarImagen.setEnabled(true);
				JOptionPane.showMessageDialog(this, "Base Cargada");
			}

		} else if ((evento.getSource() == btnCargarImagen)
				|| (evento.getSource() == menuItemCargarImagen)) {

			JFileChooser chooser = new JFileChooser(".");
			chooser.setApproveButtonText("Abrir .pgm");
			int seleccion = chooser.showOpenDialog(null);
			if (seleccion == JFileChooser.APPROVE_OPTION) {

				File f = chooser.getSelectedFile();
				imagenBuscar.setImagen(f);
				imagenBuscar.repaint();
				imagenEncontrada.setImagen(objReconocedor.reconocer(f));
				imagenEncontrada.repaint();
			}

		} else if (evento.getSource() == menuItemAcercaDe) {
			JOptionPane
					.showMessageDialog(
							null,
							"Reconocedor de Caras\nMétodos Numéricos 2014\nStephanya Casanova - Giovanna Hernández");
		}

	}

	public static void main(String argsMain[]) {
		new ReconocedorGUI();
	}
}
