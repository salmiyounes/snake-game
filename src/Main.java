import java.util.ArrayList;
import java.util.Random;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

class Main {
	final static int width  = 600;
	final static int height = 600;
	final static int blockSzie = 15;
	static JFrame frame;
	static JPanel panel;

	public static void main(String[] args)  {
		frame = new JFrame("Snake Game");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		Snake snake = new Snake(blockSzie, width, height);
		frame.add(snake);
		frame.pack();
		snake.requestFocus();
		frame.setVisible(true);

	}
}