/*
    Class summary: Attacker class
    Class details: 
        --Creates an object that returns an attack value based on provided probability inputs
        --Keeps a track of attack types that were performed every round
        --Calculates statistics for the entire fight
    Limitations: 
        --relies on the built in random class from java
        --No type checking is performed on user input, the user is just told to enter integers
    Version: 1.0
        --Attacker class is created
    Version: 2.0
        --attack counters are added
        --incrementAttackCounters function is added
        --removed the basic attack generator, only the advanced attack generator remains
*/

import java.util.Scanner;//import to use the scan object for reading user input 
import java.util.Random;//import to use the random number generator

public class Attacker
{
    //Store the attack types in class constants
    private final static int HIGH = 3;
    private final static int MEDIUM = 2;
    private final static int LOW = 1;
    
    //the following private variables store the probability of each attack
    private int lowAttackProb = 0;//numeric probability that a low attack will occur
    private int medAttackProb = 0;//numeric probability that a medium attack will occur
    private int highAttackProb = 0;//numeric probability that a high attack will occur
    private int sumOfAttackProb = 0;//stores the attack probabilities sum entered by the user
    
    //Thi Array variable will hold the attack types with the user specified probabilities. This array 
    //simulates a 100 side die with duplicate sides of each attack to represent its probability.
    private int [] probArray = new int [100];

    //initialize variables to server as hit counters of different heights
    private int lowAttackCounter = 0;
    private int medAttackCounter = 0;
    private int highAttackCounter = 0;
    private int totalAttackCounter = 0;

    private String attackString = "";//stores the generated attack

    //a no argument constructor of the Attacker object
    public Attacker()
    {
        setAttackProb();
    }

    public String generateAttack()
    {
        //if the user entered attack probabilities add up to 100 then use then generate an advanced attack
        if (sumOfAttackProb == 100)
        {
            attackString = advancedAttackGenerator();
        }
        //if the user entered attack probabilities do not add up to 100 then use a basic attack
        else if (sumOfAttackProb != 100)
        {
            attackString = basicAttackGenerator();
        }
        return attackString;
    }

    //basic attack generator function that is used if user enters attack probabilities
    //that do not sum up to 100 exactly
    public String basicAttackGenerator()
    {
        //create a reference and instantiate an object of the Random class
        Random randomIntGenerator = new Random();
        //create a random integer between 1 and 3 inclusively
        int attackInteger = randomIntGenerator.nextInt(3)+1;
        //convert the attack type from integer to string
        attackString = attackInt2Str(attackInteger);
        //increment attack counters
        incrementAttactCounters(attackInteger);

        return attackString;
    }
    
    //this advanced attack generator is able to use the provided probabilities
    public String advancedAttackGenerator()
    {
        //create a Random object reference and instantiate an object of the Random class.
        Random randomIntGenerator = new Random();
        //generate a random value between 0 (inclusive) and 100 (exclusive)
        int randomIndex = randomIntGenerator.nextInt(100);
        //use the randomly generated integer as an inder to pick a value from the simulated 100 side die
        int attackInteger = probArray[randomIndex];
        
        //convert the attack type from integer to string
        attackString = attackInt2Str(attackInteger);
        
        //increment attack counters
        incrementAttactCounters(attackInteger);
        
        //return the string value of the generated attack
        return attackString;
    }

    //mutator method that sets the attack probability attributes to passed values
    public void setAttackProb()
    {
        //prompt the user for attack probabilities
        promptUser();

        //add up all user entered probability values
        sumOfAttackProb = lowAttackProb + medAttackProb + highAttackProb; 
        
        //check if the user entered attack probabilities add up to a value of 100
        if (sumOfAttackProb == 100 )
        {
            //Create an array that will hold the attack types with the user specified probabilities.
            //This is similar to the given hint of using Java to simulate the rolling of dice.
            createProbArray();
        }
        //if the user entered attack probability values do not add up to 100 then default values are used
        else if (sumOfAttackProb !=100)
        {
            displayProbError();//inform the user that default values are used and print their numeric values
        }
    }

