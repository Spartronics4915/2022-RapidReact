package com.spartronics4915.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.spartronics4915.lib.hardware.motors.SpartronicsEncoder;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.hardware.motors.SpartronicsFalcon;
import com.spartronics4915.lib.hardware.motors.SpartronicsSimulatedMotor;
//import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

import static com.spartronics4915.frc2022.Constants.Launcher.*;


/**
 * Detailed description of Launcher.
 */
public class Launcher extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private SpartronicsMotor mFlywheelMotor;
    private SpartronicsEncoder mFlywheelEncoder;
    private SpartronicsMotor mSpinMotor;
    private boolean mLauncherToggle = false;
    // public double FlywheelRPS = 0;
    // public boolean editRPS = false;
    // public boolean enableFlywheel = false;

    /** Creates a new Launcher. */
    public Launcher()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mFlywheelMotor = SpartronicsMax.makeMotor(kFlywheelMotorId);
            mSpinMotor = SpartronicsMax.makeMotor(kSpinMotorId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);

       // mFlywheelMotor.setVelocityGains(kP, 0, 0, 0); // ref value is 0.00036
        mFlywheelMotor.setVelocityGains(Flywheel.kP, 0, 0, 0);//, Flywheel.kS, Flywheel.kV, Flywheel.kA);
 
        mFlywheelMotor.setOutputInverted(false);
        mFlywheelEncoder = mFlywheelMotor.getEncoder();
    }

    public boolean toggleLauncher() {
        mLauncherToggle = !mLauncherToggle;
        return mLauncherToggle;
    }
    public double getSliderValue() {
        return SmartDashboard.getNumber("Launcher/flywheelRPSSlider", 0);
    }
    public void setMotorSpeed(double launcherVelocity) {
        mFlywheelMotor.setVelocity(launcherVelocity);
        // }
        // else if (mLauncherToggle=false) {
        //     mFlywheelMotor.setVelocity(0);
        // }
        // logInfo(Double.toString(SmartDashboard.getNumber("Launcher/flywheelRPSSlider", 3)));
    }
    public void setToggleTrue() {
        mLauncherToggle = true;
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        // if (enableFlywheel = true)
        // mFlywheelMotor.setVelocity(FlywheelRPS);
        //logInfo(Double.toString(SmartDashboard.getNumber("Launcher/flywheelRPSSlider", 3)));
        //mFlywheelMotor.setVelocity(SmartDashboard.getNumber("/SmartDashboard/Launcher/flywheelRPSSlider", 0));
        SmartDashboard.putNumber("Launcher/flywheelRPS", mFlywheelMotor.getEncoder().getVelocity());
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
