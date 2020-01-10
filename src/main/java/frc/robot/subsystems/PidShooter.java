/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class PidShooter extends PIDSubsystem {

  public Encoder encoder;

  public SpeedControllerGroup group;

  
  /**
   * Creates a new PidShooter.
   */
  public PidShooter() {

    super(new PIDController(0.0069, 0, 0));

    Spark spark1 = new Spark(3);
    Spark spark2 = new Spark(2);
    group = new SpeedControllerGroup(spark1, spark2);

    encoder = new Encoder(0, 1);

    encoder.setReverseDirection(true);
    encoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_TICK);//probobly in ft/sec
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    group.pidWrite(output);
    
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return encoder.getRate();
  }
}
