import lejos.nxt.*;
import lejos.robotics.Colors.Color;

public class SensorTest {
	public static void main(String[] args) 
	{
  		ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S1, ColorLightSensor.TYPE_COLORGREEN);
  		ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORFULL);
  		ColorLightSensor cs3 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN);
		while(true) {
			System.out.println("S1:" + cs1.readValue() + " S2:" + cs2.readValue() + " S3:" + cs3.readValue());
		}
	}
}
