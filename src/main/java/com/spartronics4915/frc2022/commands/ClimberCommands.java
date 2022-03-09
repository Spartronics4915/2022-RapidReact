package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.OIConstants.*;

import static com.spartronics4915.frc2022.Constants.Climber.*;

import com.spartronics4915.frc2022.subsystems.Climber;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class ClimberCommands
{
    private final Climber mClimber;
    private final Joystick mArcadeController;

    public ClimberCommands(Climber climber, Joystick arcadeController)
    {
        mClimber = climber;
        mArcadeController = arcadeController;
    }

    public class WaitUntilReleasedCommand extends CommandBase
    {
        private int mButton;

        public WaitUntilReleasedCommand(int button)
        {
            mButton = button;
        }

        @Override
        public boolean isFinished()
        {
            return !mArcadeController.getRawButton(mButton);
        }
    }

    public class ExtendCommand extends SequentialCommandGroup
    {
        public ExtendCommand()
        {
            addCommands(
                new InstantCommand(() -> mClimber.setSolenoid(true)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setMotor(kClimberMotorSpeed)),
                new WaitUntilReleasedCommand(kClimberExtendButton),
                new InstantCommand(() -> mClimber.setMotor(0)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setSolenoid(false))
            );
            addRequirements(mClimber);
        }
    }

    public class RetractCommand extends SequentialCommandGroup
    {
        public RetractCommand()
        {
            addCommands(
                new InstantCommand(() -> mClimber.setSolenoid(true)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setMotor(-kClimberMotorSpeed)),
                new WaitUntilReleasedCommand(kClimberRetractButton),
                new InstantCommand(() -> mClimber.setMotor(0)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setSolenoid(false))
            );
            addRequirements(mClimber);
        }
    }
}
