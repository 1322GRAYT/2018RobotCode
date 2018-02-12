package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TC_LiftMotor;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheelsInOut;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: LIFT - Controls the Telescopic Lift System that the Claw that
 * manipulates the PowerCubes mounts to, and the hook used to hang the
 * robot mounts to as well.
 */
public class LIFT extends Subsystem {

	enum LiftActn
	  {
	  Raise, Lower;
	  }
	
    private TalonSRX lift1 = new TalonSRX(RobotMap.LIFT_1);
    private TalonSRX lift2 = new TalonSRX(RobotMap.LIFT_2);
    private Solenoid shiftO = new Solenoid(RobotMap.LIFT_SHIFT_O);
    private Solenoid shiftC = new Solenoid(RobotMap.LIFT_SHIFT_C);
    private Solenoid liftJam = new Solenoid(RobotMap.LIFT_JAM);
    private DigitalInput lowSen = new DigitalInput(RobotMap.LOW_LIFT);
    private DigitalInput midSen = new DigitalInput(RobotMap.MID_LIFT);
    private DigitalInput highSen = new DigitalInput(RobotMap.HIGH_LIFT);
    
    /** Method: shiftLiftLow - Engage Lift Motor Low Speed Gear */
    public void shiftLiftLow(){
    	shiftO.set(true);
    	shiftC.set(false);
    }

    /** Method: shiftLiftHig - Engage Lift Motor High Speed Gear */
    public void shiftLiftHigh(){
    	shiftO.set(false);
    	shiftC.set(true);
    }
    
    /** Method: getLowGear - Verify Lift Motor is in Low Speed Gear */    
    private boolean getLowGear() {
    	return !shiftO.get() && shiftC.get();
    }

    /** Method: getLowSen - Return the state of the Lift Low Position Sensor */
    public boolean getLowSen() {
    	return lowSen.get();
    }   
    
    /** Method: getMidSen - Return the state of the Lift Mid Position Sensor */
    public boolean getMidSen() {
    	return midSen.get();
    }

    /** Method: getHighSen - Return the state of the Lift High Position Sensor */
    public boolean getHighSen() {
    	return highSen.get();
    }
    
    /** Method: getHighSen - Set the Motor Speed of the Lift System Motor */    
    public void setSpeed(double speed) {
    	double upPower = dzify(speed);
    	if((lowSen.get() && highSen.get()) || 
    	   (!lowSen.get() && upPower > 0.31) || 
    	   (!highSen.get() && upPower < -0.31)) 
    	  {
    	  if(speed != 0.0)disengageJammer();
    	  lift1.set(ControlMode.PercentOutput, upPower);
    	  lift2.set(ControlMode.PercentOutput, upPower);
    	  if(getLowGear() && speed == 0.0) engageJammer(); //Check If We are in low gear
    	  } 	
    }
    
    /** Method: disengageJammer - Release the Lift Gear Block mechanism that 
     *  locks the lift in position to allow the lift to move.  */    
    public void disengageJammer() {
    	liftJam.set(true);
    }

    /** Method: engageJammer - Engage the Lift Gear Block mechanism that 
     *  locks the lift in position to prevent the lift from back-driving.  */    
    public void engageJammer() {
    	liftJam.set(true);
    }
    
    /** Method: dzify - Applies a DeadZone around a threshold limit.  */    
    private double dzify(double value) {
		double deadzone = RobotMap.lowDeadzone;
		if(value > deadzone || value < -deadzone)
		  {
		  return value;
		  }
		return 0.0;
	}
 
    /** Method: initDefaultCommand - Sets the Default Command for the Subsystem.  */      
    public void initDefaultCommand() {
       setDefaultCommand(new TC_LiftMotor());
    }
}

