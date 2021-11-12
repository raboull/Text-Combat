/*
    Class summary: Defender class
    Class details: 
        --Creates an object that is able to provide a defence description to counter an input attack
        --Adjusts to the attacker
        --Keeps a track of attack types that were performed every round
        --Calculates statistics for the entire fight
    Limitations: 
        --Not a very good defence "AI" algorithm is used, just a rolling avg of up to 10 past attacks.
    Version: 1.0
        --Defender class is created
    Version: 2.0
        --defence counters are added
        --incrementDefenceCounters function is added
    Version 3.0
        --implemented the numOfStoredAttacks variable to avoid iterating through the recentAttacks array
          when it is already storing number of ROLLING_AVG attacks, we just push a new element into the 
          array then.
*/
import java.util.Random;//import to use the random number generator

public class Defender
{
    //Store the attack types in class constants
    private final static int HIGH = 3;
    private final static int MEDIUM = 2;
    private final static int LOW = 1;
    
    //ROLLING_AVG constant dictates how many of the last attacks are used to predict the next attack
    private final static int ROLLING_AVG = 3;
    //keep a count of stored attacks
    int numOfStoredAttacks = 0;
    private int defenceInteger = 0;//stored the generated defence integer
    
    //this array will hold the latest ROLLING_AVG number of attacks
    private int [] recentAttacks = new int [ROLLING_AVG];

    //initialize variables to server as hit counters of different heights
    private int lowDefenceCounter = 0;
    private int medDefenceCounter = 0;
    private int highDefenceCounter = 0;
    private int totalDefenceCounter = 0;

    //a no argument constructor of the Defender object
    public Defender()
    {
        //set an initial defence value at random
        //create a reference and instantiate an object of the Random class
        Random randomIntGenerator = new Random();
        //create a random integer between 1 and 3 inclusively
        defenceInteger = randomIntGenerator.nextInt(3)+1;
    }
    
    public String advancedDefenceGenerator(String incomingAttackStr)
    {
        //convert the incomingAttack from a string to an int value
        int incomingAttackInt = descStr2Int(incomingAttackStr);
        String defenceString = "";

        //check we have any information about previous attacks
        if (maxElement(recentAttacks)==0)//check if there are any elements larger than the default 0
        {
            updateRecentAttacks(incomingAttackInt);//store the incoming attack in the recentAttacks array
            incrementDefencetCounters(defenceInteger);//increment defence counters
            defenceString = descInt2Str(defenceInteger);//use the randomly generated first defence
        }
        //if this is not the first attack compute a rolling average of previous attacks to use as defence
        else
        {
            defenceInteger = computeBestDefence();//get a rolling average of the previous attacks
            updateRecentAttacks(incomingAttackInt);//store the incoming attack in the recentAttacks array
            incrementDefencetCounters(defenceInteger);//increment defence counters
            defenceString = descInt2Str(defenceInteger);
        }

        return defenceString;//return the generated defence as a string description value
    }

    //this function will return the max element of the inputArray
    private int maxElement(int[]inputArray)
    {
        int max = inputArray[0];//initializa a variable to hold the first element of the inputArray
        for (int i=1; i < inputArray.length; i++)//traverse through the array starting at the second element
        {
            //if an element larger than current max is encountered then set max to the encountered element
            if (inputArray[i] > max)
            {
                max =inputArray[i];
            }
        }
        return max;
    }

    //this function updates the recentAttacks array with incoming attack values
    private void updateRecentAttacks(int incomingAttack)
    {        
        //check if there is unused room in the recentAttack array, we want to populate it sequentially
        if(numOfStoredAttacks < ROLLING_AVG)
        {
            //find the next empty element of the recentAttacks array
            for (int i = 0; i < recentAttacks.length; i++)
            {                   
                if (recentAttacks[i] == 0)//check if the current slot is empty
                {                    
                    recentAttacks[i] = incomingAttack;//set the empty element to the most recent attack
                    numOfStoredAttacks++;//increment the number of stored attacks variable
                    break;
                }
            }
        }
        //if there are no empty values in the recentAttacks matrix then push the new attack value
        else if (numOfStoredAttacks == ROLLING_AVG)
        {
            //push the new attack value at the back of the recentAttacks array, while simultaneously 
            //dropping the first element of the recentAttacks array
            recentAttacks = pushNewElement(recentAttacks, incomingAttack);
        }
    }

