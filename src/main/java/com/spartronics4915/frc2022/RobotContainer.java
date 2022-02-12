package com.spartronics4915.frc2022;

import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.frc2022.commands.DriveCommands;
import com.spartronics4915.frc2022.commands.ExampleCommand;
import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Drive;
import com.spartronics4915.frc2022.subsystems.ExampleSubsystem;
import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.
 * Since command-based is a "declarative" paradigm, very little robot logic
 * should actually be handled in the {@link Robot} periodic methods
 * (other than the scheduler calls). Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    //public final ExampleSubsystem mExampleSubsystem;
    //public final ExampleCommand mAutoCommand;
    public final DriveCommands mDriveCommands;
    
    //public final Launcher mLauncher;
    //public final Conveyor mConveyor;
    public final Drive mDrive;
  
    public static final Joystick mArcadeController = new Joystick(Constants.OIConstants.kArcadeStickPort);
    public static final Joystick mDriverController = new Joystick(Constants.OIConstants.kJoystickPort);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // ...and constructed here.
        //mExampleSubsystem = new ExampleSubsystem();
        //mAutoCommand = new ExampleCommand(mExampleSubsystem);

        //mLauncher = new Launcher();
        //mConveyor = new Conveyor();

        mDrive = new Drive();
        mDriveCommands = new DriveCommands(mDrive, mDriverController);

        configureButtonBindings();
    }

    /** Use this method to define your button ==> command mappings. */
    private void configureButtonBindings() {
    //     new JoystickButton(mArcadeController, Constants.OIConstants.kStartFlywheelButton)
    //         .whenPressed(new InstantCommand(mLauncher::testStartFlywheel, mLauncher));
    //     new JoystickButton(mArcadeController, Constants.OIConstants.kStopFlywheelButton)
    //         .whenPressed(new InstantCommand(mLauncher::testStopFlywheel, mLauncher));
    
    // //TODO: need spin buttons separate from the start flywheel buttons?
    //     new JoystickButton(mArcadeController, Constants.OIConstants.kStartFlywheelButton)
    //         .whenPressed(new InstantCommand(mLauncher::testStartSpin, mLauncher));
    //     new JoystickButton(mArcadeController, Constants.OIConstants.kStopFlywheelButton)
    //         .whenPressed(new InstantCommand(mLauncher::testStopSpin, mLauncher));
    
     
        // new JoystickButton(mArcadeController, Constants.OIConstants.kStartConveyorButton)
        //     .whenPressed(new InstantCommand(mConveyor::startConveyor, mConveyor));
        // new JoystickButton(mArcadeController, Constants.OIConstants.kStopConveyorButton)
        //     .whenPressed(new InstantCommand(mConveyor::stopConveyor, mConveyor));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        return null; // -0
    }
    
}