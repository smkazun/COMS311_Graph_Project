/**
 * 
 * @author Sebastian Kazun
 *
 */
public class CommunicationTriple {
	
	int c1;
	int c2;
	int timestamp;
	
	/**
	 * 
	 * @param c1 the first computer's id
	 * @param c2 the second computer's id 
	 * @param timestamp time the computer nodes communicated
	 */
	CommunicationTriple(int c1, int c2, int timestamp){
		this.c1 = c1;
		this.c2 = c2;
		this.timestamp = timestamp;
	}
	
	/**
	 * 
	 * @return c1 id of this triplet
	 */
	public int getC1() {
		return c1;
	}
	
	/**
	 * 
	 * @return c2 id of this triplet
	 */
	public int getC2() {
		return c2;
	}
	
	/**
	 * 
	 * @return timestamp of this triplet
	 */
	public int getTimestamp() {
		return timestamp;
	}
	
}
