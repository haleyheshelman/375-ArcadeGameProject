import java.awt.geom.Point2D;

public class ShotGun extends Projectile {
	
	public ShotGun(ArcadeGame game, Point2D centerPoint) {
		super(game, centerPoint);
		
		generateBullet(0); 
		generateBullet(-1); 
		generateBullet(1);
		
	}
	
	private void generateBullet (double xVel){
		Bullet b = new Bullet (this.getCenterPoint()); 
		b.setVelocityX(xVel);
		this.getGame().addObject(b);
		
	}
}
