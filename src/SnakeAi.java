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
 
	SnakeAi(Tuple food, int[][] board, Direction direction) {
		this.board = board;
		this.food  = food;
		this.direction = direction;
		this.map   = new HashMap<>();
		this.queue = new PriorityQueue<>(Comparator.comparingInt(item -> manhattanDist(item, food)));
	}
	public Boolean ishere(HashMap<Tuple, Boolean> map, Tuple pos) {
		for (Tuple l: map.keySet()) {
			if (l.x == pos.x && l.y == pos.y) {
				return true;
			}
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
	
	public ArrayList<Tuple> bestfirstSearch(Tuple start) {
		HashMap<Tuple, Boolean> visited = new HashMap<>();
		HashMap<Tuple, Tuple> cameFrom = new HashMap<>();
		visited.put(start, true);
		PriorityQueue<Tuple> queue = new PriorityQueue<>(Comparator.comparingInt(item -> manhattanDist(item, this.food)));
		queue.add(start);
		while (!queue.isEmpty()) {
			Tuple curr = queue.peek();
			queue.remove(curr);
			for (Tuple l : generateNeighbors(curr)) {
				if (!ishere(visited, l)) {
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
	public Boolean makeSure(Set<Tuple> list, Tuple pos) {
		for (Tuple l : list) {
			if (pos.x == l.x && pos.y == l.y) {return true;}
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
		ArrayList<Tuple> path =  bestfirstSearch(start);
		Tuple nextStep = path.get(0);
		if (nextStep.x == start.x + 1) return Direction.Right;
        if (nextStep.x == start.x - 1) return Direction.Left;
        if (nextStep.y == start.y + 1) return Direction.Down;
        if (nextStep.y == start.y - 1) return Direction.Up;
        return null;
	}


	public void AstarSearch() {}
	public int manhattanDist(Tuple s, Tuple t) {
		return (int) Math.abs(s.x - t.x) + Math.abs(s.y - t.y);
	}

}
