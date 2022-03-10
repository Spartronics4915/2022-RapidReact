package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.OIConstants.*;

import static com.spartronics4915.frc2022.Constants.Climber.*;

import com.spartronics4915.frc2022.subsystems.Climber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class ClimberCommands
{
    private final Climber mClimber;

    public ClimberCommands(Climber climber)
    {
        mClimber = climber;
    }

    public class StartExtend extends SequentialCommandGroup
    {
        public StartExtend()
        {
            addCommands(
                new InstantCommand(() -> mClimber.setSolenoid(true)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setMotor(kClimberMotorSpeed))
            );
            addRequirements(mClimber);
        }
    }

    public class StopExtend extends SequentialCommandGroup
    {
        public StopExtend()
        {
            addCommands(
                new InstantCommand(() -> mClimber.setMotor(0)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setSolenoid(false))
            );
            addRequirements(mClimber);
        }
    }

    
    public class StartRetract extends InstantCommand
    {
        public StartRetract()
        {
            addRequirements(mClimber);
        }
        
        @Override
        public void initialize()
        {
            super.initialize();
            mClimber.setMotor(-kClimberMotorSpeed);
        }
    }

    public class StopRetract extends InstantCommand
    {
        public StopRetract()
        {
            addRequirements(mClimber);
        }
        
        @Override
        public void initialize()
        {
            super.initialize();
            mClimber.setMotor(0);
        }
    }
}
