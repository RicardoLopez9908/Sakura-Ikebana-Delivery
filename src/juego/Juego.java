package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	private Sakura sakura= new Sakura(300,400,50,50);
	
	private Rasengan rasengan;
	
	private String movimientoRasengan;
	
	private int velocidadRasengan = 2;
	
	private ArrayList<Ninja> ninjas=new ArrayList<>();
	
	private ArrayList<Manzana> manzanas = new ArrayList<>();
	
	private int cantidadDeEnemigos= 6;
	
	private Image fondo=Herramientas.cargarImagen("imagenes/Fondo.png");
	
	private boolean gameOver=false;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Sakura Ikebana Delivery - Grupo XX - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
	
		
		crearMapa(this.entorno.ancho()+10,this.entorno.alto()+10);
	
		
		for(int i=1; i<=cantidadDeEnemigos;i++) {
				ninjas.add(new Ninja(i));
		}
	
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick(){
		
		
	if(gameOver==false){
		
		// Procesamiento de un instante de tiempo
		
		entorno.dibujarImagen(fondo, 405, 300, 0);
		
		
		//Creamos el mapa con las manzanas
		if(manzanas.size()!=0) {
			for(Manzana manzana: manzanas) {
				manzana.dibujar(entorno);
			}
		}
		
		//Dibujamos todos los ninjas
		
		for(Ninja ninja:ninjas) {
			ninja.dibujar(entorno);
		}
		
		
		//Dibujamos a sakura mientras exista
		if(sakura!=null) {
			sakura.dibujar(entorno);
		}
		
		revisarProximoMovimiento();  //del usuario
		
		
		//Dibujamos el Rasengan
		if(rasengan!=null) {
			rasengan.dibujar(entorno);
			revisarRasengan();
		}
		
		//Los ninjas no pueden ser menos que 4
		while(ninjas.size()<4) {
		//	generarNinja();
		}
		
		//Dibujamos todos los ninjas
		if(ninjas.size()!=0) {
			for(int i=0; i<ninjas.size();i++) {
				ninjas.get(i).dibujar(entorno);
				ninjas.get(i).mover();
				revisarMovimiento(ninjas.get(i));
				if(chocan(sakura, ninjas.get(i))) {
					sakura=null;
					gameOver=true;
				}		
				if(chocan(rasengan,ninjas.get(i))) {
					ninjas.remove(i);
				}
			}
		}
		
		
		
		
		
	}
	else {
		entorno.cambiarFont("Times New Roman", 50, Color.RED);
		entorno.escribirTexto("¡PERDISTE!", 290, 250);
	}
			
	}
	
	
	//------------METODOS----------------------------
	
	
	
	
	
	
	/**
	 * Este metodo revisa el proximo movimiento del usuario, en el cual evaluamos:
	 * Presiona tecla hacia arriba.
	 * Presiona tecla hacia abajo.
	 * Presiona tecla hacia la derecha.
	 * Presiona tecla hacia la izquierda.
	 * Presiona tecla de espacio para disparar.
	 */
	public void revisarProximoMovimiento(){
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA) 
				&& (this.sakura.getX() + this.sakura.getAncho() /2 < 810)) {
			sakura.avanzarDerecha();
			if(chocan(sakura,manzanas)){
				sakura.avanzarIzquierda();
			}

			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararDerecha();
				this.movimientoRasengan = "derecha";
			}
			}
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA) 
				&& (this.sakura.getX() - this.sakura.getAncho() /2 > 0)){
			sakura.avanzarIzquierda();
			
			if(chocan(sakura,manzanas)){
				sakura.avanzarDerecha();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararIzquierda();
				this.movimientoRasengan = "izquierda";
			}
		}
		if(this.entorno.estaPresionada(this.entorno.TECLA_ABAJO) 
				&& (this.sakura.getY() + this.sakura.getAlto()/2 < 595)) {
			sakura.avanzarAbajo();
			if(chocan(sakura,manzanas)){
				sakura.avanzarArriba();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararAbajo();
				this.movimientoRasengan = "abajo";
			}
			}
		if(this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA) 
				&& (this.sakura.getY() - this.sakura.getAlto() /2 > 0)) {
			sakura.avanzarArriba();
			if(chocan(sakura,manzanas)){
				sakura.avanzarAbajo();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararArriba();
				this.movimientoRasengan = "arriba";
			}
			}
		//-----------------------------------------------------------------------------
		
		if(this.entorno.estaPresionada('d') 
				&& (this.sakura.getX() + this.sakura.getAncho() /2 < 810)) {
			sakura.avanzarDerecha();
			if(chocan(sakura,manzanas)){
				sakura.avanzarIzquierda();
			}

			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararDerecha();
				this.movimientoRasengan = "derecha";
			}
			}
		
		if(this.entorno.estaPresionada('a') 
				&& (this.sakura.getX() - this.sakura.getAncho() /2 > 0)){
			sakura.avanzarIzquierda();
			
			if(chocan(sakura,manzanas)){
				sakura.avanzarDerecha();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararIzquierda();
				this.movimientoRasengan = "izquierda";
			}
		}
		if(this.entorno.estaPresionada('s') 
				&& (this.sakura.getY() + this.sakura.getAlto()/2 < 595)) {
			sakura.avanzarAbajo();
			if(chocan(sakura,manzanas)){
				sakura.avanzarArriba();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararAbajo();
				this.movimientoRasengan = "abajo";
			}
			}
		if(this.entorno.estaPresionada('W') 
				&& (this.sakura.getY() - this.sakura.getAlto() /2 > 0)) {
			sakura.avanzarArriba();
			if(chocan(sakura,manzanas)){
				sakura.avanzarAbajo();
			}

			
			if(this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)&& rasengan==null){
				rasengan=sakura.dispararArriba();
				this.movimientoRasengan = "arriba";
			}
			}
		
		
	}
	
	
	
	public void revisarMovimiento(Ninja ninja) {
		switch(ninja.getMovimiento()) {
		case "arriba":
			if(ninja.getY()<0) {
				ninja.teletransportarFinY();
			}
			break;
		case "abajo":
			if(ninja.getY()>600) {
				ninja.teletransportarInicioY();
			}
			break;
		case "derecha":
			if(ninja.getX()>810) {
				ninja.teletransportarInicioX();
			}
			break;
		case "izquierda":
			if(ninja.getX()<0) {
				ninja.teletransportarFinX();
			}
			break;
		}
	}
	
	
	/**
	 * En este metodo revisamos el movimiento que le corresponde al rasengan.
	 * Segun el ultimo movimiento al momento de disparar, el rasengan se mueve:
	 * izquierda, derecha, arriba o abajo.
	 */
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
	
	
	/**
	 * Generamos un ninja aleatorio, verificando que no se encuentre dentro de la lista manzanas.
	 * Una vez creado nuestro ninja, lo añadimos a nuestra lista ninjas.
	 *
	 */
	
	/**
	 * Este metodo verifica que la distancia entre Ninjas es mayor a 20
	 * @param nuevoNinja: este sera el ninja que comparemos con nuestra lista "ninjas"
	 * @return true en caso de que la distancia sea correcta / false en caso de que la distancia no lo sea.
	 */
	
	public boolean distanciaDeNijasEsCorrecta(Ninja nuevoNinja) {
		if(ninjas.size()!=0) {
			for(Ninja antiguoNinja: ninjas ) {
				if(((antiguoNinja.getX()-nuevoNinja.getX()<30) && (antiguoNinja.getX()-nuevoNinja.getX()>-30))
					||((antiguoNinja.getY()-nuevoNinja.getY()<30)) && (antiguoNinja.getY()-nuevoNinja.getY()>-30)) {
					return false;
				}
			}
			return true;
		}
		else return true;
	}
	
	
	
	/**
	 * Añadimos manzanas a nuestra lista "manzanas" para generar un mapa.
	 * @param x : nuestro limite x
	 * @param y : nuestro limite y
	 */
	
	public void crearMapa(int x, int y) {
		int refX = 0;
		int refY = 0;
		while(refX<x) {
			generarManzanaNueva(refX, refY);
			while(refY<y) {
				generarManzanaNueva(refX, refY);
				refY=refY+200;
			}
			refX=refX+200;
			refY = 0;	
		}	
	}
	
	
	/**
	 * Generamos una nueva manzana donde nos indiquen y la añadimos a nuestra lista "manzanas"
	 * @param x: punto en x
	 * @param y: punto en y
	 */
	public void generarManzanaNueva(double x, double y) {
		Manzana nueva= new Manzana(y,x);
		manzanas.add(nueva);
	}
	

	/**
	 * Revisamos si chocan los argumentos que le pasemos
	 * @param rec1 : Ninja
	 * @param rec2 : Manzana
	 * @return : true en caso de choque / false en caso de no chocar
	 */
	
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
	
	public boolean chocan(Rasengan rec1, Ninja rec2) {
		
		if(rec1!=null && rec2!=null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto()/2 < rec2.getY() + rec2.getAlto()/2) 
						&& 	   (rec1.getY() + rec1.getAlto()/2 > rec2.getY() - rec2.getAlto()/2) ; 
		
			boolean chequeoX = (rec1.getX() - rec1.getAncho()/2 < rec2.getX() + rec2.getAncho()/2) 
					&& 	   (rec1.getX() + rec1.getAncho()/2 > rec2.getX() - rec2.getAncho()/2) ; 
		
		return chequeoY && chequeoX;
		}
			
		else return false;
	}
	
	
	public boolean chocan(Sakura rec1, Ninja rec2) {
		
		if(rec1!=null && rec2!=null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto()/2 < rec2.getY() + rec2.getAlto()/2) 
						&& 	   (rec1.getY() + rec1.getAlto()/2 > rec2.getY() - rec2.getAlto()/2) ; 
		
			boolean chequeoX = (rec1.getX() - rec1.getAncho()/2 < rec2.getX() + rec2.getAncho()/2) 
					&& 	   (rec1.getX() + rec1.getAncho()/2 > rec2.getX() - rec2.getAncho()/2) ; 
		
		return chequeoY && chequeoX;
		}
			
		else return false;
	}
	
	
	/**
	 * Revisamos si chocan los argumentos que le pasemos
	 * @param rec1 : Sakura
	 * @param rec2 : Lista de manzanas
	 * @return : true en caso de choque / false en caso de no chocar
	 */
	public boolean chocan(Sakura rec1, ArrayList<Manzana> manzanas) {
		
		for(int i=0; i<manzanas.size();i++) {
		
			Manzana rec2 = manzanas.get(i);
			
			if(rec1!=null && rec2!=null) {
				boolean chequeoY = (rec1.getY() - rec1.getAlto()/2 < rec2.getY() + rec2.getAlto()/2) 
							&& 	   (rec1.getY() + rec1.getAlto()/2 > rec2.getY() - rec2.getAlto()/2) ; 
		
				boolean chequeoX = (rec1.getX() - rec1.getAncho()/2 < rec2.getX() + rec2.getAncho()/2) 
							&& 	   (rec1.getX() + rec1.getAncho()/2 > rec2.getX() - rec2.getAncho()/2) ; 
		
				if(chequeoY && chequeoX) return true;}
		}
		return false;
	}

	
	
	/*
	public void generarNinja() {
		boolean choca = true;
		Ninja nuevo=null;
		
		while(choca) {
			double randomX = Math.floor(Math.random()*(780-200+1)+20);  // Valor random entre 20 y 780, ambos incluidos
			double randomY = Math.floor(Math.random()*(580-0+1)+20);  // Valor random entre 20 y 580, ambos incluidos
			nuevo = new Ninja(randomY, randomX);
			
			choca=false;
			boolean flag=false;
			for(int i=0; i< manzanas.size() && flag==false;i++) {
				if(chocan(nuevo, manzanas.get(i)) || ! distanciaDeNijasEsCorrecta(nuevo) ) {
					flag=true;
					choca=true;
				}
			}
		}
		
		
		ninjas.add(nuevo);
	}
	
	*/

	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}



}
