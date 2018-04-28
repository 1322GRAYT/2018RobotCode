package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive Forward/Sideways using the gyro
 */
public class AC_DriveByGyroTime extends Command {

	private double strafeSpeed;
	private double forwardSpeed;
	private double startGyroPos;
	private double time;
	private final double correctionSpeed = (double)K_CmndCal.KCMD_r_DrvOpenLpCrtnPwr;
	private final double leeway = (double)K_CmndCal.KCMD_Deg_DrvOpenLpCrtnDB;
	private Timer timer = new Timer();

	
	
    public AC_DriveByGyroTime( double strafeSpeed, double forwardSpeed, double time) {
        requires(Robot.kDRIVE);
        this.forwardSpeed = forwardSpeed;
        this.strafeSpeed = strafeSpeed;
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    	timer.reset();
    	timer.start();
    }

    //Test
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Keep Robot Facing Straight at all costs
    	double gAngle = Robot.kSENSORS.getGyroAngle();
    	if((gAngle - leeway) > startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, correctionSpeed);
    	}else if((gAngle + leeway) < startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, -correctionSpeed);
    	}else {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    	}
    		    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.kAUTON.setMasterTaskCmplt(true);
    	Robot.kDRIVE.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
