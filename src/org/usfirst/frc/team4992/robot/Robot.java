
package org.usfirst.frc.team4992.robot;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

	
//public void declare(){
	//Declare Speed Controllers for Driving
	public static CANTalon leftTalon1;
	public static CANTalon leftTalon2;
	public static CANTalon rightTalon1;
	public static CANTalon rightTalon2;
	//Declare Speed Controllers for Shooting
	public static CANTalon leftShooter;
	public static CANTalon rightShooter;
	public static Relay kicker;
	//
	
	public static CANTalon liftActuator;
	//
	public static RobotDrive chassis;
	//Pneumatics
	public static Solenoid s1;
	public static Solenoid s2;
	public static Compressor comp;
	//Controls
	public static Joystick controller;
	public static JoystickButton AButton;
	public static JoystickButton BButton;
	public static JoystickButton XButton;
	public static JoystickButton leftBumper;
	public static JoystickButton rightBumper;
	//Declare Sensors
	public static Ultrasonic ranger;
	public static ADXRS450_Gyro gyro;
	public static Accelerometer accel;

    Command autonomousCommand;
    SendableChooser chooser;

//}	


    public void robotInit() {
    //Construct Speed Controllers
    	leftTalon1 = new CANTalon (RobotMap.LEFT_TALON_1);
    	leftTalon2 = new CANTalon (RobotMap.LEFT_TALON_2);
    	rightTalon1 = new CANTalon (RobotMap.RIGHT_TALON_1);
    	rightTalon2 = new CANTalon (RobotMap.RIGHT_TALON_2);
    	chassis = new RobotDrive(leftTalon1, rightTalon1);
    	//
    	kicker = new Relay (RobotMap.SPIKE);
    	leftShooter = new CANTalon (RobotMap.LEFT_SHOOTER);
    	rightShooter = new CANTalon (RobotMap.RIGHT_SHOOTER);
    	liftActuator = new CANTalon (RobotMap.LIFT_ACTUATOR);
    	
    //Construct Pneumatic Components
    	s1 = new Solenoid (RobotMap.SOLENOID_1);
    	s2 = new Solenoid (RobotMap.SOLENOID_2);
    	
    //Controls
    	controller = new Joystick (RobotMap.CONTROLLER);
    	AButton = new JoystickButton (controller, RobotMap.A_BUTTON);
    	BButton = new JoystickButton (controller, RobotMap.B_BUTTON);
    	XButton = new JoystickButton (controller, RobotMap.X_BUTTON);
    	leftBumper = new JoystickButton (controller, RobotMap.LEFT_BUMPER);
    	rightBumper = new JoystickButton (controller, RobotMap.RIGHT_BUMPER);
    //Construct Sensors
    	ranger = new Ultrasonic (RobotMap.ULTRASONIC_PING,RobotMap.ULTRASONIC_ECHO);
    	gyro = new ADXRS450_Gyro ();
    	accel = new BuiltInAccelerometer(); 
    	gyro.reset();
    	gyro.calibrate();
    	
    	chooser = new SendableChooser();
		//chooser.addDefault ("Do Nothing", Methods.shootBall())
       // chooser.addObject("My Auto", new ShootBall());
      //SmartDashboard.putData("Auto mode", chooser);    	
    }
    
    public void autonomousInit() {
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopPeriodic() {
    	Methods.oneStickDrive();
    	if (AButton.get()){
    		Methods.intakeBall();
    	}
    	if (BButton.get()){
    		Methods.shootBall();
    	}
    	if (XButton.get()){
    		Methods.toggleLifter();
    	}
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	comp = new Compressor (RobotMap.COMPRESSOR);
    Methods.fillCompressor();
    
    
    }
    
}
