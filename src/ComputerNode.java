import java.util.LinkedList;
import java.util.List;

/**
 * The ComputerNode class represents the nodes of the graph G, which are pairs (Ci, t).
 *
 * @author Sebastian Kazun
 * @author Thien Nguyen
 * @author
 */
public class ComputerNode {
	
	int id;
	int timeStamp;
	int color;
	ComputerNode pred;
	LinkedList<ComputerNode> outEdgeList = new LinkedList<ComputerNode>(); 
	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int BLACK = 2;
	
	
	public ComputerNode(int id, int timeStamp) {
		this.id = id;
		this.timeStamp = timeStamp;
		color = WHITE; 
		pred = null;
	}
	

    /**
     * Returns the ID of the associated computer.
     *
     * @return Associated Computer's ID
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the timestamp associated with this node.
     *
     * @return Timestamp for the node
     */
    public int getTimestamp() {
        return timeStamp;
    }

    /**
     * Returns a list of ComputerNode objects to which there is outgoing edge from this ComputerNode object.
     *
     * @return a list of ComputerNode objects that have an edge from this to the nodes in the list.
     */
    public List<ComputerNode> getOutNeighbors() {
        return outEdgeList;
    }
    
    //adds a directed edge between this node and the computerNode
	public void addEdge(ComputerNode v) {
		
		if(this == null || v == null) {
			throw new NullPointerException();
		}
		
		outEdgeList.add(v);
	}
	
	
	public ComputerNode getPredecessor() {
		return pred;
	}

    
    @Override
    public boolean equals(Object o) {
		
    	if(this == o) {
    		return true;
    	}
    	if(o == null || this.getClass() != o.getClass()) {
    		return false;
    	}
    	
    	ComputerNode c = (ComputerNode) o;
    	
    	return this.id == c.id &&
    			this.timeStamp == c.timeStamp;
    }
    
    @Override
    public String toString() {
    	return "("+id+", "+timeStamp+")";
    }


}
