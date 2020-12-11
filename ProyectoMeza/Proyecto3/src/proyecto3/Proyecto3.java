package proyecto3;
import java.util.*;  

/*
    explicación - por favor borren esto después de leerlo
       -Cuando se crea el grafo, se creará también un ArrayList<par> llamado TotalAxV, que
        guardará cuántas aristas tiene cada vértice.

       -Para esto necesitaremos la clase par, que permite guardar dos enteros y comparar los
        objetos base al primer entero (tuve que crearla porque en java tengo que importar una
        librería medio rara mientras que en c++ no es necesario tanto rollo jajaja)
        por lo que se guardara el total de aristas en el primer espacio, y el vértice al que
        pertenecen en el segundo espacio. 
        
       -El objetivo es iniciar el pareo a partir de los vértices que tienen un menor número de 
        aristas, y de esta forma obtener el máximo posible de vértices; así que ordenaremos el
        ArrayList en base al total de aristas, e iremos analizando vértice por vértice en la 
        función ActivarPareo(), en dicho orden.

       -Despúes, en Pareo(), revisaremos si ya están emparejados los vértices de destino de 
        las aristas de nuestro vértice de origen, basado en nuestro arreglo de pares, el cual
        guarda los vértices con los que está emparejado cada nodo.
        
       -Si, al emparejar un nodo (llamémoslo origen), se obtiene que el vértice al que apunta 
        su arista (llamémoslo destino) ya está emparejado con otro nodo:
            1.- Se tomará otra arista del vértice origen y se buscará si algún otro vértice 
                adyacente no está apareado.
            2.- Si el vértice origen ya no tiene más aristas, ahora se buscará si el nodo 
                destino puede ser emparejado con otro vértice. Si se logra hacer este cambio,
                se volverá a llamar a la función Pareo() con el vértice original, para que 
                ahora sí pueda ser apareado.

       -Este proceso se realiza antes de que el usuario elija la opción de los pareos del grafo,
        para que no se tenga que repetir el mismo procedimiento cuando quiera ver si el grafo
        es perfecto o no.
        (Igual se puede poner en un mismo inciso xd)

        Cualquier duda me dicen
                                                                                    Sandra
*/

class par //permitirá asociar a cada vértice con su total de aristas
{
    private int first; 
    private int second;

    par(int first, int second){
        this.first=first;
        this.second=second;
    }
    public int getFirst() {
        return first;
    }
    public int getSecond() {
        return second;
    }
    public static int comparar(par uno, par dos){
        return Integer.compare(uno.first,dos.first);
    }
}

public class Proyecto3 
{
    private int n; //Número de vértices
    private int matriz[][];
    private LinkedList <Integer> lista[];
    private int pares[];

    private static boolean grafoCreado = false;
    private static Scanner sc = new Scanner(System.in);
    private static Proyecto3 grafo;
    
    Proyecto3(int n)
    {
        this.n = n;
        matriz = new int[n][n];
        lista = new LinkedList[n];
        pares=new int[n];
        
        for(int i=0;i<n;i++)
        {
            lista[i] = new LinkedList();
            pares[i]=-1;
            
            for(int j = 0;j < n; j++)
            {
                matriz[i][j] = 0;
            }
        }
    }

    public int getN() 
    {
        return n;
    }

    public int[][] getMatriz() 
    {
        return matriz;
    }
    
    public int contarEntradas(int lugar)
    {
        int conteo[][] = grafo.getMatriz();
        int entradas = 0;
        
        for(int i = 0; i < grafo.getN(); i++)
        {
            if(conteo[i][lugar] == 1)
            {
                entradas++;
            }
        }
        
        return entradas;
    }
    
    public int contarSalidas(int lugar)
    {
        int conteo[][] = grafo.getMatriz();
        int salidas = 0;
        
        for(int i = 0; i < grafo.getN(); i++)
        {
            if(conteo[lugar][i] == 1)
            {
                salidas++;
            }
        }
        
        return salidas;
    }
    
