package stastny_boileau;

import robocode.*;

/**
 *
 * @author Simon Stastny
 */
public class AntiRamFireStrategy implements Strategy {

  protected HomingRobot delegate;

  @Override
  public void setDelegate(HomingRobot delegate) {
    this.delegate = delegate;
  }

  @Override
  public void cornerEscapeManeuvre() {
    // no corner escape menauvre..
    // pray that robot doesn't end up in corner
  }

  @Override
  public void wallEscapeManeuvre(HitWallEvent e) {
    delegate.turnRight(e.getBearing() / 2);
  }

  @Override
  public void underAttackEscapeManeuvre(HitByBulletEvent e) {
  }

  @Override
  public void collisionEscapeManeuvre(HitRobotEvent e) {
    delegate.turnRight(e.getBearing());

    // here comes the magic
    while (true) {
      delegate.back(180);
      delegate.fire(10);
      delegate.turnRight(90);
    }
  }

  @Override
  public void attackManeuvre(ScannedRobotEvent e) {
    if (delegate.isHomed()) {
      delegate.fire(10);
    }
  }
}
