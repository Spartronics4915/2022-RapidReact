package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import java.util.HashMap;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import com.spartronics4915.frc2022.Constants.Launcher;
import com.spartronics4915.frc2022.subsystems.Drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.sensors.SpartronicsPigeon;
import com.spartronics4915.lib.subsystems.drive.AbstractDrive;
import com.spartronics4915.lib.util.Units;

import static com.spartronics4915.frc2022.Constants.Drive.*;

public class AutonomousCommands {
    private HashMap<String, Command> mCommandAutoModes;
    private Drive mDrive;

    private IntakeCommands mIntakeCommands;
    private ConveyorCommands mConveyorCommands;
    private LauncherCommands mLauncherCommands;

    public AutonomousCommands(Drive drive, ConveyorCommands conveyorCommands, LauncherCommands launcherCommands) {
        mDrive = drive;
        mConveyorCommands = conveyorCommands;
        mLauncherCommands = launcherCommands;

        mCommandAutoModes = new HashMap<>();

        mCommandAutoModes.put(
            "Shoot; Short Wait; Drive",
            new SequentialCommandGroup(
                mLauncherCommands.new TurnOnLauncher(),
                new WaitCommand(kSpinUpDelay),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(kShootDelayShort),
                new AutonomousDriveBackwards(kDriveDistanceMeters)
            )
        );

        mCommandAutoModes.put(
            "Shoot; Long Wait; Drive",
            new SequentialCommandGroup(
                mLauncherCommands.new TurnOnLauncher(),
                new WaitCommand(kSpinUpDelay),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(kShootDelayLong),
                new AutonomousDriveBackwards(kDriveDistanceMeters)
            )
        );

        mCommandAutoModes.put( //must start facing away from hub
            "Drive; Pick Up Ball; Turn; Drive to hub; Shoot (must start with robot facing away from hub)",
            new SequentialCommandGroup(
                mIntakeCommands.new ToggleIntake(),
                mLauncherCommands.new TurnOnLauncher(),
                new AutonomousDriveForwards(Units.feetToMeters(5)), // TODO: put these numbers in constants
                new WaitCommand(0.5),
                mIntakeCommands.new ToggleIntake(),
                new AutonomousRotate(180),
                new WaitCommand(0.5),
                new AutonomousDriveForwards(Units.feetToMeters(15)),
                new WaitCommand(0.5),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(0.8),
                mConveyorCommands.new Shoot1()
            )
        );
    }

    public Command getAutoMode(String id)
    {
        return mCommandAutoModes.get(id);
    }

    public String[] getAllAutoModes() {
        return mCommandAutoModes.keySet().toArray(new String[mCommandAutoModes.size()]);
    }
    /**
     * Drives backwards until we leave the tarmac area
    */
    public class AutonomousDriveBackwards extends CommandBase {
        private double mDistance;
        public AutonomousDriveBackwards(double distance) {
            addRequirements(mDrive);
            mDistance = distance;
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(-kDriveSpeedPercent, -kDriveSpeedPercent);
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition() / kDriveGearRatio) >= mDistance
                && Math.abs(mDrive.getRightMotor().getEncoder().getPosition() / kDriveGearRatio) >= mDistance;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }

    public class AutonomousDriveForwards extends CommandBase {
        private double mDistance;
        public AutonomousDriveForwards(double distance) {
            addRequirements(mDrive);
            mDistance = distance;
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(kDriveSpeedPercent, kDriveSpeedPercent);
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition() / kDriveGearRatio) <= mDistance // <= might need to be changed to >=
                && Math.abs(mDrive.getRightMotor().getEncoder().getPosition() / kDriveGearRatio) <= mDistance;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }

    public class AutonomousRotate extends CommandBase {
        private double mAngle;
        private Rotation2d mInitialAngle;
        public AutonomousRotate(double angle) {
            addRequirements(mDrive);
            mAngle = angle;
            mInitialAngle = getAngle();
        }

        public Rotation2d getAngle() {
            return mDrive.getIMUHeading();
        }
        
        @Override
        public void initialize() {
            mDrive.arcadeDrive(0, Math.copySign(kTurnSpeedPercent, mAngle));
        }

        @Override
        public boolean isFinished() {
            return getAngle().minus(mInitialAngle).getDegrees() / mAngle >= 1;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }
}