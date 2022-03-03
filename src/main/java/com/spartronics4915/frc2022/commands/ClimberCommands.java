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
        private int mAccumulator = 0;
        
        public ExtendCommand()
        {
            addRequirements(mClimber);
        }

        @Override
        public void initialize()
        {   
            mClimber.setSolenoid(true);
        }

        @Override
        public void execute()
        {
            if (mAccumulator++ == kDelay1) // make work
            {
                mClimber.setMotor(kClimberMotorSpeed);
            }
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
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            mClimber.setSolenoid(false);
        }
    }

    public class RetractCommand extends CommandBase
    {
        private int mAccumulator = 0;
        
        public RetractCommand()
        {
            addRequirements(mClimber);
        }

        @Override
        public void initialize()
        {   
            mClimber.setSolenoid(true);
        }

        @Override
        public void execute()
        {
            if (mAccumulator++ == kDelay1) // make work
            {
                mClimber.setMotor(-kClimberMotorSpeed);
            }
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
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            mClimber.setSolenoid(false);
        }
    }
}
