package org.usfirst.frc.team1322.robot.triggers;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
  * Trigger Class: LiftHighTrig
  * Trigger: Detects when the Lift is at the position detected
  * by the HIGH digital binary magnetic position switch.
  * */
public class LiftHighTrig extends Trigger {
    private boolean TrigCondMet;
	
    public boolean get() {
    	
    	if (Robot.kLIFT.getHighSen() == false) {
    		// Magnetic Position Sensors are Normally Closed
	        TrigCondMet = true;
    	} else { 
    		TrigCondMet = false;
    	}
    	
    	return (TrigCondMet);
    }    
    
}
