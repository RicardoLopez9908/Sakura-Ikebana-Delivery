package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class AmigoDeSakura {


	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen= Herramientas.cargarImagen("imagenes/AmigoDeSakura.png");
	private Ikebana ramoDeRosas;
	private int velocidad;
	

	public AmigoDeSakura(double y, double x) {
		this.alto=43;
		this.ancho=30;
		this.x=x;
		this.y=y;
		this.velocidad=1;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad=velocidad;
	}
	
	public int getVelocidad() {
		return this.velocidad;
	}
	
	
	public void sostenerRamo() {
		this.ramoDeRosas = new Ikebana(this.x-7, this.y+12);
	}
	
	public void entregarRamo() {
		this.ramoDeRosas = null;
	}
	
	public boolean tieneRamo() {
		if(ramoDeRosas!=null) {
			return true;
		}
		else return false;
	}
	

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
		if(ramoDeRosas!=null) {
			ramoDeRosas.dibujar(entorno);
		}
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
		this.x= x-velocidad;
		if(ramoDeRosas!=null) {
			ramoDeRosas.setX(ramoDeRosas.getX()-velocidad);
		}
	}
	
	public void avanzarDerecha() {
		this.x= x+velocidad;
		if(ramoDeRosas!=null) {
			ramoDeRosas.setX(ramoDeRosas.getX()+velocidad);
		}
	}
	
	public void avanzarArriba() {
		this.y=y-velocidad;
		if(ramoDeRosas!=null) {
			ramoDeRosas.setY(ramoDeRosas.getY()-velocidad);
		}
	}
	
	public void avanzarAbajo() {
		this.y=y+velocidad;
		if(ramoDeRosas!=null) {
			ramoDeRosas.setY(ramoDeRosas.getY()+velocidad);
		}
	}
	
	public Ikebana getIkebana() {
		return ramoDeRosas;
	}
	

	
}
