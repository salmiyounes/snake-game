import java.util.ArrayList;
import java.util.Random;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

public class Snake extends JPanel implements ActionListener, KeyListener {

	enum Direction {
		Up,
		Down,
		Right,
		Left
	}

	public static int[][] board;
	public static int score = 0;
	public static int SIZE;
	public static int Width;
	public static int Height;
	public static Tuple food;
	Timer gameloop;
	Boolean gameover = false;
	public static ArrayList<Tuple> body = new ArrayList<>();
	public static Tuple head = new Tuple(5, 5);
	static Direction direction = Direction.Right;

	Snake(int size, int width, int height) {
		SIZE = size;
		Width = width;
		Height = height;
		createBoard();
		body.add(new Tuple(4, 5));
		body.add(new Tuple(3, 5));
		spwanFood();
		setPreferredSize(new Dimension(Width, Height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        gameloop = new Timer(100, this);
        gameloop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// snake head
		g.setColor(Color.GREEN);
		g.fill3DRect(head.x * SIZE, head.y * SIZE, SIZE, SIZE, true);

		// spawn food
		g.setColor(Color.RED);
		g.fill3DRect(food.x * SIZE, food.y * SIZE, SIZE, SIZE, true); 

		// snake body
		for (Tuple l : body) {
			g.setColor(Color.BLUE);
			g.fill3DRect(l.x * SIZE, l.y * SIZE, SIZE, SIZE, true);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Score : " + String.valueOf(score), SIZE - 20, SIZE);

	}

	public void createBoard() {
		board = new int[Width/SIZE][Height/SIZE];
		board[head.x][head.y] = 1;
	}

	public void spwanFood() {
		ArrayList<Tuple> spots = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < Width / SIZE; i++) {
			for (int j = 0; j < Height / SIZE; j ++) {
				if (!(body.contains(new Tuple(i, j))) && (head.x != i && head.y != j)) {
					spots.add(new Tuple(i, j));
				}
			}
		}
		food = spots.get(random.nextInt(spots.size() -1));
		board[food.x][food.y] = -1;
	}

	public boolean wallCollision(int size) {
		return ( (head.x < 0) || (head.x >= size) || (head.y < 0) || (head.y >= size) ); 
	}

	public void eatFood() {
		if (board[head.x][head.y] == -1) {
			body.add(new Tuple(food.x, food.y));
			board[food.x][food.y] = 0;
			score ++;
			spwanFood();
		}
	}

	public boolean bodyCollision() {
		for (Tuple t: body) {
			if (t.x == head.x && t.y == head.y) return true;
		}
		return false;
	}

	public void move() {
		eatFood();
		// move the snake body 
		for (int i = body.size() -1 ; i >= 0; i--) {
			Tuple snakepart = body.get(i);
			if (i == 0) {
				snakepart.x = head.x;
				snakepart.y = head.y;
			}
			else {
				Tuple prevsnakepart = body.get(i-1);
				snakepart.x = prevsnakepart.x;
				snakepart.y = prevsnakepart.y;
			}
		}

		if (direction == Direction.Up) {
			head.y --;
		}
		if (direction == Direction.Down) {
			head.y ++;
		}
		if (direction == Direction.Right) {
			head.x ++;
		}
		if (direction == Direction.Left) {
			head.x --;
		}

		if (wallCollision(Width/SIZE)) gameover = true;
		if (bodyCollision()) gameover = true;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
		move();
		if (gameover) gameloop.stop();
	} 

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP && direction != Direction.Down) {
			direction = Direction.Up;
		}
		if (key == KeyEvent.VK_DOWN && direction != Direction.Up) {
			direction = Direction.Down;
		}
		if (key == KeyEvent.VK_RIGHT && direction != Direction.Left) {
			direction = Direction.Right;
		}
		if (key == KeyEvent.VK_LEFT && direction != Direction.Right) {
			direction = Direction.Left;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}