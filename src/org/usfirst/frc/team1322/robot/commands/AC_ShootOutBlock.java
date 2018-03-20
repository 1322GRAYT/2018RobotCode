package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AC_ShootOutBlock extends TimedCommand {

    public AC_ShootOutBlock(double timeout) {
        super(timeout);
        
        requires(Robot.kSHOOTER);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kSHOOTER.clawSpeedInOut(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Called once after timeout
    protected void end() {
    	Robot.kSHOOTER.clawSpeedInOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
