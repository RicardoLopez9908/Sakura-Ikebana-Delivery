package juego;

import java.awt.Image;

public class Ninja {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;

	

	public Ninja(double y, double x, double ancho, double alto, Image imagen) {
		this.alto=alto;
		this.ancho=ancho;
		this.x=x;
		this.y=y;
		this.imagen=imagen;
	
}

	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}
	
	public void avanzarIzquierda() {
		this.x--;
	}
	
	public void avanzarDerecha() {
		this.x++;
	}
	
	public void avanzarArriba() {
		this.y--;
	}
	
	public void avanzarAbajo() {
		this.y++;
	}
		
	
	
	
}