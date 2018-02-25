package org.usfirst.frc.team1322.robot.calibrations;



/**
 * Class: K_PIDCal - Contains the Sensor Calibrations for the
 * the PID Controllers.
 */
public class K_PIDCal {

	
  /** KDRV_t_PID_LoopRt: Execution Loop Rate Period for the Drive
    * System PID Controller (sec). */
	  public static final float KDRV_t_PID_LoopRt = (float)0.020; // sec 
  
	  
  /** KDRV_t_TgtProfTmAxis: Time Since Start of Drive Segment Axis
    * for the Target Speed Profile Shaping Tables. (rpm). */
  public static final float KDRV_t_TgtProfTmAxis[] = new float[] 
      {
	      (float)0.0,
          (float)0.1,
          (float)0.2,
          (float)0.3,
    	  (float)0.4,
          (float)0.5,
          (float)0.7,
          (float)0.9,
          (float)1.1,
          (float)1.25
	  };	  
	  
  
  /** KDRV_n_TgtProfSpdAxis: Target Speed Axis for the Target
   * Speed Profile Shaping Tables. (rpm). */
  public static final int KDRV_n_TgtProfSpdAxis[] = new int[] 
      {
	      (int)500,
          (int)800,
          (int)1000,
          (int)1500,
    	  (int)2000,
          (int)2500,
          (int)3000,
          (int)3300,
          (int)3600,
          (int)4000
	  };	  

  
  /** KDRV_r_TgtProf: Target Speed Profile Shaping Table
   * for Launching a Drive Segment. (scalar). */
  public static final float KDRV_n_TgtProf[][] = new float[][] 
      { //   time0       time1		  time2        time3        time4        time5        time6        time7        time8        time9
	      {(float)2.0,  (float)3.0,  (float)2.0,  (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 500 rpm
	      {(float)1.5,  (float)2.5,  (float)2.0,  (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 800 rpm
	      {(float)1.25, (float)2.0,  (float)1.75, (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 1000 rpm
	      {(float)1.0,  (float)1.5,  (float)1.25, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 1500 rpm
	      {(float)0.8,  (float)1.2,  (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 2000 rpm
	      {(float)0.5,  (float)0.75, (float)0.85, (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 2500 rpm
	      {(float)0.4,  (float)0.6,  (float)0.8,  (float)0.9,  (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 3000 rpm
	      {(float)0.3,  (float)0.5,  (float)0.7,  (float)0.85, (float)0.9,  (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 3300 rpm
	      {(float)0.2,  (float)0.4,  (float)0.55, (float)0.65, (float)0.8,  (float)0.85, (float)0.9,  (float)0.95, (float)1.0,  (float)1.0 },  // 3600 rpm
	      {(float)0.1,  (float)0.25, (float)0.4,  (float)0.6,  (float)0.75, (float)0.85, (float)0.9,  (float)0.95, (float)0.98, (float)1.0 }   // 4000 rpm
	  };	  
	  
  
  /** KDRV_n_FdFwdErrAxis: Maximum Speed Error allowed, used
    * as a crude Rate Limiter on the Target Speed Rate to
    * Prevent Excessive Jerk on Step Responses. (rpm). */
  public static final int KDRV_n_FdFwdErrAxis[][] = new int[][] 
      {
	    { // RR: Right Rear
	      (int)500,
          (int)800,
          (int)1000,
          (int)1500,
    	  (int)2000,
          (int)2500,
          (int)3000,
          (int)3300,
          (int)3600,
          (int)4000
	    },
	    { // RF: Right Front
	      (int)500,
          (int)800,
          (int)1000,
          (int)1500,
    	  (int)2000,
          (int)2500,
          (int)3000,
          (int)3300,
          (int)3600,
          (int)4000
		},
	    { // LF: Left Front
	      (int)500,
          (int)800,
          (int)1000,
          (int)1500,
    	  (int)2000,
          (int)2500,
          (int)3000,
          (int)3300,
          (int)3600,
          (int)4000
		},
	    {// LR: Left Rear
	      (int)500,
          (int)800,
          (int)1000,
          (int)1500,
    	  (int)2000,
          (int)2500,
          (int)3000,
          (int)3300,
          (int)3600,
          (int)4000
		 }
      };  
		
  /** KDRV_Pct_FdFwdCmnd: Feed Forward Command of Drive Motor Percent Power
    * as a function of Desired Encoder Speed (% Pwr: 0-100%) */
  public static final float KDRV_Pct_FdFwdCmnd[][] = new float[][] 
      {
	    { // RR: Right Rear
	      (float)15.00,
          (float)20.00,
          (float)30.00,
          (float)40.00,
    	  (float)55.00,
          (float)70.00,
          (float)80.00,
          (float)85.00,
          (float)90.00,
          (float)95.00
	    },
	    { // RF: Right Front
	      (float)15.00,
          (float)20.00,
          (float)30.00,
          (float)40.00,
    	  (float)55.00,
          (float)70.00,
          (float)80.00,
          (float)85.00,
          (float)90.00,
          (float)95.00
		},
	    { // LF: Left Front
	      (float)15.00,
          (float)20.00,
          (float)30.00,
          (float)40.00,
    	  (float)55.00,
          (float)70.00,
          (float)80.00,
          (float)85.00,
          (float)90.00,
          (float)95.00
		},
	    {// LR: Left Rear
	      (float)15.00,
          (float)20.00,
          (float)30.00,
          (float)40.00,
    	  (float)55.00,
          (float)70.00,
          (float)80.00,
          (float)85.00,
          (float)90.00,
          (float)95.00
		 }
      };  
      	
  /** KDRV_n_ErrLimMax: Maximum Speed Error allowed, used
    * as a crude Rate Limiter on the Target Speed Rate to
    * Prevent Excessive Jerk on Step Responses. (rpm). */
  public static final float KDRV_n_ErrLimMax[] = new float[] 
      {
    	(float)500.0,  // RR: Right Rear
        (float)500.0,  // RF: Right Front
        (float)500.0,  // LF: Left Front
        (float)500.0   // LR: Left Rear
      };  
	
  /** KDRV_n_ErrDeadBand: Speed Error Dead-Band for the PID
    * Controller (rpm). */
  public static final float KDRV_n_ErrDB[] = new float[] 
      {
    	(float)5.0,  // RR: Right Rear
        (float)5.0,  // RF: Right Front
        (float)5.0,  // LF: Left Front
        (float)5.0   // LR: Left Rear
      };  

  /** KDRV_dn_ErrChgRtDB: Speed Error Change Rate Dead-Band for the
   * PID Controller (rpm). */
 public static final float KDRV_dn_ErrChgRtDB[] = new float[] 
     {
       (float)200.0,  // RR: Right Rear
       (float)200.0,  // RF: Right Front
       (float)200.0,  // LF: Left Front
       (float)200.0   // LR: Left Rear
     };  
  
  /** KDRV_n_DerivCorrErrMinEnbl: Max Speed Error to Enable
    * Derivative Correction, if SpdErr is > this Threshold
    * Derivative Correction will not be applied to the PID Correction. */
  public static final float KDRV_n_DerivCorrErrMinEnbl[] = new float[] 
      {
    	(float)10.0,  // RR: Right Rear
        (float)10.0,  // RF: Right Front
        (float)10.0,  // LF: Left Front
        (float)10.0   // LR: Left Rear
      };  
  
  /** KDRV_n_DerivCorrErrMaxEnbl: Max Speed Error to Enable
    * Derivative Correction, if SpdErr is > this Threshold
    * Derivative Correction will not be applied to the PID Correction. */
  public static final float KDRV_n_DerivCorrErrMaxEnbl[] = new float[] 
      {
    	(float)200.0,  // RR: Right Rear
        (float)200.0,  // RF: Right Front
        (float)200.0,  // LF: Left Front
        (float)200.0   // LR: Left Rear
      };  
  
  /** KDRV_k_SpdErrFiltCoef: Lag Filter Coefficient for Filtering
    * The Encoder Speed Error Calculation (0 - 1). */
  public static final float KDRV_k_SpdErrFiltCoef[] = new float[] 
      {
    	(float)0.75,  // RR: Right Rear
        (float)0.75,  // RF: Right Front
        (float)0.75,  // LF: Left Front
        (float)0.75   // LR: Left Rear
      };

  /** KDRV_k_SpdErrRtFiltCoef: Lag Filter Coefficient for Filtering
    * The Encoder Speed Change Rate Error Calculation (0 - 1). */
  public static final float KDRV_k_SpdErrRtFiltCoef[] = new float[] 
      {
    	(float)0.85,  // RR: Right Rear
        (float)0.85,  // RF: Right Front
        (float)0.85,  // LF: Left Front
        (float)0.85   // LR: Left Rear
      };
  
  /** KDRV_K_PropGx: Drive System PID Controls Proportional Gain . */
  public static final float KDRV_K_PropGx[] = new float[] 
      {
    	(float)0.01,  // RR: Right Rear
        (float)0.01,  // RF: Right Front
        (float)0.01,  // LF: Left Front
        (float)0.01   // LR: Left Rear
      };
  
  /** KDRV_K_IntglGx: Drive System PID Controls Integral Gain . */
  public static final float KDRV_K_IntglGx[] = new float[] 
      {
    	(float)0.0005,  // RR: Right Rear
        (float)0.0005,  // RF: Right Front
        (float)0.0005,  // LF: Left Front
        (float)0.0005   // LR: Left Rear
      };
  
  /** KDRV_K_DerivGx: Drive System PID Controls Derivative Gain . */
  public static final float KDRV_K_DerivGx[] = new float[] 
      {
    	(float)0.0,  // RR: Right Rear
        (float)0.0,  // RF: Right Front
        (float)0.0,  // LF: Left Front
        (float)0.0   // LR: Left Rear
      };
    
  /** KDRV_b_IntglGxRstSgnFlipEnbl: Enables the Reset of the Integral
    * Term when the Error Sign Flips and the Speed Error Accum is the
    * opposite sign.  To counteract Integral Wind-Up in a very delayed
    * system.  */
  public static final boolean KDRV_b_IntglGxRstSgnFlipEnbl = false; 
  
  /** KDRV_Pct_PropCorrLimMax: Linear Distance Traveled Forward/Rearward
    * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_PropCorrLimMax[] = new float[] 
      {
    	(float)20.0,  // RR: Right Rear
        (float)20.0,  // RF: Right Front
        (float)20.0,  // LF: Left Front
        (float)20.0   // LR: Left Rear
      };
  
  /** KDRV_Pct_PropCorrLimMax: Linear Distance Traveled Forward/Rearward
    * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_IntglCorrLimMax[] = new float[] 
      {
    	(float)15.0,  // RR: Right Rear
        (float)15.0,  // RF: Right Front
        (float)15.0,  // LF: Left Front
        (float)15.0   // LR: Left Rear
      };

  /** KDRV_Pct_DerivCorrLimMax: Linear Distance Traveled Forward/Rearward
    * per one Wheel Revolution (inches). */
  public static final float KDRV_Pct_DerivCorrLimMax[] = new float[] 
      {
    	(float)15.0,  // RR: Right Rear
        (float)15.0,  // RF: Right Front
        (float)15.0,  // LF: Left Front
        (float)15.0   // LR: Left Rear
      };
	
}
