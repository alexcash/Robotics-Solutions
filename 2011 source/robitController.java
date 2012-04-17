import lejos.nxt.*;
import lejos.robotics.navigation.*;
import java.io.File;

public class robitController {
  Pilot pilot = new TachoPilot(1.9527f, 4.9f, Motor.A, Motor.C);
  IntStack stackx = new IntStack(56);
  IntStack stacky = new IntStack(56);
  int curx, cury = 0;
  int face = 2;// 0 is north, 1 east, 2 south, 3 west
  boolean found = false;
  int DIST = 12;
  float BACKUP = -3.5f;
  int DEG = 90;
  
  ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S1, ColorLightSensor.TYPE_COLORGREEN);
  ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORFULL);
  ColorLightSensor cs3 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN);

  public void startAlign()
  { 
  	pilot.setMoveSpeed(5);
  	//pilot.setTurnSpeed(80);
  	turn(3);
  	//Motor.A.forward();
	//Motor.C.forward();
	pilot.travel(DIST, true);
	while(pilot.isMoving()){
	if(cs1.readValue() > 30){
  			pilot.stop();
  			align();}
  	if(cs3.readValue() > 30){
  			pilot.stop();
  			align();}}
  	//while(cs1.readValue() < 30 || cs3.readValue() < 30)
  	//{
  	//	if(cs1.readValue() > 30)
  	//		Motor.A.stop();
  	//	if(cs3.readValue() > 30)
  	//		Motor.C.stop();
  	//}
  	//pilot.stop();
  	//pilot.travel(BACKUP);
  	turn(0);
	pilot.travel(DIST, true);
	while(pilot.isMoving()){
	if(cs1.readValue() > 30){
  			pilot.stop();
  			align();}
  	if(cs3.readValue() > 30){
  			pilot.stop();
  			align();}}
  	//Motor.A.forward();
	//Motor.C.forward();
	//pilot.travel(DIST, true);
  	//while(cs1.readValue() < 30 || cs3.readValue() < 30)
  	//{
  	//	if(cs1.readValue() > 30)
  	//		Motor.A.stop();
  	//	if(cs3.readValue() > 30)
  	//		Motor.C.stop();
  	//}
  	//pilot.stop();
  	//pilot.travel(BACKUP);
  	turn(1);
  }
  
  public void align()
  {
  	pilot.setMoveSpeed(4);
  	//Motor.A.forward();
  	//Motor.C.forward();
  	//pilot.travel(DIST, true);
  	if(cs1.readValue() < 30)
  		Motor.A.forward();
  	if(cs3.readValue() < 30)
  		Motor.C.forward();
  	while(cs1.readValue() < 30 || cs3.readValue() < 30)
  	{
  		if(cs1.readValue() > 30)
  			Motor.A.stop();
  		if(cs3.readValue() > 30)
  			Motor.C.stop();
  		if(!Motor.A.isMoving() && !Motor.C.isMoving())
  			break;
  	}
  	pilot.stop();
  	pilot.setMoveSpeed(5);
  	pilot.travel(BACKUP);
  }
  public boolean move()
  {
  	//pilot.reset();
  	//pilot.forward();
  	pilot.travel(DIST, true);
	//while(pilot.getTravelDistance() < DIST){
	while(pilot.isMoving()){
		if(cs1.readValue() > 30  || cs3.readValue() > 30) 
		{
			pilot.stop();
			return true;//true, we want to mark a wall on a map, not a door.
		}else{
		if(found == false && isFound()){
			boolean found1 = isFound();
			pause(50);
			boolean found2 = isFound();
			pause(50);
			boolean found3 = isFound();
			if(found1 && found2 && found3)found = true;
		}}
	}
	stackx.push(getX());
	stacky.push(getY()); 
	changeCoor();
	//pilot.stop();
	return false;//false, we will not mark a wall on the map. but we will mark a door.
  }
  
   public void moveBack()
  {
  	pilot.reset();
  	pilot.forward();
  	//pilot.travel(DIST, true);
	while(pilot.getTravelDistance() < DIST){
	//while(pilot.isMoving()){
		if(cs1.readValue() > 30)
		{
			pilot.rotate(-7);
			pilot.forward();
		}
		if(cs3.readValue() > 30)
		{
			pilot.rotate(7);
			pilot.forward();
		}
	} 
	changeCoor();
	pilot.stop();
  }
  
  public void turn(int dir)
  {
  		float rotate = (getFace() - dir) * DEG;
  		if (rotate == 270) rotate = -92;
  		if (rotate == -270) rotate = 93; //left turns are made at 92 instead of 90
  		if (rotate == 90) rotate = 93; // again left turns more to compensate.
  		if (rotate == -90) rotate = -92;
  		if (rotate == -180) rotate = -184;
  		if (rotate == 180) rotate = 186; //one last time for left hand 180s.
  		pilot.rotate(rotate);
  		setFace(dir);
  }
  
  public void changeCoor()
  {
  	switch(getFace())
  	{
  		case 0:
  			curx += -1;
  			break;
  		case 2:
  			curx += 1;
  			break;
  		case 1:
  			cury += 1;
  			break;
  		case 3:
  			cury += -1;
  			break;
  	}
  }
  
  public int intFace(int difx, int dify)
  {
  	if (difx == -1) return 2; //south
  	if (difx == 1) return 0; //north
  	if (dify == -1) return 1; //east
  	if (dify == 1) return 3; //west
  	return getFace();
  }
  
  public void backtrack()
  {
  	int difx = getX() - stackx.pop();
  	int dify = getY() - stacky.pop();
  	int dir = intFace(difx,dify);
  	if (dir != getFace())
  		turn(intFace(difx, dify));
  	moveBack();
  }
  
  public void findSing()
  {
  	File file = new File("found.wav");
  	Sound.setVolume(100);
  	Sound.playSample(file);
  }
 
  public void startSing()
  {
  	File file = new File("start.wav");
  	Sound.setVolume(100);
  	Sound.playSample(file);
  }
  
  public void endSing()
  {
  	File file = new File("finish.wav");
  	Sound.setVolume(100);
  	Sound.playSample(file);
  }
  
  public void gohome()
  {
  	//DIST = 11;
  	while(!stackx.isEmpty() && !stacky.isEmpty()){
  		backtrack();
  	}
  }
  	
  public int getX()
  {
  	return curx;
  }
  public int getY()
  {
  	return cury;
  }
  
  public int getFace()
  {
  	return face;
  }
  public void setFace(int dir)
  {
  	face = dir;
  }
  
  public boolean isFound()
  {
  	if (cs2.readValue() == 5) return true;
  	return false;
  }
  
  public boolean isHome()
  {
  	if(getX() == 0 && getY() == 0) return true;
  	return false;
  }
  public void spin()
  {
  	pilot.rotate(360);
  }
  public static void pause(int time)
  {
  	try{ Thread.sleep(time);
  	}
  	catch(InterruptedException e){
  	}
  }
  public void checkHome()
  {
  	turn(0);
	
	pilot.travel(DIST, true);
	while(pilot.isMoving())
	{
		if(cs1.readValue() > 30)
		{
  			pilot.stop();
  			align();
  		}
  		if(cs3.readValue() > 30)
  		{
  			pilot.stop();
  			align();
  		}
  	}
  	
  	turn(3);
	
	pilot.travel(DIST, true);
	while(pilot.isMoving())
	{
		if(cs1.readValue() > 30)
		{
  			pilot.stop();
  			align();
  		}
  		if(cs3.readValue() > 30)
  		{
  			pilot.stop();
  			align();
  		}
  	}
  	
  }
}


