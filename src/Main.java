
import prop13.graphlibrary.*;

//In a future replace all this entries for import java.util.*
//I am doing it this way to keep track of the used structures
//so it will be easier to search for documentation.
import java.util.IdentityHashMap;
import java.util.Vector;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * Created by miquel on 20/03/15.
 */
public class Main
{
    public static void main(String[] args)
            /*Main with various testing code, temporary*/
    {
        System.out.println("Hello World!");


        //GetNeighbor Testing and functionality showcase
        //e1 = (n1,n2)
        //e2 = (n2,n3)
        /*
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Edge e1 = new Edge(n1,n2,1);
        Edge e2 = new Edge(n2,n3,1);

        System.out.println("n1: "+n1);
        System.out.println("n2: "+n2);
        System.out.println("n3: "+n3);
        System.out.println("e1 n1 neighbor: "+e1.getNeighbor(n1));
        System.out.println("e1 n2 neighbor: "+e1.getNeighbor(n2));
        System.out.println("e2 n2 neighbor: "+e2.getNeighbor(n2));
        System.out.println("e2 n3 neighbor: "+e2.getNeighbor(n3));
        */
        //End of GetNeighbor testing


        //Graph testing using TNode and TEdge
        TGraph g = new TGraph();
        Vector<TNode> vn = new Vector<TNode>();
        for(int i = 0;i<4;i++){
            vn.add(new TNode(i));
            g.addNode(vn.elementAt(i));
        }
        System.out.println("FIRST");
        g.printGraph();
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(1),0));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(2),0));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(3),0));
        g.addEdge(new TEdge(vn.elementAt(1),vn.elementAt(3),0));
        g.addEdge(new TEdge(vn.elementAt(2),vn.elementAt(3),0));
        System.out.println("SECOND");
         Set<Edge> se = g.nodeEdges(vn.elementAt(0));
        System.out.print("0: ");
        for(Edge e:se){
            ((TEdge)e).printEdge();
        }
        System.out.println();
        System.out.println("THIRD");
        g.printGraph();
        g.deleteEdge(vn.elementAt(0),vn.elementAt(1));
        g.deleteEdge(vn.elementAt(2),vn.elementAt(0));
        System.out.println("FOURTH");
        g.printGraph();
        System.out.println("FIFTH");
        g.deleteNode(vn.elementAt(3));
        g.printGraph();
        System.out.println("END OF GRAPH TESTING");
    }
}
