package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;
import org.usfirst.frc.team1322.robot.subsystems.DRIVE;
import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_SensorUpdate;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class: SENSORS - Provides Get access to Sensors Inputs, determines 
 * sensor calculations from raw units to engineering units, and
 * determines conversions from one type of unit of one to another
 * (e.g. RPM to Linear Speed).
 */
public class SENSORS extends Subsystem {

	public enum TeWhlEncdrs
	  {
	  CeRtRear, CeRtFront, CeLtFront, CeLtRear, CiNumOfEncdrs;
	  }	
	
	public enum TeDrvMtrs
	  {
	  CeMtrRtRear1, CeMtrRtRear2, CeMtrRtFront1, CeMtrRtFront2, CeMtrLtFront1, CeMtrLtFront2,
	  CeMtrLtRear1, CeMtrLtRear2, CiNumOfDrvMtrs;
	  }	
	
	
	//Power Dist. Panel
    private PowerDistributionPanel pdp = new PowerDistributionPanel();
    //Ultra Sonic Sen
    private AnalogInput rearUS = new AnalogInput(RobotMap.REAR_US);
    private AnalogInput leftUS = new AnalogInput(RobotMap.LEFT_US);
    private AnalogInput rightUS = new AnalogInput(RobotMap.RIGHT_US);
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private DigitalInput blockDetector = new DigitalInput(RobotMap.BLOCK_DETECTOR); //Block Detector Sensor

	// Drive System Encoders/Wheels
	// Idx [0] - RR: Right Rear	
	// Idx [1] - RF: Right Front	
	// Idx [2] - LF: Left Front	
	// Idx [3] - LR: Left Rear
	private double EncdrVelRaw[] = new double[4];
	private double EncdrCnt[] = new double[4];
	private double EncdrRPM[] = new double[4];
	private double WhlRPM[] = new double[4];
	private double WhlVel[] = new double[4];

	private float  USDistRear;
	private float  USDistLeft;
	private float  USDistRight;

	
    public void setGlobalBaud() {
      AnalogInput.setGlobalSampleRate(9600);
      
    }
    
    public void calibrateGyro() {
    	gyro.calibrate();
    }
    
    public double getPdpCurrent(int id) {
  	  return pdp.getCurrent(id);
  	}
	
	
	/** Method: getEncodersVelRaw - Interface to Get the array of
	  * Raw Encoder Speeds in units of Counts/100ms.
     *  @return: Array of Drive System Raw Encoder Speeds (Counts/100ms) */	
	public double[] getEncodersVelRaw(){ 
      double[] encoders = EncdrVelRaw;
      return encoders;
	}

	/** Method: getEncodersCnt - Interface to Get the array of
	  * Encoder Counts in units of RPM.
     *  @return: Array of Drive System Encoder Counts (Counts) */	
	public double[] getEncodersCnt(){ 
      double[] encoders = EncdrCnt;
      return encoders;
    }

