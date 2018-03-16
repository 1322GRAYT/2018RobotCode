package org.usfirst.frc.team1322.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Class: AUTON - Contains Variables and any Common
 * Function for the Autonomous Class that are used
 * across Autonomous Commands or Autonomous Command
 * Groups.
 */
public class AUTON extends Subsystem {
	
	// Variable Declarations
	private static String FieldData;                 // String of Field Data
	private static boolean SwitchDataCaptured;       // Has the Switch Data been Captured for this Autonomous Mode?
	private static boolean ScaleDataCaptured;        // Has the Scale  Data been Captured for this Autonomous Mode?
	private static boolean FieldDataTimedOut;        // Has the Field Data Timed Out befoe being Captured?
	private static boolean OurSwitchLeftSide;        // Is Our Side of Our Alliance Switch on the Left Side of the Field?
	private static boolean OurScaleLeftSide;         // Is Our Side of the Scale on the Left Side of the Field?
	private static DriverStation.Alliance Alliance;  // Our Alliance Color	
    

	public AUTON() {
		
	}
	
	
    /**********************************************/
    /* Public Interface Definitions        */
    /**********************************************/
    
    /** Method: getFieldData - Interface that returns Field Data String  */ 
    public String getFieldData() {
    	return FieldData;
    }

    /** Method: getSwitchDataCaptured - Interface to return the indication
     * that Switch Data had been successfully captured.  */ 
    public boolean getSwitchDataCaptured() {
    	return SwitchDataCaptured;
    }

    /** Method: getScaleDataCaptured - Interface to return the indication
     * that Field Data had been successfully captured.  */ 
    public boolean getScaleDataCaptured() {
    	return ScaleDataCaptured;
    }
    
    /** Method: getFieldDataTimedOut - Interface to return the indication
     * that the Capture Field Data function timed out before successfully
     * capturing the Field Data.  */ 
    public boolean getFieldDataTimedOut() {
    	return FieldDataTimedOut;
    }    
    
    /** Method: getOurSwitchLeftSide - Interface to return the indication
     * if Our Allicance Switch is on the Left Side.  */ 
    public boolean getOurSwitchLeftSide() {
    	return OurSwitchLeftSide;
    }

    /** Method: getOurSwitchLeftSide - Interface to return the indication
     * if Our Allicance Scale is on the Left Side.  */ 
    public boolean getOurScaleLeftSide() {
    	return OurScaleLeftSide;
    }
    
    /** Method: getAlliance - Interface to return Our Alliance Color  */ 
    public DriverStation.Alliance getAlliance() {
    	return Alliance;
    }


    /** Method: setSwitchDataCaptured - Interface to set the indication
     * that Switch Data had been successfully captured.  */ 
    public void setSwitchDataCaptured(boolean DataCaptured) {
    	SwitchDataCaptured = DataCaptured;
    }

    /** Method: setScaleDataCaptured - Interface to set the indication
     * that Scale Data had been successfully captured.  */ 
    public void setScaleDataCaptured(boolean DataCaptured) {
    	ScaleDataCaptured = DataCaptured;
    }
    
    /** Method: setFieldDataTimedOut - Interface to set the indication
     * that the Capture Field Data function timed out before successfully
     * capturing the Field Data.  */ 
    public void setFieldDataTimedOut(boolean CaptureTimedOut) {
    	FieldDataTimedOut = CaptureTimedOut;
    }    
    
    /** Method: setOurSwitchLeftSide - Interface to set the indication
     * if Our Allicance Switch is on the Left Side.  */ 
    public void setOurSwitchLeftSide(boolean SwitchLeftSide) {
    	OurSwitchLeftSide = SwitchLeftSide;
    }

    /** Method: setOurSwitchLeftSide - Interface to set the indication
     * if Our Allicance Scale is on the Left Side.  */ 
    public void setOurScaleLeftSide(boolean ScaleLeftSide) {
    	OurScaleLeftSide = ScaleLeftSide;
    }

    /** Method: resetFieldDataCapture - Resets the Field Management
     * System Capture Field Data at the initiation of Autonomous.  */ 
    public void resetFieldDataCapture() {
    	SwitchDataCaptured = false;
    	ScaleDataCaptured = false;
    	FieldDataTimedOut = false;
    	OurSwitchLeftSide = true;
    	OurScaleLeftSide = true;
    	}
    
   
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/
   
     
     
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

