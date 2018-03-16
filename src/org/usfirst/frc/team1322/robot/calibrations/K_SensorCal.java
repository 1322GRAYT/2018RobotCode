package org.usfirst.frc.team1322.robot.calibrations;


/**
 * Class: K_SensorCal - Contains the Sensor Calibrations that Convert
 * the Raw Sensor Inputs (Voltage, Current, Counts, Counts/Sec) into
 * Engineering Units.
 */
public class K_SensorCal {


  /******************************************************/
  /* Mecanum WheelSpeed Sensors / Motor Encoder Sensors */
  /******************************************************/
	
  /* KWSS_e_RefAutonDrvWhlA_Slct: Selects which Drive/Encoder/Wheel Assembly
   * is the Reference A Set to use for Autonomous Mode Drive By Distance.
   * Reference Set A and B are averaged together. */
  public static final int KWSS_e_RefAutonDrvWhlA_Slct = (int) 1;   // 0 = LF: Left Front, 1 = RF: Right Front
                                                                   // 2 = LR: Left Rear,  3 = RR: Right Rear

  /* KWSS_e_RefAutonDrvWhlB_Slct: Selects which Drive/Encoder/Wheel Assembly
   * is the Reference B_ Set to use for Autonomous Mode Drive By Distance.
   * Reference Set A and B are averaged together. */
  public static final int KWSS_e_RefAutonDrvWhlB_Slct = (int) 3;   // 0 = LF: Left Front, 1 = RF: Right Front
                                                                   // 2 = LR: Left Rear,  3 = RR: Right Rear
  
  /* KWSS_l_DistPerRevWheel: Linear Distance Traveled Forward/Rearward
   * per one Wheel Revolution (inches). */
  public static final float KWSS_l_DistPerRevWheel = (float) 12.5664;  // inch

  /* KWSS_Cnt_PulsePerRevEncoder: Number of Shaft Encoder Pulses per one
   * rotation of the Shaft the Encoder is mounted on. */
  public static final int KWSS_Cnt_PulsePerRevEncoder = (int) 1000;    // clicks
  
  /* KWSS_r_EncoderToWheel: The ratio of the number of rotations of
   * the encoder shaft to the number of rotations of the wheel axle shaft. */  
  public static final float KWSS_r_EncoderToWheel = (float) 4.583;      // ratio
  
  /* KWSS_n_EncoderSpdMaxLim: The Unloaded Encoder Speed Recorded for the
   * slowest Wheel/Motor during testing which will be used as the Maximum
   * Encoder Speed Limit (Error Protection).  */  
  public static final float KWSS_n_EncoderSpdMaxLim = (float) 3000.0;  // rev/min


	
  /********************************/	
  /* UltraSonic Distance Sensors  */
  /********************************/	
  /* UltraSonic Distance Sensor - Rear */

  /* KUSS_Fx_VoltToDistRear: Voltage to Distance Conversion Factor (inch/volt)
   * for the Rear UltraSonic Sensor. */
  public static final float KUSS_Fx_VoltToDistRear = (float) 520.2; // inch/volt
  
  /* KUSS_l_DistOfstRear: Distance Offset (inch) that the Sensor is from the edge
   * of the robot (positive number is in-board) for the Rear UltraSonic Sensor. */
  public static final float KUSS_l_DistOfstRear = (float) 0.25; // inch

  /* UltraSonic Distance Sensor - Left */

  /* KUSS_Fx_VoltToDistLeft: Voltage to Distance Conversion Factor (inch/volt)
   * for the Left UltraSonic Sensor. */
  public static final float KUSS_Fx_VoltToDistLeft = (float) 102.4; // inch/volt

  /* KUSS_l_DistOfstLeft: Distance Offset (inch) that the Sensor is from the edge
   * of the robot (positive number is in-board) for the Left UltraSonic Sensor. */
  public static final float KUSS_l_DistOfstLeft = (float) 2.0; // inch
	
  /* UltraSonic Distance Sensor - Right */

  /* KUSS_Fx_VoltToDistRight: Voltage to Distance Conversion Factor (inch/volt)
   * for the Right UltraSonic Sensor. */
  public static final float KUSS_Fx_VoltToDistRight = (float) 102.4; // inch/volt

  /* KUSS_l_DistOfstRight: Distance Offset (inch) that the Sensor is from the edge
   * of the robot (positive number is in-board) for the Right UltraSonic Sensor. */
  public static final float KUSS_l_DistOfstRight = (float) 2.0; // inch

  /* KUSS_r_StrfToDrvRat: Ratio of Strafe to Drive Wheel Rotation to Travel the
   * same distance due to Mecanum Drive angle and Wheel Slip. */
  public static final float KUSS_r_StrfToDrvRat = (float) 1.0; // inch
  
}
