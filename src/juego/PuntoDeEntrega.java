package juego;

import java.awt.Image;
import java.awt.Point;

import entorno.Entorno;
import entorno.Herramientas;

public class PuntoDeEntrega {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int numeroDeEntrega;
	private Point punto;
	private Image imagen;
	

	
	public PuntoDeEntrega(int numeroDeEntrega) {
		this.alto=35;
		this.ancho=30;
		this.imagen=Herramientas.cargarImagen("imagenes/Flecha.png");
		this.numeroDeEntrega=numeroDeEntrega;
		actualizarPuntoDeEntrega();
	}

	public int getNumeroDeEntrega() {
		return numeroDeEntrega;
	}
	
	public void setNumeroDeEntrega(int numeroDeEntrega) {
		this.numeroDeEntrega=numeroDeEntrega;
		actualizarPuntoDeEntrega();
	}
	
	public Point getPunto() {
		return this.punto;
	}
	
	
	private void actualizarPuntoDeEntrega() {
		switch(this.numeroDeEntrega) {
		case 0:
			this.x=150;
			this.y=130;
			this.punto= new Point(150,220);
			break;
		case 1:
			this.x=725;
			this.y=230;
			this.punto= new Point(725,220);
			break;
		case 2:
			this.x=150;
			this.y=430;
			this.punto= new Point(150,520);
			break;
		case 3:
			this.x=460;
			this.y=120;
			this.punto= new Point(400,150);
			break;
		case 4:
			this.x=265;
			this.y=420;
			this.punto= new Point(200,450);
			break;
		case 5:
			this.x=475;
			this.y=260;
			this.punto= new Point(415,290);
			break;
		}
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

	public double getAlto() {
		return alto;
	}

	public Image getImagen() {
		return imagen;
	}
	

	

}
