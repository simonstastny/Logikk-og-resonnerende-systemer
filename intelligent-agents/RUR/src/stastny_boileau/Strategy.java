package stastny_boileau;

import robocode.*;

/**
 *
 * @author Simon Stastny
 */
public interface Strategy {
  
  public void setDelegate(HomingRobot delegate);
  
  public void attackManeuvre(ScannedRobotEvent e);
  
  public void cornerEscapeManeuvre();
  
  public void wallEscapeManeuvre(HitWallEvent e);
  
  public void underAttackEscapeManeuvre(HitByBulletEvent e);
  
  public void collisionEscapeManeuvre(HitRobotEvent e);  
  
}
