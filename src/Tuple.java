
public class Tuple{ 

    public Integer x; 
    public Integer y; 
    public Tuple(Integer x, Integer y) { 
        this.x = x; 
        this.y = y; 
    }

    public void goLeft() {
    	this.y -= 1;
    }

    public void goRight() {
    	this.y += 1;
    }

    public void goUp() {
    	this.x -= 1;
    }

    public void goDown() {
    	this.x += 1;
    }
    public void update(Tuple other) {
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }


}

