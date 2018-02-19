package org.usfirst.frc.team1322.robot;


/**
 * Class: K_SensorCal - Contains the Sensor Calibrations that Convert
 * the Raw Sensor Inputs (Voltage, Current, Counts, Counts/Sec) into
 * Engineering Units.
 */
public class K_SensorCal {
  
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
  public static final float KUSS_l_DistOfstLeft = (float) 5.375; // inch
	
  /* UltraSonic Distance Sensor - Right */

  /* KUSS_Fx_VoltToDistRight: Voltage to Distance Conversion Factor (inch/volt)
   * for the Right UltraSonic Sensor. */
  public static final float KUSS_Fx_VoltToDistRight = (float) 102.4; // inch/volt

  /* KUSS_l_DistOfstRight: Distance Offset (inch) that the Sensor is from the edge
   * of the robot (positive number is in-board) for the Right UltraSonic Sensor. */
  public static final float KUSS_l_DistOfstRight = (float) 5.375; // inch

  
}
