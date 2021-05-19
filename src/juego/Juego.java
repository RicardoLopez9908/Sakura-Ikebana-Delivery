package juego;


import java.awt.Color;
import java.util.ArrayList;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	private Sakura sakura= new Sakura(400,300,20,40);
	
	private Rasengan rasengan;
	
	private String movimientoRasengan;
	
	private int velocidadRasengan = 2;
	
	private ArrayList<Ninja> ninjas=new ArrayList<>();
	
	private ArrayList<Manzana> manzanas = new ArrayList<>();
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Sakura Ikebana Delivery - Grupo XX - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
	
		
		crearMapa();
	

		generarNinja();
		generarNinja();
		generarNinja();
		generarNinja();
		generarNinja();
		generarNinja();
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		
		sakura.dibujar(entorno);
		
		
		revisarProximoMovimiento();
		
		if(rasengan!=null) {
		rasengan.dibujar(entorno);
		revisarRasengan();
		}
		
		if(manzanas.size()!=0) {
			for(Manzana manzana: manzanas) {
				manzana.dibujar(entorno);
			}
		}
		
		
		if(ninjas.size()!=0) {
			for(Ninja ninja: ninjas) {
				ninja.dibujar(entorno);
			}
		}
		
		while(ninjas.size()<4) {
			generarNinja();
		}
		
		
		
	}
	
	
	//------------METODOS----------------------------
	
	
	public void revisarProximoMovimiento(){
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA) && (this.sakura.getX() + this.sakura.getAncho() /2 < 810)) {
			sakura.avanzarDerecha();

			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararDerecha();
				this.movimientoRasengan = "derecha";
			}
			}
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA) && (this.sakura.getX() - this.sakura.getAncho() /2 > 0)) {
			sakura.avanzarIzquierda();
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararIzquierda();
				this.movimientoRasengan = "izquierda";
			}
		}
		if(this.entorno.estaPresionada(this.entorno.TECLA_ABAJO) && (this.sakura.getY() + this.sakura.getAlto()/2 < 595)) {
			sakura.avanzarAbajo();
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararAbajo();
				this.movimientoRasengan = "abajo";
			}
			}
		if(this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA) && (this.sakura.getY() - this.sakura.getAlto() /2 > 0)) {
			sakura.avanzarArriba();
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararArriba();
				this.movimientoRasengan = "arriba";
			}
			}
		
		
		

	}
	
	
	public void revisarRasengan() {
		if((this.movimientoRasengan.equals("derecha"))&&(rasengan.getX()+rasengan.getAncho()/2 <810)) {
			this.rasengan.setX(rasengan.getX()+velocidadRasengan);
		}
		else if((this.movimientoRasengan.equals("izquierda"))&&(rasengan.getX()-rasengan.getAncho()/2 >0)) {
			this.rasengan.setX(rasengan.getX()-velocidadRasengan);
		}
		else if((this.movimientoRasengan.equals("arriba"))&&(rasengan.getY()-rasengan.getAlto()/2 > 0)) {
			this.rasengan.setY(rasengan.getY()-velocidadRasengan);
		}
		else if((this.movimientoRasengan.equals("abajo"))&&(rasengan.getY()+rasengan.getAlto()/2 <605)) {
			this.rasengan.setY(rasengan.getY()+velocidadRasengan);
		}
		else rasengan=null;
		
	}
	
	
	public void generarNinja() {
		boolean choca = true;
		Ninja nuevo=null;
		
		while(choca) {
			double randomX = Math.floor(Math.random()*(780-0+1)+0);  // Valor random entre 0 y 800, ambos incluidos
			double randomY = Math.floor(Math.random()*(580-0+1)+0);  // Valor random entre 0 y 600, ambos incluidos
			nuevo = new Ninja(randomY, randomX);
			
			choca=false;
			boolean flag=false;
			for(int i=0; i< manzanas.size() && flag==false;i++) {
				if(chocan(nuevo, manzanas.get(i))) {
					flag=true;
					choca=true;
				}
			}
		}
		ninjas.add(nuevo);
		
	
		
	}
	
	
	
	public void crearMapa() {
		double x = 0;
		double y = 0;
		while(x<810) {
			generarManzanaNueva(x, y);
			while(y<650) {
				generarManzanaNueva(x, y);
				y=y+200;
			}
			x=x+200;
			y = 0;	
		}
		
	}
	
	public void generarManzanaNueva(double x, double y) {
		Manzana nueva= new Manzana(y,x);
		manzanas.add(nueva);
	}
	

	public boolean chocan(Ninja rec1, Manzana rec2) {
		
		if(rec1!=null && rec2!=null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto()/2 < rec2.getY() + rec2.getAlto()/2) 
						&& 	   (rec1.getY() + rec1.getAlto()/2 > rec2.getY() - rec2.getAlto()/2) ; 
		
			boolean chequeoX = (rec1.getX() - rec1.getAncho()/2 < rec2.getX() + rec2.getAncho()/2) 
					&& 	   (rec1.getX() + rec1.getAncho()/2 > rec2.getX() - rec2.getAncho()/2) ; 
		
		return chequeoY && chequeoX;
		}
			
		else return false;
	}
	
	
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}



}
