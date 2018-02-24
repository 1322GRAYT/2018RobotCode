package org.usfirst.frc.team1322.robot.calibrations;



/**
 * Class: K_PIDCal - Contains the Sensor Calibrations for the
 * the PID Controllers.
 */
public class K_PIDCal {


  /* KDRV_n_ErrLimMax: Maximum Speed Error allowed, used
   * as a crude Rate Limiter on the Target Speed Rate to
   * Prevent Excessive Jerk on Step Responses. (rpm). */
  public static final float KDRV_n_ErrLimMax[] = new float[] 
      {
    	(float)500.0,  // RR: Right Rear
        (float)500.0,  // RF: Right Front
        (float)500.0,  // LF: Left Front
        (float)500.0   // LR: Left Rear
      };  
	
	
  /* KDRV_n_ErrDeadBand: Speed Error Dead-Band for the PID
   * Controller (rpm). */
  public static final float KDRV_n_ErrDeadBand[] = new float[] 
      {
    	(float)5.0,  // RR: Right Rear
        (float)5.0,  // RF: Right Front
        (float)5.0,  // LF: Left Front
        (float)5.0   // LR: Left Rear
      };  
  
  
  /* KDRV_K_PropGx: Drive System PID Controls Proportional Gain . */
  public static final float KDRV_K_PropGx[] = new float[] 
      {
    	(float)0.01,  // RR: Right Rear
        (float)0.01,  // RF: Right Front
        (float)0.01,  // LF: Left Front
        (float)0.01   // LR: Left Rear
      };
  
  /* KDRV_K_IntglGx: Drive System PID Controls Integral Gain . */
  public static final float KDRV_K_IntglGx[] = new float[] 
      {
    	(float)0.0001,  // RR: Right Rear
        (float)0.0001,  // RF: Right Front
        (float)0.0001,  // LF: Left Front
        (float)0.0001   // LR: Left Rear
      };
  
  /* KDRV_K_DerivGx: Drive System PID Controls Derivative Gain . */
  public static final float KDRV_K_DerivGx[] = new float[] 
      {
    	(float)0.0,  // RR: Right Rear
        (float)0.0,  // RF: Right Front
        (float)0.0,  // LF: Left Front
        (float)0.0   // LR: Left Rear
      };
    
  /* KDRV_b_IntglGxRstSgnFlipEnbl: Enables the Reset of the Integral
   * Term when the Error Sign Flips and the Speed Error Accum is the
   * opposite sign.  To counteract Integral Wind-Up in a very delayed
   * system.  */
  public static final boolean KDRV_b_IntglGxRstSgnFlipEnbl = false; 
  
  /* KDRV_Pct_PropCorrLimMax: Linear Distance Traveled Forward/Rearward
   * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_PropCorrLimMax[] = new float[] 
      {
    	(float)20.0,  // RR: Right Rear
        (float)20.0,  // RF: Right Front
        (float)20.0,  // LF: Left Front
        (float)20.0   // LR: Left Rear
      };
  
  /* KDRV_Pct_PropCorrLimMax: Linear Distance Traveled Forward/Rearward
   * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_IntglCorrLimMax[] = new float[] 
      {
    	(float)15.0,  // RR: Right Rear
        (float)15.0,  // RF: Right Front
        (float)15.0,  // LF: Left Front
        (float)15.0   // LR: Left Rear
      };

  /* KDRV_Pct_DerivCorrLimMax: Linear Distance Traveled Forward/Rearward
   * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_DerivCorrLimMax[] = new float[] 
      {
    	(float)15.0,  // RR: Right Rear
        (float)15.0,  // RF: Right Front
        (float)15.0,  // LF: Left Front
        (float)15.0   // LR: Left Rear
      };
  
  
	
}
