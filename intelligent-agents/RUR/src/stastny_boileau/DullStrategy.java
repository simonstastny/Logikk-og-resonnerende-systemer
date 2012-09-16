package stastny_boileau;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/**
 *
 * @author Simon Stastny
 */
public class DullStrategy implements Strategy {

  protected HomingRobot delegate;

  @Override
  public void setDelegate(HomingRobot delegate) {
    this.delegate = delegate;
  }

  @Override
  public void attackManeuvre(ScannedRobotEvent e) {    
    delegate.fire(10);
    delegate.fire(10);
    delegate.fire(10);
    delegate.fire(10);
  }

  @Override
  public void cornerEscapeManeuvre() {
  }

  @Override
  public void wallEscapeManeuvre(HitWallEvent e) {
  }

  @Override
  public void underAttackEscapeManeuvre(HitByBulletEvent e) {
  }

  @Override
  public void collisionEscapeManeuvre(HitRobotEvent e) {
  }
}
