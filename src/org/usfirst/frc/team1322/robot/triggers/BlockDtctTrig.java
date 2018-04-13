package org.usfirst.frc.team1322.robot.triggers;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.calibrations.K_ClawCal;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
  * Trigger Class: BlockDtctTrig
  * Trigger: Detects when the Block is within the Claw
  * While the Claw Wheels are Running In to be able to
  * trigger the Close Claw Command .
  * */
public class BlockDtctTrig extends Trigger {
    private boolean TrigCondMet;
    private Timer blockTimer = new Timer();
	
    public boolean get() {
    	
    	if ((dzify(OI.AuxStick.getY(Hand.kLeft)) < 0) &&
    		(Robot.kSENSORS.getBlock())) {
    		// Y Trigger Down Greater than DeadZone
    		blockTimer.start();
    		TrigCondMet = true;
	    		/*
	    		if (blockTimer.get() >= K_ClawCal.KCLW_t_TeleBlckDtctCloseTrig) {
	        		TrigCondMet = true;
	    		} else {
	        		TrigCondMet = false;    			
	    		}
	    		*/  			
    	} else { 
    		TrigCondMet = false;
			blockTimer.stop();
			blockTimer.reset();
    	}
    	
	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
	        updateSmartDashData();       	
	    
    	return (TrigCondMet);
    }

    
	private double dzify(double value) {
		double deadzone = RobotMap.deadzone;
		if(value > deadzone || value < -deadzone) {
			return -value;
		}
		return 0.0;
	}    
    
 
	
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {
    	SmartDashboard.putNumber("Block Detect Timer : ", blockTimer.get());

    	
    	System.out.println("Block Detect Timer : " + blockTimer.get());
    }
		
}
