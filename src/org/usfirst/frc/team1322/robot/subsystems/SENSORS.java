package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;
import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_SensorUpdate;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SENSORS extends Subsystem {
	
	//Power Dist. Panel
    private PowerDistributionPanel pdp = new PowerDistributionPanel();
    //Ultra Sonic Sen
    private AnalogInput rearUS = new AnalogInput(RobotMap.REAR_US);
    private AnalogInput leftUS = new AnalogInput(RobotMap.LEFT_US);
    private AnalogInput rightUS = new AnalogInput(RobotMap.RIGHT_US);
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private DigitalInput blockDetector = new DigitalInput(RobotMap.BLOCK_DETECTOR); //Block Detector Sensor

    
    
    public void init() {
    	AnalogInput.setGlobalSampleRate(9600);
    }
    
    public double getPdpCurrent(int id) {
		return pdp.getCurrent(id);
	}

	/** Method: getRearUSDistance - Calculates the Distance Sensed by the
	 *  Rear Side UltraSonic Sensor.
     *  @return: Sensed Distance by Left UltraSonic Sensor */
    public double getRearUSDistance() {
    	float DistRear;  // inch
/*    	DistRear = (float)rearUS.getAverageVoltage(); */
    	DistRear = calcDistUSRear((float)rearUS.getAverageVoltage());
    	return (double)DistRear;
    }

	/** Method: getLeftUSDistance - Calculates the Distance Sensed by the
	 *  Left Side UltraSonic Sensor.    
     *  @return: Sensed Distance by Left UltraSonic Sensor */
    public double getLeftUSDistance() {
    	float DistLeft;  // inch
/*    	DistLeft = (float)leftUS.getAverageVoltage()(); */
    	DistLeft = calcDistUSLeft((float)leftUS.getAverageVoltage());
    	return (double)DistLeft;
    }

	/** Method: getRightUSDistance - Calculates the Distance Sensed by the
	 *  Right Side UltraSonic Sensor.
     *  @return: Sensed Distance by Right UltraSonic Sensor */
    public double getRightUSDistance() {
    	float DistRight;  // inch
/*    	DistRight = (float)rightUS.getAverageVoltage(); */
    	DistRight = calcDistUSRight((float)rightUS.getAverageVoltage());
    	return (double)DistRight;
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


  /*****************************************************************/
  /* Wheel Speed / Motor Encoder Conversion Calculations           */
  /*****************************************************************/
	

	
	  /** Method: calcRPMEncoder - Calculates the Rotational
	    *  speed of an Encoder Shaft from the number of Encoder
	    *  Counts in the sample and the Sample Time.  Result
	    *  is in rpm.
	    *  @param1: Sample Counts (counts)
	    *  @param2: Sensor Period (seconds)
	    *  @return: Encoder Shaft Speed (rpm) */	
	  private float calcRPMEncoder(int SmplCnts,
	  	                           float SmplPeriod)
	    {
	    float SmplCntsPerSec;
	    float SpdEncoder;

	    if (SmplPeriod > (float)0.0)
	      {
	      SmplCntsPerSec = (float)(((float)SmplCnts)/SmplPeriod);
	      SpdEncoder =  (float)(SmplCntsPerSec/K_SensorCal.KWSS_Cnt_PulsePerRevEncoder)*((float)60);
	      }
	    else if (SmplCnts > (int) 0)
	      {
	      SpdEncoder = (float)10000.0;	
	      }
	    else
	      {
	      SpdEncoder = (float)0.0;
	      }
	      
	    return SpdEncoder;
		}

	  
	  /** Method: calcRPMWheel - Calculates the Rotational
	    *  speed of a Wheel Shaft from the Encoder Shaft
	    *  Rotational Speed.  Result is in rpm.
	    *  @param: Encoder Shaft Speed (rpm)
	    *  @return: Wheel Shaft Speed  (rpm) */	
	  private float calcRPMWheel(float SpdEncoder)
	    {
	    return ((float)(K_SensorCal.KWSS_r_EncoderToWheel * SpdEncoder));
		}

	  
	  /** Method: calcLinealSpd - Calculates the Linear 
	    *  Speed of the Robot from the Average Wheel Speed
	    *  when moving directly forward or rearward.
	    *  @param: Average Wheel Shaft Speed (rpm)
	    *  @return: Robot Linear Speed (fps) */	
	  private float calcLinealSpd(float SpdWhl)
	    {
	    return ((float)((K_SensorCal.KWSS_l_DistPerRevWheel * SpdWhl)/(float)60));
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

