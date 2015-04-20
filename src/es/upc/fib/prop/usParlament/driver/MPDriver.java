package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;
import es.upc.fib.prop.usParlament.domain.TGraph;

import java.util.Scanner;

/**
 * Created by miquel on 7/04/15.
 */
public class MPDriver
{
    public static void main(String[] args)
    {
        Graph g = new TGraph();
        Scanner reader = new Scanner(System.in);
        Boolean active = true;
        while(active){
            int input;
            System.out.println("Entra 1 per crear MP, 2 per imprimir, 0 per sortir");
            input = reader.nextInt();
            if(input == 1) {
                System.out.println("Entra el nom ,l'estat(two letters abbrv) i el districte(numero) en aquest ordre");
                System.out.println(reader.nextLine());
                g.addNode(new MP(reader.nextLine(), State.valueOf(reader.nextLine().toUpperCase()), Integer.parseInt(reader.nextLine())));
            }else if(input == 2) {
                System.out.println(g);
            }else if(input == 0) {
                active = false;
                System.out.println("Fi del driver");
            }else{
                System.out.println("Comanda dsconeguda");
            }


        }

    }
}
