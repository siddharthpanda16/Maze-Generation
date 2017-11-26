import java.util.*;

public class MazeGeneration {

	/**
	 * Initially all walls across all the cells were created except the first and the last items
	 * @param row
	 * @param col
	 * @param matrix
	 * @return
	 */
	public String[][] CreateWalls(int row,int col,String[][] matrix)
	{
		int count=0;
		for(int rowInd=0;rowInd<=2*row;rowInd++)
		{
			
			for(int colInd=0;colInd<=2*col;colInd++)
			{
				if(rowInd % 2 == 0 && colInd % 2 !=0)
					if(!(rowInd==0 && colInd==1) && !((rowInd==2*row && colInd==2*col-1)))
					{
						matrix[rowInd][colInd]="-";
					}
					else
						matrix[rowInd][colInd]=" ";
				
				else if (rowInd % 2 != 0 && colInd % 2 ==0)
					if(!(rowInd==2*row-1 && colInd==2*col) && !(rowInd==1 && colInd==0))
						matrix[rowInd][colInd]="|";
					else
							matrix[rowInd][colInd]=" ";
				else if(rowInd % 2 != 0 && colInd % 2 !=0)
				{
					matrix[rowInd][colInd]=String.format("%d",count);
					count++;
				}
				else
					matrix[rowInd][colInd]=" ";
			}			
		}
		return matrix;
	}
	
	/**
	 * break the walls between the elements for which the root is same that is after union 
	 * @param row
	 * @param col
	 * @param matrix
	 * @return
	 */
	public String[][] CrackWalls(int row,int col,String[][] matrix)
	{
		DisjSets dj = new DisjSets(row*col);

		while(dj.find(0)!=dj.find(row*col-1))
		{
			Random r=new Random();
			int selectedVal=r.nextInt(row*col);
			Random secondNum=new Random();
			int secondVal= secondNum.nextInt(4); //0--> left , 1--> right, 2--> up, 3--> down
			int rowInd=0, colInd=0,newRowInd=0,newColInd=0;
			
			rowInd=2*(selectedVal/ col)+1;
			colInd=2*(selectedVal % col)+1;
			
			switch(secondVal) {
			case 0:
					newColInd=colInd-2;
					newRowInd=rowInd;
				break;
			case 1:
					newRowInd=rowInd;
					newColInd=colInd+2;
				break;
			case 2:
					newRowInd=rowInd-2;
					newColInd=colInd;
				break;
			case 3:
					newRowInd=rowInd+2;
					newColInd=colInd;
				break;
			default: 
				continue;
			}	
			
			if(newRowInd>0 && newRowInd<2*row+1 && newColInd>0 && newColInd<2*col+1)
			{
				if(dj.find(selectedVal)!=dj.find(Integer.parseInt(matrix[newRowInd][newColInd])))
				{
					dj.union(dj.find(selectedVal), dj.find(Integer.parseInt(matrix[newRowInd][newColInd]))); // union between adjacent items
					matrix[(rowInd+newRowInd)/2][(newColInd+colInd)/2]=" "; // removed the wall
				}
			}
		}
		
		for(int rows=0;rows<=2*row;rows++)
		{			
			for(int cols=0;cols<=2*col;cols++)
			{
				if(rows % 2 != 0 && cols % 2 !=0)
				{
					matrix[rows][cols]=" ";			//removing the numbers added in the cells for reference to Disjoint Sets		
				}
			}
		}
		return matrix;
	}
	
	/**
	 * printing the maze
	 * @param charMatrix
	 * @param rowsCount
	 * @param colsCount
	 */
	public static void print(String[][] charMatrix, int rowsCount, int colsCount) {
		for (int i=0;i<2*rowsCount+1;i++)
		{
			for(int j=0;j<2*colsCount+1;j++)
				System.out.print(charMatrix[i][j]);
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		Scanner rows=new Scanner(System.in);
	    System.out.println("Enter input for number of rows...");
		int rowsCount=0,colsCount=0;
	    while(rows.hasNextInt())
	    {
	    	rowsCount=rows.nextInt();
		    	if(rowsCount>0)
		    		break;
	    	System.out.println("Enter valid input for number of Rows...(greater than 0)");
	    }
	    
	    Scanner cols=new Scanner(System.in);
	    System.out.println("Enter input for number of columns...");
	    while(cols.hasNextInt())
	    {	   
	    	colsCount=cols.nextInt();	    
		    	if(colsCount>0)    	
		    		break;
	    		System.out.println("Enter valid input for number of Columns... (greater than 0)");
	    }
	    if(rowsCount>0 && colsCount>0)
	    {
	    	try
	    	{
	    		String[][] charMatrix=new String[2*rowsCount+1][2*colsCount+1];	  
	    		
	    		MazeGeneration maze=new MazeGeneration();
	    		charMatrix=maze.CreateWalls(rowsCount,colsCount,charMatrix);
	    		
	    		charMatrix=maze.CrackWalls(rowsCount,colsCount,charMatrix);
	    		
	    		StringBuilder sb=new StringBuilder();
	    		
	    		for (int i=0;i<2*rowsCount+1;i++)
	    		{
	    			for(int j=0;j<2*colsCount+1;j++)
	    				sb.append(charMatrix[i][j]);
	    			sb.append("\n");
	    		}
	    		System.out.print(sb.toString());
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("Error generating the maze "+ e.getLocalizedMessage()+"\n");
	    		e.printStackTrace();
	    	}
	    }
	    else
	    {
	    	System.out.println("Invalid input provided....Please try again!!!");
	    }
    	rows.close();
	    cols.close();
	}
}
