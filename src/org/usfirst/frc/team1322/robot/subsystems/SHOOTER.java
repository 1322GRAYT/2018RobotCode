package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TM_VariableClaw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Claw Subsystem
 */
public class SHOOTER extends Subsystem {

	//Create New Instances of required things
	private TalonSRX clawL = new TalonSRX(RobotMap.CLAW_L);				//Claw Motor Left
	private TalonSRX clawR = new TalonSRX(RobotMap.CLAW_R);				//Claw Motor Right

    public SHOOTER() {
    	clawL.setNeutralMode(NeutralMode.Brake);
    	clawR.setNeutralMode(NeutralMode.Brake); 	
    }
	
	
	/**
	 * Set the in/out speed of the claw motors
	 * @param speed Speed at which the claw motors should run in/out
	 */
	public void clawSpeedInOut(double speed) {
		clawL.set(ControlMode.PercentOutput, speed);
		clawR.set(ControlMode.PercentOutput, speed);
	}

	
	/**
	 * Set the rotation speed of the claw motors
	 * @param speed Speed at which the claw motors should spin the block
	 */
	public void clawSpeedRotate(double speed) {
		clawL.set(ControlMode.PercentOutput, -speed);
		clawR.set(ControlMode.PercentOutput, speed);
	}
	

	
	
	/**
	 * Sets the default command so that it is ALWAYS running throughout teleop
	 */
    public void initDefaultCommand() {
        setDefaultCommand(new TM_VariableClaw());
    }
}

