package juego;


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
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Sakura Ikebana Delivery - Grupo XX - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...

		
		
		
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
			this.rasengan.setX(rasengan.getX()+1);
		}
		else if((this.movimientoRasengan.equals("izquierda"))&&(rasengan.getX()-rasengan.getAncho()/2 >0)) {
			this.rasengan.setX(rasengan.getX()-1);
		}
		else if((this.movimientoRasengan.equals("arriba"))&&(rasengan.getX()-rasengan.getAlto()/2 >0)) {
			this.rasengan.setY(rasengan.getY()-1);
		}
		else if((this.movimientoRasengan.equals("abajo"))&&(rasengan.getX()+rasengan.getAlto()/2 <605)) {
			this.rasengan.setY(rasengan.getY()+1);
		}
		else rasengan=null;
		
	}

	
	
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
