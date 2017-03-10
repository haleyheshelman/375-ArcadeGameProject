import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
		/*
		 * Testing screen dimensions g2.fill(new Rectangle2D.Double(1, 1, 2,
		 * 2)); g2.fill(new Rectangle2D.Double(398, 1, 1, 2)); g2.fill(new
		 * Rectangle2D.Double(399, 2, 1, 2)); g2.fill(new Rectangle2D.Double(0,
		 * 3, 399, 2));
		 */

		if (this.game.USE_IMAGES) {

			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("gameBackground.png"));
			} catch (IOException exception) {

				exception.printStackTrace();
			}

			g2.drawImage(image, 0, 0, this.game.width, this.game.height + 40,
					Color.BLACK, this);
		}

		List<Drawable> drawableParts = this.game.getDrawableParts();
		for (Drawable curDraw : drawableParts) {
			drawDrawable(g2, curDraw);
		}

	}

	/**
	 * This helper method draws the given drawable object on the given graphics
	 * area.
	 *
	 * @param g2
	 * @param d
	 */
	private void drawDrawable(Graphics2D g2, Drawable d) {
		Color color = d.getColor();
		if (color == null) {
			System.out.println("null color");
			return;
		}
		Shape shape = d.getShape();
		if (shape == null) {
			System.out.println("null shape");
			return;
		}

		if (this.game.USE_IMAGES) {

			BufferedImage image = null;
			Rectangle tlr = shape.getBounds();

			if (d.getClass().getName().equals("Mushroom")) {

				try {

					Mushroom m = (Mushroom) d;

					if (m.isPoisonous()) {

						/*
						 * Numbers below are just thresholds for different
						 * health 'levels'
						 */

						if (m.getHealth() == Mushroom.DEFAULT_MUSHROOM_HEALTH) {

							image = ImageIO.read(new File(
									"poisonedMushroomFinal.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - (10)) {

							image = ImageIO.read(new File(
									"poisonedMushroomFinalDamage1.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - (20)) {

							image = ImageIO.read(new File(
									"poisonedMushroomFinalDamage2.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - 30) {

							image = ImageIO.read(new File(
									"poisonedMushroomFinalDamage3.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - 39) {

							image = ImageIO.read(new File(
									"poisonedMushroomFinalDamage4.png"));
						}

					} else {
						if (m.getHealth() == Mushroom.DEFAULT_MUSHROOM_HEALTH) {

							image = ImageIO.read(new File("mushroomFinal.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - (10)) {

							image = ImageIO.read(new File(
									"mushroomFinalDamage1.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - (20)) {

							image = ImageIO.read(new File(
									"mushroomFinalDamage2.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - 30) {

							image = ImageIO.read(new File(
									"mushroomFinalDamage3.png"));

						} else if (m.getHealth() >= Mushroom.DEFAULT_MUSHROOM_HEALTH - 39) {

							image = ImageIO.read(new File(
									"mushroomFinalDamage4.png"));
						}

						// m.setImage(image);
						// image = m.getImage();
					}
				} catch (IOException exception) {

					exception.printStackTrace();
				}

			} else if (d.getClass().getName().equals("Centipede")) {

				try {

					Centipede c = (Centipede) d;

					if (c.xDirection == 1) {

						if (c.poisoned) {

							image = ImageIO.read(new File(
									"centipedePosionedFinal.png"));
						} else {

							if (c.getHealth() < Monster.DEF_MONST_HEALTH) {

								image = ImageIO.read(new File(
										"centipedeFinalDamaged.png"));
							} else {

								image = ImageIO.read(new File(
										"centipedeFinal.png"));
							}
						}

					} else {

						if (c.poisoned) {

							image = ImageIO.read(new File(
									"centipedePosionedFinalTwo.png"));
						} else {

							if (c.getHealth() < Monster.DEF_MONST_HEALTH) {

								image = ImageIO.read(new File(
										"centipedeFinalTwoDamaged.png"));

							} else {

								image = ImageIO.read(new File(
										"centipedeFinalTwo.png"));
							}
						}
					}

				} catch (IOException exception) {

					exception.printStackTrace();
				}

			} else if (d.getClass().getName().equals("Spider")) {

				try {

					Spider s = (Spider) d;
					if (s.getHealth() < Monster.DEF_MONST_HEALTH) {

						image = ImageIO
								.read(new File("spiderFinalDamaged.png"));

					} else {

						image = ImageIO.read(new File("spiderFinal.png"));
					}

				} catch (IOException exception) {

					exception.printStackTrace();
				}

			} else if (d.getClass().getName().equals("Ship")) {

				try {
					image = ImageIO.read(new File("shipFinal.png"));
				} catch (IOException exception) {

					exception.printStackTrace();
				}
			} else if (d.getClass().getName().equals("Scorpion")) {

				try {
					image = ImageIO.read(new File("scorpionFinal.png"));
				} catch (IOException exception) {

					exception.printStackTrace();
				}
			} else if (d.getClass().getName().equals("Flea")) {

				try {
					image = ImageIO.read(new File("fleaFinal.png"));
				} catch (IOException exception) {

					exception.printStackTrace();
				}

			} else {

				g2.setColor(color);
				g2.fill(shape);
			}
			g2.drawImage(image, tlr.x, tlr.y, tlr.width, tlr.height,
					Color.BLACK, this);
		} else {
			g2.setColor(color);
			g2.fill(shape);
		}
	}

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
