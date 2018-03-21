package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TM_VariableClaw extends Command {

    public TM_VariableClaw() {
        requires(Robot.kSHOOTER);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if((dzify(Robot.m_oi.AuxStick.getY(Hand.kLeft)) != 0)) {
    		Robot.kSHOOTER.clawSpeedInOut(dzify(Robot.m_oi.AuxStick.getY(Hand.kLeft)));
    	}else {
    		Robot.kSHOOTER.clawSpeedRotate(dzify(Robot.m_oi.AuxStick.getX(Hand.kLeft)));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
	private double dzify(double value) {
		double deadzone = RobotMap.deadzone;
		if(value > deadzone || value < -deadzone) {
			return -value;
		}
		return 0.0;
	}
}
