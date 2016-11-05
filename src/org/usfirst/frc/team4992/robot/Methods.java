package org.usfirst.frc.team4992.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

public class Methods extends Robot {

	public Methods() {
		
	}
	//Driving Methods
	public static void twoStickDrive(){
		chassis.drive(-controller.getRawAxis(RobotMap.RIGHT_STICK_Y), controller.getRawAxis(RobotMap.LEFT_STICK_X) );
		leftTalon2.set(leftTalon1.get());
		rightTalon2.set(rightTalon1.get());
		
	}
	public static void oneStickDrive (){
		chassis.drive(-controller.getRawAxis(RobotMap.LEFT_STICK_Y), controller.getRawAxis(RobotMap.LEFT_STICK_X));
		leftTalon2.set(leftTalon1.get());
		rightTalon2.set(rightTalon1.get());
		
	}
	public static void triggerDrive(){
		double triggerVal = controller.getRawAxis(RobotMap.LEFT_TRIGGER)-controller.getRawAxis(RobotMap.RIGHT_TRIGGER);
		chassis.drive(triggerVal,controller.getRawAxis(RobotMap.LEFT_STICK_X));
		leftTalon2.set(leftTalon1.get());
		rightTalon2.set(rightTalon1.get());		
	}
	//Pneumatics Methods
	private static boolean isExtended = false;
	public static void toggleLifter(){
		if (isExtended){
			retract();
		}
		else if (!isExtended){
			extend();
		}
	}
    private static void retract (){
    	s2.set(true);
    	s1.set(false);
    	isExtended = false;
    	System.out.println("Lifter Retracted");
    	
    }
    private static void extend (){
    	s1.set(true);
    	s2.set(false);
    	isExtended = true;
    	System.out.println("Lifter Extended");
    }
    public static void fillCompressor(){
    	comp.setClosedLoopControl(true);
    }
	//Extend Lift
	public static void extendUp(){
		liftActuator.set(0.1);
	}
	public static void extendDown(){
		liftActuator.set(-0.1);
	}
	//Shooting Methods
	private static void shoot(){
		leftShooter.set(-1);
		rightShooter.set(1);
	}
	private static void intake(){
		leftShooter.set(1);
		rightShooter.set(-1);
	}
	private static void stopShooter(){
		leftShooter.set(0);
		rightShooter.set(0);
	}
	private static void extendKicker (){
		kicker.set(Relay.Value.kForward);
//		kickerExtended = true;
	}
	private static void retractKicker(){
		kicker.set(Relay.Value.kReverse);
//		kickerExtended = false;
	}
	public static void shootBall(){
		shoot();
		Timer.delay(1);
		extendKicker();
		Timer.delay(2);
		stopShooter();
		retractKicker();
		System.out.println("Ball Released");
	}
	public static void intakeBall (){
		retractKicker();
		intake();
		Timer.delay(4);
		stopShooter();	
	}

}