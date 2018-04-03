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
	private static DriverStation.Alliance AllianceColor;  // Our Alliance Color	
	private static String  FieldData;                     // String of Field Data
	private static boolean SwitchDataCaptured;            // Has the Switch Data been Captured for this Autonomous Mode?
	private static boolean ScaleDataCaptured;             // Has the Scale  Data been Captured for this Autonomous Mode?
	private static boolean FieldDataTimedOut;             // Has the Field Data Timed Out befoe being Captured?
	private static boolean OurSwitchLeftSide;             // Is Our Side of Our Alliance Switch on the Left Side of the Field?
	private static boolean OurScaleLeftSide;              // Is Our Side of the Scale on the Left Side of the Field?
	private static boolean MasterTaskCmplt;              // Trigger to Slave Parallel Tasks that Primary Task is Complete.
    

	public AUTON() {
		
	}
	

    /**********************************************/
    /* Public Set Interface Definitions        */
    /**********************************************/
	
    /** Method: updateAllianceColor - Interface to refresh Alliance Color Data
      *  from the Field Management System.  */ 
    public void updateAllianceColor() {
    	AllianceColor = captureAllianceColor();
    }
    
    /** Method: updateFieldData - Interface to refresh Field Data from the
      * the Field Management System.  */ 
    public void updateFieldData() {
    	FieldData = captureFieldData();
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
	
    /** Method: setMasterTaskCmplt - Interface to set the indication
      * of whether of not a Primary/Master task in a set of parallel tasks
      * has completed in order to trigger the termination of a Secondary/
      * Slave task. */ 
    public void setMasterTaskCmplt(boolean TaskIsCmplt) {
    	MasterTaskCmplt = TaskIsCmplt;
    }

    
    /**********************************************/
    /* Public Get Interface Definitions        */
    /**********************************************/
	
    /** Method: getAlliance - Interface to return Our Alliance Color  */ 
    public DriverStation.Alliance getAlliance() {
    	return AllianceColor;
    }
	
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
        
    /** Method: getMasterTaskCmplt - Interface to get the indication
      * of whether of not a Primary/Master task in a set of parallel tasks
      * has completed in order to trigger the termination of a Secondary/
      * Slave task. */ 
    public boolean getMasterTaskCmplt() {
    	return MasterTaskCmplt;
    }
    
    
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/
     
    /** Method: captureAllianceColor - Capture Alliance Color
      * from the Field Management System.  */ 
    private static DriverStation.Alliance captureAllianceColor() {
    	DriverStation.Alliance allianceColor;
    	
    	allianceColor = DriverStation.getInstance().getAlliance();
    	
    	return allianceColor;
    }

    /** Method: captureFieldData - Capture Field Data from
      * from the Field Management System.  */ 
    private static String captureFieldData() {
    	String fieldData; 

    	fieldData = DriverStation.getInstance().getGameSpecificMessage();

    	return fieldData;
    }    
    
	/** Method: dtrmnOurSwitchPstn() -  Determine Our Alliance Switch Position
	  * from the Field Management System Data  A boolean is returned to indicate
	  * Valid data has been captured.  A getOurSwitchLeftSide() is then used
	  * to retrieve the Switch information.
     * @return: SwchPosDataVld: Is The Switch Position Data Valid? (boolean) 
     * */
	 public boolean dtrmnOurSwitchPstn() {
  	 
	      if(FieldData.length() > 0) {
	    	  if (FieldData.charAt(0) == 'L') {
	    		 SwitchDataCaptured = true;
                 OurSwitchLeftSide = true;
	    	  } else if (FieldData.charAt(0) == 'R') {
	    		  SwitchDataCaptured = true;
                 OurSwitchLeftSide = false;
	    	  } else {
	    		  SwitchDataCaptured = false;
	    	  }
	      }else {
	    	  SwitchDataCaptured = false;
	      }
	      
	      return SwitchDataCaptured;    	 
    }

	/** Method: dtrmnOurScalePstn() -  Determine Our Alliance Switch Position
	  * from the Field Management System Data  A boolean is returned to indicate
	  * Valid data has been captured.  A getOurScaleLeftSide() is then used
	  * to retrieve the Scale information.
     * @return: SclPosDataVld: Is The Scale Position Data Valid? (boolean)
     */
    public boolean dtrmnOurScalePstn() {
   	   	 
	      if(FieldData.length() > 0) {
	    	  if (FieldData.charAt(1) == 'L') {
	    		  ScaleDataCaptured = true;
                  OurScaleLeftSide = true;
	    	  } else if (FieldData.charAt(1) == 'R') {
	    		  ScaleDataCaptured = true;
                  OurScaleLeftSide = false;
	    	  } else {
	    		  ScaleDataCaptured = false;
	    	  }
	      }else {
	    	  ScaleDataCaptured = false;
	      }
	      
	      return ScaleDataCaptured;    	 
    }

    
 	/** Method: getColorFromAlliance() -  Determine the color of our Alliance. */
    public static char getColorFromAlliance() {    	 
        if(AllianceColor.equals(DriverStation.Alliance.Red)) {
    	     return "R".charAt(0);
    	 }else {
    	     return "B".charAt(0);
    	 }
    }

    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

