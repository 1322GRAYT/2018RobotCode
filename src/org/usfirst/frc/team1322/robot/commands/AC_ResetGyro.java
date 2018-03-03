package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AC_ResetGyro extends InstantCommand {

    public AC_ResetGyro() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.kSENSORS);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.kSENSORS.resetGyro();
    }

}
