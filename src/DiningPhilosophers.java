import java.util.Random;

public class DiningPhilosophers{

   static  class Philosopher implements Runnable {
	private int id;
	private long tiempoPensar; // el tiempo que estará pensando
	private long tiempoComer; // el tiempo que estará comiendo
	private DiningServer ds;
	private  Random random;
	
	public Philosopher(int id, long tiempoPensar, long tiempoComer, DiningServer ds) {
	    this.id = id;
	    this.tiempoPensar = tiempoPensar;
	    this.tiempoComer = tiempoComer;
	    this.ds = ds;
	    this.random = new Random();
	    System.out.printf("Este es el filosofo %d. Tiempo pensando: %d, tiempo comiendo %d \n", id, tiempoPensar, tiempoComer);
	    new Thread(this).start();
	}

	/**
	 * el filósofo piensa y su hilo se bloquea durante el tiempo que piense 
	 */
	private void piensa() {
	    random.setSeed(tiempoPensar*1000);
	    long tiempoPensando = (random.nextLong());
	    //tiempoPensar = 1 + (long) (Math.random());
	    System.out.printf("Tiempo: %d, el filósofo %d está pensando por %d ms \n",
			      System.currentTimeMillis(), id, tiempoPensando);
	    try {
		Thread.sleep(tiempoPensando);
	    } catch(InterruptedException ie) {ie.printStackTrace();}
	}

	/**
	 * el filósofo come y su hilo se bloquea durante el tiempo que coma
	 */
	private void come() {
	    random.setSeed(tiempoComer);
	    long tiempoComiendo = (random.nextLong());
	    //tiempoComer = 1 + (long) (Math.random());
	    System.out.printf("Tiempo: %d, el filósofo %d está comiendo por %d ms \n",
			      System.currentTimeMillis(), id, tiempoComiendo);
	    try {
		Thread.sleep(tiempoComiendo);
	    } catch(InterruptedException ie) {ie.printStackTrace();}
	}

	/**
	 * El filósofo piensa o intenta comer
	 */
	@Override
	public void run() {
	    while (true) {
		this.piensa();
		System.out.printf("Tiempo: %d, el filósofo %d quiere comer \n",System.currentTimeMillis(),id);
		ds.tomaTenedor(this.id); // intenta tomar los tenedores
		this.come(); //si logra tomarlos, come
		ds.regresaTenedor(this.id); // devuelve los tenedores después de comer
	    }
	}
    }

    /**
    * Ejecución principal del programa
    */
    public static void main (String [] args) {
	int numFilos = 5;
	int runTime = 70000;
	boolean checkStarving = false;

	System.out.printf("Filósofos llegando a la cena: %d, tiempo de ejecución: %d\n", numFilos, runTime);
	long tiempoPensando  = 9; // el tiempo para pensar de cada filósofo
	long tiempoComiendo = 11; // el tiempo para comer de cada filósofo

	DiningServer ds = new DiningServer(numFilos, checkStarving);
	for (int x = 0; x < numFilos; x++)
	    new Philosopher(x, tiempoPensando, tiempoComiendo, ds);
	System.out.println("Todos los filósofos se han sentado en la mesa");
	try{
	    Thread.sleep(runTime*1000);
	} catch(InterruptedException ie) {ie.printStackTrace();}
	System.out.println("La cena ha terminado");
	System.exit(0);
    }
}
