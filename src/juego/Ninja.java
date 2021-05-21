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
	private String movimiento;
	
		
	public Ninja(int lugar) {
		this.alto=25;
		this.ancho=20;
		switch(lugar) {
		case 1:
			this.x=200;
			this.y=100;
			this.movimiento="abajo";
			break;
		case 2:
			this.x=600;
			this.y=75;
			this.movimiento="izquierda";
			break;
		case 3:
			this.x=100;
			this.y=525;
			this.movimiento="derecha";
			break;
		case 4:
			this.x=600;
			this.y=500;
			this.movimiento="abajo";
			break;
		case 5:
			this.x=300;
			this.y=225;
			this.movimiento="derecha";
			break;
		case 6:
			this.x=500;
			this.y=375;
			this.movimiento="izquierda";
			break;
			
		}
}
	
	
	
	public String getMovimiento() {
		return this.movimiento;
	}
	
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}
	
	public void mover() {
		switch(this.movimiento) {
		case "arriba":
			this.y--;
			break;
		case "abajo":
			this.y++;
			break;
		case "derecha":
			this.x++;
			break;
		case "izquierda":
			this.x--;
			break;
		}
	}
	
	
	public void teletransportarInicioY() {
		this.y=0;
	}
	
	public void teletransportarInicioX() {
		this.x=0;
	}
	
	public void teletransportarFinY() {
		this.y=600;
	}
	
	public void teletransportarFinX() {
		this.x=810;
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
	
	
	public Image getImagen() {
		return imagen;
	}
	
	
	
	
}