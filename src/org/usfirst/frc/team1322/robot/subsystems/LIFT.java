package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LIFT extends Subsystem 
{
	private TalonSRX Lift_1 = new TalonSRX(RobotMap.Lift_1);
	private TalonSRX Lift_2 = new TalonSRX(RobotMap.Lift_2);
	private Solenoid Lift_Shift = new Solenoid(RobotMap.Lift_Shift);
	
	public void ShiftLiftUp()
	{
		Lift_Shift.set(true);
	}
	
	public void ShiftLiftDown()
	{
		Lift_Shift.set(false);
	}

	public void setSpeed(double speed)
	{
		Lift_1.set(ControlMode.PercentOutput, speed);
		Lift_2.set(ControlMode.PercentOutput, speed);
	}
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

