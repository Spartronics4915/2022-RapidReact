package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import java.util.HashMap;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import com.spartronics4915.frc2022.Constants.Launcher;
import com.spartronics4915.frc2022.subsystems.Drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.sensors.SpartronicsPigeon;
import com.spartronics4915.lib.subsystems.drive.AbstractDrive;
import com.spartronics4915.lib.subsystems.estimator.RobotStateMap;
import com.spartronics4915.lib.util.Units;

import org.opencv.core.Mat;

import static com.spartronics4915.frc2022.Constants.Drive.*;

public class AutonomousCommands {
    private HashMap<String, Command> mCommandAutoModes;
    private Drive mDrive;

    private IntakeCommands mIntakeCommands;
    private ConveyorCommands mConveyorCommands;
    private LauncherCommands mLauncherCommands;

    public AutonomousCommands(Drive drive, IntakeCommands intakeCommands, ConveyorCommands conveyorCommands, LauncherCommands launcherCommands) {
        mDrive = drive;
        mIntakeCommands = intakeCommands;
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
                new AutonomousDriveBackwards(kDriveDistanceMeters, false)
            )
        );

        mCommandAutoModes.put(
            "Shoot; Long Wait; Drive",
            new SequentialCommandGroup(
                mLauncherCommands.new TurnOnLauncher(),
                new WaitCommand(kSpinUpDelay),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(kShootDelayLong),
                new AutonomousDriveBackwards(kDriveDistanceMeters, false)
            )
        );

        mCommandAutoModes.put( //must start facing away from hub
            "Drive; Pick Up Ball; Turn; Drive to hub; Shoot (must start with robot facing away from hub)",
            new SequentialCommandGroup(
                mIntakeCommands.new ToggleIntake(),
                mLauncherCommands.new TurnOnLauncher(),
                new AutonomousDrive(Units.feetToMeters(3)), // TODO: put these numbers in constants
                new WaitCommand(0.5),
                new AutonomousRotate(-170),
                new AutonomousDrive(Units.feetToMeters(8.9), true),
                new WaitCommand(0.5),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(0.8),
                mConveyorCommands.new Shoot1()
            )
        );

        mCommandAutoModes.put(
            "drive forward 1 foot (test)", // drives 11.5ish in w/o error correction
            new SequentialCommandGroup(
                new InstantCommand(() -> mDrive.enableBrakeMode()),
                new AutonomousDrive(Units.feetToMeters(errorCorrect(1)) * 0.8) // 0.8 is magic number for now
            )
        );

        mCommandAutoModes.put(
            "drive forward 2 foot (test)", // went 24 ish in w/o error correction
            new SequentialCommandGroup(
                new InstantCommand(() -> mDrive.enableBrakeMode()),
                new AutonomousDrive(Units.feetToMeters(errorCorrect(2)) * 0.8)
            )
        );

        mCommandAutoModes.put(
            "drive forward 3 foot (test)", // went 32 ish w/o error correction
            new SequentialCommandGroup(
                new InstantCommand(() -> mDrive.enableBrakeMode()),
                new AutonomousDrive(Units.feetToMeters(errorCorrect(3)) * 0.8)
            )
        );

        // mCommandAutoModes.put(
        //     "drive 1 foot twice (test)", // went 20 ish w/o error correction
        //     new SequentialCommandGroup(
        //         new InstantCommand(() -> mDrive.enableBrakeMode()),
        //         new AutonomousDrive(Units.feetToMeters(errorCorrect(1)) * 0.8),
        //         new AutonomousDrive(Units.feetToMeters(errorCorrect(1)) * 0.8)
        //     )
        // );
    }

    public double errorCorrect(double n) {
        return (24 / 17) * (n - (5 / 9)); 
    }

    public Command getAutoMode(String id)
    {
        return mCommandAutoModes.get(id);
    }

    public String[] getAllAutoModes() {
        return mCommandAutoModes.keySet().toArray(new String[mCommandAutoModes.size()]);
    }

    public class AutonomousDrive extends CommandBase {
        private double mDistance;
        private double mSpeed;
        
        /**
         * Set speed manually
         * @param distance
         * @param speed
         */
        public AutonomousDrive(double distance, double speed) {
            addRequirements(mDrive);
            mDistance = distance;
            mSpeed = speed;
        }

        /**
         * Use default speed and choose to enable slow mode
         * @param distance
         * @param slow
         */
        public AutonomousDrive(double distance, boolean slow) {
            addRequirements(mDrive);
            mDistance = distance;
            mSpeed = slow ? kDriveSpeedPercent * kSlowModeMultiplier : kDriveSpeedPercent;
        }

        /**
         * Use default speed
         * @param distance
         */
        public AutonomousDrive(double distance) {
            addRequirements(mDrive);
            mDistance = distance;
            mSpeed = kDriveSpeedPercent;
        }

        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(Math.copySign(mSpeed, mDistance), Math.copySign(mSpeed, mDistance));
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition()) / kDriveGearRatio >= Math.abs(mDistance)
                && Math.abs(mDrive.getRightMotor().getEncoder().getPosition()) / kDriveGearRatio >= Math.abs(mDistance);
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }

    /**
     * Drives backwards until we leave the tarmac area
    */
    public class AutonomousDriveBackwards extends CommandBase {
        private double mDistance;
        private double mSpeedPercent;
        public AutonomousDriveBackwards(double distance, boolean slow) {
            addRequirements(mDrive);
            mDistance = distance;
            mSpeedPercent = slow ? kSlowModeMultiplier * kDriveSpeedPercent : kDriveSpeedPercent;
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(-mSpeedPercent, -mSpeedPercent);
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

    public class AutonomousDriveForwards extends CommandBase { // doesn't work
        private double mDistance;
        private double mSpeedPercent;
        public AutonomousDriveForwards(double distance, boolean slow) {
            addRequirements(mDrive);
            mDistance = distance;
            mSpeedPercent = slow ? kSlowModeMultiplier * kDriveSpeedPercent : kDriveSpeedPercent;
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(mSpeedPercent, mSpeedPercent);
        }

        @Override
        public void execute() {
            mDrive.logInfo("mDrive.getLeftMotor().getEncoder().getPosition() = " + mDrive.getLeftMotor().getEncoder().getPosition());
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition() / kDriveGearRatio) >= mDistance // <= might need to be changed to >=
                && Math.abs(mDrive.getRightMotor().getEncoder().getPosition() / kDriveGearRatio) >= mDistance;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }

    public class AutonomousRotate extends CommandBase {
        private double mRelativeTargetRotation;
        private double mInitialBearing;
        private double mCurrentBearing;
        private double mCurrentRelativeBearing;

        /**
         * Positive angle turns clockwise (uses bearing angles)
         * @param targetRotation
         */
        public AutonomousRotate(double targetRotation) {
            mInitialBearing = -mDrive.getIMUHeading().getDegrees(); // negative because rotation2d uses ccw -> positive but bearing uses cw -> positive
            mRelativeTargetRotation = targetRotation;
            mCurrentBearing = mInitialBearing;
            mCurrentRelativeBearing = 0;
        }

        public void initialize() {
            addRequirements(mDrive);
            mDrive.arcadeDrive(0, Math.copySign(1, mRelativeTargetRotation));
        }

        public void execute() {
            mCurrentBearing = -mDrive.getIMUHeading().getDegrees();
            mCurrentRelativeBearing = mCurrentBearing - mInitialBearing;
        }

        public boolean isFinished() {
            return Math.abs(mCurrentRelativeBearing) >= Math.abs(mRelativeTargetRotation);
        }

        public void end(boolean interrupted) {
            mDrive.arcadeDrive(0, 0);
        }
    }

    /**
     * Positive angle turns right
     */

    // initial = getangle;
    // goal = abs(getangle+target);
    // direction = getsign of target ( -1)

    // move at (direction * speed) until getCurrentAngle-initial >=goal

    // public class AutonomousRotate extends CommandBase {
    //     private double mAngle;
    //     private double mInitialAngle;
    //     private double mGoal;
    //     private double mDirection;
    //     /**
    //      * @param angle The bearing angle in degrees
    //      */
    //     public AutonomousRotate(double angle) {
    //         addRequirements(mDrive);
    //         mAngle = angle;
    //         mInitialAngle = mDrive.getAngle();
    //         mGoal = mInitialAngle + mAngle;
    //         mDirection = Math.signum(mAngle);
    //     }
        
    //     @Override
    //     public void initialize() {
    //         mDrive.arcadeDrive(0, Math.copySign(kTurnSpeedPercent, mDirection));
    //     }

    //     @Override
    //     public boolean isFinished() {
    //         return (mDirection == 1) ? (mDrive.getAngle() > mGoal) : (mDrive.getAngle() < mGoal);
    //     }

    //     @Override
    //     public void end(boolean interrupted) {
    //         mDrive.tankDrive(0, 0);
    //     }
    // }
}