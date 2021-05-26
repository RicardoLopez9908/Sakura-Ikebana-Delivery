package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Ikebana {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;
	
	
	
	public Ikebana(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho = 20;
		this.alto = 20;
		this.imagen = Herramientas.cargarImagen("imagenes/RamoDeRosas.png");
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
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
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	public double getAlto() {
		return alto;
	}
	public void setAlto(double alto) {
		this.alto = alto;
	}
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}


	
	

}
