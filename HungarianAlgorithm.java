
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

public class HungarianAlgorithm {
int loop=0;
	public  static void main(String args[]) throws IOException
	{
	
	HungarianAlgorithm hung1 = new HungarianAlgorithm();
	hung1.ReadingInput();
}

public void ReadingInput() throws FileNotFoundException,IOException
{
int col=0,row=0;
//Reading Input stored in testfile1.txt file .To test different cases need to modify the this test file accordingly.
String file_name = JOptionPane.showInputDialog(null, "Enter the file name you have created to test");
System.out.println("File name entered is "+file_name);
Scanner s = new Scanner(new File(file_name));

row = s.nextInt();
col = s.nextInt();
int orig_row =0,orig_col =0;
orig_row = row;
orig_col = col;
if(row>col)
	col = row;
else if(row<col)
	row = col;
int a[][]=new int[row][col];
//Reading Input (matrix elements)
//Person Names will start from 0 to n-1 and Jobs will start from 0 to n-1 respectively.
for(int i=0;i<orig_row;i++)
	for(int j=0;j<orig_col;j++)
		a[i][j] = s.nextInt();

//System.out.println("The List :"+array);
System.out.println("The row: "+row+" and "+col);
System.out.println("The matrix is :");
for(int i=0;i<row;i++)
{
	for(int j=0;j<col;j++)
		System.out.print(a[i][j]+"\t");
	System.out.println();
}

int temp[][]=new int[row][col];


//Find the maximum element of Array
int max_value = max(a,orig_row,orig_col);

// Adding dummy rows and columns if required and filling it with maximum value of array
boolean dummy[]=new boolean[row]; // To skip the assignemnt if exists in dummy row or dummy column
for(int temp1=0;temp1<dummy.length;temp1++) dummy[temp1]=false; 
// false means it is not dummy row/column and true for dummy rows and dummy columns
if(orig_row!=orig_col)
{
	if(orig_row>orig_col)
		for(int i=0;i<row;i++)
			for(int j=orig_col;j<col;j++){
				a[i][j] = max_value;
	            dummy[j]=true;
			}
	else if(orig_col>orig_row)
		for(int i=0;i<col;i++)
			for(int j=orig_row;j<row;j++){
				a[j][i] = max_value;
				dummy[j]=true;
			}
	 
}

//Copying the array in a temporary array.
for(int i=0;i<row;i++)
	for(int j=0;j<col;j++)	
		temp[i][j]=a[i][j];

System.out.println("The matrix is:");
printoutput(temp,row,col);
//Making step 1 row reduction of matrix given
rowreduction(temp,row,col);
System.out.println("After Row Reduction:");
printoutput(temp,row,col);
//Performing step 2 column reduction of matrix given
colreduction(temp,row,col);
System.out.println("After Column Reduction:");
printoutput(temp,row,col);
//System.out.println("The zeros marked here are:");
markzero(temp,row,col,a,dummy);
//printoutput(temp,row,col);
}



public void arbitaryassignment(int a[][],int row,int col,int arr[][],boolean dummy[])
{
	int pos=0;
	int col_count[]=new int[col];
	boolean assignment[][]=new boolean[row][col];
	boolean row_marked[]=new boolean[row];
	boolean col_marked[]=new boolean[col];
	for(int i=0;i<row;i++)
	{
		row_marked[i]=false;
	col_marked[i]=false;
	for(int j=0;j<col;j++)
		assignment[i][j]=false;
    }
	System.out.println("Greedy Method applied for assignment ");
	
	//Greedy Method was used to make assignment
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
			col_count[j]=0; 
			if(a[i][j]==0)
			{
				if(row_marked[i]==false && col_marked[j]==false)
				{
					for(int k=0;k<col;k++)
						if(a[k][j]==0 && row_marked[k]==false && col_marked[j]==false) // Taking count of zeros of each column and making assignment to element whose count is minimum
							col_count[j]++;
					//System.out.println("Col count "+col_count[j]+"at loop"+i);
					
				}
			}
		}
		int min_count = 999999;
		for(int index=0;index<col_count.length;index++)
		{
			if(min_count>col_count[index] && col_count[index]!=0)
			{
				min_count = col_count[index];
				pos = index;
			}
		}
		// Making assignment according to greedy approach.
		if(min_count!=999999)
		{
		assignment[i][pos]=true;
		row_marked[i]=true;
		col_marked[pos]=true;
		}
	}
	
	//Printing the assigned positions element with cost value excluding the dummy rows and dummy columns(if jobs < persons)
	String result="";
	int sum=0;
	for(int i=0;i<row;i++)
		for(int j=0;j<col;j++)
			if(assignment[i][j]==true && dummy[j]==false){
	System.out.println("Assignment done at "+i+","+j+"with cost"+arr[i][j]);
	result=result+"Person "+i+" does Job "+j+"with cost "+arr[i][j]+"\n";
			sum=sum + arr[i][j];
			}
	
	
	System.out.println("Hungarian Algorithm successfully done using greedy approach");
	System.out.println(result);
	JOptionPane.showMessageDialog(null,result);
	System.out.println("Minimum Cost required is "+ sum);
	System.exit(0);
}

