import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

/**
 * Write a description of Assignment here.
 * this is the class used for a ranking system that doesn't skip numbers!
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniProject {
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
      int boyN = 0;
      int girlN = 0;
      //same idea of looping
      for (CSVRecord rec: fr.getCSVParser(false)){
         int numBorn = Integer.parseInt(rec.get(2));
         totalBirths += numBorn;
         if (rec.get(1).equals("M")){//if its male..
            totalBoys += numBorn;//then add to total for totalBoys
            boyN++;
          }
           else{
            totalGirls += numBorn;
            girlN++;
           }//order doesnt matter in this code
       }
       
      System.out.println("Total births: " + totalBirths);
      System.out.println("Total girls: " + totalGirls);//all popularity added
      System.out.println("Total boys: " + totalBoys);
      System.out.println("Total B names: "+boyN+" Total G names: "+girlN);
    }
     
  public void testTotalBirths(){
      
    FileResource fr = new FileResource();
    totalBirths(fr);
      
 }
    
  public int getRank (int year, String name, String gender){
     FileResource fr = new FileResource();

      int rank = 0;
      int currentCount = -1;
          for (CSVRecord rec: fr.getCSVParser(false)){//forL or feLoop?

              if (rec.get(1).contains(gender)) {
                    String infoName = rec.get(0);
                    int count = Integer.parseInt(rec.get(2));
                    if(currentCount != count) {
                        rank++;//only add when you see a number different from number in get(0)
                    }
                    if(infoName.equals(name)) {
                        return rank;
                    }
                    currentCount = count;

                }
            }

          
            return -1;
        }
    
   public String getName(int year, int rank, String gender){//return name at a rank
     FileResource fr = new FileResource();
     int initRank = 0;
     int currentCount = -1;
     for (CSVRecord rec: fr.getCSVParser(false)){
           if (rec.get(1).contains(gender)) {
              String name = rec.get(0);
              int count = Integer.parseInt(rec.get(2));
            if(currentCount != count) {
                initRank++;//only add when you see a number different from number in get(0)
                    }
            if(initRank==rank) {
                 return name;
                    }
             currentCount = count;

         } 
        
        
        }
    
     return "noname";
    
    }
    
   public void testRank(){
    int rank = getRank(1960,"Emily","F");
    System.out.println("Rank for Emily: "+rank);
    
    rank = getRank(1971,"Frank","M");
    System.out.println("Rank for Frank: "+rank);
    
    //String name = getName(1982,450,"M");
    //System.out.println("Name: "+name);
    
    //name = getName(2013,3,"M");
    //System.out.println("Name in 2013 that is 3rd most pop(men): "+name);
    
    
    }
    
   public String whatIsNameInYear(String name, int year, String gender, int newYear){
     //get rank of your name in your birth year, find the name of the same rank in the other year
     //find name of same rank in the other year
    
     //open FR once for birth year rank (your "name" is Ashley ^.^)
     int birthRank = getRank(year,name, gender);
     
     //open new file for name in new year
     FileResource fr = new FileResource();
     int newRank = 0;
     int initNum = -1;
     for (CSVRecord rec : fr.getCSVParser(false)){
         //loop over every row in newYear
         if (rec.get(1).equals(gender)){
            int pop = Integer.parseInt(rec.get(2));
            String newName = rec.get(0);
             if (initNum != pop){
               newRank++;
             }
             
             if (newRank == birthRank){
               return newName;
                } 
                
            initNum = pop;     
            }
           
        }
        
        return "noname";
    }
    
  public void newNamenewYear(){
    String name = "Ashley";
    int year = 1997;
    int newY = 1893;
    String newname = whatIsNameInYear(name, year, "F", newY);
    System.out.println(name+" born in "+year+" would be "+newname+" in "+newY);
    
    name = "Owen";
    year = 1974;
    newY = 2014;
    newname = whatIsNameInYear(name, year, "M", newY);
     System.out.println(name+" born in "+year+" would be "+newname+" in "+newY);
    }  
    
    
  public int yearOfHighestRank(String name, String gender){
   DirectoryResource dr = new DirectoryResource();
   int year = 0;
   int startRank = 0; 
   for (File f : dr.selectedFiles()){  
     //FileResource fr = new FileResource(f);
     String fName = f.getName();
     String yob = "yob";
     int startPos = fName.indexOf(yob);
     int currYear = Integer.parseInt(fName.substring(startPos+3,startPos+7));
     int currRank = getRank(currYear,name,gender);
     
     if(year == 0){
        year = currYear;
        startRank = currRank; 
        }
     System.out.println("startRank= "+startRank);
     
     if(currRank != -1){
         
       if (currRank<startRank){
         
        year = currYear;
        startRank=currRank;
      }
      
    }
    
    }
    
    return year;
  }  
  
  public void testYearOfHighestRank(){
    String name = "Genevieve";
    int highFile = yearOfHighestRank(name, "F");
    System.out.println(name+" has the highest rank in year "+highFile);
    

    } 
    
  public double getAverageRank(String name, String gender){
    DirectoryResource dr = new DirectoryResource();
    double avgRank = 0.0;
    double num = 0.0;
    //int currRank = getRank(0,name,gender);
    for (File f : dr.selectedFiles()){
     //it never ENDS
     //FileResource file = new FileResource(f);
     String fName = f.getName();
     String yob = "yob";
     int startPos = fName.indexOf(yob);
     int currYear = Integer.parseInt(fName.substring(startPos+3,startPos+7));
     double currRank = getRank(currYear,name,gender);
     
     if (currRank != -1.0){
        avgRank = avgRank + currRank;
        num++;
        }
    
     
    }
    
    return avgRank/num;
    
    }
    
  public void testAverageRank(){
    
      Double avg = getAverageRank("Susan","F");
      System.out.println("Average rank for Susan is "+ avg);
      
    }
    
    
  public int getTotalBirthsRankedHigher(int year, String name, String gender){
      int rank = 0;
      int currentCount = -1;
      int nameRank = getRank (year, name, gender);
      int cumuCount = 0;
      
      FileResource fr = new FileResource();
          for (CSVRecord rec: fr.getCSVParser(false)){//forL or feLoop?
            if (rec.get(1).equals(gender)){
                        
              //String name = rec.get(1);
              int count = Integer.parseInt(rec.get(2));
             if(currentCount != count) {
                rank++;//only add when you see a number different from number in get(0)
                    }
              currentCount = count;      
             if(rank<nameRank) {
                 cumuCount = cumuCount + count;
                   }
             
                
            }   
             
            }
    
        return cumuCount;
    
    }  
    
    public void GTBRHTest(){
     int Ethan = getTotalBirthsRankedHigher(1990,"Drew", "M");
     System.out.println("Total births ranked higher are: "+Ethan);
    
    }
}
