package com.spartronics4915.lib.hardware.motors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.spartronics4915.lib.hardware.CANCounter;
import com.spartronics4915.lib.util.Logger;

import edu.wpi.first.wpilibj.RobotBase;

public class SpartronicsFalcon implements SpartronicsMotor
{
    private static final int kVelocitySlotIdx = 0;
    private static final int kPositionSlotIdx = 1;

    // This conversion could go into the sensor model, but the per 100ms thing is
    // Talon-only, so it's not worth it.
    private static final double kMetersPer100msToMetersPerSecond = 10;
    private static final double kMetersPerSecondToMetersPer100ms = 1
        / kMetersPer100msToMetersPerSecond;

    private final TalonFX mFalconFX;
    private final TalonFX mFollower;
    private final SpartronicsEncoder mEncoder;
    private final SensorModel mSensorModel;
    private final boolean mHadStartupError;

    private boolean mBrakeMode = false;
    /** Volts */
    private double mVoltageCompSaturation = 12.0;
    /** Native units/sec, converted to meters on get and set */
    private double mMotionProfileCruiseVelocity = 0.0;
    /** Native units/sec^2, converted to meters on get and set */
    private double mMotionProfileAcceleration = 0.0;
    private boolean mUseMotionProfileForPosition = false;

    private ControlMode mLastControlMode = null;

    public class SpartronicsFalconEncoder implements SpartronicsEncoder
    {

        @Override
        public double getVelocity()
        {
            return mSensorModel.toCustomUnits(mFalconFX.getSelectedSensorVelocity())
                * kMetersPer100msToMetersPerSecond;
        }

        @Override
        public double getPosition()
        {
            return mSensorModel.toCustomUnits(mFalconFX.getSelectedSensorPosition());
        }

        @Override
        public void setPhase(boolean isReversed)
        {
            mFalconFX.setSensorPhase(isReversed);
        }

        @Override
        public boolean setPosition(double position)
        {
            mFalconFX.getSensorCollection()
                .setIntegratedSensorPosition(mSensorModel.toNativeUnits(position), 0);
            return true;
        }
    }

    public static SpartronicsMotor makeMotor(int deviceNumber, SensorModel sensorModel,
        FeedbackDevice feedbackDevice)
    {
        if (RobotBase.isSimulation())
        {
            return new SpartronicsSimulatedMotor(deviceNumber);
        }
        return new SpartronicsFalcon(new TalonFX(deviceNumber), sensorModel, feedbackDevice, null);
    }

    public static SpartronicsMotor makeMotor(int deviceNumber, SensorModel sensorModel)
    {
        return makeMotor(deviceNumber, sensorModel, FeedbackDevice.QuadEncoder);
    }

    public static SpartronicsMotor makeMotor(int deviceNumber)
    {
        return makeMotor(deviceNumber, SensorModel.fromMultiplier(1));
    }

    public static SpartronicsMotor makeMotor(int deviceNumber, SensorModel sensorModel,
        int followerDeviceNumber)
    {
        if (RobotBase.isSimulation())
        {
            return new SpartronicsSimulatedMotor(deviceNumber, followerDeviceNumber);
        }
        var master = new TalonFX(deviceNumber);
        var follower = new TalonFX(followerDeviceNumber);
        follower.follow(master);

        return new SpartronicsFalcon(master, sensorModel, FeedbackDevice.QuadEncoder, follower);
    }

    private SpartronicsFalcon(TalonFX talon, SensorModel sensorModel, FeedbackDevice encoder,
        TalonFX follower)
    {
        mFalconFX = talon;
        mFollower = follower;
        mSensorModel = sensorModel;

        ErrorCode err = mFalconFX.configSelectedFeedbackSensor(encoder, 0, 5);
        if (err != ErrorCode.OK)
        {
            Logger.error("TalonSRX on with ID " + mFalconFX.getDeviceID()
                + " returned a non-OK error code on sensor configuration... Is the encoder plugged in?");
            mHadStartupError = true;
        }
        else
        {
            mHadStartupError = false;
        }
        CANCounter.addDevice(mHadStartupError);

        mEncoder = new SpartronicsFalconEncoder();

        mFalconFX.configFactoryDefault();
        mFalconFX.configVoltageCompSaturation(mVoltageCompSaturation);
        mFalconFX.enableVoltageCompensation(true);
    }

    @Override
    public SpartronicsEncoder getEncoder()
    {
        return mEncoder;
    }

    @Override
    public boolean hadStartupError()
    {
        return false;//mHadStartupError; Change back when comp season is over!
    }

    @Override
    public double getVoltageOutput()
    {
        return mFalconFX.getMotorOutputVoltage();
    }

    @Override
    public boolean getOutputInverted()
    {
        return mFalconFX.getInverted();
    }

    @Override
    public void setOutputInverted(boolean inverted)
    {
        mFalconFX.setInverted(inverted);
    }

    @Override
    public boolean getBrakeMode()
    {
        return mBrakeMode;
    }

    @Override
    public void setBrakeMode(boolean mode)
    {
        mBrakeMode = mode;
        mFalconFX.setNeutralMode(mode ? NeutralMode.Brake : NeutralMode.Coast);
    }

    @Override
    public double getVoltageCompSaturation()
    {
        return mVoltageCompSaturation;
    }

    @Override
    public void setVoltageCompSaturation(double voltage)
    {
        mVoltageCompSaturation = voltage;
        mFalconFX.configVoltageCompSaturation(mVoltageCompSaturation);
        mFalconFX.enableVoltageCompensation(true);
    }

