import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**
 * The CommunicationsMonitor class represents the graph G
 * built to answer infection queries.
 *
 * @author Sebastian Kazun
 * @author Thien Nguyen
 * @author
 */
public class CommunicationsMonitor  {

	
	HashMap<Integer, List<ComputerNode>> map; //for timestamps
	LinkedList<ComputerNode> list; //for outedgelist
	boolean graphCreatedFlag;
	ArrayList<CommunicationTriple> tripleList;
	
    /**
     * Constructor with no parameters
     */
    public CommunicationsMonitor() {
    	graphCreatedFlag = false;
    	map = new HashMap<Integer, List<ComputerNode>>();
    	tripleList = new ArrayList<CommunicationTriple>();
    	list = new LinkedList<ComputerNode>();
    }

    /**
     * Takes as input two integers c1, c2, and a timestamp. This triple represents the fact that the computers with IDs
     * c1 and c2 have communicated at the given timestamp. This method should run in O(1) time. Any invocation of this
     * method after createGraph() is called will be ignored.
     *
     * @param c1        First ComputerNode in the communication pair.
     * @param c2        Second ComputerNode in the communication pair.
     * @param timestamp Time the communication took place.
     */
    public void addCommunication(int c1, int c2, int timestamp) {
    	
    	if(!graphCreatedFlag && timestamp > -1 && c1 > -1 && c2 > -1)
        	tripleList.add(new CommunicationTriple(c1, c2, timestamp));
    }

    /**
     * Constructs the data structure as specified in the Section 2. This method should run in O(n + m log m) time.
     */
    public void createGraph() {
    	
    	sortTriples(tripleList);
    	
    	Iterator<CommunicationTriple> it = tripleList.iterator();
    	CommunicationTriple current;
    	
    	while(it.hasNext()) {
    		
    		current = it.next();

    		ComputerNode node1  = new ComputerNode(current.getC1(), current.getTimestamp());
    		ComputerNode node2 = new ComputerNode(current.getC2(), current.getTimestamp());
        	
        	addToMap(node1);
        	addToMap(node2);
        	
        	//for outedgelist
    		if(!list.contains(node1))
    			list.add(node1);
    		if(!list.contains(node2))
    			list.add(node2);
    		ComputerNode graphNode1 = list.get(findNodeIndex(node1, list));
    		graphNode1.addEdge(node2);
    		ComputerNode graphNode2 = list.get(findNodeIndex(node2, list));
    		graphNode2.addEdge(node1);
    		
    	}
    	//sets flag so addComunication is not called again
    	graphCreatedFlag = true;
    }
  
    /**
     * Sort the triples by non-decreasing timestamp
     * @param list the list to sort
     */
    public void sortTriples(ArrayList<CommunicationTriple> list) {
    	
    	QuickSort sort = new QuickSort();
    	sort.quicksort(list);
    }
    
    
    //adds node to the graph
    public void addToMap(ComputerNode c) {
    	List<ComputerNode> list = new ArrayList<ComputerNode>();
    	list =  getComputerMapping(c.getID());
    	
    	//list doesnt exist so add it
    	if(list == null) {
    		map.put(c.getID(), new ArrayList<ComputerNode>());
    		list =  getComputerMapping(c.getID());
    	}
    	
    	if(list.size() <= 0) {
    		list.add(c); //first element in the list
    	}
    	else {
    		
    		//checking for dupes
    		ComputerNode last = list.get(list.size() - 1);
    		
    		if(last.getTimestamp() == c.getTimestamp()) {
    			last = c;
    		}
    		else {
    			list.add(c); //add to list
    			last.addEdge(c); //make edge with previous node
    			//c.addEdge(last);
    		}
    	}
    }
    

