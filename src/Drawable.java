import java.awt.Color;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents objects that have a shape and a fill color.
 * 
 * @author deradaam,lub and verlaqd. Created Oct 28, 2015.
 */
public interface Drawable {

	/**
	 * Gets the fill color that should be drawn to represent this object.
	 * 
	 * @return the fill color
	 */
	Color getColor();
	
	/**
	 * Gets the shape the should be drawn to represent this object.
	 * 
	 * @return the shape to draw
	 */
	Shape getShape();

	/**
	 * 
	 * Sets the color of this object.
	 *
	 * @param color
	 */
	void setColor(Color color);

	/**
	 * Gets the image of the object.
	 *
	 * @return
	 * @throws IOException
	 */
	default BufferedImage getImage() throws IOException {
		return null;
	}
}
