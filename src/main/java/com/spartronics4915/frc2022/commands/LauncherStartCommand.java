package com.spartronics4915.frc2022.commands;

import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Detailed description of ExampleCommand.
 */
public class LauncherStartCommand extends CommandBase
{
    private final Launcher mLauncher;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public LauncherStartCommand(Launcher subsystem)
    {
        mLauncher = subsystem;
        addRequirements(mLauncher); // Declares subsystem dependencies
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

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