	/** Method: getEncoderRefCnt - Interface to Get the current
	 * Average Encoder Count of the Reference A Motor/Encoder/Wheel Assembly
	 * and the Reference B Motor/Encoder/Wheel Assembly.
    *  @return: Count of the Reference Encoder (Counts) */	
	public double getRefEncoderCnt(){
	    double RefACnts, RefBCnts;
	    
	    RefACnts = EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlA_Slct];
	    RefBCnts = EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlB_Slct];
	    
     return ((RefACnts + RefBCnts)/2);
   }
		
	/** Method: getEncodersRPM - Interface to Get the array of
	  * Encoder Speeds in units of RPM.
      *  @return: Array of Drive System Encoder Speeds (RPM) */	
	public double[] getEncodersRPM(){ 
      double[] encoders = EncdrRPM;
      return encoders;
	}

	/** Method: getWhlsRPM - Interface to Get the array of
	  * Wheel Speeds in units of RPM.
      *  @return: Array of Drive System Wheel Speeds (RPM) */
    public double[] getWhlsRPM(){ 
      double[] encoders = WhlRPM;
      return encoders;
	}
 
	/** Method: getWhlsVel - Interface to Get the array of
	  * Wheel Linear Velocity in units of inches/sec.
     *  @return: Array of Drive System Linear Wheel Speeds (inches/sec) */
    public double[] getWhlsVel(){ 
      double[] encoders = WhlVel;
      return encoders;
	}   

    /** Method: getCntsToDrv - Calculates the number of counts
    *  to Drive given the desired feet to drive straight.
    *  @param: Desired Distance (feet)
    *  @return: Encoder Counts (cnts) */
    public double getCntsToDrv(float Feet)
      {
      return (cvrtDistToCnts(Feet));
      }
    
	/** Method: getRearUSDistance - Calculates the Distance Sensed by the
	 *  Rear Side UltraSonic Sensor.
     *  @return: Sensed Distance by Left UltraSonic Sensor */
    public double getRearUSDistance() {
      return (double)USDistRear;
    }

	/** Method: getLeftUSDistance - Calculates the Distance Sensed by the
	 *  Left Side UltraSonic Sensor.    
     *  @return: Sensed Distance by Left UltraSonic Sensor */
    public double getLeftUSDistance() {
      return (double)USDistLeft;
    }

	/** Method: getRightUSDistance - Calculates the Distance Sensed by the
	 *  Right Side UltraSonic Sensor.
     *  @return: Sensed Distance by Right UltraSonic Sensor */
    public double getRightUSDistance() {
      return (double)USDistRight;
    }
    
	/** Method: getUSDistanceFromId - Calculates the Distance Sensed by the
	 *  UltraSonic Sensor specified by the Argument Supplied.
	 *  @param: ID of UltraSonic Sensor that Distance Reading is Desired
	 *  @return: Sensed Distance from Specified UltraSonic Sensor */
    public double getUSDistanceFromId(double id) {
    	if(id == RobotMap.REAR_US) {
    		return getRearUSDistance();
    	}else if(id == RobotMap.LEFT_US) {
    		return getLeftUSDistance();
    	}else if(id == RobotMap.RIGHT_US) {
    		return getRightUSDistance();
    	}
    	return 0;
    }
    
    public double getGyroAngle() {
    	return gyro.getAngle();
    }
    
    public void resetGyro() {
    	gyro.reset();
    }
    
	/**
	 * Gets the value of the block sensor
	 * @return Have Block
	 */
	public boolean getBlock() {
		return blockDetector.get();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new BM_SensorUpdate());
    }


  /** Method: updateSensorData - Updates the Derived Input Sensor Data.  */
  public void updateSensorData() {
	int idx;
    double RPM_Raw;
    
    /* Drive Speed Inputs */    
	EncdrVelRaw = Robot.kDRIVE.getEncodersVelocity();  // tics/100msec
	EncdrCnt = Robot.kDRIVE.getEncoders();             // tic count
    for (idx=0; idx<4; idx++)
      {
      RPM_Raw = (EncdrVelRaw[idx]/K_SensorCal.KWSS_Cnt_PulsePerRevEncoder)*(600);          // rpm
      EncdrRPM[idx] = RPM_Raw;
      WhlRPM[idx] = EncdrRPM[idx]/K_SensorCal.KWSS_r_EncoderToWheel;                       // rpm
      WhlVel[idx] = (WhlRPM[idx]*K_SensorCal.KWSS_l_DistPerRevWheel)/60;                   // inches/sec
      }
    
    /* UltraSonic Position Inputs */
	USDistRear = calcDistUSRear((float)rearUS.getAverageVoltage());    // inches
	USDistLeft = calcDistUSLeft((float)rearUS.getAverageVoltage());    // inches
	USDistRight = calcDistUSRight((float)rearUS.getAverageVoltage());  // inches

    }
	

	  
  /** Method: cvrtAngToLinSpd - Calculates the Linear 
    *  Speed of the Robot from the Angular Wheel Speed
    *  when moving directly forward or rearward.
    *  @param: Wheel Angular Speed (rpm)
    *  @return: Robot Linear Speed (fps) */	
  private float cvrtAngToLinSpd(float SpdWhl)
    {
    return ((float)((K_SensorCal.KWSS_l_DistPerRevWheel * SpdWhl)/(float)720));
	}
	  
  /** Method: cvrtDistToCnts - Calculates the nominal number 
   *  of Drive encoder counts that would be registered if
   *  the the Drive Wheel traveled forward/backward the
   *  desired distance given (cnts).
   *  @param: Desired Distance (feet)
   *  @return: Encoder Counts (cnts) */
 private double cvrtDistToCnts(float DistFeet)
   {
   double WhlRPM;
   double EncdrRPM;
   double EncdrCnts;
   
   WhlRPM = (double)((DistFeet * 12) / K_SensorCal.KWSS_l_DistPerRevWheel);	 
   EncdrRPM = WhlRPM * (double)K_SensorCal.KWSS_r_EncoderToWheel;	 
   EncdrCnts = EncdrRPM * (double)K_SensorCal.KWSS_Cnt_PulsePerRevEncoder;
   
   return EncdrCnts;
   }

    
  
	
  /*****************************************************************/
  /* UltraSonic Distance Measurement Conversion Calculations       */
  /*****************************************************************/
	
  /** Method: calcDistUSRear - Calculates the Distance Sensed by the
	*  Rear Side UltraSonic Sensor.
    *  @param: Sensor Sensed Voltage
    *  @return: Sensor Sensed Distance */
  private float calcDistUSRear(float VoltSnsd)
	{
    float V2DConvFx; // Voltage to Distance Conversion Factor (inch/volt)
    float DistOfst;  // Distance Offset from Edge of Robot, inboard is positive (inch)
    float DistCalcd; // Calculated Distance (inches)
      
    V2DConvFx = K_SensorCal.KUSS_Fx_VoltToDistRear;   // inch/volt
    DistOfst = K_SensorCal.KUSS_l_DistOfstRear;       // inch

    DistCalcd = calcDistUSSensor(VoltSnsd, V2DConvFx, DistOfst);
      
    return (DistCalcd);
    }


  /** Method: calcDistUSLeft - Calculates the Distance Sensed by the
	*  Left Side UltraSonic Sensor.
    *  @param: Sensor Sensed Voltage
    *  @return: Sensor Sensed Distance */
  private float calcDistUSLeft(float VoltSnsd)
    {
    float V2DConvFx; // Voltage to Distance Conversion Factor (inch/volt)
    float DistOfst;  // Distance Offset from Edge of Robot, inboard is positive (inch)
    float DistCalcd; // Calculated Distance (inches)
      
    V2DConvFx = K_SensorCal.KUSS_Fx_VoltToDistLeft;   // inch/volt
    DistOfst = K_SensorCal.KUSS_l_DistOfstLeft;       // inch

    DistCalcd = calcDistUSSensor(VoltSnsd, V2DConvFx, DistOfst);
      
    return (DistCalcd);
    }

	
  /** Method: calcDistUSRight - Calculates the Distance Sensed by the
    *  Right Side UltraSonic Sensor.
    *  @param: Sensor Sensed Voltage
    *  @return: Sensor Sensed Distance */
  private float calcDistUSRight(float VoltSnsd)
  	{
    float V2DConvFx; // Voltage to Distance Conversion Factor (inch/volt)
    float DistOfst;  // Distance Offset from Edge of Robot, inboard is positive (inch)
    float DistCalcd; // Calculated Distance (inches)
      
    V2DConvFx = K_SensorCal.KUSS_Fx_VoltToDistRight;  // inch/volt
    DistOfst = K_SensorCal.KUSS_l_DistOfstRight;      // inch

    DistCalcd = calcDistUSSensor(VoltSnsd, V2DConvFx, DistOfst);
      
    return (DistCalcd);
    }

	
  /** Method: calcDistUSSensor - Calculates the Distance Sensed
    * by UltraSonic Sensors from the Voltage to Distance Conversion
    * Factor and the Distance Offset that the Sensor is in-board
    * from the edge of the robot.
    *  @param1: Sensor Sensed Voltage
    *  @param2: Sensor Voltage to Distance Conversion Factor
    *  @param3: Sensor Distance Offset from Edge of Robot 
    *  @return: Sensor Sensed Distance */	
  private float calcDistUSSensor(float SnsdVolt,
  	                             float V2DConvFx,
                                 float DistOfst)
    {
    float SnsdDist; // Sensed Distance     
      
    SnsdDist = (float)((SnsdVolt * V2DConvFx) - DistOfst);
    if (SnsdDist < (float)0.0) SnsdDist = (float)0.0; 
      
    return SnsdDist;
	}

  
}

