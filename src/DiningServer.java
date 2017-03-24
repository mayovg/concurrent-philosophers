class DiningServer{
    private int numFilos; // número de filósofos en la mesa
    private int [] estados;
    // posibles estados del filosofos
    private static final int
	PENSANDO = 0, 
	HAMBRIENTO = 1, 
	COMIENDO = 2;

    /*Constructor del servicio*/
    public DiningServer(int numFilos) {
	this.numFilos = numFilos;
	estados = new int[numFilos];
	for (int x = 0; x < numFilos; x++) estados[x] = PENSANDO;
    }

    /*el que esté a la izquierda del i-ésimo filósofo*/
    private int izq(int i) {
	return (numFilos + i - 1) % this.numFilos;
    }

    /* el que está a la derecha del i-ésimo filósofo*/
    private int der(int i){
	return (i + 1) % this.numFilos;
    }

    /*
     * El k-esimo filosofo se fija si los filosofos a su lado 
     * no están comiendo o hambrientos
     */
    private void pruebaHambre(int k) {
	// revisa si el filosofo tiene hambre y los que están a su lado  no están comiendo
	if (estados[k] == HAMBRIENTO &&
	    estados[izq(k)] != COMIENDO && estados[der(k)] != COMIENDO){
	    estados[k] = COMIENDO; //El filosofo no puede comer y se pone a pensar
	    this.notifyAll();
	} //else {
	    // Si los filosofos a su lado están comiendo o quieren comer, se pone a pensar
	    //estados[k] = PENSANDO;
	//}
    }
    
    /**
    * El i-ésimo filósofo intenta tomar sus tenedores para poder comer
    * @param int i - el lugar que ocupa en la mesa el filósofo que tomará los tenedores
    */
    public synchronized void tomaTenedor(int i){
	estados[i] = HAMBRIENTO;
	pruebaHambre(i);
	if (estados[i] != COMIENDO){
	    try{
		this.wait();
	    } catch (InterruptedException ie){
		ie.printStackTrace();
	    }
	}
    }

    /**
     * El i-ésimo filósofo devuelve los tenedores a la mesa para que otro filósofo coma
     * @param int i - el lugar que ocupa en la mesa el filósofo que devolverá los tenedores
     */

    public synchronized void regresaTenedor(int i){
	estados[i] = PENSANDO; // Si ya comió, se pone a pensar (o dormir)
	// revisa el estado de sus vecinos
	pruebaHambre(izq(i));
	pruebaHambre(der(i));
	//notifica a todos los procesos que dejó de comer 
	this.notifyAll();

    }
    
}
