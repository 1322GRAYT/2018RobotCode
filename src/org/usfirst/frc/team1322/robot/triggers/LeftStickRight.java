package org.usfirst.frc.team1322.robot.triggers;

import org.usfirst.frc.team1322.robot.OI;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class LeftStickRight extends Trigger {

    public boolean get() {
        return OI.AuxStick.getX(Hand.kLeft) <= .3;
    }
}
