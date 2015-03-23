package prop13.graphlibrary;

/**
 * Created by miquel on 20/03/15.
 */
public abstract class Edge
{
    private Node n1;
    private Node n2;
    private int weight;


	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

    public Edge(Node m1,Node m2,int w)
    {
        n1 = m1;
        n2 = m2;
        weight = w;
    }

	/**
	 * Given one of :the two nodes of the Edge, it returns the other one.
	 It is implemented like this due to the fact that it is not a directed edge
	 therefore there no "origin" or "destiny" node.
	 * @param n
	 * @return
	 */
    public Node getNeighbor(Node n)
    {
        if(n==n1){
            return n2;
        }else if(n==n2){
            return n1;
        }else{
            System.out.println("Not part of the edge");
            return null;
        }
    }

	/**
	 *
	 * @return RANDOM node of the edge
	 */
    public Node getNode()
    {
        return n1;
    }


}
