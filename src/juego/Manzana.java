package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class Manzana {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Color color;
	private Image imagen;
	
	
	public Manzana(double y, double x, Image imagen) {
		this.alto=150;
		this.ancho=150;
		this.x=x;
		this.y=y;
		this.imagen=imagen;
		this.color=color.green;
}
	public Manzana(double y, double x) {
		this.alto=150;
		this.ancho=150;
		this.x=x;
		this.y=y;
		this.color=color.green;
}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0,color);
	}
	
	public double getBordeIzquierdo() {
		return this.x - (this.ancho/2);
	}
	
	public double getBordeDerecho() {
		return this.x + (this.ancho/2);
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
	
	public double getAncho() {
		return this.ancho;
	}
	
	public double getAlto() {
		return this.alto;
	}


}
