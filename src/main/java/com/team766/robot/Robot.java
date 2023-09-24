package com.team766.robot;

import com.team766.robot.mechanisms.*;

public class Robot {
	// Declare mechanisms here

	public static Shooter shooter;
	public static void robotInit() {
		// Initialize mechanisms here
		shooter = new Shooter();
	}
}
