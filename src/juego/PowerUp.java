package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class PowerUp {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen = Herramientas.cargarImagen("imagenes/PowerUp.png");
	
	
	public PowerUp(int lugar) {
		this.ancho = 45;
		this.alto = 45;
		switch(lugar) {
		case 0:
			this.x=200;
			this.y=100;
			break;
		case 1:
			this.x=600;
			this.y=75;
			break;
		case 2:
			this.x=100;
			this.y=525;
			break;
		case 3:
			this.x=600;
			this.y=500;
			break;
		case 4:
			this.x=300;
			this.y=225;
			break;
		case 5:
			this.x=500;
			this.y=375;
			break;
	}
	}
		
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}
	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
	
	
}
