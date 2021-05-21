package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Manzana {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;
	

	public Manzana(double y, double x,int estilo) {
		this.alto=100;
		this.ancho=150;
		this.x=x;
		this.y=y;
		if(estilo%2==0) {
			this.imagen=Herramientas.cargarImagen("imagenes/Manzana2.png");
		}
		else this.imagen=Herramientas.cargarImagen("imagenes/Manzana1.png");
}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
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
