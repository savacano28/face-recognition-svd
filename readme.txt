
RECONOCIMIENTO ROSTROS UV-2014
Reconocimiento de Rostros Aplicando SVD


1. Para compilar, en una terminal, ubicado en la carpeta codigo_proyecto, ejecutar la linea:
	Linux:
		javac -cp .:Jama-1.0.3.jar *.java
	Windows:
		javac -cp .;Jama-1.0.3.jar *.java

2 .Para Ejecutar, en una terminal, ubicado en la carpeta codigo_proyecto, ejecutar la linea:
	Linux:
		java -cp .:Jama-1.0.3.jar -Xms1024M -Xmx1024M ReconocedorGUI
	Windows:
		java -cp .;Jama-1.0.3.jar -Xms1024M -Xmx1024M ReconocedorGUI

2. Seleccionar con el boton Base de Datos.
	
	Este boton solo sirve para escoger la ruta donde se encuentra las imagenes base para el conjunto de entrenamiento, 
	imagenes asccii que deben llamarse s1_ascii, s2_ascii, etc)
	
	El programa se demora aproximadamente medio minuto en cargar la base y hacer los calulso previos correspondientes.

3. Seleccionar con el boton cargarImagen, una imagen para buscarla en la base.


NOTAS :
Requiere JAVA version 7 o superior.
EL PROGRAMA SOLO FUNCIONA CON imagenes ASCII
