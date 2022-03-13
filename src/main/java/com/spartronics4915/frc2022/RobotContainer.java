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
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
    
    public final Climber mClimber;
    public final ClimberCommands mClimberCommands;
    
    public final Drive mDrive;
    public final DriveCommands mDriveCommands;

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

        mLauncher = new Launcher();
        // mDrive = new Drive();
        mClimber = new Climber();
        mConveyor = new Conveyor();
        mIntake = new Intake();
        
        mLauncherCommands = new LauncherCommands(mLauncher, mArcadeController);
        // mDriveCommands = new DriveCommands(mDrive, mDriverController);
        mClimberCommands = new ClimberCommands(mClimber);
        mConveyorCommands = new ConveyorCommands(mConveyor, mIntake);
        mIntakeCommands = new IntakeCommands(mIntake);

        configureButtonBindings();
    }

    /** Use this method to define your button ==> command mappings. */
    private void configureButtonBindings() {
        new JoystickButton(mArcadeController, OIConstants.kIntakeToggleButton)
            .whenPressed(mIntakeCommands.new TryToggleIntake())
            .whenPressed(mConveyorCommands.new TryToggleConveyor())
            .whenPressed(mLauncherCommands.new ToggleLauncher());

        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBothButton)
            .whileHeld(mConveyorCommands.new ReverseBoth());
        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBottomButton)
            .whileHeld(mConveyorCommands.new ReverseBottom());
            
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootButton)
            .whenPressed(new SequentialCommandGroup(
                mIntakeCommands.new RetractIntake(),
                mConveyorCommands.new Shoot1() /* or ShootAll() once we're good enough */
            ));
        new JoystickButton(mArcadeController, OIConstants.kLauncherToggleButton)
            .whenPressed(mLauncherCommands.new ToggleLauncher());
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootFarButton)
            .whileHeld(mLauncherCommands.new ShootFar());
            
        new JoystickButton(mArcadeController, OIConstants.kClimberExtendButton)
            .whenPressed(mClimberCommands.new StartExtend())
            .whenReleased(mClimberCommands.new StopExtend());
        new JoystickButton(mArcadeController, OIConstants.kClimberRetractButton)
            .whenPressed(mClimberCommands.new StartRetract())
            .whenReleased(mClimberCommands.new StopRetract());
    
        // new JoystickButton(mArcadeController, Constants.OIConstants.kIntakeReverseButton)
        //     .whileHeld(mConveyorCommands.new FillConveyors());
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

    public Command getTeleopCommand()
    {
        return mLauncherCommands.new ToggleLauncher();
    }
}
