/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  XboxController driver = new XboxController(0);
  Talon in = new Talon(1);
  Talon up = new Talon(2);
  Spark shootL = new Spark(4);
  Spark shootR = new Spark(5);
  VictorSP rDrive1 = new VictorSP(6);
  VictorSP rDrive2 = new VictorSP(7);
  VictorSP lDrive1 = new VictorSP(8);
  VictorSP lDrive2 = new VictorSP(9);
  SpeedControllerGroup rDrive = new SpeedControllerGroup(rDrive1, rDrive2);
  SpeedControllerGroup lDrive = new SpeedControllerGroup(lDrive1, lDrive2);
  Talon hang = new Talon(3);
  
  DifferentialDrive drive = new DifferentialDrive(rDrive, lDrive);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */

  //drive train 
  @Override
  public void teleopPeriodic() {
    drive.tankDrive(driver.getY(Hand.kRight), driver.getY(Hand.kLeft));

    //gun (shooter)
    if(driver.getAButton()) {
      shoot(1);
    } else {
      shoot(0);
    }

    //hook 
    if(driver.getBumper(Hand.kLeft)) {
      hang.set(1);
    } else if(driver.getBumper(Hand.kRight)) {
      hang.set(-1);
    } else {
      hang.set(0);
    }

    //succ (intake)
    if(driver.getXButton()) {
      in.set(.5);
    } else {
      in.set(0);
    }

    //rail
    if(driver.getBButton()) {
      up.set(.5);
    } else {
      up.set(0);
    }

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void shoot(double n) {
    shootL.set(-n);
    shootR.set(n);
  }
}
