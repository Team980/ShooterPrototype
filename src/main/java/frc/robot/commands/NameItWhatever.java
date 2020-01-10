/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PidShooter;

public class NameItWhatever extends CommandBase {
  private PidShooter pidShooter;
  private XboxController xboxController;
  
  /**
   * Creates a new NameItWhatever.
   */
  public NameItWhatever(PidShooter pidShooter1, XboxController xboxController1) {
    
    pidShooter = pidShooter1;
    xboxController = xboxController1;
   
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidShooter.enable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidShooter.setSetpoint(-40*xboxController.getY(Hand.kLeft));

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pidShooter.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
