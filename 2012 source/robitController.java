import lejos.nxt.*;
import lejos.robotics.navigation.*;
import java.io.File;
import java.util.*;

public class robitController {
  Pilot pilot = new TachoPilot(1.9527f, 4.9f, Motor.A, Motor.C);
  IntStack stackx = new IntStack(56);
  IntStack stacky = new IntStack(56);
  int curx, cury = 0;
  int face = 2;// 0 is north, 1 east, 2 south, 3 west
  boolean found = false;
  int DIST = 12;
  float BACKUP = -3.5f;
  float FORWARD = 8.7f;
  int DEG = 90;
  int THRESH = 34;

  ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S1, ColorLightSensor.TYPE_COLORGREEN);
  ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORFULL);
  ColorLightSensor cs3 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN);

  TouchSensor ts = new TouchSensor(SensorPort.S4);

  public void startAlign()
  { 
	//pilot.travel(-BACKUP);
  	pilot.setMoveSpeed(4);
  	//pilot.setTurnSpeed(80);
  	turn(3);
  	//Motor.A.forward();
	//Motor.C.forward();
	pilot.travel(DIST, true);
	while(pilot.isMoving()){
		if(cs1.readValue() > THRESH){
	  			pilot.stop();
	  			align();
				pilot.travel(BACKUP);
		}
	  	if(cs3.readValue() > THRESH){
	  			pilot.stop();
	  			align();
				pilot.travel(-3);
		}
	}
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
		if(cs1.readValue() > THRESH){
	  			pilot.stop();
	  			align();
				pilot.travel(BACKUP);
		}
	  	if(cs3.readValue() > THRESH){
	  			pilot.stop();
	  			align();
				pilot.travel(-3);
		}
	}
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
	pilot.stop();
  	pilot.setMoveSpeed(5);
  	//Motor.A.forward();
  	//Motor.C.forward();
  	//pilot.travel(DIST, true);
  	if(cs1.readValue() < THRESH)
  		Motor.A.forward();
	else
  		Motor.C.forward();
  	while(cs1.readValue() < THRESH || cs3.readValue() < THRESH)
  	{
  		if(cs1.readValue() > THRESH)
  			Motor.A.stop();
  		if(cs3.readValue() > THRESH)
  			Motor.C.stop();
  		if(!Motor.A.isMoving() && !Motor.C.isMoving())
  			break;
  	}
  	pilot.stop();
  	pilot.setMoveSpeed(4);
  	//pilot.travel(BACKUP);
  }
  public void next(){
	pilot.travel(FORWARD, true);
	while(pilot.isMoving()){
		if(found == false && isFound()){
			boolean found1 = isFound();
			pause(50);
			boolean found2 = isFound();
			pause(50);
			boolean found3 = isFound();
			if(found1 && found2 && found3){
				//pilot.stop();
				found = true;
			}// else if(ts.isPressed()){
			// 					pilot.stop();
			// 					pilot.travel(BACKUP);
			// 					//return true;
			// 			}
		}
	}
	changeCoor();
  }
  public void backup(){
	pilot.travel((float)-1.5);
  }
  public boolean move()
  {
  	//pilot.reset();
  	//pilot.forward();
  	pilot.travel(DIST, true);
	//while(pilot.getTravelDistance() < DIST){
	while(pilot.isMoving()){
		if(cs1.readValue() > THRESH  || cs3.readValue() > THRESH) 
		{
			pilot.stop();
			break;
			//return true;//true, we want to mark a wall on a map, not a door.
		}else if(ts.isPressed()){
			pilot.stop();
			return true;
		}else{
		if(found == false && isFound()){
			boolean found1 = isFound();
			pause(50);
			boolean found2 = isFound();
			pause(50);
			boolean found3 = isFound();
			if(found1 && found2 && found3){
				pilot.stop();
				found = true;
				return false;
			}
		}}
	}
	stackx.push(getX());
	stacky.push(getY()); 
	//System.out.println("push" + getX() + " " + getY());
	//pilot.stop();
	return false;//false, we will not mark a wall on the map. but we will mark a door.
  }
  
   public void moveBack()
  {
  	pilot.travel(DIST, true);
	while(pilot.isMoving()){
		if(cs1.readValue() > THRESH  || cs3.readValue() > THRESH) 
		{
			pilot.stop();
			break;
		}
	}
  	align();
	next();
}
  
  public void turn(int dir)
  {
		//pilot.travel(BACKUP);
  		float rotate = (getFace() - dir) * DEG;
  		if (rotate == 270) rotate = -90;
  		if (rotate == -270) rotate = 91; //left turns are made at 92 instead of 90
  		if (rotate == 90) rotate = 91; // again left turns more to compensate.
  		if (rotate == -90) rotate = -90;
  		if (rotate == -180) rotate = -178;
  		if (rotate == 180) rotate = 180; //one last time for left hand 180s.
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
	System.out.println(" x " +stackx.peek());
  	int difx = getX() - stackx.pop();
	//System.out.println(getY() + " " + stacky.pop());
	//System.out.println("" + getFace() + "   " + getX() + " , " + getY());
	System.out.println(" y " +stacky.peek());
  	int dify = getY() - stacky.pop();
  	int dir = intFace(difx,dify);
  	if (dir != getFace()){
  		turn(intFace(difx, dify));
		//System.out.println("I should turn!!!");
	}
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
		if(cs1.readValue() > THRESH)
		{
  			pilot.stop();
  			align();
			pilot.travel(BACKUP);
  		}
  		if(cs3.readValue() > THRESH)
  		{
  			pilot.stop();
  			align();
			pilot.travel(BACKUP);
  		}
  	}
  	
  	turn(3);
	
	pilot.travel(DIST, true);
	while(pilot.isMoving())
	{
		if(cs1.readValue() > THRESH)
		{
  			pilot.stop();
  			align();
			pilot.travel(BACKUP);
  		}
  		if(cs3.readValue() > THRESH)
  		{
  			pilot.stop();
  			align();
			pilot.travel(BACKUP);
  		}
  	}
  	
  }

  	public void cleanStack(){
		int[] xVals = stackx.getArray();
		int[] yVals = stacky.getArray();
		
		int xsize = stackx.size();
		int ysize = stacky.size();

		for(int i = 0; i < xsize-1; i++)
		{
			for(int j = i+1; j < xsize; j++){
				if((xVals[i] == xVals[j]) && (yVals[i] == yVals[j])){
					for(int k = 0; k < xsize; k++){
						xVals[i + k] = xVals[j + k];
						yVals[i + k] = yVals[j + k];
						xsize--;
					}
					i = 0;
				}
			}
		}
		
		for(int i = 1; i < ysize; i++)
		{
			if(xVals[i] == 0 && yVals[i] == 0){
				xsize = i-1;
				break;
			}
		}
		
		if(xVals[xsize] == 0 && yVals[xsize] == 0)
			xsize--;
		

		stackx.setArray(xVals, xsize + 1);
		stacky.setArray(yVals, xsize + 1);	
	}

  
}


