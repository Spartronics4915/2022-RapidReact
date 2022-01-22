package com.spartronics4915.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

/**
 * Detailed description of Launcher.
 */
public class Launcher extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private CANSparkMax mLauncherMotor;

    /** Creates a new Launcher. */
    public Launcher()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
