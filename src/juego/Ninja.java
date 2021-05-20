package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Ninja {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen=Herramientas.cargarImagen("imagenes/Ninja.png");
	private Color color;
	private boolean vertical;
	
	

	public Ninja(double y, double x, double ancho, double alto) {
		this.alto=alto;
		this.ancho=ancho;
		this.x=x;
		this.y=y;
		this.vertical=false;	
}

	
	public Ninja(double y, double x) {
		this.alto=50;
		this.ancho=40;
		this.x=x;
		this.y=y;
		this.color=color.blue;
		this.vertical=false;
}
	
	public void setVerticalTrue() {
		this.vertical=true;
	}

	
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}
	
	public double getAncho() {
		return this.ancho;
	}
	
	public double getAlto() {
		return this.alto;
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
		
	public Image getImagen() {
		return imagen;
	}
	
	
	
	
}