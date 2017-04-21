import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Renders a arcade game on the GUI.
 *
 * @author deradaam, lub and verlaqd. Created Oct 28, 2015.
 */
@SuppressWarnings("serial")
public class ArcadeGameComponent extends JComponent {

	public static int FRAMES_PER_SECOND = 40;
	protected static long REPAINT_INTERVALS_MS = 1000 / FRAMES_PER_SECOND;

	protected ArcadeGame game;
	protected static Random rand = new Random();

	/**
	 * Constructs a component for rendering the given game environment on the
	 * GUI.
	 *
	 * @param game
	 */
	public ArcadeGameComponent(ArcadeGame game) {

		this.game = game;
		this.setFocusable(true);
		ArcadeGameKeyListener a = new ArcadeGameKeyListener(this, this.game);
		addKeyListener(a);

		Runnable refresh = new Runnable() {
			@Override
			public void run() {
				// Periodically asks Java to repaint this component
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVALS_MS);
						if (!ArcadeGameComponent.this.game.isPaused) {
							ArcadeGameComponent.this.onEveryRefresh();
						}
					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(refresh).start();
	}

	protected void onEveryRefresh() {
		this.game.onEveryRefresh();
		repaint();

	}

	/**
	 * Paints the game on the GUI.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (this.game.USE_IMAGES) {
			drawBackground(g2);
		}

		drawDrawables(g2);
	}

	/**
	 * Draw all drawables in the game
	 * 
	 * @param graphics
	 */
	private void drawDrawables(Graphics2D graphics) {
		for (Drawable eachDrawable : this.game.getDrawableParts()) {
			drawDrawable(graphics, eachDrawable);
		}
	}

	/**
	 * Draw the background
	 *
	 * @param graphics
	 */
	private void drawBackground(Graphics2D graphics) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File("gameBackground.png"));
			graphics.drawImage(image, 0, 0, ArcadeGame.width, ArcadeGame.height + 40,
					Color.BLACK, this);

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Draw a specific drawable
	 *
	 * @param graphics
	 * @param drawable
	 */
	private void drawDrawable(Graphics2D graphics, Drawable drawable) {
		Color color = drawable.getColor();
		Shape shape = drawable.getShape();
		if (shape == null || color == null)
			return;

		if (this.game.USE_IMAGES) {
			try {
				drawWithImages(graphics, drawable);
				return;
			} catch (Exception exception) {
				/*
				 * nothing to do with exception; we'll just be drawing shape and
				 * color below
				 */
			}
		}
		graphics.setColor(color);
		graphics.fill(shape);

	}

	/**
	 * Attempt to draw a Drawable with rich graphics
	 *
	 * @param g2
	 * @param d
	 * @param shape
	 * @throws Exception
	 */
	private void drawWithImages(Graphics2D g2, Drawable d) throws Exception {
		BufferedImage image = d.getImage();
		Shape shape = d.getShape();
		if (image == null)
			throw new NullPointerException(
					"No image for this Drawable: " + d.toString());

		Rectangle bounds = shape.getBounds();
		g2.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height,
				Color.BLACK, this);
	}

	/**
	 * Change FPS
	 *
	 * @param deltaFPS
	 */
	public void changeFPS(int deltaFPS) {
		if (FRAMES_PER_SECOND >= 10 && FRAMES_PER_SECOND <= 150) {
			FRAMES_PER_SECOND += deltaFPS;
		} else if (FRAMES_PER_SECOND > 150) {
			FRAMES_PER_SECOND = 150;
		} else if (FRAMES_PER_SECOND < 10) {
			FRAMES_PER_SECOND = 10;
		}
		REPAINT_INTERVALS_MS = 1000 / FRAMES_PER_SECOND;
	}

}
