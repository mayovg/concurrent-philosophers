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
	    System.out.printf("Este es el filosofo %d.\n", this.id);
	    new Thread(this).start();
	}

	/**
	 * el filósofo piensa y su hilo se bloquea durante el tiempo que piense 
	 */
	private void piensa() {
	    // no ver la siguiente linea
	    long tiempoPensando = ((long) (random.nextInt((int)tiempoPensar)/10));
	    System.out.printf("Tiempo: %d, el filósofo %d está pensando por %d ms \n",
			      System.currentTimeMillis(), this.id, tiempoPensando);
	    try {
		Thread.sleep(tiempoPensando);
	    } catch(InterruptedException ie) {ie.printStackTrace();}
	}

	/**
	 * el filósofo come y su hilo se bloquea durante el tiempo que coma
	 */
	private void come() {
	    long tiempoComiendo = ((long) (random.nextInt((int)tiempoComer))/10);
	    System.out.printf("Tiempo: %d, el filósofo %d está comiendo por %d ms \n",
			      System.currentTimeMillis(), this.id, tiempoComiendo);
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
	int runTime = 20000; // número arbitrario
	if (args.length > 0){
	    try{
		runTime = Integer.parseInt(args[0]);
		if (runTime <= 0) {
		    runTime = 20000;
		}
	    } catch(NumberFormatException nfe){nfe.printStackTrace();}
	} 
	System.out.printf("Filósofos llegando a la cena: %d\n", numFilos);
        Random random = new Random(runTime/5);
	long tiempoPensando  = (long) (Math.random() * runTime); // el tiempo para pensar de cada filósofo
	long tiempoComiendo = (long) (Math.random() * runTime); // el tiempo para comer de cada filósofo

	DiningServer ds = new DiningServer(numFilos);
	for (int x = 0; x < numFilos; x++)
	    new Philosopher(x, tiempoPensando, tiempoComiendo, ds);
	System.out.println("Todos los filósofos se han sentado en la mesa");

	/*
	Había pensado en que el programa se detuviera después de un tiempo
	determinado (runTime) pero no les daba tiempo de comer a todos los filósofos
	try{
	  Thread.sleep(runTime*20000);
	} catch(InterruptedException ie) {ie.printStackTrace();}
	System.out.println("La cena ha terminado");
	System.exit(0);
	*/
    }
}
