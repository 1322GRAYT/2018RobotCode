package org.usfirst.frc.team1322.robot.triggers;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.commands.BM_ShiftLift;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DpadUp extends Trigger {

    public boolean get() {
    	return OI.AuxStick.getPOV() == 0;
    }
}