    public static void crearGrafo()
    {        
        try 
        {
            int v;
            ArrayList<par> TotalAxV;
            do{
                System.out.println("Inserte el número de vértices del grafo: ");
                v = sc.nextInt(); //Número de vértices
                grafo = new Proyecto3(v);
                TotalAxV = new ArrayList<par>(v);
                if(v<=0){
                    System.out.println("El valor debe de ser mayor a 0");
                }
            }while(v<=0);
            
            for (int i = 0;i < v; i++)
            {
                int e,arista;
                do{
                    System.out.println("Inserte el número de aristas del vértice " + i + ": ");
                    e = sc.nextInt();
                    if(e<0){
                        System.out.println("El valor debe de ser mayor o igual a 0");
                    }
                }while(e<0);
  
                for (int j = 0; j < e; j++)
                {
                    do{
                        System.out.println("Inserte el vértice al que se conecta la arista " + j + ": ");
                        arista = sc.nextInt();
                    if(arista<0){
                            System.out.println("La arista debe de ser mayor o igual a 0");
                        }
                    } while(arista<0);
                        grafo.add(i, arista);
                        
                    
                }
                TotalAxV.add(i, new par(e,i)); //se guarda el numero total de aristas que tiene cada vértice
            }
            TotalAxV.sort(par::comparar); //se ordenan los vértices del menor al mayor número de aristas (se está pasando como el comparador del sort() una función estática por referencia, por eso el par::comparar)
            grafo.ActivarPareo(TotalAxV);
            grafoCreado = true;
        } catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    
    void add(int origen, int destino)
    {
        if(matriz[origen][destino] == 0)
        {
             matriz[origen][destino] = 1;
             lista[origen].add(destino);
        }
    }
    
    void MostrarMatriz()
    {
        System.out.println("Matriz de adyacencia");
        for(int i = 0;i < n; i++)
        {
            for(int j = 0;j < n; j++)
            {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    void MostrarLista()
    {
        System.out.println("Lista de adyacencia");
        for(int i = 0;i < n; i++)
        {
            Iterator<Integer> num = lista[i].listIterator();
            System.out.print(i + "| ");
            
            while(num.hasNext())
            {
                System.out.print(num.next() + " ");
            }
            System.out.println();
        }
    }
    
    
    
    public void DFS(int r)
    {
        //Marcar todos los vértices como no visitados
        boolean visited[] = new boolean[grafo.getN()];
 
        //Función recursiva
        DFSUtil(r, visited);
    }
    
    //El método que usa el método DFS
    public void DFSUtil(int r, boolean visited[])
    {
        //Se marca el actual como visitado
        visited[r] = true;
        System.out.print(r + " ");
 
        //Recursividad para los nodos adyacentes
        Iterator<Integer> i = lista[r].listIterator();
        while (i.hasNext()) 
        {
            int h = i.next();
            if (!visited[h])
                DFSUtil(h, visited);
        }
    }
    
    void BFS(int s)
    {
        //Se marcan todos los vértices como no visitados
        boolean visited[] = new boolean[grafo.getN()];
 
        //Se utiliza una cola en este algoritmo
        LinkedList<Integer> queue = new LinkedList<Integer>();
 
        //Se marca el nodo actual como visitado y se añade a la cola
        visited[s] = true;
        queue.add(s);
 
        while (!queue.isEmpty())
        {
            //Se retira el nodo de la cola y se imprime
            s = queue.poll();
            System.out.print(s + " ");
 
            //Se buscan los vértices adyacentes, si no han sido visitados, se marcan y se ponen en la cola
            Iterator<Integer> i = lista[s].listIterator();
            while (i.hasNext())
            {
                int h = i.next();
                if (!visited[h])
                {
                    visited[h] = true;
                    queue.add(h);
                }
            }
        }
    }
    
    void ImprimirPareos(boolean prf)
    {
        boolean perfecto=true;
        System.out.println("Pareo de nodos");
        for(int i = 0;i < n; i++)
        {   
            if(pares[i]==-1){
                System.out.println(i + " no tiene par");
                perfecto = false;
            }
            else
                System.out.println(i + " y " + pares[i]);
        }
        System.out.println("");
        if(prf){
            if(perfecto)
                System.out.println("El grafo sí tiene pareos perfectos, pues, al emparejar la mayor cantidad \nposible de vértices, todos los vértices del grafo quedan emparejados.");
            else
                System.out.println("El grafo no tiene pareos perfectos, pues, al emparejar la mayor cantidad \nposible de vértices, no todos los vértices del grafo quedan emparejados.");
            }
    }


    boolean Pareo(int origen, int cambio){
        Iterator<Integer> i = lista[origen].listIterator(); 
        while(i.hasNext()) 
        {//itera por todas las aristas del vértice de origen hasta hallar un vértice de destino que no este apareado
            int destino=i.next();
                if(pares[origen]==-1)
                {
                    if(destino==cambio) //cambio determina si dicha arista ya había sido usada en un pareo anterior de ese vértice
                    { //se ignorará esta arista si se ha llamado a la función con el objetivo de cambiar el pareo de un vertice que ya había sido apareado anteriormente
                        continue;
                    }
                    if(pares[destino]==-1)
                    { //si ambos vértices de la arista no han sido apareados, se juntarán
                        pares[origen]=destino;
                        pares[destino]=origen;
                        return true; //y la función termina después de crear un pareo exitoso  
                    } 
                    else
                    { //si el vértice de destino ya está apareado
                        if(i.hasNext())
                            continue;   //iterar sobre los demás vértices
                        else if (lista[destino].size()>1)
                        { //si no hay más vértices adyacentes en el vértice de origen, buscar si en el vértice de destino hay otros vértices con los que pueda aparearse
                            int aux = pares[destino];
                            pares[aux]=-1; //se deshace el pareo
                            pares[destino]=-1;
                            if (Pareo(destino, aux)) //si se logró un cambio...
                                Pareo(origen, -1); //entonces ya podremos aparear a nuestro vértice original con el vértice de destino original
                        }
                }
            }  
        }
        return false;
    }

    void ActivarPareo(ArrayList<par>TotalAxV)
    {
        for(int i = 0;i < n; i++)
        {
            int numAristas= TotalAxV.get(i).getFirst(); //iniciamos con los vértices de menor número de aristas
            int vert= TotalAxV.get(i).getSecond();
            if(numAristas>0){
                Pareo(vert,-1);
            }

        }
    }
    void problemaBipartito(){//es lo que se me ocurrió :c 
        System.out.println("Supongamos que una aplicación de citas se basa en los\n"
                + "gustos y afinidades de las personas para poder generar la\n"
                + "mayor cantidad de parejas con una buena probabilidad de éxito,\n"
                + "para lograr esto a cada perfil se le asigna un número y se\n"
                + "relaciona sólo con los de aquellas personas con las que será\n"
                + "mayormente compatible. En caso de que alguna persona no haya\n"
                + "sido emparejada se le notificará con un mensaje para que no\n"
                + "desista en el amor. Si se toma en cuenta el grafo proporcionado\n"
                + "como las personas registradas en la aplicación, lo que se le\n"
                + "mostrará  a cada persona será los siguiente:\n ");
        
        for(int i = 0;i < n; i++)
        {   
            if(pares[i]==-1){//mensajes chidos jajaja
                System.out.println("Perfil " + i + ": El amor está a la vuelta de la esquina");
                
            }
            else
                System.out.println("Perfil " + i + ": " + "Se le mostrará el perfil de la persona "+pares[i]);
        }
        System.out.println("");
    }

    public static void main(String[] args) throws InterruptedException 
    {
        boolean salir = false; 
        
        while(!salir)
        {
            System.out.println("\nMenú");
            System.out.println("1) Crear nuevo grafo");
            System.out.println("2) Imprimir matriz y lista de adyacencia");
            System.out.println("3) Recorrido en profundidad (DFS)");
            System.out.println("4) Recorrido en anchura (BFS)");
            System.out.println("5) Problema con uso de grafos dirigidos");
            System.out.println("6) Determinar si el grafo tiene pareos perfectos");
            System.out.println("7) Obtener el pareo maximal");
            System.out.println("8) Problema con uso de grafos bipartitos");
            System.out.println("9) Salir ");
            System.out.print("Opción: ");
            char op = sc.next().charAt(0);
            System.out.println("");
            switch(op)
            {
                case('1'):
                {
                    crearGrafo();
                    break;
                }
                case('2'):
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        grafo.MostrarMatriz();
                        grafo.MostrarLista();
                    }
                        
                    break;
                }
                case('3'): //Con ciclos
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        int raiz;
                        do 
                        {                            
                            System.out.print("Ingresa el nodo raíz: ");
                            raiz = sc.nextInt();
                        }while (raiz < 0 || raiz > grafo.getN());
                        grafo.DFS(raiz);
                    }
                        
                    break;
                }
                case('4'): //Con ciclos
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        int raiz;
                        do 
                        {                            
                            System.out.print("Ingresa el nodo raíz: ");
                            raiz = sc.nextInt();
                        }while(raiz < 0 || raiz > grafo.getN());
                        grafo.BFS(raiz);
                    }
                        
                    break;
                }
                case('5'): //Fabor de no criticar, es la 1:45am D:
                {
                    System.out.println("\nSuponga que a lo largo de su colonia, usted ha ubicado 5 puntos estratégicos, digamos: una tienda de ropa"
                            + ", \nun puesto de comida, una papelería, una escuela y, por supuesto, su hogar. Ahora bien, con el apogeo de las plata-"
                            + "\nformas de entrega de comida a domicilio (dada la situación actual), usted también se ha visto en la necesidad, o el"
                            + "\nsimple deseo, de ordenar comida a su hogar. No auxante, los repartidores suelen enfrentar un problema que cada vez"
                            + "\nparece más común: Google Maps con frecuencia marca calles en sentidos contrarios. Ante esta situación, usted ha deci"
                            + "\ndido apoyar a los repartidores indicándoles la manera en que pueden atravesar su colonia, de lado a lado, a partir de"
                            + "\nlos sitios característicos, sin incurrir en calles en sentido contrario, lo que los ayuda a prevenir tomar parte de un"
                            + "\naccidente grave. Así pues, usted indica los caminos que pueden tomar a partir de cualquier punto, al mismo tiempo que les"
                            + "\nindica el número de calles de entrada y salida. Para ello, usted cuenta con"
                            + "\nla numeración (hecha por usted) de estos sitios.\n");
                    Thread.currentThread().sleep(25000); //Pausa la ejecución del programa por 25000 milisegundos o 25 segundos
                    if(!grafoCreado)
                    {
                        System.out.println("¡Para ver el problema, primero carga tu grafo dirigido!");
                    }else
                    {
                        System.out.println("¿En qué sitio se encuentra ahora mismo el repartidor?");
                        int sitio = sc.nextInt();
                        
                        System.out.println("\nA partir de aquí, usted cuenta con " + grafo.contarSalidas(sitio) + " caminos para salir y  al sitio desembocan " + grafo.contarEntradas(sitio) + " calles");
                        
                        System.out.print("Un camino que puede tomar para circular la colonia es a través de los puntos: ");
                        grafo.DFS(sitio);
                        System.out.println("\n");
                    }
                    break;
                }
                case('6'):
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        grafo.ImprimirPareos(true);
                    }  
                    break;
                }
                case('7'):
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        grafo.ImprimirPareos(false);
                    }
                        
                    break;
                }
                case('8'):
                {
                    if(!grafoCreado)
                    {
                        System.out.println("¡Debes crear un grafo primero!");
                    }else
                    {
                        grafo.problemaBipartito();
                    }
                        
                    break;
                }
                case('9'):
                {
                    salir = true;
                    break;
                }
                default:
                {
                    System.out.println("¡Opción inválida!");
                }
            }
        }
    }
}
    

