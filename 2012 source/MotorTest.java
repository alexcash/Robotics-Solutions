import lejos.nxt.*;
import lejos.robotics.navigation.*;
import lejos.robotics.Colors.Color;

public class MotorTest 
{
	public static void main(String[] args)
	{
		Pilot pilot = new TachoPilot(1.9527f, 4.9f, Motor.A, Motor.C);
		while(true)
			pilot.forward();
		// pilot.travel(12);
		// pilot.rotate(93);
		// pilot.travel(12);
		// pilot.rotate(93);
		// pilot.travel(12);
		// pilot.rotate(93);
		// pilot.travel(12);
		// pilot.rotate(93);
	}
}