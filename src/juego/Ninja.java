package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class Ninja {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;
	private Color color;
	

	public Ninja(double y, double x, double ancho, double alto, Image imagen) {
		this.alto=alto;
		this.ancho=ancho;
		this.x=x;
		this.y=y;
		this.imagen=imagen;
	
}

	
	public Ninja(double y, double x) {
		this.alto=30;
		this.ancho=20;
		this.x=x;
		this.y=y;
		this.color=color.blue;
}

	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);
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
		
	
	
	
}