package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Conveyor.*;

import javax.lang.model.util.ElementScanner6;

import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.spartronics4915.frc2022.Constants;

public class ConveyorCommands {
    private final Conveyor mConveyor;
    private final Intake mIntake;
    
    public ConveyorCommands(Conveyor conveyor, Intake intake) {
        mConveyor = conveyor;
        mIntake = intake;
    }

    public class ToggleFillConveyors extends CommandBase {
        public ToggleFillConveyors() {
            addRequirements(mConveyor);
        }

        @Override
        public void initialize() {
            if (mConveyor.anyMotorsRunning())
                mConveyor.setMotors(0, 0);
            else
                mConveyor.setMotors(1, 0);
        }
        
        @Override
        public boolean isFinished() {
            return true;
        }
    }

    public class TryToggleConveyor extends ConditionalCommand{
        public TryToggleConveyor() {
            super(
                new ConditionalCommand(
                    new ToggleFillConveyors(),
                    new SequentialCommandGroup(
                        new WaitCommand(kStopConveyorsDelay),
                        new ToggleFillConveyors()
                    ),
                    mConveyor::hasTopBall
                ),
                new ToggleFillConveyors(),
                mIntake::getToggleState
            );
        }
    }

    public class ReverseBoth extends CommandBase {
        public ReverseBoth() {
            addRequirements(mConveyor, mIntake);
        }

        @Override
        public void initialize(){
            mConveyor.setMotors(-1, -1);
            mIntake.startIntake(true);
        }

        @Override
        public void end(boolean interrupted) {
            mConveyor.setMotors(1, 0);
            mIntake.startIntake(false);
        }
    }

    public class ReverseBottom extends CommandBase {
        public ReverseBottom(){
            addRequirements(mConveyor, mIntake);
        }

        @Override
        public void initialize(){
            mConveyor.setMotors(-1, 0);
            mIntake.startIntake(true);
        }

        @Override
        public void end(boolean interrupted){
            mConveyor.setMotors(1, 0);
            mIntake.startIntake(false);
        }
    }


    public class ShootWith1Ball extends SequentialCommandGroup {
        public ShootWith1Ball() {
            addCommands(
                new InstantCommand(() -> mConveyor.setMotors(0, 1)),
                new WaitCommand(kTopConveyorTime),
                new InstantCommand(() -> mConveyor.setMotors(0, 0))
            );

            addRequirements(mConveyor);
        }
    }

    public class ShootWith2Balls extends SequentialCommandGroup {
        public ShootWith2Balls() {
            addCommands(
                new InstantCommand(() -> mConveyor.setMotors(1, 1)),
                new WaitCommand(kTopConveyorTime + kBottomConveyorTime),
                new InstantCommand(() -> mConveyor.setMotors(0, 0))
            );

            addRequirements(mConveyor);
        }
    }

    public class Shoot1 extends ConditionalCommand {
        public Shoot1(){
            super(
                new ShootWith2Balls(),
                new ShootWith1Ball(),
                mConveyor::hasTopBall
            );
        }
    }
    
    public class ShootAll extends SequentialCommandGroup {
        public ShootAll(){
            addCommands(
                new InstantCommand(() -> mConveyor.setMotors(1, 1)),
                new WaitCommand(kTopConveyorTime + kBottomConveyorTime),
                new InstantCommand(() -> mConveyor.setMotors(0, 0))
            );

            addRequirements(mConveyor);
        }
    }
}
