
public class SnakeAi {
	Tuple start;
	Tuple goal;
	Queue queue;
	HashMap<Tuple, Boolean> map;
	
 
	SnakeAi(Tuple start, Tuple goal) {
		this.start = start;
		this.goal  = goal;
		this.map = new HashMap<>();
		this.queue = Queue();
	}

	public int countNeighbors() {

	}

	public int manhattanDist(Tuple s, Tuple t) {
		return (int) Math.abs(s.x - t.x) + Math.abs(s.y - t.y);
	}

	public int bestFirstSearch() {
		return -1;

	}
	public void AstarSearch() {}
}

class Queue {
	static class Node {
		int data;
		int priority;

		Node next;
	}

	Node node = new Node();

	static Node newNode(int data, int p) {
		Node temp = new Node();
		temp.data = data;
		temp.priority = p;
		temp.next = null;

		return temp;
	}

	static int peek(Node head) {
		return (head).data;
	}

	static Node pop(Node head) {
		Node temp = head;
		(head) = (head).next;
		return head;
	}

	static Node push(Node head, int data, int p) {
		Node start = (head) ;
		Node temp = newNode(data, p);

		if ((head).priority > p) {
			temp.next = head;
			(head) = temp;
		}
		else {

			while (start.next != null && start.next.priority < p) {
				start = start.next;
			}

			temp.next = start.next;
			start.next = temp;
		}
		return head;
	}

	static boolean isEmpty(Node head) {
		return ((head) == null) ? true : false;
	}

}

