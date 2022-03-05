package com.team766.hal.wpilib;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team766.hal.CANSpeedController;
import com.team766.logging.Category;
import com.team766.logging.Logger;
import com.team766.logging.Severity;

public class CANVictorSpeedController extends WPI_VictorSPX implements CANSpeedController {

	private double m_feedForward = 0.0;
	
	public CANVictorSpeedController(int deviceNumber) {
		super(deviceNumber);
	}

	@Override
	public void set(ControlMode mode, double value) {
		com.ctre.phoenix.motorcontrol.ControlMode ctre_mode = null;
		boolean useFourTermSet = true;
		switch (mode) {
		case PercentOutput:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;
			useFourTermSet = false;
			break;
		case Position:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Position;
			break;
		case Velocity:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Velocity;
			break;
		case Current:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Current;
			break;
		case Follower:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
			useFourTermSet = false;
			break;
		case MotionProfile:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile;
			break;
		case MotionMagic:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic;
			break;
		case MotionProfileArc:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.MotionProfileArc;
			break;
		case Disabled:
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Disabled;
			useFourTermSet = false;
			break;
		}
		if (ctre_mode == null) {
			Logger.get(Category.HAL).logRaw(
					Severity.ERROR,
					"CAN ControlMode is not translatable: " + mode);
			ctre_mode = com.ctre.phoenix.motorcontrol.ControlMode.Disabled;
		}
		if (useFourTermSet) {
			super.set(ctre_mode, value, DemandType.ArbitraryFeedForward, m_feedForward);
		} else {
			super.set(ctre_mode, value);
		}
	}

	@Override
	public void stopMotor() {
		super.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
	}

	@Override
	public double getSensorPosition() {
		return super.getSelectedSensorPosition(0);
	}

	@Override
	public double getSensorVelocity() {
		return super.getSelectedSensorVelocity(0);
	}
	
	@Override
	public void setPosition(int position){
		super.setSelectedSensorPosition(position, 0, 20);
	}

	@Override
	public void follow(CANSpeedController leader) {
		try {
			super.follow((IMotorController)leader);
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException("Victor can only follow another CTRE motor controller", ex);
		}
	}

	@Override
	public void configOpenLoopRamp(double secondsFromNeutralToFull, int timeoutMs) {
		super.configOpenloopRamp(secondsFromNeutralToFull, timeoutMs);
	}

	@Override
	public void configClosedLoopRamp(double secondsFromNeutralToFull, int timeoutMs) {
		super.configClosedloopRamp(secondsFromNeutralToFull, timeoutMs);
	}

	@Override
	public ErrorCode config_kF(int slotIdx, double value, int timeoutMs) {
		super.config_kF(slotIdx, value, timeoutMs);
		return ErrorCode.OK;
	}

	@Override
	public ErrorCode configMotionCruiseVelocity(int sensorUnitsPer100ms) {
		return super.configMotionCruiseVelocity(sensorUnitsPer100ms);
	}

	@Override
	public ErrorCode configMotionAcceleration(int sensorunitsPer100msPerSec) {
		return super.configMotionAcceleration(sensorunitsPer100msPerSec);
	}
}