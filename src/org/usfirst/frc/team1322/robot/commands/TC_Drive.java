package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_Drive extends Command {
	double StrfPwrCmndRaw;
	double StrfPwrCmndLim;
	double DrvPwrCmndRaw;
	double DrvPwrCmndLim;
	double RotPwrCmndRaw;
	double RotPwrCmndLim;

    public TC_Drive() {
        requires(Robot.kDRIVE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kDRIVE.enable();
    	Robot.kDRIVE.resetEncoders();
    	StrfPwrCmndLim = 0.0;
    	DrvPwrCmndLim = 0.0;;
    	RotPwrCmndLim = 0.0;;    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Create a new mecanum drive instance
    	
    	StrfPwrCmndRaw = OI.DriverStick.getX(Hand.kLeft);
    	StrfPwrCmndLim = USERLIB.RateLimOnInc(StrfPwrCmndRaw, StrfPwrCmndLim, K_DriveCal.KDRV_r_StrfPwrDeltIncLimMax);
    	
    	DrvPwrCmndRaw = -OI.DriverStick.getY(Hand.kLeft);
    	DrvPwrCmndLim = USERLIB.RateLimOnInc(DrvPwrCmndRaw, DrvPwrCmndLim, K_DriveCal.KDRV_r_DrvPwrDeltIncLimMax);
    	
    	RotPwrCmndRaw = OI.DriverStick.getX(Hand.kRight);
    	RotPwrCmndLim = USERLIB.RateLimOnInc(RotPwrCmndRaw, RotPwrCmndLim, K_DriveCal.KDRV_r_RotPwrDeltIncLimMax);

//    	Robot.kDRIVE.mechDrive(StrfPwrCmndRaw, DrvPwrCmndRaw, RotPwrCmndRaw);
    	Robot.kDRIVE.mechDrive(StrfPwrCmndLim, DrvPwrCmndLim, RotPwrCmndLim);
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
    	end();
    }
}