public int max(int a[][],int row,int col)
{
	int max=0;
	max = a[0][0];
	for(int i=0;i<row;i++)
		for(int j=0;j<col;j++)
			if(max<a[i][j])
				max = a[i][j];
	return max;
}

public void rowreduction(int a[][],int x, int y)
{
	//Row Reduction Process
	int min_row[]=new int [x];
	for(int i=0;i<x;i++)
	{
		for(int j=0;j<y;j++)
		{
			if(j==0)
				min_row[i] = a[i][j];
			else
				if(min_row[i]>a[i][j])
					min_row[i] = a[i][j];
		}	
	}
	for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
			a[i][j] = a[i][j] - min_row[i];
}

public void colreduction(int a[][],int x, int y)
{
	//Column Reduction Process
	int min_col[]=new int [x];
	for(int i=0;i<x;i++)
	{
		for(int j=0;j<y;j++)
		{
			if(j==0)
				min_col[i] = a[j][i];
			else
				if(min_col[i]>a[j][i])
					min_col[i] = a[j][i];
		}	
	}
	for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
			a[j][i] = a[j][i] - min_col[i];
}

public void printoutput(int a[][],int row,int col)
{
	//Displaying matrix element in command prompt.
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
			System.out.print(a[i][j] + "\t");
		System.out.println();
	}
}

