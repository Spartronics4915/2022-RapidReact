package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ClimberCommands //TODO: BUTTON CONTROL
{
    private final Climber mClimber;

    public ClimberCommands(Climber climber)
    {
        mClimber = climber;
    }

    //TODO: Replace with commands controlled by drivers. Should be handled  in RobotContainer i believe?
    /*
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
            return true;
        }
    }
    */
    /*
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
            return true;
        }
    }
    */
}
