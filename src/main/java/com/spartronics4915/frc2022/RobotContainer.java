package com.spartronics4915.frc2022;

import com.spartronics4915.frc2022.subsystems.Intake;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants.OIConstants;
import com.spartronics4915.frc2022.commands.ExampleCommand;
import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.ExampleSubsystem;
import com.spartronics4915.frc2022.subsystems.Launcher;
import com.spartronics4915.frc2022.Constants.OIConstants;

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
    public final ExampleSubsystem mExampleSubsystem;
    public final ExampleCommand mAutoCommand;
    public final Intake mIntake;
    //public final Launcher mLauncher;
    //public final Conveyor mConveyor;
  
    public static final Joystick mArcadeController = new Joystick(OIConstants.kArcadeStickPort);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // ...and constructed here.
        mExampleSubsystem = new ExampleSubsystem();
        mAutoCommand = new ExampleCommand(mExampleSubsystem);
        mIntake = new Intake();
        //mLauncher = new Launcher();
        //mConveyor = new Conveyor();

        configureButtonBindings();
    }

    /** Use this method to define your button ==> command mappings. */
    private void configureButtonBindings() {
        new JoystickButton(mArcadeController, OIConstants.kStartIntakeButton)
            .whenPressed(new InstantCommand(mIntake::startIntake, mIntake));
        new JoystickButton(mArcadeController, OIConstants.kStopIntakeButton)
            .whenPressed(new InstantCommand(mIntake::stopIntake, mIntake));
        /*new JoystickButton(mArcadeController, OIConstants.kCheckIntakeStateButton)
            .whenPressed(new InstantCommand(mIntake::showArmState, mIntake));
        new JoystickButton(mArcadeController, OIConstants.kStartConveyorButton)
            .whenPressed(new InstantCommand(mConveyor::startConveyor, mConveyor));
        new JoystickButton(mArcadeController, OIConstants.kStopConveyorButton)
            .whenPressed(new InstantCommand(mConveyor::stopConveyor, mConveyor));
        new JoystickButton(mArcadeController, OIConstants.kStartFlywheelButton)
                                .whenPressed(new InstantCommand(mLauncher::testStartFlywheel, mLauncher));
        new JoystickButton(mArcadeController, OIConstants.kStopFlywheelButton)
                                .whenPressed(new InstantCommand(mLauncher::testStopFlywheel, mLauncher));
    //TODO: need spin buttons separate from the start flywheel buttons?
        new JoystickButton(mArcadeController, OIConstants.kStartFlywheelButton)
                                .whenPressed(new InstantCommand(mLauncher::testStartSpin, mLauncher));
        new JoystickButton(mArcadeController, OIConstants.kStopFlywheelButton)
                                .whenPressed(new InstantCommand(mLauncher::testStopSpin, mLauncher));
        new JoystickButton(mArcadeController, OIConstants.kStartConveyorButton)
        .whenPressed(new InstantCommand(mConveyor::startConveyor, mConveyor));
        new JoystickButton(mArcadeController, OIConstants.kStopConveyorButton)
        .whenPressed(new InstantCommand(mConveyor::stopConveyor, mConveyor));*/
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    /*public Command getAutonomousCommand;
    {
        return mAutoCommand;
    }*/
    
}
