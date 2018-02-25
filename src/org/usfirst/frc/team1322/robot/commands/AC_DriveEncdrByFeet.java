package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  cvrtDistToCnts
 */
public class AC_DriveEncdrByFeet extends Command {
		
	// Autonomous Pattern Vars
	private double EncdrInitRefCnt;
	private double EncdrDsrdTrvlCnts;	
	private double EncdrTgtDclCnts;	
	private double EncdrTgtRefCnt;
	
	
	boolean DrctnIsFwd;
    float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
	
    public AC_DriveEncdrByFeet(boolean DrctnIsFwd,
    		               float   DsrdDistFeet,
    		               float   DsrdPriPwr,
    		               float   DsrdDclFeet,
    		               float   DsrdDclPwr) {
        requires(Robot.kSENSORS);
        requires(Robot.kDRIVE);        
        this.DrctnIsFwd = DrctnIsFwd;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	EncdrTgtDclCnts = EncdrTgtRefCnt - DsrdDclFeet;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double CurrEncdrCnt;
    	double PriPwr;
    	double DclPwr;

    	CurrEncdrCnt = Robot.kSENSORS.getRefEncoderCnt();    	
    	
    	if (DrctnIsFwd == false) {
    		PriPwr = (double)-(DsrdPriPwr);
    	    DclPwr = (double)-(DsrdDclPwr);
    	}
    	else {
    		PriPwr = (double)DsrdPriPwr;
    	    DclPwr = (double)DsrdDclPwr;    		
    	}
    	
    	
    	if (CurrEncdrCnt < EncdrTgtDclCnts)
    	  {
          Robot.kDRIVE.mechDrive(0.0, PriPwr, 0.0);
    	  }
    	else // (CurrEncdrCnt >= EncdrTgtDclCnts)
    	  {
    	  Robot.kDRIVE.mechDrive(0.0, DclPwr, 0.0);
    	  }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double CurrEncdrCnt;
    	
    	CurrEncdrCnt = Robot.kSENSORS.getRefEncoderCnt();

    	return (CurrEncdrCnt >= EncdrTgtRefCnt);
    }

    // Called once after isFinished returns true
    protected void end() {
  	  Robot.kDRIVE.mechDrive(0.0, 0.0, 0.0);    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
