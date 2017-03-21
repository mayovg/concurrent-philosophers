class DiningServer{
    private boolean checkStarving; // revisa la hambruna de los filosofos
    private int numFilos; // número de filósofos en la mesa
    private int [] estados; 
    // posibles estados del filosofos
    private static final int
	PENSANDO = 0, 
	HAMBRIENTO = 1, 
	FAMELICO = 2, //con mucha mucha hambre!!!
	COMIENDO = 3;

    /*Constructor del servicio*/
    public DiningServer(int numFilos, boolean checkStarving) {
	this.numFilos = numFilos;
	this.checkStarving = checkStarving;
	estados = new int[numFilos];
	for (int x = 0; x < numFilos; x++) estados[x] = PENSANDO;
	System.out.println("Revisión de hambruna: " + checkStarving);
    }

    /*el i-ésimo filosofo mira a su izquierda*/
    private int izq(int i) {
	return (this.numFilos + i - 1) % this.numFilos;
    }

    /*el i-ésimo filósofo mira a su derecha*/
    private int der(int i){
	return (i + 1) % this.numFilos;
    }

    /*El filosofo se muere de hambre*/
    private void hambre(int k) {
	// revisa si el filosofo tiene hambre y si los filosofos a su lado no mueran de hambre
	if (estados[k] == HAMBRIENTO &&
	    estados[izq(k)] != FAMELICO && estados[der(k)] != FAMELICO){
	    estados[k] = FAMELICO;
	}
    }

    private void pruebaHambre(int k, boolean checkStarving) {
	if(estados[izq(k)] != COMIENDO && estados[izq(k)] != FAMELICO &&
	   (estados[k] == HAMBRIENTO || estados[k] == FAMELICO) &&
	   estados[der(k)] != FAMELICO && estados[der(k)] != COMIENDO)
	    estados[k] = COMIENDO;
	else if (checkStarving)
	    hambre(k);
    }
    
    /**
    * El i-ésimo filósofo intenta tomar sus tenedores para poder comer
    * @param int i - el lugar que ocupa en la mesa el filósofo que tomará los tenedores
    */
    public synchronized void tomaTenedor(int i){
	estados[i] = HAMBRIENTO;
	pruebaHambre(i, false);
	while (estados[i] != COMIENDO){
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
	pruebaHambre(izq(i), checkStarving);
	pruebaHambre(der(i), checkStarving);
	this.notifyAll();
    }
    
}
