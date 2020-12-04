
package proyecto3;
import java.util.*;  

public class Proyecto3 {
    int n;
    int matriz[][];
    LinkedList <Integer> lista[];
    Proyecto3(int n){
        this.n=n;
        matriz=new int[n][n];
        lista=new LinkedList[n];
        for(int i=0;i<n;i++){
            lista[i]=new LinkedList();
            for(int j=0;j<n;j++){
                matriz[i][j]=0;
            }
            
        }
    }
     void add(int origen, int destino){
        if(matriz[origen][destino]==0){
             matriz[origen][destino]=1;
             lista[origen].add(destino);
        }
    }
    void MostrarMatriz(){
        System.out.println("Matriz de adyacencia");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println("");
        }
    }
    void MostrarLista(){
        System.out.println("Lista de adyacencia");
        for(int i=0;i<n;i++){
            Iterator<Integer> num=lista[i].listIterator();
            System.out.print(i+"| ");
            while(num.hasNext()){
                System.out.print(num.next()+" ");
            }
            System.out.println();
        }
    }
    void MostrarPares(){
        System.out.println("Pares de nodos");
        for(int i=0;i<n;i++){
            Iterator<Integer> num=lista[i].listIterator();
             while(num.hasNext()){
                System.out.print("["+i+", "+num.next()+"]");
            }
        }
        System.out.println("");
    }
    
    public static void main(String[] args) {
        // ´porque
        
    }
    
    public void ok(){
        //es una prueba
        //desde vscode :)
        //aaaaaaah
        //yay
        //henlo
        //nada IMANOLlllll
}
    
}
