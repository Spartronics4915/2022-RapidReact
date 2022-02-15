package com.spartronics4915.frc2022.commands;

import com.spartronics4915.frc2022.subsystems.Intake;
import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Detailed description of ExampleCommand.
 */
public class ToggleIntake extends CommandBase
{
    private final Intake mIntake;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ToggleIntake(Intake subsystem)
    {
        mIntake = subsystem;
        addRequirements(mIntake); // Declares subsystem dependencies
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (mIntake.toggleIntake()) {
            mIntake.startIntake();
        } else {
            mIntake.stopIntake();
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
}
