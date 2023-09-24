package com.team766.robot.mechanisms;


import com.team766.framework.Mechanism;
import com.team766.hal.*;

public class Shooter extends Mechanism {
	
	private MotorController bottomBarMotor;
	private MotorController topBarMotor;

	private static final String bottomBarMotorConfigName = "bottomBarMotor";
	private static final String topBarMotorConfigName = "topBarMotor";

	private static final double bottomBarSpeed = 0.25d;
	private static final double topBarSpeed = 0.25d;

	public Shooter(){
		bottomBarMotor = RobotProvider.instance.getMotor(bottomBarMotorConfigName);
		topBarMotor = RobotProvider.instance.getMotor(topBarMotorConfigName);
	}

	public void shoot(){
		checkContextOwnership();

		bottomBarMotor.set(bottomBarSpeed);
		topBarMotor.set(-topBarSpeed);
	}

	public void intake(){
		checkContextOwnership();

		bottomBarMotor.set(-bottomBarSpeed);
		topBarMotor.set(topBarSpeed);
	}

	public void off(){
		checkContextOwnership();
		
		bottomBarMotor.set(0d);
		topBarMotor.set(0d);
	}
}
