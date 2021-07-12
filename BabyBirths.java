/**
 * Print out the names for which 100 or fewer babies were born in a chosen CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
public class BabyBirths {
	public void printNames () {
	  FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}
	
	public void totalBirths (FileResource fr){//total boys and girls
	  int totalBirths = 0;//seen no births :)
	  int totalBoys = 0;
	  int totalGirls = 0;
	  //same idea of looping
	  for (CSVRecord rec: fr.getCSVParser(false)){
	     int numBorn = Integer.parseInt(rec.get(2));
	     totalBirths += numBorn;
	     if (rec.get(1).equals("M")){//if its male..
	       totalBoys += numBorn;//then add to total for totalBoys
	       }
	       else{
	       totalGirls += numBorn;
	       }//order doesnt matter in this code
	   }
	   
	  System.out.println("Total births: " + totalBirths);
	  System.out.println("Total girls: " + totalGirls);
	  System.out.println("Total boys: " + totalBoys); 
	}
	
	public void testTotalBirths(){
	  
	  FileResource fr = new FileResource();
	  totalBirths(fr);
	  
	   }
	
	
}
