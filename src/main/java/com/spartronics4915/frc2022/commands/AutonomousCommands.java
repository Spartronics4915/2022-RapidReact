package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import java.util.HashMap;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;

import com.spartronics4915.frc2022.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutonomousCommands {
    private HashMap<String, Command> mCommandAutoModes;
    private Drive mDrive;

    private ConveyorCommands mConveyorCommands;

    public AutonomousCommands(Drive drive, ConveyorCommands conveyorCommands) {
        mDrive = drive;
        mConveyorCommands = conveyorCommands;
        
        mCommandAutoModes.put("Short Wait; Shoot; Drive",
            new SequentialCommandGroup(
                new WaitCommand(kShootDelayShort),
                mConveyorCommands.new Shoot1(),
                new AutonomousDrive()
            )
        );
        mCommandAutoModes.put("Long Wait; Shoot; Drive",
            new SequentialCommandGroup(
                new WaitCommand(kShootDelayLong),
                mConveyorCommands.new Shoot1(),
                new AutonomousDrive()
            )
        );
    }

    public Command getAutoMode(String id)
    {
        return mCommandAutoModes.get(id);
    }

    public String[] getAllAutoModes() {
        return (String[]) mCommandAutoModes.keySet().toArray();
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