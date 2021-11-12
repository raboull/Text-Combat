/*
    Program summary: Text based fighting simulator.
    Program details: 
        --A number of fight rounds is simulated with user input of probabilities.
        --Initiates an attacker and a defender object
        --Simulates user entered number of fight rounds
        --Reports the results of each fight round
        --Reports overal results about all rounds
    Limitations:
        --Not a very good defence "AI" algorithm is used, just a rolling avg of up to 10 past attacks.
        --No type checking is performed on user input, the user is just told to enter integers
    Version: 1.0
        --Manager class is created
    Version 2.0 
        --attack counters are refactored to be embadded in the Attacker class.
        --hit statistics calculations are refactored to be within class objects instead of in Manager class.
*/
import java.util.Scanner;//import to use the scan object for reading user input 

public class Manager 
{
    public static void main(String [] args)
    {
        //create a scanner object that will be used to get user input
        Scanner in = new Scanner (System.in);
       
        //create a variable that stores the number of rounds to fight.
        int numOfRounds;

        //variables to store statistics of the entire simulation
        int hitCount = 0;
        int blockCount = 0;

        //Prompt the user for a number of rounds that will be fought.
        System.out.println("Please enter the number of attack rounds as an integer, "
                            + "between 1 and 100 inclusively: ");
        numOfRounds = in.nextInt();//store the user entered value for the number of attack rounds.
        //set a default number of rounds if user entered value is outside an expected range.
        if (numOfRounds <1 || numOfRounds>100)
        {
            System.out.println("Entered value is outside of expected range, will simulate 10 rounds.");
            System.out.println();//output a blank line for readibility
            numOfRounds = 10;
        }
        
        //declare a reference variable to the Attacker class and instantiate the Attacker class
        Attacker attacker = new Attacker();
        //declare a reference variable to the Defender class and instantiate the Defender class
        Defender defender = new Defender();

        //print a message for the user to indicate start of simulation
        System.out.println("");
        System.out.println("Kombat Begins!");
        System.out.println("--------------");

        //start the simulation loop
        for (int i=1; i<=numOfRounds; i++)
        {
            //prompt the attacker to generate an attack value
            String attackValue = attacker.generateAttack();
            
            //prompt the defender to respond to the current attack
            String defenceValue = defender.advancedDefenceGenerator(attackValue);
            
            //assign a description of the round outcome to the roundResult variable
            String roundResult;
            if (attackValue.equals(defenceValue))
            {
                roundResult = "Block";
                blockCount++;
            }
            else
            {
                roundResult = "Hit";
                hitCount++;
            }  

            //output actions and result of current round
            System.out.printf("%5s %-3d%s   Attacker: %-6s     Defender: %-6s     %-5s\n", "Round",  
                              i, "...", attackValue, defenceValue, roundResult);       
        }
        //Print statistics for the entire simulation
        System.out.println("");
        System.out.println("Summary of Kombat");
        System.out.printf("Total hits: %d   Total Blocks: %d\n", hitCount, blockCount);
        attacker.printAttackerStats();//print attacker statistics for the entire simulation
        defender.printDefenderStats();//print defender statistics for the entire simulation

        in.close();//close the scanner object
    }
}