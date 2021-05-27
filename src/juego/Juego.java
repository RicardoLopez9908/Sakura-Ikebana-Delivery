package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// ENEMIGOS ---------------------------------

	private ArrayList<Ninja> ninjas = new ArrayList<>();

	// PERSONAJES Y HABILIDADES ------------------

	private Sakura sakura = null;

	private Rasengan rasenganUsuario1;

	private Rasengan rasenganUsuario2;

	private String movimientoRasengan;

	private String movimientoRasengan2;

	private int velocidadRasengan = 2;

	private int velocidadRasengan2 = 2;

	private AmigoDeSakura amigoDeSakura = null;

	private Image imagenRasengan1 = Herramientas.cargarImagen("imagenes/Rasengan.png");

	private Image imagenRasengan2 = Herramientas.cargarImagen("imagenes/Rasengan2.png");

	// CONFIGURACION DEL JUEGO --------------------

	private Entorno entorno;

	private ArrayList<Manzana> manzanas = new ArrayList<>();

	private int cantidadMinimaDeNinjas = 6;

	private int cantidadDeNinjas = 6;

	private boolean gameOver = false;

	private Image fondo = Herramientas.cargarImagen("imagenes/Fondo.png");
	
	private Image fondoMenu = Herramientas.cargarImagen("imagenes/FondoMenu.png");

	private Image fondoFinalP = Herramientas.cargarImagen("imagenes/FondoFinalP.png");
	
	private Image fondoFinalG = Herramientas.cargarImagen("imagenes/FondoFinalG.png");
	
	private PuntoDeEntrega puntoDeEntrega;

	private int numeroDeEntrega = 0;

	private Floreria floreria = new Floreria(400, 25);

	private int puntajeUsuario1 = 0;

	private int puntajeUsuario2 = 0;

	private int cantidadDeNinjasEliminados1 = 0;

	private int cantidadDeNinjasEliminados2 = 0;

	private int tiempo = 0;

	private String resultadoJuego = "Ganaste !!";

	private int puntajeMaximo = 15;

	private boolean inicio = true;

	private boolean multijugador;
	
	private PowerUp powerUp = null;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Sakura Ikebana Delivery - Grupo XIIV - v1", 800, 600);

		crearMapa(this.entorno.ancho() + 10, this.entorno.alto() + 10);

		for (int i = 0; i <= cantidadDeNinjas - 1; i++) {
			ninjas.add(new Ninja(i));
		}

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo

		if (inicio == true) {
			
			imprimirPantallaInicio();
		}

		else if (gameOver == true) {
			imprimirPantallaFinal();
		} else {
			if ((puntajeUsuario1 >= puntajeMaximo) || (puntajeUsuario2 >= puntajeMaximo)) {
				
				resultadoJuego = "¡¡Ganaste!!";

				gameOver = true;
			}
			entorno.dibujarImagen(fondo, 405, 300, 0);

			// Creamos el mapa con las manzanas
			if (manzanas.size() != 0) {
				for (Manzana manzana : manzanas) {
					manzana.dibujar(entorno);
				}
			}

			this.floreria.dibujar(entorno);
			
			if(powerUp!=null) {
				powerUp.dibujar(entorno);
			}
			
			imprimirEstadisticas();
			puntoDeEntrega = new PuntoDeEntrega(numeroDeEntrega);
			this.puntoDeEntrega.dibujar(entorno);

			
		if(sakura!=null) {	
			
			sakura.dibujar(entorno);


			revisarProximoMovimientoDelUsuario();
			
			if (sakura.tieneRamo()) {
				if (chocan(sakura, puntoDeEntrega.getPunto())) {
					sakura.entregarRamo();
					while (numeroDeEntrega == puntoDeEntrega.getNumeroDeEntrega()) {
						Random r = new Random();
						numeroDeEntrega = r.nextInt(6); // Entre 0 y 5
					}
					this.puntajeUsuario1 += 5;
				}
				;
			} else {
				if (chocan(sakura, floreria.getPunto())) {
					sakura.sostenerRamo();
				}
			}

			// Dibujamos el Rasengan
			if (rasenganUsuario1 != null) {
				rasenganUsuario1.dibujar(entorno);
				revisarRasengan();
			}
			
			if(velocidadRasengan<=2 && chocan(sakura, powerUp)) {
				velocidadRasengan+=2;
				powerUp=null;
			}
			
		}
		
		
		
		
			// Dibujamos todos los ninjas
			if (ninjas.size() != 0) {
				for (int i = 0; i < ninjas.size(); i++) {
					ninjas.get(i).dibujar(entorno);
					ninjas.get(i).mover();
					revisarMovimiento(ninjas.get(i));
					try {
					if (chocan(sakura, ninjas.get(i))) {
						sakura = null;
						resultadoJuego = "¡¡Perdiste!!";
					}
					if (chocan(rasenganUsuario1, ninjas.get(i))) {
						ninjas.remove(i);
						rasenganUsuario1 = null;
						this.cantidadDeNinjasEliminados1++;
					}
					if (chocan(amigoDeSakura, ninjas.get(i))) {
						amigoDeSakura = null;
						resultadoJuego = "¡¡Perdiste!!";
					}
					if (chocan(rasenganUsuario2, ninjas.get(i))) {
						ninjas.remove(i);
						rasenganUsuario2 = null;
						this.cantidadDeNinjasEliminados2++;
					}
					}
					catch(IndexOutOfBoundsException ignorar){}

				}

				// MULTIJUGADOR:

				if (this.multijugador == true && amigoDeSakura!=null) {
					
					amigoDeSakura.dibujar(entorno);
					
					revisarProximoMovimientoDelUsuario2();

					if (amigoDeSakura.tieneRamo()) {
						if (chocan(amigoDeSakura, puntoDeEntrega.getPunto())) {
							amigoDeSakura.entregarRamo();
							while (numeroDeEntrega == puntoDeEntrega.getNumeroDeEntrega()) {
								Random r = new Random();
								numeroDeEntrega = r.nextInt(6); // Entre 0 y 5
							}
							this.puntajeUsuario2 += 5;
						}
						;
					} else {
						if (chocan(amigoDeSakura, floreria.getPunto())) {
							amigoDeSakura.sostenerRamo();
						}
					}

					// Dibujamos el Rasengan
					if (rasenganUsuario2 != null) {
						rasenganUsuario2.dibujar(entorno);
						revisarRasengan2();
					}
					
					if(velocidadRasengan2<=2 && chocan(amigoDeSakura, powerUp)) {
						velocidadRasengan2+=2;
						powerUp=null;
					}

				}

			}

			// revisamos que hayan cierta cantidad de ninjas vivos

			if (ninjas.size() < cantidadMinimaDeNinjas) {
				if( tiempo > 250) {
				Random r = new Random();
				Ninja ninjaNuevo = new Ninja(r.nextInt(5)); // Entre 0 y 5
				if (!estaCerca(ninjaNuevo, sakura) && !estaCerca(ninjaNuevo, amigoDeSakura)) {
					ninjas.add(ninjaNuevo);
					tiempo = 0;
				}}
			tiempo++;
			}
			
			if(sakura== null && amigoDeSakura== null) {
				gameOver=true;
			}
			
			if(puntajeUsuario1+puntajeUsuario2>=10 && (velocidadRasengan<=2 || velocidadRasengan<=2) && powerUp == null) {
				Random r = new Random();
				powerUp = new PowerUp(r.nextInt(5));			// Entre 0 y 5
			}
			
		}
		
	}

	// ------------------------------------[METODOS]------------------------------------------//

	public void imprimirPantallaInicio() {
		
		entorno.dibujarImagen(fondoMenu, 400, 300, 0);
		//Sombra Titulo:
		entorno.cambiarFont("Times New Roman", 50, Color.BLACK);
		entorno.escribirTexto("Bienvenido a Sakura Ikebana Delivery", 17, 197);
		
		//Titulo
		entorno.cambiarFont("Times New Roman", 50, Color.WHITE);
		entorno.escribirTexto("Bienvenido a Sakura Ikebana Delivery", 20, 200);

		//Sombra Menu
		entorno.cambiarFont("Arial", 30, Color.BLACK);
		entorno.escribirTexto("Para un jugador, presione el 1", 147, 497);
		entorno.escribirTexto("Para dos jugadores, presione el 2", 148, 547);
		
		//Menu
		entorno.cambiarFont("Arial", 30, Color.WHITE);
		entorno.escribirTexto("Para un jugador, presione el 1", 150, 500);
		entorno.escribirTexto("Para dos jugadores, presione el 2", 150, 550);
		

		if (this.entorno.estaPresionada('1')) {
			sakura = new Sakura(270, 400);
			this.multijugador = false;
			this.inicio = false;
			
			
			
		} else if (this.entorno.estaPresionada('2')) {
			sakura = new Sakura(270, 400);
			amigoDeSakura = new AmigoDeSakura(320, 400);
			this.multijugador = true;
			this.inicio = false;
		}

	}

	public void imprimirPantallaFinal() {
		
		
		
		if (this.resultadoJuego.equals("¡¡Ganaste!!")) {
			
			entorno.dibujarImagen(fondoFinalG, 400, 300, 0);
			
			//Resultado
			entorno.cambiarFont("Times New Roman", 50, Color.GREEN);
			entorno.escribirTexto(resultadoJuego, 250, 250);
		} else{
			entorno.dibujarImagen(fondoFinalP, 400, 300, 0);
			//Sombra Resultado
			entorno.cambiarFont("Arial", 50, Color.BLACK);
			entorno.escribirTexto(resultadoJuego, 247, 247);
			//Resultado
			entorno.cambiarFont("Arial", 50, Color.RED);
			entorno.escribirTexto(resultadoJuego, 250, 250);
		}
		
		entorno.cambiarFont("Times New Roman", 30, Color.white);
		entorno.escribirTexto("Puntaje Jugador 1: " + puntajeUsuario1 , 250, 310);
		entorno.escribirTexto("Kills jugador 1: " + cantidadDeNinjasEliminados1, 250, 350);
		entorno.escribirTexto("Puntaje Jugador 2: " + puntajeUsuario2, 250, 390);
		entorno.escribirTexto("Kills jugador 2: " + cantidadDeNinjasEliminados2, 250, 430);
	}

	public void imprimirEstadisticas() {
		
		if(sakura !=null) {
			// sombras:
			entorno.cambiarFont("Arial", 30, Color.BLACK);
			entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados1, 12, 37);
			entorno.cambiarFont("Arial", 30, Color.BLACK);
			entorno.escribirTexto("Puntaje: " + puntajeUsuario1, 627, 37);
			
	
			// estadisticas;
			entorno.cambiarFont("Arial", 30, Color.WHITE);
			entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados1, 15, 40);
			entorno.cambiarFont("Arial", 30, Color.WHITE);
			entorno.escribirTexto("Puntaje: " + puntajeUsuario1, 630, 40);
		}
		else {
			// sombras:
			entorno.cambiarFont("Arial", 30, Color.BLACK);
			entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados1, 12, 37);
			entorno.cambiarFont("Arial", 30, Color.BLACK);
			entorno.escribirTexto("Puntaje: " + puntajeUsuario1, 627, 37);
			

			// estadisticas;
			entorno.cambiarFont("Arial", 30, Color.RED);
			entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados1, 15, 40);
			entorno.cambiarFont("Arial", 30, Color.RED);
			entorno.escribirTexto("Puntaje: " + puntajeUsuario1, 630, 40);
		}

		if (multijugador == true) {
			
			if(amigoDeSakura != null) {
			
				// sombras:
				entorno.cambiarFont("Arial", 30, Color.BLACK);
				entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados2, 12, 567);
				entorno.cambiarFont("Arial", 30, Color.BLACK);
				entorno.escribirTexto("Puntaje: " + puntajeUsuario2, 627, 567);
	
				// estadisticas;
				entorno.cambiarFont("Arial", 30, Color.WHITE);
				entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados2, 15, 570);
				entorno.cambiarFont("Arial", 30, Color.WHITE);
				entorno.escribirTexto("Puntaje: " + puntajeUsuario2, 630, 570);
			}
			else {
				// sombras:
				entorno.cambiarFont("Arial", 30, Color.BLACK);
				entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados2, 12, 567);
				entorno.cambiarFont("Arial", 30, Color.BLACK);
				entorno.escribirTexto("Puntaje: " + puntajeUsuario2, 627, 567);
	
				// estadisticas;
				entorno.cambiarFont("Arial", 30, Color.RED);
				entorno.escribirTexto("Kills: " + cantidadDeNinjasEliminados2, 15, 570);
				entorno.cambiarFont("Arial", 30, Color.RED);
				entorno.escribirTexto("Puntaje: " + puntajeUsuario2, 630, 570);
			}

		}

	}

	/**
	 * Este metodo revisa el proximo movimiento del usuario, en el cual evaluamos:
	 * Presiona tecla hacia arriba. Presiona tecla hacia abajo. Presiona tecla hacia
	 * la derecha. Presiona tecla hacia la izquierda. Presiona tecla de espacio para
	 * disparar.
	 */
	public void revisarProximoMovimientoDelUsuario() {

		char disparo = entorno.TECLA_ESPACIO;
		char arriba = 'w';
		char abajo = 's';
		char izquierda = 'a';
		char derecha = 'd';
		
		if(multijugador == true) {
			disparo = entorno.TECLA_ENTER;
			arriba = entorno.TECLA_ARRIBA;
			abajo = entorno.TECLA_ABAJO;
			izquierda = entorno.TECLA_IZQUIERDA;
			derecha = entorno.TECLA_DERECHA;
		}
		
		if ((this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)
				|| this.entorno.estaPresionada(derecha))
				&& (this.sakura.getX() + this.sakura.getAncho() / 2 < 810)) {
			sakura.avanzarDerecha();
			if (chocan(sakura, manzanas))
				sakura.avanzarIzquierda();

			if (this.entorno.estaPresionada(disparo) && rasenganUsuario1 == null) {
				rasenganUsuario1 = new Rasengan(this.sakura.getY(), this.sakura.getX(), imagenRasengan1);

				this.movimientoRasengan = "derecha";
			}
		}

		if ((this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)
				|| this.entorno.estaPresionada(izquierda))
				&& (this.sakura.getX() - this.sakura.getAncho() / 2 > 0)) {
			sakura.avanzarIzquierda();

			if (chocan(sakura, manzanas))
				sakura.avanzarDerecha();

			if (this.entorno.estaPresionada(disparo) && rasenganUsuario1 == null) {
				rasenganUsuario1 = new Rasengan(this.sakura.getY(), this.sakura.getX(), imagenRasengan1);
				this.movimientoRasengan = "izquierda";
			}
		}

		if ((this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)
				|| this.entorno.estaPresionada(abajo))
				&& (this.sakura.getY() + this.sakura.getAlto() / 2 < 595)) {
			sakura.avanzarAbajo();
			if (chocan(sakura, manzanas))
				sakura.avanzarArriba();

			if (this.entorno.estaPresionada(disparo) && rasenganUsuario1 == null) {
				rasenganUsuario1 = new Rasengan(this.sakura.getY(), this.sakura.getX(), imagenRasengan1);
				this.movimientoRasengan = "abajo";
			}
		}

		if ((this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA)
				|| this.entorno.estaPresionada(arriba))
				&& (this.sakura.getY() - this.sakura.getAlto() / 2 > 0)) {
			sakura.avanzarArriba();
			if (chocan(sakura, manzanas))
				sakura.avanzarAbajo();

			if (this.entorno.estaPresionada(disparo) && rasenganUsuario1 == null) {
				rasenganUsuario1 = new Rasengan(this.sakura.getY(), this.sakura.getX(), imagenRasengan1);
				this.movimientoRasengan = "arriba";
			}
		}
	}

	public void revisarProximoMovimientoDelUsuario2() {
		// --------------------------------/A/W/S/D/--------------------------------------------------

		if (this.entorno.estaPresionada('d') && (this.amigoDeSakura.getX() + this.amigoDeSakura.getAncho() / 2 < 810)) {
			amigoDeSakura.avanzarDerecha();
			if (chocan(amigoDeSakura, manzanas))
				amigoDeSakura.avanzarIzquierda();

			if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO) && rasenganUsuario2 == null) {
				rasenganUsuario2 = new Rasengan(amigoDeSakura.getY(), amigoDeSakura.getX(), imagenRasengan2);
				this.movimientoRasengan2 = "derecha";
			}
		}

		if (this.entorno.estaPresionada('a') && (this.amigoDeSakura.getX() - this.amigoDeSakura.getAncho() / 2 > 0)) {
			amigoDeSakura.avanzarIzquierda();

			if (chocan(amigoDeSakura, manzanas))
				amigoDeSakura.avanzarDerecha();

			if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO) && rasenganUsuario2 == null) {
				rasenganUsuario2 = new Rasengan(amigoDeSakura.getY(), amigoDeSakura.getX(), imagenRasengan2);
				this.movimientoRasengan2 = "izquierda";
			}
		}
		if (this.entorno.estaPresionada('s') && (this.amigoDeSakura.getY() + this.amigoDeSakura.getAlto() / 2 < 595)) {
			amigoDeSakura.avanzarAbajo();

			if (chocan(amigoDeSakura, manzanas))
				amigoDeSakura.avanzarArriba();

			if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO) && rasenganUsuario2 == null) {
				rasenganUsuario2 = new Rasengan(amigoDeSakura.getY(), amigoDeSakura.getX(), imagenRasengan2);
				this.movimientoRasengan2 = "abajo";
			}
		}
		if (this.entorno.estaPresionada('W') && (this.amigoDeSakura.getY() - this.amigoDeSakura.getAlto() / 2 > 0)) {
			amigoDeSakura.avanzarArriba();

			if (chocan(amigoDeSakura, manzanas))
				amigoDeSakura.avanzarAbajo();

			if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO) && rasenganUsuario2 == null) {
				rasenganUsuario2 = new Rasengan(amigoDeSakura.getY(), amigoDeSakura.getX(), imagenRasengan2);
				this.movimientoRasengan2 = "arriba";
			}
		}

	}

	public void revisarMovimiento(Ninja ninja) {
		switch (ninja.getMovimiento()) {
		case "arriba":
			if (ninja.getY() < 0) {
				ninja.teletransportarFinY();
			}
			break;
		case "abajo":
			if (ninja.getY() > 600) {
				ninja.teletransportarInicioY();
			}
			break;
		case "derecha":
			if (ninja.getX() > 810) {
				ninja.teletransportarInicioX();
			}
			break;
		case "izquierda":
			if (ninja.getX() < 0) {
				ninja.teletransportarFinX();
			}
			break;
		}
	}

	/**
	 * En este metodo revisamos el movimiento que le corresponde al rasengan. Segun
	 * el ultimo movimiento al momento de disparar, el rasengan se mueve: izquierda,
	 * derecha, arriba o abajo.
	 */
	public void revisarRasengan() {
		if ((this.movimientoRasengan.equals("derecha"))
				&& (rasenganUsuario1.getX() + rasenganUsuario1.getAncho() / 2 < 810)
				&& (!chocaConManzanas(rasenganUsuario1))) {
			this.rasenganUsuario1.setX(rasenganUsuario1.getX() + velocidadRasengan);
		} else if ((this.movimientoRasengan.equals("izquierda"))
				&& (rasenganUsuario1.getX() - rasenganUsuario1.getAncho() / 2 > 0)
				&& (!chocaConManzanas(rasenganUsuario1))) {
			this.rasenganUsuario1.setX(rasenganUsuario1.getX() - velocidadRasengan);
		} else if ((this.movimientoRasengan.equals("arriba"))
				&& (rasenganUsuario1.getY() - rasenganUsuario1.getAlto() / 2 > 0)
				&& (!chocaConManzanas(rasenganUsuario1))) {
			this.rasenganUsuario1.setY(rasenganUsuario1.getY() - velocidadRasengan);
		} else if ((this.movimientoRasengan.equals("abajo"))
				&& (rasenganUsuario1.getY() + rasenganUsuario1.getAlto() / 2 < 605)
				&& (!chocaConManzanas(rasenganUsuario1))) {
			this.rasenganUsuario1.setY(rasenganUsuario1.getY() + velocidadRasengan);
		} else
			rasenganUsuario1 = null;
	}

	/**
	 * En este metodo revisamos el movimiento que le corresponde al rasengan. Segun
	 * el ultimo movimiento al momento de disparar, el rasengan se mueve: izquierda,
	 * derecha, arriba o abajo.
	 */
	public void revisarRasengan2() {
		if ((this.movimientoRasengan2.equals("derecha"))
				&& (rasenganUsuario2.getX() + rasenganUsuario2.getAncho() / 2 < 810)
				&& (!chocaConManzanas(rasenganUsuario2))) {
			this.rasenganUsuario2.setX(rasenganUsuario2.getX() + velocidadRasengan2);
		} else if ((this.movimientoRasengan2.equals("izquierda"))
				&& (rasenganUsuario2.getX() - rasenganUsuario2.getAncho() / 2 > 0)
				&& (!chocaConManzanas(rasenganUsuario2))) {
			this.rasenganUsuario2.setX(rasenganUsuario2.getX() - velocidadRasengan2);
		} else if ((this.movimientoRasengan2.equals("arriba"))
				&& (rasenganUsuario2.getY() - rasenganUsuario2.getAlto() / 2 > 0)
				&& (!chocaConManzanas(rasenganUsuario2))) {
			this.rasenganUsuario2.setY(rasenganUsuario2.getY() - velocidadRasengan2);
		} else if ((this.movimientoRasengan2.equals("abajo"))
				&& (rasenganUsuario2.getY() + rasenganUsuario2.getAlto() / 2 < 605)
				&& (!chocaConManzanas(rasenganUsuario2))) {
			this.rasenganUsuario2.setY(rasenganUsuario2.getY() + velocidadRasengan2);
		} else
			rasenganUsuario2 = null;
	}
	
	/**
	 * Añadimos manzanas a nuestra lista "manzanas" para generar un mapa.
	 * 
	 * @param x : nuestro limite x
	 * @param y : nuestro limite y
	 */

	public void crearMapa(int x, int y) {
		int refX = 100;
		int refY = 0;
		int contador = 0;
		while (refX < x) {
			generarManzanaNueva(refX, refY, contador);
			while (refY < y) {
				generarManzanaNueva(refX, refY, contador++);
				refY = refY + 150;
			}
			contador--;
			refX = refX + 200;
			refY = 0;
		}
	}

	/**
	 * Generamos una nueva manzana donde nos indiquen y la añadimos a nuestra lista
	 * "manzanas"
	 * 
	 * @param x: punto en x
	 * @param y: punto en y
	 */
	public void generarManzanaNueva(double x, double y, int contador) {
		Manzana nueva = new Manzana(y, x, contador);
		manzanas.add(nueva);
	}


	

	public boolean estaCerca(Ninja rec1, Sakura rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + (rec2.getAlto() / 2) + 100)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - (rec2.getAlto() / 2) + 100);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + (rec2.getAncho() / 2) + 100)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - (rec2.getAncho() / 2) + 100);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	public boolean estaCerca(Ninja rec1, AmigoDeSakura rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + (rec2.getAlto() / 2) + 100)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - (rec2.getAlto() / 2) + 100);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + (rec2.getAncho() / 2) + 100)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - (rec2.getAncho() / 2) + 100);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}


	
	
	public boolean chocaConManzanas(Rasengan rasengan) {
		boolean resultado = false;
		for (Manzana manzana : this.manzanas) {
			if (chocan(rasengan, manzana)) {
				resultado = true;
			}
		}
		return resultado;
	}



	/**
	 * Revisamos si chocan los argumentos que le pasemos
	 * 
	 * @param rec1 : Ninja
	 * @param rec2 : Manzana
	 * @return : true en caso de choque / false en caso de no chocar
	 */
	public boolean chocan(Ninja rec1, Manzana rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	public boolean chocan(Sakura rec1, Point rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY())
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY());

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX())
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX());

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	public boolean chocan(AmigoDeSakura rec1, Point rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY())
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY());

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX())
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX());

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	/**
	 * Revisamos si chocan los argumentos que le pasemos
	 * 
	 * @param rec1 : Ninja
	 * @param rec2 : Manzana
	 * @return : true en caso de choque / false en caso de no chocar
	 */

	public boolean chocan(Rasengan rec1, Manzana rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	
	public boolean chocan(Rasengan rec1, Ninja rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	public boolean chocan(Sakura rec1, PowerUp rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}
	
	public boolean chocan(AmigoDeSakura rec1, PowerUp rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}
	
	
	public boolean chocan(Sakura rec1, Ninja rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	public boolean chocan(AmigoDeSakura rec1, Ninja rec2) {

		if (rec1 != null && rec2 != null) {
			boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
					&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

			boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
					&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

			return chequeoY && chequeoX;
		}

		else
			return false;
	}

	/**
	 * Revisamos si chocan los argumentos que le pasemos
	 * 
	 * @param rec1 : Sakura
	 * @param rec2 : Lista de manzanas
	 * @return : true en caso de choque / false en caso de no chocar
	 */
	public boolean chocan(Sakura rec1, ArrayList<Manzana> manzanas) {

		for (int i = 0; i < manzanas.size(); i++) {

			Manzana rec2 = manzanas.get(i);

			if (rec1 != null && rec2 != null) {
				boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
						&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

				boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
						&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

				if (chequeoY && chequeoX)
					return true;
			}
		}
		return false;
	}

	public boolean chocan(AmigoDeSakura rec1, ArrayList<Manzana> manzanas) {

		for (int i = 0; i < manzanas.size(); i++) {

			Manzana rec2 = manzanas.get(i);

			if (rec1 != null && rec2 != null) {
				boolean chequeoY = (rec1.getY() - rec1.getAlto() / 2 < rec2.getY() + rec2.getAlto() / 2)
						&& (rec1.getY() + rec1.getAlto() / 2 > rec2.getY() - rec2.getAlto() / 2);

				boolean chequeoX = (rec1.getX() - rec1.getAncho() / 2 < rec2.getX() + rec2.getAncho() / 2)
						&& (rec1.getX() + rec1.getAncho() / 2 > rec2.getX() - rec2.getAncho() / 2);

				if (chequeoY && chequeoX)
					return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
