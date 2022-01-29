package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

/**
 * Detailed description of ExampleSubsystem.
 */
public class ExampleSubsystem extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private SpartronicsMotor mFlywheelMasterMotor;
    private double mTargetRPS;

    private SimpleMotorFeedforward mFeedforwardCalculator;

    /** Creates a new ExampleSubsystem. */
    public ExampleSubsystem()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mFlywheelMasterMotor = SpartronicsMax.makeMotor(Constants.ExampleSubsystem.kFlywheelMasterId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

        /**
     * Sets target RPS for flywheel to given RPS.
     * Call this in execute() method of a command to have the motor
     * constantly run at the target RPS.
     * <p>
     * Does not allow values greater than 90 RPS (currently, refer to
     * Constants.Launcher.kMaxRPS).
     * @param rps RPS you want the flywheel to target
     */
    public void runFlywheel(double rps)
    {
        mTargetRPS = Math.min(rps, Constants.ExampleSubsystem.kHighRPS);
        mFlywheelMasterMotor.setVelocity(mTargetRPS,
            mFeedforwardCalculator.calculate(mTargetRPS / 60.0));
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
