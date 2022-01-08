package com.spartronics4915.frc.commands;

import com.spartronics4915.frc.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Detailed description of ExampleCommand.
 */
public class ExampleCommand extends CommandBase
{
    private final ExampleSubsystem mExampleSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ExampleCommand(ExampleSubsystem subsystem)
    {
        mExampleSubsystem = subsystem;
        addRequirements(mExampleSubsystem); // Declares subsystem dependencies
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

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
