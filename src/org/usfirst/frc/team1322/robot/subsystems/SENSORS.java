package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SENSORS extends Subsystem {
	
    private PowerDistributionPanel pdp = new PowerDistributionPanel();
    private AnalogInput rearUS = new AnalogInput(RobotMap.REAR_US);
    private AnalogInput leftUS = new AnalogInput(RobotMap.LEFT_US);
    
    public void init() {
    	AnalogInput.setGlobalSampleRate(9600);
    }

    public double getPdpCurrent(int id) {
		return pdp.getCurrent(id);
	}
    
    public double getRearUSDistance() {
    	return rearUS.getAverageValue();
    }
    
    public double getLeftUSDistance() {
    	return leftUS.getAverageValue();
    }



	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