public void markzero(int a[][],int row,int col,int arr[][],boolean dummy[])
{
	int count,pos=0;
	int pos_row[] = new int[row];
	int pos_col[] = new int[row];
	int zero[] = new int[row];
	boolean assignment[][]=new boolean[row][col];
	int k=0;
	boolean row_marked[]=new boolean[row];
	boolean col_marked[]=new boolean[col];
	boolean tick_row[]=new boolean[row];
	boolean tick_column[]=new boolean[col];
	//Assiging all flag arrays to false initially
	for(int i=0;i<row;i++)
	{
		tick_row[i]=false;
		tick_column[i]=false;
		row_marked[i]=false;
	col_marked[i]=false;
	for(int j=0;j<col;j++)
		assignment[i][j]=false;
}
	//Taking count of Zeros in each row
	for(int i=0;i<row;i++)
	{
		
		count = 0;
		for(int j=0;j<col;j++){
			if(a[i][j]==0)
				{
				if(col_marked[j]==false && row_marked[i]==false){
				count ++;
				pos = j;
				}
				
				}
		}
		//If row has only zero then we will make an assignement.
		if(count==1)
		{
			
			pos_row[k] = i;
			pos_col[k] = pos;
			zero[k] = arr[i][pos];
			row_marked[i]=true;
			col_marked[pos]=true;
			assignment[i][pos]=true;
			//System.out.println("Row marked " +row_marked[i] +"at"+i +"column Marked"+col_marked[pos]+"at"+pos);
			//System.out.println(" ROW The  zero was found at position: ("+pos_row[k]+","+pos_col[k]+")");
			k++;
		}
	}
	
	//Taking count of Zeros in each column.
	for(int i=0;i<row;i++)
	{
		//System.out.println("In column Zero Assignment");
		count = 0;
		for(int j=0;j<col;j++)
			if(a[j][i]==0)
				{
				if(col_marked[i]==false && row_marked[j]==false && dummy[j]==false){
				count ++;
				pos = j;
				//System.out.println(a[j][i] +"valid Number");
				}
				}
		//System.out.println(count);
		//If column has only zero then we will make an assignment.
		if(count==1)
		{
		//for(int index=0;index<k;index++)
			//if(pos == pos_row[k])
				//break;
			pos_row[k] = pos;
			pos_col[k] = i;
			assignment[pos][i]=true;
			zero[k] = arr[pos][i];
			col_marked[i]=true;
			row_marked[pos]=true;
			// System.out.println("The zero was found at position: ("+pos_row[k]+","+pos_col[k]+")");
			// System.out.println("K value is "+k);
			k++;
			//System.out.println("Row marked " +row_marked[i] +"column Marked"+col_marked[i]);
		}
	}
	//System.out.println("k value is "+k);
	//If number of assignment made (k value) is equal to number of person.Then all possible assignments are covered.
	if(k == row)
	{
		System.out.println("Hungarian Algorithm successfully done.");
		String result="";
		int sum=0;
	for(k=0;k<zero.length;k++)
	{
		result=result +"Person "+pos_row[k]+" does Job "+pos_col[k]+" with cost "+zero[k]+"\n";
		sum =sum+zero[k];
	// System.out.println("Person "+pos_row[k]+" does Job "+pos_col[k]+"with cost "+zero[k]+"\n");
	}
	
	System.out.println(result);
	System.out.println("Minimum Cost required is "+ sum);
	// Displaying in Message Box 
	JOptionPane.showMessageDialog(null,result);
	
	System.exit(0);
	}
	else
	{
		System.out.println("Dual Adjustment process begin ");
		//min=dualadjustment(a,row,col,pos_row,pos_col);
		
		// 5 (i)Printing Assigned Rows upto now (Ticking)
		for(int i=0;i<row_marked.length;i++){
			if(row_marked[i]==false)
				tick_row[i]=true;
		}
		
		// 5(ii)For ticket row  and has a zero, then tick the corresponding column. Store it in Tick_Column Flag Array as true
		for(int i=0;i<tick_row.length;i++){
			if (tick_row[i]==true){
				for(int j=0;j<col;j++)
				{
					if(a[i][j]==0) 
				tick_column[j]=true;
				}
			}
		}
		
//  5(iii)	If a column is ticked and has an assignment then tick the corresponding row. Store it in Tick_row flag array as true.
	for(int j=0;j<tick_column.length;j++){
		if (tick_column[j]==true){
		for(int k1=0;k1<row;k1++){
			if(assignment[k1][j]== true) tick_row[k1]=true;		
			}
		}	
		
	}	
	}
	//Finding Minimum value of Theta in the cost matrix to perform dual adjustment. 
	int min_theta=0;
	for(int i=0;i<row;i++)
		for(int j=0;j<col;j++)
			if(tick_row[i]==true && tick_column[j]==false)
				{
				min_theta=a[i][j];
				i=row;
				j=col;
				}
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
		
		if(min_theta > a[i][j] && tick_row[i]==true && tick_column[j]==false ) 
		{
			min_theta=a[i][j];
			
		}
		}
	}
	
	System.out.println("The minimum value in dual adjustment step is "+min_theta);
	
	//Dual Adjustment Process
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
			if(tick_row[i]==true && tick_column[j]==false) //element is uncovered by lines.  
				a[i][j]= a[i][j]- min_theta;
			else if(tick_row[i]==false && tick_column[j]==true) //elements covered once by a lines twice.
				a[i][j]= a[i][j]+ min_theta;
		}
	}
	System.out.println("Matrix after dual adjustment");
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
			System.out.print(a[i][j] + "\t");
			
		}
		System.out.println();
	}
		
	// If min theta value is 0.The algorithm will go to infinite loop.So we are using greedy method in this case.
	if(min_theta == 0)
	{
		arbitaryassignment(a,row,col,arr,dummy);
		//System.out.println("Max limit exceeded "+ loop+"times looped in the function");
	}
	loop++;
	markzero(a,row,col,arr,dummy);
}
}

