package com.spartronics4915.frc2022.commands;

import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.spartronics4915.frc2022.Constants.Launcher.*;

/**
 * Detailed description of ExampleCommand.
 */
public class LauncherCommands
{
    private final Launcher mLauncher;

    public LauncherCommands(Launcher subsystem, Joystick mArcadecontroller)
    {
        mLauncher = subsystem;
        mLauncher.setDefaultCommand(new ToggleLauncher());
    }

    public class ToggleLauncher extends CommandBase
    {
        public ToggleLauncher() {
            addRequirements(mLauncher);
        }
        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            if (mLauncher.toggleLauncher()) {
                mLauncher.setMotorSpeed(mLauncher.getSliderValue());
            }
            else {
                mLauncher.setMotorSpeed(0);
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
            return true;
        }

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {}
    }

    public class ShootFar extends CommandBase
    {
        public ShootFar() {
            addRequirements(mLauncher);
        }
        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
                mLauncher.setMotorSpeed(kFlywheelFarRPS);
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // No isFinished because command is a WhileHeld

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
            mLauncher.setMotorSpeed(mLauncher.getSliderValue());
            mLauncher.setToggleTrue();
        }
    }
}
