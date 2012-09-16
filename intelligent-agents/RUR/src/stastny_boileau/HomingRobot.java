package stastny_boileau;

import robocode.*;

/**
 * 
 * @author Simon Stastny
 */
public abstract class HomingRobot extends AdvancedRobot {
  
  protected boolean homed = false;

  protected void home() {
    double battleFieldCentreHorizontalDistance = getBattleFieldWidth() / 2 - getX();
    double battleFieldCentreVerticalDistance = getBattleFieldHeight() / 2 - getY();
    
    if (battleFieldCentreHorizontalDistance < 0) {
      turnWest();
    } else {
      turnEast();
    }
    ahead(Math.abs(battleFieldCentreHorizontalDistance));

    if (battleFieldCentreVerticalDistance < 0) {
      turnSouth();
    } else {
      turnNorth();
    }
            
    ahead(Math.abs(battleFieldCentreVerticalDistance));
    
    homed = true;
    scan();
  }

  protected void turnNorth() {
    if (getHeading() > 180) {
      turnRight(360 - getHeading());
    } else {
      turnLeft(getHeading());
    }
  }

  protected void turnSouth() {
    if (getHeading() < 180) {
      turnRight(180 - getHeading());
    } else {
      turnLeft(180 + getHeading());
    }
  }

  protected void turnEast() {
    if (getHeading() < 90) {
      turnRight(90 - getHeading());
    } else if (getHeading() > 270) {
      turnRight(360 - getHeading() + 90);
    } else {
      turnLeft(getHeading() - 90);
    }
  }

  protected void turnWest() {
    if (getHeading() < 90) {
      turnLeft(getHeading() + 90);
    } else if (getHeading() > 270) {
      turnLeft(getHeading() - 270);
    } else {
      turnRight(270 - getHeading());
    }
  }

  public boolean isHomed() {
    return homed;
  }

  public void setHomed(boolean homed) {
    this.homed = homed;
  }
}