    //this function will drop the first element of the inputArray and append the newElement value, it
    //creates queue like behavior.
    private int[] pushNewElement(int[] inputArray, int newElement)
    {
        //create a temporary array with the same length as the ROLLING_AVG value
        int [] tempArray = new int[ROLLING_AVG];
        
        //copy the first (ROLLING_AVG-1) number of inputArray elements into the temp array
        for (int i = 1; i < inputArray.length; i++)
        {
            tempArray[i-1] = inputArray[i]; 
        }
        
        //insert the latest attack into the last slot of the tempArray 
        tempArray[ROLLING_AVG-1] = newElement;
        
        //return the tempArray
        return tempArray;
    }

    //this function computes the avarage of stored attacks in the recentAttacks array
    private int computeBestDefence()
    {
        int avgDefence = 0;//variable to hold the calculated average defence value
        int elemSum = 0;//variable to hold the sum of 0 to ROLLING_AVG recent attacks
        
        //compute avg defence value from all non empty elements of the recentAttacks array
        for (int i=0; i<recentAttacks.length; i++)
        {
            if (recentAttacks[i] != 0)
            {
                elemSum = elemSum+recentAttacks[i];
            }
        }
        avgDefence = elemSum/numOfStoredAttacks;
        return avgDefence;
    }

    //convert an attack String to an integer
    private int descStr2Int(String descStr)
    {
        //initialize the attackInt variable
        int descInt=0;
        //set the attackInt variable to corresponding attack height String
        if (descStr.equals("Low"))
            descInt = LOW;
        else if (descStr.equals("Medium"))
            descInt = MEDIUM;
        else if (descStr.equals("High"))
            descInt = HIGH;

        return descInt;
    }

    //convert move description from integer to string
    private String descInt2Str(int descInt)
    {
        //initialize the attackInt variable
        String descStr = "";
        //set the attackInt variable to corresponding attack height String
        if (descInt == LOW)
            descStr = "Low";
        else if (descInt == MEDIUM)
            descStr = "Medium";
        else if (descInt == HIGH)
            descStr = "High";

        return descStr;
    }

    //this function will increment the private defence counter variables
    private void incrementDefencetCounters(int defenceInteger)
    {
        if (defenceInteger == LOW)//increment the lowDefenceCounter if the defence is low
        {
            lowDefenceCounter++;
        }
        else if (defenceInteger == MEDIUM)//increment the medDefenceCounter if the defence is medium
        {
            medDefenceCounter++;
        }
        else if (defenceInteger == HIGH)//increment the highDefenceCounter if the defence is high
        {
            highDefenceCounter++;
        }
        totalDefenceCounter++;//increment the toatl defence counter every time there is an attack
    }

    public void printDefenderStats()
    {
        System.out.printf("Defender proportions:   Low: %6.2f%%       Medium: %6.2f%%      High: %6.2f%%\n", 
                           getLowDefencePcnt(), getMedDefencePcnt(), 
                           getHighDefencePcnt());
    }
    
    //this getter function returns the percentage of high defences out of the total defences
    public float getHighDefencePcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float highDefencePcnt = (float)highDefenceCounter/(float)totalDefenceCounter*100;
        return highDefencePcnt;
    }

    //this getter function returns the percentage of medium defences out of the total defences
    public float getMedDefencePcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float medDefencePcnt = (float)medDefenceCounter/(float)totalDefenceCounter*100;
        return medDefencePcnt;
    }

    //this getter function returns the percentage of low defences out of the total defences
    public float getLowDefencePcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float lowDefencePcnt = (float)lowDefenceCounter/(float)totalDefenceCounter*100;
        return lowDefencePcnt;
    }
}