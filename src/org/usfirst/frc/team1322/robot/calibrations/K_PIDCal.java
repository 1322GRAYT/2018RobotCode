package org.usfirst.frc.team1322.robot.calibrations;



/**
 * Class: K_PIDCal - Contains the Sensor Calibrations for the
 * the PID Controllers.
 */
public class K_PIDCal {


  /* KDRV_n_ErrDeadBand: Linear Distance Travelled Forward/Rearward
   * per one Wheel Revolution (inches). */
  public static final float KDRV_n_ErrDeadBand[] = new float[] 
      {
    	(float)5.0,  // RF: Right Front
        (float)5.0,  // LF: Left Front
        (float)5.0,  // RR: Right Rear
        (float)5.0   // LR: Left Rear
      };

	
	
	
}
