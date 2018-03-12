package org.usfirst.frc.team1322.robot.calibrations;



/**
 * Class: K_PIDCal - Contains the Sensor Calibrations for the
 * the PID Controllers.
 */
public class K_PIDCal {

	
  /** KDRV_t_PID_LoopRt: Execution Loop Rate Period for the Drive
    * System PID Controller (sec). */
	  public static final float KDRV_t_PID_LoopRt = (float)0.020; // sec 
  
	  
	  
/*****************************************************************/
/* Drive Launch Shaping Control Calibrations                */
/*****************************************************************/	  
	  
  /** KDRV_t_TgtProfTmAxis: Time Since Start of Drive Segment Axis
    * for the Target Speed Profile Shaping Tables. (Seconds). */
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
	  
  
  /** KDRV_r_TgtPwrSpdAxis: Target Percent Power Axis for the Target
   * Power Launch Profile Shaping Tables. (Percent Power). */
  public static final int KDRV_r_TgtPwrSpdAxis[] = new int[] 
      {
	      (int)10,
          (int)15,
          (int)20,
          (int)30,
    	  (int)40,
          (int)50,
          (int)60,
          (int)70,
          (int)80,
          (int)90
	  };	  

  
  /** KDRV_r_TgtProf: Target Speed Profile Shaping Table
   * for Launching a Drive Segment. (scalar). */
  public static final float KDRV_n_TgtProf[][] = new float[][] 
      { //   time0       time1		  time2        time3        time4        time5        time6        time7        time8        time9
	      {(float)2.0,  (float)3.0,  (float)2.0,  (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 5  %power
	      {(float)1.5,  (float)2.5,  (float)2.0,  (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 10 %power
	      {(float)1.25, (float)2.0,  (float)1.75, (float)1.5,  (float)1.25, (float)1.15, (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0 },  // 20 %power
	      {(float)1.0,  (float)1.5,  (float)1.25, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 30 %power
	      {(float)0.8,  (float)1.2,  (float)1.1,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 40 %power
	      {(float)0.5,  (float)0.75, (float)0.85, (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 50 %power
	      {(float)0.4,  (float)0.6,  (float)0.8,  (float)0.9,  (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 60 %power
	      {(float)0.3,  (float)0.5,  (float)0.7,  (float)0.85, (float)0.9,  (float)0.95, (float)1.0,  (float)1.0,  (float)1.0,  (float)1.0 },  // 70 %power
	      {(float)0.2,  (float)0.4,  (float)0.55, (float)0.65, (float)0.8,  (float)0.85, (float)0.9,  (float)0.95, (float)1.0,  (float)1.0 },  // 80 %power
	      {(float)0.1,  (float)0.25, (float)0.4,  (float)0.6,  (float)0.75, (float)0.85, (float)0.9,  (float)0.95, (float)0.98, (float)1.0 }   // 90 %power
	  };	  

	  
  /*****************************************************************/
  /* Drive Course Heading Control PID Control Calibrations                */
  /*****************************************************************/	  
  
  /** KDRV_Deg_PosErrDB: Drive System Course Heading Position Error DeadBand (degree). */
  public static final float KDRV_Deg_PosErrDB = (float) 1.5;
  
  /** KDRV_K_PropGx: Drive System Course Heading PID Controls Proportional Gain. */
  public static final float KDRV_K_PropGx = (float) 1.0;
  
  /** KDRV_K_IntglGx: Drive System Course Heading PID Controls Integral Gain. */
  public static final float KDRV_K_IntglGx = (float) 0.001;
 
  /** KDRV_Pct_PropCorrMax: Drive Course Heading Rotate PID Controls Proportional
   * Correction Max Limit. (percent) */
  public static final float KDRV_Pct_PropCorrMax = (float) 50.0;
  
  /** KDRV_Pct_PropCorrMax: Drive Course Heading Rotate PID Controls Intgral
   * Correction Max Limit. (percent) */
  public static final float KDRV_Pct_IntglCorrMax = (float) 50.0;

  
  
  /*****************************************************************/
  /* Drive Rotate PID Control Calibrations                         */
  /*****************************************************************/	    
  
  /** KROT_Deg_PosErrDB: Drive System Rotate Position Error DeadBand (degree). */
  public static final float KROT_Deg_PosErrDB = (float) 1.5;
  
  /** KROT_K_PropGx: Drive System Rotate PID Controls Proportional Gain. */
  public static final float KROT_K_PropGx = (float) 1.14;
  
  /** KROT_K_IntglGx: Drive System Rotate PID Controls Integral Gain. */
  public static final float KROT_K_IntglGx = (float) 0.005;
 
  /** KROT_Pct_PropCorrMax: Drive System Rotate PID Controls Proportional
   * Correction Max Limit. (percent) */
  public static final float KROT_Pct_PropCorrMax = (float) 90.0;
  
  /** KROT_Pct_PropCorrMax: Drive System Rotate PID Controls Intgral
   * Correction Max Limit. (percent) */
  public static final float KROT_Pct_IntglCorrMax = (float) 50.0;
  
  /** KROT_t_PstnTgtSyncMetThrsh: Amount of time that the Position Error must
   * be held within the Error DeadBand in order for the Drive System Rotate
   * PID Controls to consider the Position Target conditions met. (seconds) */
  public static final float KROT_t_PstnTgtSyncMetThrsh = (float) 0.50;
   
}
