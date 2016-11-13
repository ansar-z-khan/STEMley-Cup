package org.usfirst.frc.team4992.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import java.util.ArrayList
;public class Methods extends Robot {

	public Methods() {
		
	}
	 public static boolean  goToHeading (double target){
		  int hedge = 15;//amount of degrees to stop short of turning
        // System.out.println(gyro.getAngle());
		 double heading = (gyro.getAngle()%360);
       if (heading<0){
     		heading += 360;
     	}
     	
     	if (Math.abs(heading)>360){
	        	heading = 0;
	        }

		  int turn = (int) (target - heading);
		  if (turn > 180){turn -=360;}
		  else if (turn < -180){turn +=360;}
	        if (turn > 0 && turn < 180 ){
	        	chassis.arcadeDrive(0.,-0.8);
	        }
	        else {
	            chassis.arcadeDrive(0., 0.8);
	        }
       
		  return (Math.abs(turn)>=hedge);
		  
	    }

	 public static double calcMovingAverage (double[] data, double value){
	       double mean = 0;
	       for (int i = 1; i<data.length; i++){
	           data [i-1] = data [i];
	           mean += data [i];
	       }
	       data [data.length-1] = value;
	       mean += value;
	       
	       return mean / (double) data.length;
	   }


	 public static double calcMovingAverage (ArrayList<Double> data, double value){
	       double mean = 0;
	       
	       data.add(value);
	       data.remove(0);
	       for(Double d: data ){   
	    	   mean+=d;
	       }
	      
	       return mean / (double) data.size();
	   }
	 
	  public static boolean  goToHeading (double heading, double target){
		  int hedge = 15;//amount of degrees to stop short of turning
         // System.out.println(gyro.getAngle());


//		  if (Math.abs(heading-gyro.getAngle())>=hedge){
		  int turn = (int) (target - heading);
		  if (turn > 180){turn -=360;}
		  else if (turn < -180){turn +=360;}
	        if (turn > 0 && turn < 180 ){
	        	chassis.arcadeDrive(0.,-0.8);
	        }
	        else {
	            chassis.arcadeDrive(0., 0.8);
	        }
	//	  }
	    	
        //    System.out.println( "********************************************************************");	
       //     System.out.println( turn +"        , "+target);
            System.out.println( "********************************************************************");	
        
		  return (Math.abs(turn)>=hedge);
		  
	    }





	//Driving Methods
	public static void twoStickDrive(){
		chassis.drive(-controller.getRawAxis(RobotMap.RIGHT_STICK_Y), controller.getRawAxis(RobotMap.LEFT_STICK_X) );
		leftTalon2.set(leftTalon1.get());
		rightTalon2.set(rightTalon1.get());
		
	}
	public static void oneStickDrive (){
		///chassis.arcadeDrive(-controller.getRawAxis(RobotMap.LEFT_STICK_Y), controller.getRawAxis(RobotMap.LEFT_STICK_X));
		chassis.arcadeDrive(-controller.getRawAxis(RobotMap.LEFT_STICK_Y), -controller.getRawAxis(RobotMap.LEFT_STICK_X));
		leftTalon2.set(leftTalon1.get());
		rightTalon2.set(rightTalon1.get());
		 
	}

	public static void triggerDrive(){
		double triggerVal = controller.getRawAxis(RobotMap.LEFT_TRIGGER)-controller.getRawAxis(RobotMap.RIGHT_TRIGGER);
		chassis.drive(controller.getRawAxis(RobotMap.LEFT_STICK_Y),controller.getRawAxis(RobotMap.LEFT_STICK_X));
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
		liftActuator.set(1);
	//	Timer.delay(0.1);
	//	liftActuator.set(0);
		
	}
	public static void extendDown(){
		liftActuator.set(-1);
	//	Timer.delay(0.1);
	//	liftActuator.set(0);		
	}
	public static void stopLifter (){
		liftActuator.set(0);
	}
	//Shooting Methods
	private static void intake(){
		leftShooter.set(-0.3);
		rightShooter.set(0.3);
	}
	private static void shoot(){
		leftShooter.set(1);
		rightShooter.set(-1);
	}
	private static void stopShooter(){
		leftShooter.set(0);
		rightShooter.set(0);
	}
	private static void extendKicker (){
		kicker.set(Relay.Value.kOn);//		kickerExtended = true;
	}
	private static void retractKicker(){
		kicker.set(Relay.Value.kOff);
//		kickerExtended = false;
	}
	public static void shootBall(){
		shoot();
		Timer.delay(1);
		extendKicker();
		Timer.delay(0.5);
		retractKicker();
		Timer.delay(2);
		stopShooter();
		System.out.println("Ball Released");
	}
	public static void intakeBall (){
		intake();
		Timer.delay(4);
		stopShooter();	
	}


}