package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.sensors.SpartronicsPigeon;
import com.spartronics4915.lib.subsystems.drive.AbstractDrive;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;

import static com.spartronics4915.frc2022.Constants.Drive.*;

/**
 * An implementation of the AbstractDrive class for this year's motor configuration and size
 */
public class Drive extends AbstractDrive
{
    private DifferentialDrivePoseEstimator mPoseEstimator;

    public Drive()
    {
        super(
            kMotorConstructor.apply(
                kLeftMotorID,
                SensorModel.fromWheelDiameter(kWheelDiameter, kNativeUnitsPerRevolution),
                kLeftFollowerMotorID
            ),
            kMotorConstructor.apply(
                kRightMotorID,
                SensorModel.fromWheelDiameter(kWheelDiameter, kNativeUnitsPerRevolution),
                kRightFollowerMotorID
            ),
            new SpartronicsPigeon(kPigeonID)
        );

        // output inversion
        mLeftMotor.setOutputInverted(kLeftOutputInverted);
        mRightMotor.setOutputInverted(kRightOutputInverted);
        mLeftMotor.getFollower().setOutputInverted(kLeftFollowerOutputInverted);
        mRightMotor.getFollower().setOutputInverted(kRightFollowerOutputInverted);

        // PID
        mLeftMotor.setVelocityGains(kP, kD);
        mRightMotor.setVelocityGains(kP, kD);

        // stator current limits
        mLeftMotor.setStatorCurrentLimit(kMaxStatorCurrent);
        mRightMotor.setStatorCurrentLimit(kMaxStatorCurrent);
        mLeftMotor.getFollower().setStatorCurrentLimit(kMaxStatorCurrent);
        mRightMotor.getFollower().setStatorCurrentLimit(kMaxStatorCurrent);


    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
