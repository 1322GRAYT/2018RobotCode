package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CLAW extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private TalonSRX clawL = new TalonSRX(RobotMap.CLAW_L);
	private TalonSRX clawR = new TalonSRX(RobotMap.CLAW_R);
	private Solenoid clawPneO = new Solenoid(RobotMap.CLAW_CLOSE_O);
	private Solenoid clawPneC = new Solenoid(RobotMap.CLAW_CLOSE_C);
	private Solenoid clawLiftO = new Solenoid(RobotMap.CLAW_LIFT_O);
	private Solenoid clawLiftC = new Solenoid(RobotMap.CLAW_LIFT_C);
	
	public void clawSpeed(double speed) {
		clawL.set(ControlMode.PercentOutput, speed);
		clawR.set(ControlMode.PercentOutput, speed);
	}
	
	public void openClaw() {
		clawPneO.set(true);
		clawPneC.set(false);
	}
	
	public void closeClaw() {
		clawPneO.set(false);
		clawPneC.set(true);
	}
	
	public void liftClaw() {
		clawLiftO.set(true);
		clawLiftC.set(false);
	}
	
	public void lowerClaw() {
		clawLiftO.set(false);
		clawLiftC.set(true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

