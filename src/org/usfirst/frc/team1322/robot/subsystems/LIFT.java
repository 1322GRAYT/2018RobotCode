package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TC_LiftMotor;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lift Subsystem
 */
public class LIFT extends Subsystem {

	//Crete New Instances of all required things	
    private TalonSRX lift1 = new TalonSRX(RobotMap.LIFT_1); 				//Lift Motor 1
    private TalonSRX lift2 = new TalonSRX(RobotMap.LIFT_2); 				//Lift Motor 2
    private Solenoid shiftO = new Solenoid(RobotMap.LIFT_SHIFT_O); 			//Shift Solenoid Valve 1
    private Solenoid shiftC = new Solenoid(RobotMap.LIFT_SHIFT_C); 			//Shift Solenoid Valve 2
    private Solenoid liftJam = new Solenoid(RobotMap.LIFT_JAM); 			//Lift Jammer 
    private DigitalInput lowSen = new DigitalInput(RobotMap.LOW_LIFT); 		//Sensor at bottom of lift
    private DigitalInput midSen = new DigitalInput(RobotMap.MID_LIFT); 		//Sensor at middle of lift
    private DigitalInput highSen = new DigitalInput(RobotMap.HIGH_LIFT); 	//Sensor at top of lift
    
    /**
     * Shifts the lift to low
     */
    public void shiftLiftLow(){
    	shiftO.set(true);
    	shiftC.set(false);
    }
    
    /**
     * Shifts the lift to high
     */
    public void shiftLiftHigh(){
    	shiftO.set(false);
    	shiftC.set(true);
    }
    
    /**
     * Returns whether the lift is in low gear
     * @return Is Low Gear
     */
    private boolean getLowGear() {
    	return !shiftO.get() && shiftC.get();
    }
    
    /**
     * Returns whether the lift is in high gear
     * @return Is High Gear
     */
    private boolean getHighGear() {
    	return shiftO.get() && !shiftC.get();
    }
    
    /**
     * Gets the low sensor value
     * @return Low Lift Sensor Value (default true)
     */
    public boolean getLowSen() {
    	return lowSen.get();
    }
    /**
     * Gets the mid sensor value
     * @return Mid Lift Sensor Value (default true)
     */
    public boolean getMidSen() {
    	return midSen.get();
    }
    
    /**
     * Gets the high sensor value
     * @return High Lift Sensor Value (default true)
     */
    public boolean getHighSen() {
    	return highSen.get();
    }
        
    /**
     * Disengages the jammer
     */
    public void disengageJammer() {
    	liftJam.set(true);
    }
    
    /**
     * Engages the jammer
     */
    public void engageJammer() {
    	liftJam.set(true);
    }
    
    /**
     * Set the speed of the lift
     * @param speed What speed you want the lift to go
     */
    public void setSpeed(double speed) {
    	double upPower = dzify(speed);
    	if(
    			(lowSen.get() && highSen.get()) || 
    			(!lowSen.get() && upPower > 0.31) || 
    			(!highSen.get() && upPower < -0.31)) {
    		if(speed != 0.0)disengageJammer();
    		lift1.set(ControlMode.PercentOutput, upPower);
    		lift2.set(ControlMode.PercentOutput, upPower);
    		if(getLowGear() && speed == 0.0) engageJammer(); //Check If We are in low gear
    	}
    	
    }
   
    /**
	 * 	Deadzonifies a double to the set deadzone in RobotMap
	 * @param A double between -1 and 1
	 * @return A double that is now been deadzoned
	 */
    private double dzify(double value) {
		double deadzone = RobotMap.lowDeadzone;
		if(value > deadzone || value < -deadzone) {
			return value;
		}
		return 0.0;
	}
    
    /**
	 * Sets the default command so that it is ALWAYS running throughout teleop
	 */
    public void initDefaultCommand() {
       setDefaultCommand(new TC_LiftMotor());
    }
}

