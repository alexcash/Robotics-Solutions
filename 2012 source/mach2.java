public class mach2 
{
    public static void main(String[] args) 
    {
 		robitController robit = new robitController();
 		maze aMaze = new maze();
 		
 		robit.startSing();
		robit.startAlign();

		
		while(robit.found == false)
		{
			if(robit.move()) // move(int) now returns true if we need to mark a wall, false if we need to mark a door.
			{
				aMaze.addWall(robit.getX(), robit.getY(), robit.getFace());
				robit.backup();
				int turnval = aMaze.nextDir(robit.getX(), robit.getY(), robit.getFace());
				while(turnval == -1){
					robit.backtrack();
					turnval = aMaze.nextDir(robit.getX(), robit.getY(), robit.getFace());
					
				}
				if (turnval != robit.getFace()) robit.turn(turnval);
			}
			else
			{
				if (robit.found == true) break;
				aMaze.addDoor(robit.getX(), robit.getY(), robit.getFace());
				robit.align();
				robit.next();
				if (robit.found == true) break;
				int turnval = aMaze.nextDir(robit.getX(), robit.getY(), robit.getFace());
				while(turnval == -1){
					robit.backtrack();
					turnval = aMaze.nextDir(robit.getX(), robit.getY(), robit.getFace());
					
				}
				if (turnval != robit.getFace()){
					robit.turn(turnval);
				}
			}
			
			//System.out.println("" + robit.getFace() + "   " + robit.getX() + " , " + robit.getY());
		}
		
		//System.out.println("" + robit.getFace() + "   " + robit.getX() + " , " + robit.getY());
		System.out.println("WOAH! WE'RE HALFWAY THERE");
		robit.findSing();
		robit.cleanStack();
		
		while(!robit.isHome())
		{
		robit.gohome();
		}

		robit.checkHome();
		robit.endSing();
		System.out.println("WE ARE AWESOME!");
		robit.spin();
    }
    
    
    

}
