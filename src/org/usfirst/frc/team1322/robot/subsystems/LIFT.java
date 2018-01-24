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

    private TalonSRX lift_1 = new TalonSRX(RobotMap.LIFT_1);
    private TalonSRX lift_2 = new TalonSRX(RobotMap.LIFT_2);
    private Solenoid shift = new Solenoid(RobotMap.LIFT_SHIFT);
    
    public void ShiftLiftUp()
    {
    	shift.set(true);
    }
    
    public void ShiftLiftDown()
    {
    	shift.set(false);
    }
    
    public void setSpeed(double speed) 
    {
    	lift_1.set(ControlMode.PercentOutput, speed);
    	lift_2.set(ControlMode.PercentOutput, speed);
    }
    
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

