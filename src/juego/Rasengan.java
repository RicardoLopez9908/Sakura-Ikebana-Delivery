package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class Rasengan {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;
	
	
	public Rasengan(double y, double x, double ancho, double alto, Image imagen) {
		this.alto=alto;
		this.ancho=ancho;
		this.x=x;
		this.y=y;
		this.imagen=imagen;
		
	}

	public Rasengan(double y, double x, double ancho, double alto) {
		this.alto=alto;
		this.ancho=ancho;
		this.x=x;
		this.y=y;
		
	}


	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x,this.y, this.ancho, this.alto, 0, Color.BLUE);		
	}
	
	
	public void setImagen(Image imagen) {
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

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
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
