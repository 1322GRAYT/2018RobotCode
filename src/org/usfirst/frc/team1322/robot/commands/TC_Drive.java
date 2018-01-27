package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_Drive extends Command {

    public TC_Drive() {
        requires(Robot.kDRIVE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kDRIVE.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kDRIVE.mechDrive(OI.DriverStick.getY(Hand.kLeft), OI.DriverStick.getX(Hand.kLeft), OI.DriverStick.getX(Hand.kRight));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
