package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AC_ResetEncoders extends TimedCommand {

	static final double[] resetValue = {0,0,0,0};
    public AC_ResetEncoders(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.kDRIVE);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	Robot.kDRIVE.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }
    
    @Override
    protected boolean isFinished() {
    	// TODO Auto-generated method stub
    	return super.isFinished() || (Robot.kDRIVE.getEncoders() == resetValue);
    }

    // Called once after timeout
    protected void end() {
    	Robot.kAUTON.setMasterTaskCmplt(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
