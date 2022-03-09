package com.spartronics4915.frc2022.commands;

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

    public class FillConveyors extends CommandBase {

        public FillConveyors()
        {
            addRequirements(mConveyor); // Declares subsystem dependencies
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mConveyor.setMotors(1, 0);
        }

        @Override
        public boolean isFinished(){
            return mConveyor.hasTopBall();
        }

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
            mConveyor.setMotors(0, 0);
        }
    }

    public class ReverseBoth extends CommandBase {
        public ReverseBoth(){
            addRequirements(mConveyor, mIntake);
        }

        @Override
        public void initialize(){
            mConveyor.setMotors(-1, -1);
            mIntake.startIntake(true);
        }

        @Override
        public void end(boolean interrupted){
            mConveyor.setMotors(0,0);
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
            mConveyor.setMotors(0,0);
            mIntake.startIntake(false);
        }
    }


    public class ShootWith1Ball extends SequentialCommandGroup {
        public ShootWith1Ball() {
            addCommands(
                new InstantCommand(() -> mConveyor.setMotors(0, 1)),
                new WaitCommand(Constants.Conveyor.kTopConveyorTime),
                new FillConveyors()
            );

            addRequirements(mConveyor);
        }
    }

    public class ShootWith2Balls extends SequentialCommandGroup {
        public ShootWith2Balls() {
            addCommands(
                new InstantCommand(() -> mConveyor.setMotors(0, 1)),
                new WaitCommand(Constants.Conveyor.kTopConveyorTime),
                new InstantCommand(() -> mConveyor.setMotors(1, 0)),
                new WaitCommand(Constants.Conveyor.kBottomConveyorTime),
                new FillConveyors()
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
                new WaitCommand(Constants.Conveyor.kTopConveyorTime + Constants.Conveyor.kBottomConveyorTime),
                new InstantCommand(() -> mConveyor.setMotors(0, 0)),
                new FillConveyors()
            );

            addRequirements(mConveyor);
        }
    }
}
