/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinSparks extends CommandBase {
  /**
   * Creates a new SpinSparks.
   */

  private static double SPIN_SPEED = 1.0;


  private Spark spark1;
  private Spark spark2;


  public SpinSparks(Spark spark1, Spark spark2) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.spark1 = spark1;
    this.spark2 = spark2;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("start");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    spark1.set(SPIN_SPEED);
    spark2.set(SPIN_SPEED);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    spark1.stopMotor();
    spark2.stopMotor();

    System.out.println("stop");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
