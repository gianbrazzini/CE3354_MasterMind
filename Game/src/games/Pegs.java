import java.awt.Color; 

public enum Pegs {
	
	RED(Color.RED, "r"),
	GREEN(Color.GREEN, "g"),
	BLUE(Color.BLUE, "b"),
	YELLOW(Color.YELLOW, "y"),
	ORANGE(Color.ORANGE, "o"),
	WHITE(Color.WHITE, "w"),
	BLACK(Color.BLACK, "b"),
	PURPLE(Color.MAGENTA, "p");
	
	Color c;
	String name;
	
	Pegs(Color c, String displayName)
	{
		this.c = c;
		this.name = displayName;
	}
	
}
