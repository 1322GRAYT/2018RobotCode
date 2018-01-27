package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

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
    
    public void shiftLiftHigh(){
    	shiftO.set(true);
    	shiftC.set(false);
    }
    
    public void shiftLiftLow(){
    	shiftO.set(false);
    	shiftC.set(true);
    }
    
    public void setSpeed(double speed) {
    	lift1.set(ControlMode.PercentOutput, speed);
    	lift2.set(ControlMode.PercentOutput, speed);
    }
    
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

