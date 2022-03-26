package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import java.util.HashMap;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import com.spartronics4915.frc2022.Constants.Launcher;
import com.spartronics4915.frc2022.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutonomousCommands {
    private HashMap<String, Command> mCommandAutoModes;
    private Drive mDrive;

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
                new AutonomousDrive()
            )
        );

        mCommandAutoModes.put(
            "Shoot; Long Wait; Drive",
            new SequentialCommandGroup(
                mLauncherCommands.new TurnOnLauncher(),
                new WaitCommand(kSpinUpDelay),
                mConveyorCommands.new Shoot1(),
                new WaitCommand(kShootDelayLong),
                new AutonomousDrive()
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
    public class AutonomousDrive extends CommandBase {
        public AutonomousDrive() {
            addRequirements(mDrive);
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(-kDriveSpeedPercent, -kDriveSpeedPercent);
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition() / kDriveGearRatio) >= kDriveDistanceMeters
                && Math.abs(mDrive.getRightMotor().getEncoder().getPosition() / kDriveGearRatio) >= kDriveDistanceMeters;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }
}