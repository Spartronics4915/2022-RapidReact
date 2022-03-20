package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Intake.*;

import com.spartronics4915.frc2022.subsystems.Intake;
import com.spartronics4915.frc2022.subsystems.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class IntakeCommands 
{
    private final Intake mIntake;
    private final Conveyor mConveyor;

    public IntakeCommands(Intake intake, Conveyor conveyor)
    {
        mIntake = intake;
        mConveyor = conveyor;

        mIntake.setDefaultCommand(new RunIntake());
    }

    public class RunIntake extends CommandBase {
        public RunIntake() {
            addRequirements(mIntake);
        }

        @Override
        public void execute() {
            if (mConveyor.isFull() && mIntake.getToggleState())
                mIntake.stopIntake();
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
                mIntake.startIntake(false);
            } else {
                mIntake.stopIntake();
            }
        }

        // Returns true when the command should end.
        @Override
        public boolean isFinished()
        {
            return true;
        }
    }

    public class RetractIntakeDelayedMotor extends SequentialCommandGroup
    {
        public RetractIntakeDelayedMotor()
        {
            addCommands(
                new InstantCommand(() -> mIntake.retractArm()),
                new WaitCommand(kIntakeStopMotorDelay),
                new InstantCommand(() -> mIntake.stopMotorAndToggle())
                );
            addRequirements(mIntake); // Declares subsystem dependencies
        }
    }

    public class TryToggleIntake extends ConditionalCommand {
        public TryToggleIntake() {
            super(
                new SequentialCommandGroup(
                    new WaitCommand(kRetractIntakeDelay),
                    new RetractIntakeDelayedMotor()
                ),
                new ToggleIntake(),
                mIntake::getToggleState
            );
        }
    }
 
    public class RetractIntake extends ConditionalCommand {
        public RetractIntake() {
            super(
                new SequentialCommandGroup(
                    new WaitCommand(kRetractIntakeDelay),
                    new ToggleIntake()
                ),
                new WaitCommand(0),
                mIntake::getToggleState
            );
        }
    }
}


