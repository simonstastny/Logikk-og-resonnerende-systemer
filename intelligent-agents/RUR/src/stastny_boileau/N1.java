//-----------------------------------------------------------------------------
// N1 - a robot by Nicolas Boileau
// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
//-----------------------------------------------------------------------------
// Who When     What
// --- -------- ---------------------------------------------------------------
// NCB 31/08/12 Written.
// NCB 03/09/12 Modified.
//-----------------------------------------------------------------------------

package stastny_boileau;
import robocode.*;
import java.awt.Color;


public class N1 extends AdvancedRobot
{
    boolean m_movingForward = true;

    public void run() {
    //-------------------------------------------------------------------------
    // run: N1's default behavior
    //-------------------------------------------------------------------------
        // Initialisation
        setColors(Color.black,Color.white,Color.green,Color.red,Color.green);
        // body,gun,radar,bullet,scan

        double BFHeight = getBattleFieldHeight();
        // double BFWidth = getBattleFieldWidth();

        setTurnLeft(getHeading()); // Turn toward the North!
        setTurnGunLeft(180); // Turn the gun around.        
        waitFor(new TurnCompleteCondition(this));

        setTurnGunLeft(1200); // Turn the gun around while moving North.
        ahead(BFHeight);  // Go far enough to be sure to hit a wall.

	// Robot main loop
	while(true) {
            move(4000);
            searchTarget();
	}
    }

    public void move(double dist) {
    //-------------------------------------------------------------------------
    // move: Move
    //-------------------------------------------------------------------------
        if(!m_movingForward){
            setBack(dist);
        }else{
            setAhead(dist);
        }
    }

    public void setOppositeDirection() {
    //-------------------------------------------------------------------------
    // setOppositeDirection: Change the direction
    //-------------------------------------------------------------------------
        m_movingForward = !m_movingForward;
    }

    public void searchTarget(){
    //-------------------------------------------------------------------------
    // searchTarget: Scan the battle field while moving along a wall
    //-------------------------------------------------------------------------
        turnGunLeft(getGunHeading() - getHeading()); // Initialise gun position
        turnGunRight(180); // Search for a target
    }

    public void onScannedRobot(ScannedRobotEvent e) {
    //-------------------------------------------------------------------------
    // onScannedRobot: Fire!
    //-------------------------------------------------------------------------
        fire(2); // Fire!
    }

//    public void onHitByBullet(HitByBulletEvent e) {
//    //-------------------------------------------------------------------------
//    // onHitByBullet:
//    //-------------------------------------------------------------------------
//        setOppositeDirection(1000);
//    }

    public void onHitWall(HitWallEvent e) {
    //-------------------------------------------------------------------------
    // onHitWall: Turn 90ï¿½ to the right (or to the left if moving backward)
    //-------------------------------------------------------------------------

        if(!m_movingForward){
            stop();
            turnLeft(90);
        }else{
            stop();
            turnRight(90);
        }
        turnGunLeft(getGunHeading() - getHeading()); // Initialise gun position
    }

    public void onHitRobot(HitRobotEvent e) {
    //-------------------------------------------------------------------------
    // onHitRobot: Reverse the direction
    //-------------------------------------------------------------------------
            setOppositeDirection();
    }
}