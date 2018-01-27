package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DRIVE extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private TalonSRX lF_Drive_1= new TalonSRX(RobotMap.LF_MECH_1);
	private TalonSRX lF_Drive_2= new TalonSRX(RobotMap.LF_MECH_2);
	private TalonSRX rF_Drive_1= new TalonSRX(RobotMap.RF_MECH_1);
	private TalonSRX rF_Drive_2= new TalonSRX(RobotMap.RF_MECH_2);
	private TalonSRX lR_Drive_1= new TalonSRX(RobotMap.LR_MECH_1);
	private TalonSRX lR_Drive_2= new TalonSRX(RobotMap.LR_MECH_2);
	private TalonSRX rR_Drive_1= new TalonSRX(RobotMap.RR_MECH_1);
	private TalonSRX rR_Drive_2= new TalonSRX(RobotMap.RR_MECH_2);

	public void mechDrive(double forwardSpeed, double strafeSpeed, double turnSpeed) {
		double r = Math.hypot(strafeSpeed, forwardSpeed);
		double robotAngle = Math.atan2(forwardSpeed, strafeSpeed) - Math.PI / 4;
		final double v1 = r * Math.cos(robotAngle) + turnSpeed;
		final double v2 = r * Math.sin(robotAngle) - turnSpeed;
		final double v3 = r * Math.sin(robotAngle) + turnSpeed;
		final double v4 = r * Math.cos(robotAngle) - turnSpeed;

		lF_Drive_1.set(ControlMode.PercentOutput, v1);
		lF_Drive_1.set(ControlMode.PercentOutput, v1);
		rF_Drive_1.set(ControlMode.PercentOutput, v2);
		rF_Drive_2.set(ControlMode.PercentOutput, v2);
		lR_Drive_1.set(ControlMode.PercentOutput, v3);
		lR_Drive_2.set(ControlMode.PercentOutput, v3);
		rR_Drive_1.set(ControlMode.PercentOutput, v4);
		rR_Drive_2.set(ControlMode.PercentOutput, v4);
	}
	
	public void disable() {
		lF_Drive_1.set(ControlMode.Disabled, 0);
		lF_Drive_1.set(ControlMode.Disabled, 0);
		rF_Drive_1.set(ControlMode.Disabled, 0);
		rF_Drive_2.set(ControlMode.Disabled, 0);
		lR_Drive_1.set(ControlMode.Disabled, 0);
		lR_Drive_2.set(ControlMode.Disabled, 0);
		rR_Drive_1.set(ControlMode.Disabled, 0);
		rR_Drive_2.set(ControlMode.Disabled, 0);
	}
	
	public void enable() {
		lF_Drive_1.set(ControlMode.PercentOutput, 0);
		lF_Drive_1.set(ControlMode.PercentOutput, 0);
		rF_Drive_1.set(ControlMode.PercentOutput, 0);
		rF_Drive_2.set(ControlMode.PercentOutput, 0);
		lR_Drive_1.set(ControlMode.PercentOutput, 0);
		lR_Drive_2.set(ControlMode.PercentOutput, 0);
		rR_Drive_1.set(ControlMode.PercentOutput, 0);
		rR_Drive_2.set(ControlMode.PercentOutput, 0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

