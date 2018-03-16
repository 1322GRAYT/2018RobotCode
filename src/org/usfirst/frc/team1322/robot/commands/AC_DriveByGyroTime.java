package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive Forward/Sideways using the gyro
 */
public class AC_DriveByGyroTime extends Command {

	boolean liftHldEnbl;
	private double strafeSpeed;
	private double forwardSpeed;
	private double startGyroPos;
	private double time;
	private final double correctionSpeed = RobotMap.autonDriveCorrectionSpeed;
	private final double leeway = RobotMap.autonDriveLeeway;
	private Timer timer = new Timer();

	
	
    public AC_DriveByGyroTime( double strafeSpeed, double forwardSpeed, double time, boolean liftHldEnbl) {
        requires(Robot.kDRIVE);
        requires(Robot.kLIFT);
        this.forwardSpeed = forwardSpeed;
        this.strafeSpeed = strafeSpeed;
        this.time = time;
        this.liftHldEnbl = liftHldEnbl;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    	timer.reset();
    	timer.start();
    }

    //Test
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double LftPwrCmnd;
    	
    	//Keep Robot Facing Straight at all costs
    	double gAngle = Robot.kSENSORS.getGyroAngle();
    	if((gAngle - leeway) > startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, correctionSpeed);
    	}else if((gAngle + leeway) < startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, -correctionSpeed);
    	}else {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    	}

    	
	    // Keep Lift in Elevated Position
	    if ((this.liftHldEnbl == true) &&
	    	(Robot.kLIFT.getMidSen() == true) &&
		    (Robot.kLIFT.getHighSen() == true)) {
	        // PwrCube not sensed by N/C Sensor
	    	LftPwrCmnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
	    } else {
	    	// PwrCube is sensed by N/C Sensor
	    	LftPwrCmnd = 0.0;	
	    }
	    
	    Robot.kLIFT.setSpeed(LftPwrCmnd);    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
  	    Robot.kLIFT.setSpeed(0.0); 
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
