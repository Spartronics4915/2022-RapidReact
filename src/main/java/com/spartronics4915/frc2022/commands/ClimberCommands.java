package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Climber.*;

import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.frc2022.subsystems.Climber;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberCommands {
    private final Climber mClimber;
    private final Joystick mArcadeController;

    public ClimberCommands(Climber climber, Joystick arcadeController) {
        mClimber = climber;
        mArcadeController = arcadeController;
    }

    public class ExtendTheMotor extends CommandBase {
        public ExtendTheMotor() {
            addRequirements(mClimber);
        }

        @Override
        public void initialize() {
            if (!mClimber.isRotatedTooMuch()) {
                mClimber.setMotor(kClimberMotorSpeed);
            }
        }

        @Override
        public boolean isFinished() {
            return mClimber.isRotatedTooMuch() || mArcadeController.getRawButtonReleased(Constants.OIConstants.kClimberExtendButton);
        }

        @Override
        public void end(boolean interrupted) {
            mClimber.setMotor(0);
            mClimber.setSolenoid(false);
        }
    }

    public class StartExtend extends SequentialCommandGroup {
        public StartExtend() {
            addCommands(
                new InstantCommand(() -> mClimber.setSolenoid(true)),
                new WaitCommand(kDelay),
                new ExtendTheMotor()
            );
            addRequirements(mClimber);
        }
    }

    public class StopExtend extends SequentialCommandGroup {
        public StopExtend() {
            addCommands(
                new InstantCommand(() -> mClimber.setMotor(0)),
                new WaitCommand(kDelay),
                new InstantCommand(() -> mClimber.setSolenoid(false))
            );
            addRequirements(mClimber);
        }
    }

    public class RetractTheMotor extends CommandBase {
        public RetractTheMotor() {
            addRequirements(mClimber);
        }

        @Override
        public void initialize() {
            if (!mClimber.isRotatedTooLittle()) {
                mClimber.setMotor(-kClimberMotorSpeed);
            }
        }

        @Override
        public boolean isFinished() {
            return mClimber.isRotatedTooLittle();
        }

        @Override
        public void end(boolean interrupted) {
            mClimber.setMotor(0);
        }
    }
}