    private void promptUser()
    {
        //create a scanner object that will be used to get user input
        Scanner in = new Scanner (System.in);
        
        //Prompt the user for a number of attack rounds.
        System.out.println("Enter percentages as integers for the number of attacks that will be directed: "
                        + "low, medium, high. The total of the three percentages must sum to 100%");
        System.out.print("Percentage of attacks that will be aimed low: ");
        lowAttackProb = in.nextInt();//store the user entered value for low attack probability
        System.out.print("Percentage of attacks that will be aimed at medium height: ");
        medAttackProb = in.nextInt();//store the user entered value for medium attack probability
        System.out.print("Percentage of attacks that will be aimed high: ");
        highAttackProb = in.nextInt();//store the user entered value for high attack probability
        System.out.println();//output a blank line for readibility
        in.close();//close the scanner object
    }

    private void createProbArray()
    {
        //create an array of 100 elements where we will hold values 1, 2, or 3. The frequency at which
        //these values occur in the array will match the value of the user specified probabilies.
        for (int i=0; i<lowAttackProb; i++)
        {
            probArray[i] = LOW;
        }
        for (int i=lowAttackProb; i<lowAttackProb+medAttackProb; i++)
        {
            probArray[i] = MEDIUM;
        }
        for (int i=lowAttackProb+medAttackProb; i<lowAttackProb+medAttackProb+highAttackProb; i++)
        {
            probArray[i] = HIGH;
        }
    }

    //The displayProbError function informs the user that the entered attack probabilities do not add
    //up to 100, so the program will use default values.
    private void displayProbError()
    {
        System.out.println("The entered attack probability values do not sum to 100. " +
                            "Attacks of equal probability will be used instead.");
    }

    //this function will increment the private attack counter variables
    private void incrementAttactCounters(int attackInteger)
    {
        if (attackInteger == LOW)//increment the lowAttackCounter if the attack is low
        {
            lowAttackCounter++;
        }
        else if (attackInteger == MEDIUM)//increment the medAttackCounter if the attack is medium
        {
            medAttackCounter++;
        }
        else if (attackInteger == HIGH)//increment the highAttackCounter if the attack is high
        {
            highAttackCounter++;
        }
        totalAttackCounter++;//increment the toatl attack counter every time there is an attack
    }

    //this function converts from an integer attack value to a descriptive String value
    private String attackInt2Str(int attackInt)
    {
        //initialize the attackString variable
        String attackString = "";
        //set the attackString variable to corresponding attack height integer
        if (attackInt == LOW)
            attackString = "Low";
        else if (attackInt == MEDIUM)
            attackString = "Medium";
        else if (attackInt == HIGH)
            attackString = "High";

        return attackString;
    }

    public void printAttackerStats()
    {
        System.out.printf("Attacker proportions:   Low: %6.2f%%       Medium: %6.2f%%      High: %6.2f%%\n", 
                          getLowAttackPcnt(), getMedAttackPcnt(), getHighAttackPcnt());
    }

    //this getter function returns the percentage of high attacks out of the total attacks
    private float getHighAttackPcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float highAttackPcnt = (float)highAttackCounter/(float)totalAttackCounter*100;
        return highAttackPcnt;
    }

    //this getter function returns the percentage of medium attacks out of the total attacks
    private float getMedAttackPcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float medAttackPcnt = (float)medAttackCounter/(float)totalAttackCounter*100;
        return medAttackPcnt;
    }

    //this getter function returns the percentage of low attacks out of the total attacks
    private float getLowAttackPcnt()
    {
        //cast the integer variables as floats to avoid rounding
        float lowAttackPcnt = (float)lowAttackCounter/(float)totalAttackCounter*100;
        return lowAttackPcnt;
    }
}