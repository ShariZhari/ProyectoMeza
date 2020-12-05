package proyecto3;
import java.util.*;  

public class Proyecto3 
{
    private int n;
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
    
    public static void crearGrafo()
    {
        grafoCreado = true;
        
        try 
        {
            System.out.println("Inserte el número de vértices del grafo: ");
            int v = sc.nextInt(); //Número de vértices
            grafo = new Proyecto3(v);
            for (int i = 0;i < v; i++)
            {
                System.out.println("Inserte el número de aristas del vértice " + (i+1) + ": ");
                int e = sc.nextInt();
                for (int j = 0; j < e; j++)
                {
                    System.out.println("Inserte el vértice al que se conecta la arista " + (j+1) + ": ");
                    int arista = sc.nextInt();
                    grafo.add(i, arista);
                }
            }
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
    
    public static void main(String[] args) 
    {
        boolean salir = false; 
        
        while(!salir)
        {
            System.out.println("Menú");
            System.out.println("1) Crear nuevo grafo");
            System.out.println("2) Imprimir matriz y lista de adyacencia");
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
    

