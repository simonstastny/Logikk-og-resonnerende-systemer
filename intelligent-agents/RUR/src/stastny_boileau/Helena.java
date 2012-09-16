package stastny_boileau;

import robocode.*;

/**
 *
 * @author Simon
 */
public class Helena extends HomingRobot {

  protected Strategy strategy = new DullStrategy();

  protected boolean unspotted = true;

  // variables to keep track of bullet hits and rams
  protected int robotHits = 0;
  protected int bulletHits = 0;

  @Override
  public void run() {
    strategy.setDelegate(this);

    // go to thwe centre of the battlefield
    home();
    homed = true;

    // scan for other robots
    while (unspotted) {
      turnLeft(10);
    }
  }

  @Override
  public void onScannedRobot(ScannedRobotEvent e) {
    if (homed) {
      unspotted = false;
    }
    
    // start attack maneuvre (strategy-dependand)
    strategy.attackManeuvre(e);
  }

  @Override
  public void onHitWall(HitWallEvent e) {
    // start wall escape maneuvre (strategy-dependand)
    strategy.wallEscapeManeuvre(e);
  }

  @Override
  public void onHitRobot(HitRobotEvent e) {
    robotHits++;
    resolveStrategy();
    // start colission escape maneuvre (strategy-dependand)
    strategy.collisionEscapeManeuvre(e);
  }

  @Override
  public void onHitByBullet(HitByBulletEvent e) {
    bulletHits++;
    resolveStrategy();
    // start defense maneuvre (strategy-dependand)
    strategy.underAttackEscapeManeuvre(e);
  }

  // this functions is responsible for switching to the right strategy
  protected void resolveStrategy() {
    if (bulletHits * 3 > (robotHits )) {
      if (!(strategy instanceof AntiCrazyStrategy)) {
        strategy = new AntiCrazyStrategy();
        strategy.setDelegate(this);
      }
      // Crazy probably
    } else {
      // RamFire probably
      if (!(strategy instanceof AntiRamFireStrategy)) {
        strategy = new AntiRamFireStrategy();
        strategy.setDelegate(this);
      }
    }

  }
}