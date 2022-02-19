package com.spartronics4915.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.spartronics4915.lib.hardware.motors.SpartronicsEncoder;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;
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

    // Subsystem methods - actions the robot can take - should be placed here.
    // public void testStartFlywheel() {
    //     mFlywheelMotor.setVelocity(FlywheelRPS);
    //     // mFlywheelMotor.setPercentOutput(0.5);
    // }
    // public void testStopFlywheel() {
    //     mFlywheelMotor.setVelocity(0);
    //     // mFlywheelMotor.setBrakeMode(true);
    // }
    // public void testStartSpin() {
    //     mSpinMotor.setPercentOutput(kSpinMotorSpeed);
    // } 
    // public void testStopSpin() {
    //     mSpinMotor.setPercentOutput(0);
    // }
    // public void enableFlwheel() {
    //     enableFlywheel = true;
    // }
    // public void disableFlywheel() {
    //     enableFlywheel = false;
    // }
    // public void enableEditRPS() {
    //     editRPS = true;
    // }
    // public void disableEditRPS() {
    //     editRPS = false;
    // }
    // public void resetRPS() {
    //     if (editRPS = true) {
    //         FlywheelRPS = 5;
    //     }
    // }
    // public void increaseRPS() {
    //     if (editRPS = true) {
    //         FlywheelRPS = FlywheelRPS + 0.1;
    //     }
    // }
    // public void decreaseRPS() {
    //     if (editRPS = true) {
    //         FlywheelRPS = FlywheelRPS - 0.1;
    //     }
    // }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        // if (enableFlywheel = true)
        // mFlywheelMotor.setVelocity(FlywheelRPS);
        mFlywheelMotor.setVelocity((double) SmartDashboard.getNumber("/SmartDashboard/Launcher/flywheelRPSSlider",  1));
        SmartDashboard.putNumber("Launcher/flywheelRPS", mFlywheelMotor.getEncoder().getVelocity());
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}