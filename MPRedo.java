import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
/**
 * Write a description of MPRedo here.
 * This rank skips numbers!!!
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MPRedo {
  
  public int getRank(int year, String name, String gender){
             FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
            int rank = 0;
            int previousNumBirths = -1;
            int countSameRank = 1;
            for (CSVRecord rec: fr.getCSVParser(false)){

                if (rec.get(1).contains(gender)) {
                    String infoName = rec.get(0);
                    int currentNumBirths = Integer.parseInt(rec.get(2));
                    if(previousNumBirths != currentNumBirths) {
                        rank += countSameRank;
                        countSameRank = 1;//number was different, so it's only there once
                    }else {//when a double is found, we skip rank and add to CSR
                       countSameRank++;//counting amt of times # is there
                    }
                    if(infoName.equals(name)) {
                        return rank;
                    }
                    previousNumBirths = currentNumBirths;

                }
            }


            return -1;
    
    } 
    
   public void testRank(){
    int rank = getRank(1960,"Emily","F");
    System.out.println("Rank for Emily is: "+rank);
    
    rank = getRank(1974,"Owen","M");
    System.out.println("Rank for Owen is: " +rank);
    
    }
    
    public String getName(int year, int rank, String gender){
                
                FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
              int srank = 0;
            int previousNumBirths = -1;
            int countSameRank = 1;
                for (CSVRecord rec : fr.getCSVParser(false)){
                      if (rec.get(1).contains(gender)) {
                    String infoName = rec.get(0);
                    
                    int currentNumBirths = Integer.parseInt(rec.get(2));
                    if(previousNumBirths != currentNumBirths) {
                        srank += countSameRank;
                        //System.out.println("Rank is "+srank+ " for "+infoName);
                        countSameRank = 1;//number was different, so it's only there once
                    }else {//when a double is found, we skip rank and add to CSR
                       countSameRank++;//counting amt of times # is there
                    }
                    if(srank == rank) {
                        return infoName;
                    }
                    previousNumBirths = currentNumBirths;

                }
            }
            
            
            return "noname";
    }
    
    public void testName(){
    
     String name = getName(1980, 351,"F");//no girl 350, two 349 (Mia and another)
     System.out.println("Girl's name rank 350 is "+name);
     
     //name = getName(2014,429,"M");
     //System.out.println(name);//(2014)Danny and Leonel is 429, Kayson after is 431
     
     name = getName(1982,450,"M");
     System.out.println("Boy's name rank 450 is "+name);
     
    
    }
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender){
       int birthRank = getRank(year, name,gender);
       int newRank = 0;
       int prevNumBirths = -1;
       int countSameRank = 1;
       
       FileResource fr = new FileResource("us_babynames_by_year/yob" + newYear + ".csv");
       for(CSVRecord rec : fr.getCSVParser(false)){
         
          if (rec.get(1).contains(gender)){
            int currNumBirths = Integer.parseInt(rec.get(2));
            String newName = rec.get(0);
             
            if(prevNumBirths != currNumBirths){
             newRank+=countSameRank;
             
             countSameRank = 1;
            } else{
              countSameRank++;
            
            }
            
            
             if (newRank == birthRank && newRank != -1){
              return newName;
            
            }
            
            prevNumBirths = currNumBirths;
            
            }
        
         
        } 
        
        return "NoNAME";
        
    }
    
    public void testWwhatsnameinYear(){
      String newB = whatIsNameInYear("Owen",1974,2014,"M");
      System.out.println("Owen (1974) would be "+newB+" in 2014");
     
    
    }
    
    public int  yearOfHighestRank(String name, String gender){
      
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
        //startRank = currRank; 
        }
     //System.out.println("startRank= "+startRank+" in "+currYear);
     
       
       if (currRank<startRank && currRank != -1){
         
        year = currYear;
        startRank=currRank;
       }
    
    //What happened w/ Mich???
    
    }
    
    return year;
  }  
    

  public void testYOHR(){
    //int year = yearOfHighestRank("Genevieve","F");
    //System.out.println("Genevieve had the highest Rank in: "+year);//
    //1880-2014
    
    int year = yearOfHighestRank("Mich","M");
    System.out.println("Mich had highest rank in: "+year);
    
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
  
  public void testAverage(){
    double avg = getAverageRank("Robert","M");
    System.out.println("The average rank for Robert is "+avg);
    
    }
  
  public int getTotalBirthsRankedHigher(int year, String name, String gender){
            int nameRank = getRank (year, name, gender);
           
            int rank = 0;
            int birthsSoFar = 0;
            int previousNumBirths = -1;
            int countSameRank = 1;
            FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
            for (CSVRecord rec: fr.getCSVParser(false)){

                if (rec.get(1).contains(gender)) {
                    String infoName = rec.get(0);
                    int currentNumBirths = Integer.parseInt(rec.get(2));
                    if(previousNumBirths != currentNumBirths) {
                        rank += countSameRank;
                        countSameRank = 1;//number was different, so it's only there once
                    }else {//when a double is found, we skip rank and add to CSR
                       countSameRank++;//counting amt of times # is there
                    }
                    if(rank < nameRank) {
                        birthsSoFar += currentNumBirths;
                    }
                    previousNumBirths = currentNumBirths;

                }
            }


            return birthsSoFar;
      
    }
  
  public void testGTBRH(){
    int total = getTotalBirthsRankedHigher(1990,"Emily","F");
    System.out.println("tbrh for Emily is "+total);
    
    total = getTotalBirthsRankedHigher(1990,"Drew","M");
    System.out.println("tbrh for Emily is "+total);
    
    
    
    }
  
    }
    
    