import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * Write a description of VideoCode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VideoCode {
  public void printNames(){
    FileResource fr = new FileResource();
    for (CSVRecord rec : fr.getCSVParser(false)){//iterate over file using CSVParser, put databyte "false" when creating parser b/c data file does not have a header row
      int numBorn = Integer.parseInt(rec.get(2));
      if (numBorn <= 100){
       System.out.println("Name "+ rec.get(0) +//get numbers instead of name, using values
                         " Gender "+rec.get(1)+
                         " Num Born "+rec.get(2));
             }
    }
    
    }
  
}
