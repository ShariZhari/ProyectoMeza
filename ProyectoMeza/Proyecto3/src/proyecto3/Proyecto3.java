package proyecto3;
import java.util.*;  
import javax.swing.JOptionPane;

public class Proyecto3 
{
    private int n; //Número de vértices
    private int matriz[][];
    private LinkedList <Integer> lista[];
    
    private static boolean grafoCreado = false;
    private static Scanner sc = new Scanner(System.in);
    private static Proyecto3 grafo;
    
    Proyecto3(int n)
    {
        this.n = n;
        matriz = new int[n][n];
        lista = new LinkedList[n];
        
        for(int i=0;i<n;i++)
        {
            lista[i] = new LinkedList();
            
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
            System.out.println("Inserte el número de vértices del grafo: ");
            int v = sc.nextInt(); //Número de vértices
            grafo = new Proyecto3(v);
            
            for (int i = 0;i < v; i++)
            {
                System.out.println("Inserte el número de aristas del vértice " + i + ": ");
                int e = sc.nextInt();
                for (int j = 0; j < e; j++)
                {
                    System.out.println("Inserte el vértice al que se conecta la arista " + j + ": ");
                    int arista = sc.nextInt();
                    grafo.add(i, arista);
                }
            }
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
    
    void MostrarPares()
    {
        System.out.println("Pares de nodos");
        for(int i = 0;i < n; i++)
        {
            Iterator<Integer> num = lista[i].listIterator();
             while(num.hasNext())
             {
                System.out.print("[" + i + ", " + num.next() + "]");
             }
        }
        System.out.println("");
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
    
    public static void main(String[] args) throws InterruptedException 
    {
        boolean salir = false; 
        
        while(!salir)
        {
            System.out.println("Menú");
            System.out.println("1) Crear nuevo grafo");
            System.out.println("2) Imprimir matriz y lista de adyacencia");
            System.out.println("3) Recorrido en profundidad (DFS)");
            System.out.println("4) Recorrido en anchura (BFS)");
            System.out.println("5) Problema con uso de grafos dirigidos");
            System.out.println("... ");
            System.out.println("7) Salir ");
            System.out.print("Opción: ");
            char op = sc.next().charAt(0);

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
                            + "\nsimple deseo, de ordenar comida a su hogar. No obstante, los repartidores suelen enfrentar un problema que cada vez"
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
                case('7'):
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
    

