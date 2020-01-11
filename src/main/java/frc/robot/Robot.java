/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.sql.Time;
import java.util.Timer;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.SpinSparks;
import frc.robot.Constants;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  public static final double ENCODER_DISTANCE_PER_TICK = 2 * Math.PI * Constants.SHOOTER_WHEEL_RADIUS_FEET / 2048;

  private RobotContainer m_robotContainer;
  
  public Spark spark1;
  public Spark spark2;

  public Encoder encoder;

  public NetworkTable table;

  public PIDController controller;

  public SpeedControllerGroup group;

  public XboxController stick;

  int counter = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  
    spark1 = new Spark(3);
    spark2 = new Spark(2);

    stick = new XboxController(0);

    
    SpinSparks spinSparks = new SpinSparks(spark1,  spark2);

    encoder = new Encoder(0, 1);
    encoder.setReverseDirection(true);
    encoder.setDistancePerPulse(ENCODER_DISTANCE_PER_TICK);

    encoder.setPIDSourceType(PIDSourceType.kRate);

     group = new SpeedControllerGroup(spark1, spark2);

    controller = new PIDController(.01, 0, 0.03, encoder, group);
    //controller.setF(0.005);

    //controller = new PIDController(.5, 0, 0, encoder, group);
    controller.setPIDSourceType(PIDSourceType.kRate);

    table = NetworkTableInstance.getDefault().getTable("debug");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();


    double speedRpm = 60.0 * encoder.getRate() / (2*Math.PI*Constants.SHOOTER_WHEEL_RADIUS_FEET);

    table.getEntry("shooter rpm").setNumber(speedRpm);
    table.getEntry("shooter fps").setNumber(encoder.getRate());

    table.getEntry("test counter").setNumber(counter++);
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    controller.disable();
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    table.getEntry("encoder speed").setNumber(encoder.getRate());

    controller.enable();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    //group.set(-stick.getY(Hand.kLeft));
    //controller.setSetpoint(-40* stick.getY(Hand.kLeft));
    controller.setSetpoint(20);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
