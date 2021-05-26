package juego;

import java.awt.Image;
import java.awt.Point;

import entorno.Entorno;
import entorno.Herramientas;

public class Floreria {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Point punto;
	private Image imagen;
	

	public Floreria(double x, double y) {
		this.alto=61;
		this.ancho=50;
		this.x=x;
		this.y=y;
		this.punto= new Point((int)x,(int)y);
		this.imagen=Herramientas.cargarImagen("imagenes/Floreria.png");
}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}

	public Point getPunto() {
		return this.punto;
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
