package com.spartronics4915.frc2022;

import com.spartronics4915.frc2022.subsystems.Intake;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants.OIConstants;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.frc2022.commands.DriveCommands;
import com.spartronics4915.frc2022.commands.IntakeCommands;
import com.spartronics4915.frc2022.commands.LauncherCommands;
import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Drive;
import com.spartronics4915.frc2022.subsystems.Launcher;
import com.spartronics4915.frc2022.subsystems.Climber;
import com.spartronics4915.frc2022.commands.ClimberCommands;
import com.spartronics4915.frc2022.commands.ConveyorCommands;

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
    public final DriveCommands mDriveCommands;
    public final ClimberCommands mClimberCommands;
    
    public final Drive mDrive;
    public final Climber mClimber;

    public final Intake mIntake;
    public final IntakeCommands mIntakeCommands;

    public final Conveyor mConveyor;
    public final ConveyorCommands mConveyorCommands;

    public final Launcher mLauncher;
    public final LauncherCommands mLauncherCommands;
  
    public static final Joystick mArcadeController = new Joystick(Constants.OIConstants.kArcadeStickPort);
    public static final Joystick mDriverController = new Joystick(Constants.OIConstants.kJoystickPort);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // ...and constructed here.
        //mExampleSubsystem = new ExampleSubsystem();
        //mAutoCommand = new ExampleCommand(mExampleSubsystem);
        mIntake = new Intake();
        mIntakeCommands = new IntakeCommands(mIntake);
        (mIntakeCommands.new ToggleIntake()).schedule();

        mLauncher = new Launcher();
        mLauncherCommands = new LauncherCommands(mLauncher, mArcadeController);
        (mLauncherCommands.new ToggleLauncher()).schedule();

        mDrive = new Drive();
        mDriveCommands = new DriveCommands(mDrive, mDriverController);

        mClimber = new Climber();
        mClimberCommands = new ClimberCommands(mClimber);

        mConveyor = new Conveyor();
        mConveyorCommands = new ConveyorCommands(mConveyor, mIntake);
        (mConveyorCommands.new FillConveyors()).schedule();

        configureButtonBindings();
    }

    /** Use this method to define your button ==> command mappings. */
    private void configureButtonBindings() {
        new JoystickButton(mArcadeController, OIConstants.kIntakeToggleButton)
            .whenPressed(mIntakeCommands.new ToggleIntake());
        new JoystickButton(mArcadeController, OIConstants.kIntakeReverseButton)
            .whileHeld(mIntakeCommands.new EjectIntake());

        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBothButton)
            .whileHeld(mConveyorCommands.new ReverseBoth())
            .whenReleased(mConveyorCommands.new FillConveyors());
        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBottomButton)
            .whileHeld(mConveyorCommands.new ReverseBottom())
            .whenReleased(mConveyorCommands.new FillConveyors());
            
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootButton)
            .whenPressed(mConveyorCommands.new Shoot1() /* or ShootAll() once we're good enough */);
        new JoystickButton(mArcadeController, OIConstants.kLauncherToggleButton)
            .whenPressed(mLauncherCommands.new ToggleLauncher());
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootFarButton)
            .whileHeld(mLauncherCommands.new ShootFar());
            
        new JoystickButton(mArcadeController, Constants.OIConstants.kClimberExtendButton)
            .whenPressed(mClimberCommands.new StartExtend())
            .whenReleased(mClimberCommands.new StopExtend());
        new JoystickButton(mArcadeController, Constants.OIConstants.kClimberRetractButton)
            .whenPressed(mClimberCommands.new StartRetract())
            .whenReleased(mClimberCommands.new StopRetract());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    /*public Command getAutonomousCommand;
    {
        return null; // -0
    }*/
}
