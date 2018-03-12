package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AC_ShootInBlock extends TimedCommand {

    public AC_ShootInBlock(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.kCLAW);
    }

 // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kCLAW.clawSpeedInOut(-1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Called once after timeout
    protected void end() {
    	Robot.kCLAW.clawSpeedInOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