    @Override
    public double getMotionProfileCruiseVelocity()
    {
        return mSensorModel.toCustomUnits(mMotionProfileCruiseVelocity)
            * kMetersPer100msToMetersPerSecond;
    }

    @Override
    public void setMotionProfileCruiseVelocity(double velocityMetersPerSecond)
    {
        mMotionProfileCruiseVelocity = mSensorModel
            .toNativeUnits(velocityMetersPerSecond * kMetersPerSecondToMetersPer100ms);
        mFalconFX.configMotionCruiseVelocity((int) mMotionProfileCruiseVelocity);
    }

    @Override
    public double getMotionProfileMaxAcceleration()
    {
        return mSensorModel.toCustomUnits(mMotionProfileAcceleration)
            * kMetersPer100msToMetersPerSecond;
    }

    @Override
    public void setMotionProfileMaxAcceleration(double accelerationMetersPerSecondSq)
    {
        mMotionProfileAcceleration = mSensorModel
            .toNativeUnits(accelerationMetersPerSecondSq * kMetersPerSecondToMetersPer100ms);
        mFalconFX.configMotionAcceleration((int) mMotionProfileAcceleration);
    }

    @Override
    public void setUseMotionProfileForPosition(boolean useMotionProfile)
    {
        mUseMotionProfileForPosition = useMotionProfile;
    }

    @Override
    public void setPercentOutput(double dutyCycle, double arbitraryFeedForwardVolts)
    {
        mLastControlMode = ControlMode.PercentOutput;
        mFalconFX.set(ControlMode.PercentOutput, dutyCycle, DemandType.ArbitraryFeedForward,
            arbitraryFeedForwardVolts / mVoltageCompSaturation);
    }

    @Override
    public void setPercentOutput(double dutyCycle)
    {
        setPercentOutput(dutyCycle, 0.0);
    }

    @Override
    public void setVelocity(double velocityMetersPerSecond, double arbitraryFeedForwardVolts)
    {
        if (mLastControlMode != ControlMode.Velocity)
        {
            mFalconFX.selectProfileSlot(kVelocitySlotIdx, 0);
            mLastControlMode = ControlMode.Velocity;
        }

        double velocityNative = mSensorModel
            .toNativeUnits(velocityMetersPerSecond * kMetersPerSecondToMetersPer100ms);
        mFalconFX.set(ControlMode.Velocity, velocityNative, DemandType.ArbitraryFeedForward,
            arbitraryFeedForwardVolts / mVoltageCompSaturation);
    }

    @Override
    public void setVelocityGains(double kP, double kD)
    {
        setVelocityGains(kP, 0, kD, 0);
    }

    @Override
    public void setVelocityGains(double kP, double kI, double kD, double kF)
    {
        mFalconFX.config_kP(kVelocitySlotIdx, kP);
        mFalconFX.config_kI(kVelocitySlotIdx, kI);
        mFalconFX.config_kD(kVelocitySlotIdx, kD);
        mFalconFX.config_kF(kVelocitySlotIdx, kF);
    }

    @Override
    public void setPosition(double positionMeters)
    {
        if (mLastControlMode != ControlMode.Position)
        {
            mFalconFX.selectProfileSlot(kPositionSlotIdx, 0);
            mLastControlMode = ControlMode.Position;
        }

        double positionNative = mSensorModel.toNativeUnits(positionMeters);
        mFalconFX.set(mUseMotionProfileForPosition ? ControlMode.MotionMagic : ControlMode.Position,
            positionNative);
    }

    @Override
    public void setPositionGains(double kP, double kD)
    {
        setPositionGains(kP, 0, kD, 0);
    }

    @Override
    public void setPositionGains(double kP, double kI, double kD, double kF)
    {
        mFalconFX.config_kP(kPositionSlotIdx, kP);
        mFalconFX.config_kI(kPositionSlotIdx, kI);
        mFalconFX.config_kD(kPositionSlotIdx, kD);
        mFalconFX.config_kF(kPositionSlotIdx, kF);
    }

    @Override
    public SensorModel getSensorModel()
    {
        return mSensorModel;
    }

    @Override
    public void setVelocity(double velocityMetersPerSecond)
    {
        setVelocity(velocityMetersPerSecond, 0.0);
    }

    @Override
    public void setNeutral()
    {
        mFalconFX.set(ControlMode.Disabled, 0.0, DemandType.Neutral, 0.0);
    }

    @Override
    public double getOutputCurrent()
    {
        return mFalconFX.getStatorCurrent();
    }

    @Override
    public SpartronicsMotor getFollower()
    {
        return new SpartronicsFalcon(mFollower, mSensorModel, FeedbackDevice.None, null);
    }

    @Override
    public int getDeviceNumber()
    {
        return mFalconFX.getDeviceID();
    }

    @Override
    public void setSoftLimits(double forwardLimitCustomUnits, double reverseLimitCustomUnits)
    {
        mFalconFX.configForwardSoftLimitEnable(true);
        mFalconFX.configReverseSoftLimitEnable(true);

        mFalconFX.configForwardSoftLimitThreshold(
            (int) Math.round(mSensorModel.toNativeUnits(forwardLimitCustomUnits)));
        mFalconFX.configReverseSoftLimitThreshold(
            (int) Math.round(mSensorModel.toNativeUnits(reverseLimitCustomUnits)));
    }

    @Override
    public void setSupplyCurrentLimit(int limitAmps, double maxTimeAtLimit)
    {
        mFalconFX.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, limitAmps, limitAmps, maxTimeAtLimit));
    }

}
