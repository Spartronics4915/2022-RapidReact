package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

/**
 * Detailed description of Intake.
 */
public class Intake extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    
    /** Creates a new Intake. */
    public Intake()
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
