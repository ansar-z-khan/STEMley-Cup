
package org.usfirst.frc.team4992.robot;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;


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
	public static JoystickButton lThumb;
	//Declare Sensors
	public static ADXRS450_Gyro gyro;
	public static Accelerometer accel;
	public static DigitalInput autoSwitch;
    Command autonomousCommand;
    SendableChooser chooser;
    SmartDashboard dash;
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
    	lThumb= new JoystickButton (controller, 8);
    //Construct Sensors
    	gyro = new ADXRS450_Gyro ();
    	accel = new BuiltInAccelerometer(); 
    	autoSwitch = new DigitalInput (4);
    	
    	gyro.reset();
    	gyro.calibrate();
    	s1.set(false);
    	s2.set(false);
        for (int i = 0; i< accelXArray.length; i++){
        	accelXArray [i] = 0;
        	accelYArray [i] = 0;
        	accelZArray [i] = 0;
        	accelXList.add(0.);
        	accelYList.add(0.);
        	accelZList.add(0.);
        }
    }
    
    public Timer driveTimer = new Timer();
    public int state;//controls the autonomous state
    boolean turningAutonomous = false;//used in the auto and teleop periodic
    boolean switch1;
    boolean switch2 = false;
    double robotSpeed = 0.65;
    double [] accelXArray = new double[20];
    ArrayList <Double> accelXList = new ArrayList<Double>(); 
    ArrayList <Double> accelYList = new ArrayList<Double>(); 
    ArrayList <Double> accelZList = new ArrayList<Double>(); 
    double [] accelYArray=  new  double[20];
    double [] accelZArray = new double[20];

    double turnX;
    double turnY;
    
    
    public void autonomousInit() {
    	switch1 = autoSwitch.get();
        gyro.reset();
		chassis.setSafetyEnabled(false);
		//Maybe use this ...  drive.setInvertedMotor
		//CC
		state = 0;
		turningAutonomous = false;
    }
    
    public void yell(String msg){
    	System.out.println("############################################");
    	System.out.println(msg);
    	System.out.println("############################################");
    }


    
    //overloaded method for driving and also converting 
	 

    
   /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	yell (""+autoSwitch);
    	double autoSpeed = 0.8;
    	if (!autoSwitch.get()){
    		chassis.arcadeDrive(autoSpeed, 0);
    		Timer.delay(0.75);
    		chassis.arcadeDrive(0, 0);
    	}
    	else {
    		chassis.arcadeDrive(autoSpeed*0.25, 0);
    		Timer.delay(0.75);
    		chassis.arcadeDrive(0, 0);
    	}
    	
    	/*
    	   double accelX = Methods.calcMovingAverage (accelXList, accel.getX());
           double accelY = Methods.calcMovingAverage (accelYList, -accel.getY());
           double accelZ = Methods.calcMovingAverage (accelZArray, accel.getZ());
           double inclineTrigger = 5.;
           
           
           //boolean isBreaching = false;
          // double angleY = Math.toDegrees (Math.asin(Math.toRadians(accelY)));
          // angleY = (Math.toDegrees(Math.atan2(accelY,accelZ)));
       //    System.out.println(angleY);
           //Sets speed in controller in same gearbox to be equal

           
           switch(state){
           
           case 0:
        	   if(switch1){
        		   state += 20;
        	   }
        	   if (switch2){
        		   state+=100;
        	   }
        	   state ++;
        	   break;
           
           case 1:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY < Math.sin(-inclineTrigger)){
        		   yell("climbing");
        		   state++;
        	   }
        	   break;


           case 2:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY > Math.sin(inclineTrigger)){
        		   yell("sinking");
        		   state++;
        	   }
        	   break;


           case 3:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY < Math.abs(Math.sin(inclineTrigger/2.))){
        		   yell("level");
        		   
        		   state++;
        	   }
        	   break;


           case 4:
        	   if (!Methods.goToHeading(0)){
        		   yell("straightening out");
        		   state++;
        	   }
        	   break;
           case 5:
        	//   chassis.arcadeDrive(0.7, 0);
           //    Timer.delay(0.1); 
           //    chassis.arcadeDrive(0, 0);
        	   
           //    if (!Methods.goToHeading(180)){
        		   yell("waiting");
        //		   state++;
   //     	   }
        	   break;


           case 6:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY < Math.sin(-inclineTrigger)){
        		   yell("climbing");
        		   state++;
        	   }
        	   break;


           case 7:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY > Math.sin(inclineTrigger)){
        		   yell("sinking");
        		   state++;
        	   }
        	   break;


           case 8:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY < Math.abs(Math.sin(inclineTrigger/2.))){
        		   yell("level");
        		   
        		   state++;
        	   }
        	   break;


           case 9:
        	   if (!Methods.goToHeading(0)){
        		   yell("straightening out");
        		   state++;
        	   }
        	   break;
           case 10:
        	   chassis.arcadeDrive(robotSpeed, 0);
               Timer.delay(0.4);             
        	   
               if (!Methods.goToHeading(180)){
        		   yell("turning about");
        		   state++;
        	   }
        	   break;
           case 21:
        	   chassis.arcadeDrive(robotSpeed, 0);
        	   if (accelY < Math.sin(-inclineTrigger)){
        		   yell("climbing");
                   Timer.delay(0.2);  
                   chassis.arcadeDrive(0, 0);
        		   state++;
        		   break;
        	   }
           case 22:
        	//   yell("im done!, and I'm at " + angleY);
        	   break;
        	  default:
        	   yell("sitting on hands");
        	   break;
           }   
           yell (""+state + ": " + accelY + ": "+ accel.getY());
           leftTalon2.set(leftTalon1.get());
           rightTalon2.set(rightTalon1.get());
           
       */    
    }


    
    
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
    		Timer.delay(1);
    	}
    	if (leftBumper.get()){
    		Methods.extendUp();
    	}
        if (rightBumper.get()){
        	Methods.extendDown();
        }
        if (!rightBumper.get() && !leftBumper.get()){
        	Methods.stopLifter();
        }
        
        double accelX = Methods.calcMovingAverage (accelXArray, accel.getX());
        double accelY = Methods.calcMovingAverage (accelYArray, accel.getY());
        double accelZ = Methods.calcMovingAverage (accelZArray, accel.getZ());
        //boolean isBreaching = false;
        
        double angleY = Math.toDegrees (Math.asin(Math.toRadians(accelY))-1.5);
        angleY = (Math.toDegrees(Math.atan2(accelY,accelZ)));
        System.out.println(angleY);
 		    double stickX2 = controller.getRawAxis(4);
 	        double stickY2 = controller.getRawAxis(5);
 	        double stickX = controller.getRawAxis(0);
 	        double stickY = controller.getRawAxis(1);
 	        
 	        if (((Math.abs(stickY2) > 0.5)||(Math.abs(stickX2) > 0.5))&& turningAutonomous == false){
 	        	turningAutonomous = true;
 	        	turnX = stickX2;
 	        	turnY = -stickY2;
 	        }
 	        
 	        if (!(Math.abs(stickY) < 0.4)||!(Math.abs(stickX) < 0.4)){//if user not steering with left stick
 	        	turningAutonomous = false;
 	        }
 	        
 	        if (turningAutonomous){
 	        	double turnAngle = Math.atan2(turnX,turnY);//fix the thumbstick angle


 		        
 	        	turnAngle *= 180/Math.PI;//convert to degrees 
 	       	//turnAngle += 180;
 	        	//need to fix the angle 
 	        	if (turnAngle<0){
 	        		turnAngle += 360;
 	        	}
 	        	
 	        	if (Math.abs(turnAngle)>360){
 		        	turnAngle = 0;
 		        }
 	        //	stick.get
 	        	
 	        	double heading = (gyro.getAngle()%360);
 	        	turnAngle = 15.*(Math.round(turnAngle/15));
 	        	if (heading<0){
 	        		heading += 360;
 	        	}
 	        	
 	        	if (Math.abs(heading)>360){
 		        	heading = 0;
 		        }


 	        	//turnAngle = stick.getDirectionDegrees();
 	        	
 	            System.out.println( heading +"        , "+turnAngle);
 	            
 	           // System.out.println(turnAngle);
 	            if (!lThumb.get()){
 	            	turningAutonomous =  Methods.goToHeading (heading,turnAngle);
 	            }
 	        }
 	        else{
 	        //	turnAngle = 0;
 	        	chassis.arcadeDrive(-stickY, -stickX);
 	        }
         
     	   





        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	comp = new Compressor (RobotMap.COMPRESSOR);
    	Methods.fillCompressor();
    	yell (""+autoSwitch);
    }
    
}
