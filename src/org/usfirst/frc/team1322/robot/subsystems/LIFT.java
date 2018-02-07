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
 *
 */
public class LIFT extends Subsystem {

    private TalonSRX lift1 = new TalonSRX(RobotMap.LIFT_1);
    private TalonSRX lift2 = new TalonSRX(RobotMap.LIFT_2);
    private Solenoid shiftO = new Solenoid(RobotMap.LIFT_SHIFT_O);
    private Solenoid shiftC = new Solenoid(RobotMap.LIFT_SHIFT_C);
    private Solenoid liftJam = new Solenoid(RobotMap.LIFT_JAM);
    private DigitalInput lowSen = new DigitalInput(RobotMap.LOW_LIFT);
    private DigitalInput midSen = new DigitalInput(RobotMap.MID_LIFT);
    private DigitalInput highSen = new DigitalInput(RobotMap.HIGH_LIFT);
    
    public void shiftLiftLow(){
    	shiftO.set(true);
    	shiftC.set(false);
    }
    
    public void shiftLiftHigh(){
    	shiftO.set(false);
    	shiftC.set(true);
    }
    
    private boolean getLowGear() {
    	return !shiftO.get() && shiftC.get();
    }
    
    public boolean getMidSen() {
    	return midSen.get();
    }
    
    public boolean getHighSen() {
    	return highSen.get();
    }
    
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
    
    public void disengageJammer() {
    	liftJam.set(true);
    }
    
    public void engageJammer() {
    	liftJam.set(true);
    }
    
    private double dzify(double value) {
		double deadzone = RobotMap.lowDeadzone;
		if(value > deadzone || value < -deadzone) {
			return value;
		}
		return 0.0;
	}
    
    public void initDefaultCommand() {
       setDefaultCommand(new TC_LiftMotor());
    }
}

