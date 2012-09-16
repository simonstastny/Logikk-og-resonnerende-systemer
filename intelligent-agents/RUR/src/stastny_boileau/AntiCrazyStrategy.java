package stastny_boileau;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

/**
 *
 * @author Simon Stastny
 */
public class AntiCrazyStrategy implements Strategy {

  protected HomingRobot delegate;

  @Override
  public void setDelegate(HomingRobot delegate) {
    this.delegate = delegate;
  }

  @Override
  public void attackManeuvre(ScannedRobotEvent e) {
    delegate.fire(10);
  }

  @Override
  public void cornerEscapeManeuvre() {
    delegate.home();
  }

  @Override
  public void wallEscapeManeuvre(HitWallEvent e) {
    delegate.turnRight(180);
    delegate.home();
  }

  @Override
  public void underAttackEscapeManeuvre(HitByBulletEvent e) {
    defaultManeuvre();
  }

  @Override
  public void collisionEscapeManeuvre(HitRobotEvent e) {
    defaultManeuvre();
  }

  protected void defaultManeuvre() {
    while (true) {
      double result = Math.random();
      if (result < 0.20f) {
        delegate.setAhead(40000);
        delegate.setTurnLeft(78);
        delegate.waitFor(new TurnCompleteCondition(delegate));
      } else if (result < 0.40f) {
        delegate.setAhead(40000);
        delegate.setTurnRight(56);
        delegate.waitFor(new TurnCompleteCondition(delegate));
      } else if (result < 0.70f) {
        delegate.turnRight(45);
      } else {
        delegate.turnLeft(45);
      }
      delegate.scan();
    }
  }
}
