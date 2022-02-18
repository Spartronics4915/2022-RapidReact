package com.spartronics4915.frc2022.commands;

import com.spartronics4915.frc2022.subsystems.Intake;
import com.spartronics4915.frc2022.subsystems.Launcher;
import static com.spartronics4915.frc2022.Constants.Intake.*;
import static com.spartronics4915.frc2022.Constants.OIConstants.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeCommands 
{
    private final Intake mIntake;
    private final Joystick mArcadeController;

    public IntakeCommands(Intake intake, Joystick arcadeController)
    {
        mIntake = intake;
        mArcadeController = arcadeController;
    }

    public class EjectIntake extends CommandBase 
    {
    
        public EjectIntake ()
        {
            addRequirements(mIntake); // Declares subsystem dependencies
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mIntake.eject();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {
        }

        // Returns true when the command should end.
        /*@Override
        public boolean isFinished()
        {
            return !mArcadeController.getRawButton(kHoldToEjectIntakeButton);
        }*/

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
            mIntake.stopIntakeMotor();
        }
    }
    public class ToggleIntake extends CommandBase
    {

        public ToggleIntake()
        {
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
            return true;
        }

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {}
    }

}