    /**
     * Determines whether computer c2 could be infected by time y if computer c1 was infected at time x. If so, the
     * method returns an ordered list of ComputerNode objects that represents the transmission sequence. This sequence
     * is a path in graph G. The first ComputerNode object on the path will correspond to c1. Similarly, the last
     * ComputerNode object on the path will correspond to c2. If c2 cannot be infected, return null.
     * <p>
     * Example 3. In Example 1, an infection path would be (C1, 4), (C2, 4), (C2, 8), (C4, 8), (C3, 8)
     * <p>
     * This method can assume that it will be called only after createGraph() and that x <= y. This method must run in
     * O(m) time. This method can also be called multiple times with different inputs once the graph is constructed
     * (i.e., once createGraph() has been invoked).
     *
     * @param c1 ComputerNode object to represent the Computer that is hypothetically infected at time x.
     * @param c2 ComputerNode object to represent the Computer to be tested for possible infection if c1 was infected.
     * @param x  Time c1 was hypothetically infected.
     * @param y  Time c2 is being tested for being infected.
     * @return List of the path in the graph (infection path) if one exists, null otherwise.
     */
    public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {

    	//x must be <= y
    	if(x > y || !graphCreatedFlag) { 
    		return null;
    	}
    	
    	ComputerNode first = null;
    	ArrayList<ComputerNode> compList = (ArrayList<ComputerNode>) map.get(c1);
    	if(compList == null) {
    		return null;
    	}
    	
    	//get the next node in the list that has time greater than x
    	Iterator<ComputerNode> it = compList.iterator();
    	while(it.hasNext()) {
    		ComputerNode next = it.next();
    		if(next.getTimestamp() >= x) {
    			first = next;
    			break;
    		}
    	}
    	
    	if(first == null) {
    		return null;
    	}
    	
    	ComputerNode result = BFS(c2, first, y);
    	if(result == null) {
    		return null;
    	}
    	
    	//resulting bfs path into stack and pop it for correct ordered path
    	ArrayList<ComputerNode> path = new ArrayList<ComputerNode>();
    	Stack<ComputerNode> stack = new Stack<ComputerNode>();
    	
    	while(!result.equals(first) && result.getPredecessor() != null) {
    		stack.add(result);
    		result = result.getPredecessor();
    	}
    	
    	stack.add(first);
    	while(!stack.isEmpty()) {
    		path.add(stack.pop());
    	}
    	
        return path;

    }

    /**
     * Returns a HashMap that represents the mapping between an Integer and a list of ComputerNode objects. The Integer
     * represents the ID of some computer Ci, while the list consists of pairs (Ci, t1),(Ci, t2),..., (Ci, tk),
     * represented by ComputerNode objects, that specify that Ci has communicated with other computers at times
     * t1, t2,...,tk. The list for each computer must be ordered by time; i.e., t1\<t2\<...\<tk.
     *
     * @return HashMap representing the mapping of an Integer and ComputerNode objects.
     */
    public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
    	
        return map;
    }

    /**
     * Returns the list of ComputerNode objects associated with computer c by performing a lookup in the mapping.
     *
     * @param c ID of computer
     * @return ComputerNode objects associated with c.
     */
    public List<ComputerNode> getComputerMapping(int c) {

    	if(!map.containsKey(c)) {
    		return null;
    	}
    	
    	return map.get(c);
    }

    /**
     * Performs bfs
     * @param c2 node to search to
     * @param source the source node
     * @param y timestamp value
     * @return
     */
    public ComputerNode BFS(int c2, ComputerNode source, int y) {
	
    	if(source == null) {
    		throw new NullPointerException();
    	}
    	clearVisited();
    	
    	//add intitial node to queue
    	Queue<ComputerNode> q = new LinkedList<ComputerNode>();
    	q.add(source);
    	source.pred = null;
    	source.color = ComputerNode.GRAY;
    	
    	while(!q.isEmpty()) {
    		
    		//remove the first node to begin processing it
    		ComputerNode node = (ComputerNode) q.remove(); 		
    		List<ComputerNode> neighbors = node.getOutNeighbors();
    		//for each neighbor
    		for(int i = 0; i <  neighbors.size(); i++) {
    			ComputerNode n = neighbors.get(i);
    			
    			if(n.color == ComputerNode.WHITE) {
    				n.color = ComputerNode.GRAY;
    				n.pred = node;
    				q.add(n);
    			}
    			
    			if( n.getTimestamp() <= y && n.getID() == c2) {
    				return n;
    			}
    		}
    		node.color = ComputerNode.BLACK;
    	}
    	
    	clearVisited();
    	return null;
    }

    
    /**
     * Clears the nodes (i.e. resets them for next run)
     */
    public void clearVisited() {
    	Iterator<Integer> it = map.keySet().iterator();
    	while(it.hasNext()) {
    		
    		List<ComputerNode> nextNode = map.get(it.next());
    		for(int i = 0; i < nextNode.size(); ++i ) {
    			nextNode.get(i).color = ComputerNode.WHITE;
    			nextNode.get(i).pred = null;
    		}
    	}
    }

    private int findNodeIndex(ComputerNode n, LinkedList<ComputerNode> linked) {
    	for(int i=0; i<linked.size(); i++) {
    		if(linked.get(i).equals(n))
    			return i;
    	}
    	return -1;
    }

}

