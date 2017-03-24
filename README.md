### Computación Concurrente 2017-2
### Práctica 3: Solución al problema de los 5 filósofos usando monitores 
#### Luis Pablo Mayo Vega 

1. Para compilar y ejecutar el programa, en el directorio src hacemos lo siguiente:
```
  javac DiningPhilophers.java
  java DiningPhilosophers <runtime>
  
```
**Nota:**El runtime es opcional y solo se usa para calcular los tiempos que los filósofos piensen y coman 

2. El deadlock más común de este problema se presenta cuando todos los filósofos intentan tomar sus tenedores (tomando el de un lado primero y después el otro) ya que ninguno puede tomar ambos y comenzar a comer.

3. En general funciona para números impares de filósofos.  Si hay n tenedores y solo n-1 filósofos en la cena, siempre habrá uno que pueda comer aunque tenga que seguir un protocolo muy estricto para adquirir sus tenedores.

4. Thread.sleep() no libera el candado del objeto a diferencia de wait() que sí lo hace, lo que permite que otros procesos que estén esperando ese candado puedan ejecutarse.

Basado en la implementación descrita por Stephen J. Hartley en 1997 para su libro "Concurrent Programming: The Java Programming Language"
