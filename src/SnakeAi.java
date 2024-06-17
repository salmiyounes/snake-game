import java.util.*;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SnakeAi {
	int[][] board;
	Direction direction;
	Tuple start;
	Tuple goal;
	Tuple food;
	HashMap<Tuple, Boolean> map;
	PriorityQueue<Tuple> queue;
	ArrayList<Tuple> snakebody; 
 
	SnakeAi(Tuple food, int[][] board, Direction direction, ArrayList<Tuple> snakebody) {
		this.snakebody = snakebody;
		this.board = board;
		this.food  = food;
		this.direction = direction;
		this.map   = new HashMap<>();
	}
	public Boolean ishere(PriorityQueue<Tuple> queue, Tuple pos) {
		Iterator<Tuple> iterate = queue.iterator();
		while (iterate.hasNext()) {
			Tuple t = iterate.next();
			if (t.x == pos.x && t.y == pos.y ) return true;
		}
		return false;
	}
	public ArrayList<Tuple> generateNeighbors(Tuple pos) {
		ArrayList<Tuple> result = new ArrayList<>();
		result.add(new Tuple(pos.x + 1, pos.y));
		result.add(new Tuple(pos.x - 1, pos.y));
		result.add(new Tuple(pos.x, pos.y + 1));
		result.add(new Tuple(pos.x, pos.y -1 ));
		return result;
	}
	public Boolean partoftheBody(ArrayList<Tuple> body, Tuple pos) {
		for (Tuple part: body) {
			if (part.x == pos.x && part.y == pos.y) {return true;}
		}
		return false;
	}
	public ArrayList<Tuple> bestfirstSearch(Tuple start) {
		HashMap<Tuple, Boolean> visited = new HashMap<>();
		HashMap<Tuple, Tuple> cameFrom = new HashMap<>();
		PriorityQueue<Tuple> queue = new PriorityQueue<>(Comparator.comparingInt(item -> manhattanDist(item, this.food)));
		queue.add(start);
		while (!queue.isEmpty()) {
			Tuple curr = queue.peek();
			System.out.println(curr);
			queue.remove(curr);
			for (Tuple l : generateNeighbors(curr)) {
				if (!(isgoodMove(l)) && !(partoftheBody(this.snakebody, l))) {
					if (l.x == this.food.x && l.y == this.food.y) {
						cameFrom.put(l, curr);
						return reconstructPath(cameFrom, l);
						}
					else {
						visited.put(l, true);
						queue.add(l);
						cameFrom.put(l, curr);
					}
				}
			}
		}

		return null;
	}
	public Boolean isgoodMove(Tuple pos) {
		return ( (pos.x >= this.board.length) || (pos.x < 0) || (pos.y >= this.board.length) || (pos.y < 0)) ;
	}
	
	public Boolean makeSure(Set<Tuple> list, Tuple pos) {
		for (Tuple l : list) {
			if (pos != null && pos.x == l.x && pos.y == l.y) {return true;}
		}
		return false;
	}
	public ArrayList<Tuple> reconstructPath(HashMap<Tuple, Tuple> cameFrom, Tuple curr) {
		ArrayList<Tuple> path = new ArrayList<>();
		while (makeSure(cameFrom.keySet(), curr)) {
			path.add(curr);
			curr = cameFrom.get(curr);

		}
		Collections.reverse(path);

		return path;
	}

	public Direction nextMove(Tuple start) {
		ArrayList<Tuple> path =  AstarSearch(start);
		if ((path != null))
			if (!(path.isEmpty())) {
				Tuple nextStep = path.get(0);
				if (nextStep.x == start.x + 1) return Direction.Right;
        		if (nextStep.x == start.x - 1) return Direction.Left;
        		if (nextStep.y == start.y + 1) return Direction.Down;
        		if (nextStep.y == start.y - 1) return Direction.Up;
        }
        if (isgoodMove(new Tuple(start.x + 1, start.y))) return Direction.Right;
        if (isgoodMove(new Tuple(start.x - 1, start.y ))) return Direction.Left;
        if (isgoodMove(new Tuple(start.x, start.y +1))) return Direction.Down;
        if (isgoodMove(new Tuple(start.x, start.y -1))) return Direction.Up;
		return Direction.Right;
	}

	public ArrayList<Tuple> AstarSearch(Tuple start) {
		HashMap<Tuple, Integer> fScore = new HashMap<>();
    	PriorityQueue<Tuple> openset = new PriorityQueue<>(Comparator.comparingInt(item -> fScore.getOrDefault(item, Integer.MAX_VALUE)));
    	HashMap<Tuple, Tuple> cameFrom = new HashMap<>();
    	HashMap<Tuple, Integer> gScore = new HashMap<>();
    
    	gScore.put(start, 0);
    	fScore.put(start, euclideanDistance(start, this.food));
    	openset.add(start);

    	while (!openset.isEmpty()) {
        	Tuple curr = openset.poll();  // Retrieve and remove the head of the queue
        	if (curr.x == this.food.x && curr.y == food.y) {
        		if (!(isgoodMove(curr)) && !(partoftheBody(this.snakebody, curr))) {
            		return reconstructPath(cameFrom, curr);
        		}
        	}

        	for (Tuple neighbor : generateNeighbors(curr)) {
        		if (isgoodMove(neighbor) || partoftheBody(this.snakebody, neighbor)) {
    	            continue;
	            }

            	int tentative_gScore = gScore.getOrDefault(curr, Integer.MAX_VALUE) + euclideanDistance(curr, neighbor);

            	if (tentative_gScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                	cameFrom.put(neighbor, curr);
                	gScore.put(neighbor, tentative_gScore);
                	fScore.put(neighbor, tentative_gScore + euclideanDistance(neighbor, this.food));

                	if (!(ishere(openset, neighbor))) {
                    	openset.add(neighbor);
                	}
            	}
        	}
    	}

    	return null;  // Return null if no path is found
	}

	public int manhattanDist(Tuple s, Tuple t) {
		return (int) Math.abs(s.x - t.x) + Math.abs(s.y - t.y);
	}
	public int euclideanDistance(Tuple s, Tuple t) {
		return (int) Math.sqrt(Math.pow(s.x - t.x, 2) + Math.pow(s.y - t.y, 2));
	}

}
