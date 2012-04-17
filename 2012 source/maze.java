public class maze 
{
	char[][][] Maze = new char[8][8][4];//3d array of chars instead of 2d array of ints
	
    public maze() 
    {

        for(int i = 0; i < 8; i++)
        {
        	for(int j = 0; j < 8; j++)
        	{
        		for(int k = 0; k < 4; k++)
        		{
        			Maze[i][j][k] = '?'; //0000 represents open doors on all sides of every box NESW (north, east, south, west)
        		}
        	}
        }
    	
    	for(int i = 0; i < 8; i++) //This loop makes the outer edges of the Maze have walls
    	{
    		Maze[0][i][0] = 'W'; //North side wall
    		Maze[6][i][2] = 'W'; //South side is wall
    		Maze[i][5][1] = 'W'; //East side is wall
    		Maze[i][0][3] = 'W'; //West side is wall
    	}
    }
	
	public int inverseDir(int dir)
	{
		return (dir+2) % 4;
	}

	public int rowinc(int dir)
	{
		switch(dir)
		{
			case(0):return -1;//case north
			case(1):return 0;//case east
			case(2):return 1;//case south
			default:return 0; //case west
		}
	}
	public int colinc(int dir)
	{
		switch(dir)
		{
			case(0):return 0;//case north
			case(1):return 1;//case east
			case(2):return 0;//case south
			default:return -1; //case west
		}
	}
	
	public void addWall(int x, int y, int dir)
	{
		int inv = inverseDir(dir);
		Maze [x][y][dir] = 'W';
		int testx = x+rowinc(dir);
		int testy = y+colinc(dir);
		if (!(testx <0 || testy <0 || testx > 6 || testy > 7)){
			Maze [x + rowinc(dir)][y + colinc(dir)][inv] = 'W';	}	
	}
	
    public void addDoor(int x, int y, int dir)
	{
		int inv = inverseDir(dir);
		Maze [x][y][dir] = 'D';
		Maze [x + rowinc(dir)][y + colinc(dir)][inv] = 'D';
	}
	
	public int nextDir(int x, int y, int dir)
	{
		for (int i = 0; i < 4; i++)
			if (Maze[x][y][i] == '?') return i;
		return -1;
	}
}