package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.OIConstants.*;
import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.subsystems.Climber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ClimberCommands
{
    private final Climber mClimber;
    private final Joystick mArcadeController;

    public ClimberCommands(Climber climber, Joystick arcadeController)
    {
        mClimber = climber;
        mArcadeController = arcadeController;
    }

    public class ExtendCommand extends CommandBase
    {
        public ExtendCommand()
        {
            addRequirements(mClimber);
        }

        @Override
        public void initialize()
        {
            mClimber.setMotor(kClimberMotorSpeed);
        }

        @Override
        public boolean isFinished()
        {
            return !mArcadeController.getRawButton(kClimberExtendButton);
        }

        @Override
        public void end(boolean interrupted)
        {
            mClimber.setMotor(0);
        }
    }

    public class RetractCommand extends CommandBase
    {
        public RetractCommand()
        {
            addRequirements(mClimber);
        }

        @Override
        public void initialize()
        {
            mClimber.setMotor(-kClimberMotorSpeed);
        }

        @Override
        public boolean isFinished()
        {
            return !mArcadeController.getRawButton(kClimberRetractButton);
        }

        @Override
        public void end(boolean interrupted)
        {
            mClimber.setMotor(0);
        }
    }
}
