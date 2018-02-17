package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.RobotMap;
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
    
    public double getRearUSDistance() {
    	return rearUS.getValue();
    }
    
    public double getLeftUSDistance() {
    	return leftUS.getValue();
    }
    
    public double getRightUSDistance() {
    	return rightUS.getValue();
    }
    
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
}

