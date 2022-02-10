package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.subsystems.Climber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ClimberCommands //TODO: BUTTON
{
    private final Climber mClimber;

    public ClimberCommands(Climber climber)
    {
        mClimber = climber;
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
        public void end(boolean interrupted)
        {
            mClimber.setMotor(0);
        }

        @Override
        public boolean isFinished()
        {
            return mClimber.isExtended();
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
        public void end(boolean interrupted)
        {
            mClimber.setMotor(0);
        }

        @Override
        public boolean isFinished()
        {
            return mClimber.isRetracted();
        }
    }
}
