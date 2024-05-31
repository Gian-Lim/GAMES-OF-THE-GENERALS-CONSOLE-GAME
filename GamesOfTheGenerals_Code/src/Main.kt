import java.util.*



fun main(args: Array<String>) {
    val userInput = Scanner(System.`in`)
    val resetColor = "\u001B[0m"
    val redColor = "\u001B[31m"
    val yellowColor = "\u001B[33m"
    val magentaColor = "\u001B[35m"
    val greenColor = "\u001B[32m"

    // These are the colors that I applied on some println

    instructions()
    // This is the method that I call to print about the instructions

    // This is a Mutable map that consist my team
    val player1_Characters = mutableMapOf(
        1 to "5 Star General",
        2 to "3 Star General",
        3 to "Colonel",
        4 to "Private",
        5 to "Spy",
        6 to "Private",
        7 to "Spy"
    )

    // This is a Mutable map that consist the enemy team
    var enemy_Characters = mutableMapOf(
        1 to "5 Star General",
        2 to "3 Star General",
        3 to "Colonel",
        4 to "Private",
        5 to "Spy",
        6 to "Private",
        7 to "Spy"
    )

    // Asking the user to enter his/her name first, then to get the name I utilize a scanner
    print("Enter your name ${yellowColor}Player1:${resetColor} ")
    var player1_Name = userInput.nextLine()

    // Start of the Game:
    println("\nGame Announcer: ${greenColor}$player1_Name${resetColor}, your ${redColor}enemy${resetColor} has deployed its piece. " +
            "You may now choose from your team the character you want to use to fight your opponent.\n")

    // Code for getting a random enemy within the Mutable map named "enemy_Characters"
    var currentEnemyKey = enemy_Characters.keys.random()

    // Creating a while loop that continue when I don't defeat all of my enemy which means the mutable List named "enemy_Characters"
    while (enemy_Characters.isNotEmpty()) {

        val enemyName = enemy_Characters[currentEnemyKey] ?: continue
        // Getting the current enemy name and skipping the iteration if the enemy is null

        println("${redColor}Hidden Enemy: ${resetColor}Unknown \n")
        // Printing this when enemy has deployed its piece

        for((key, values) in player1_Characters){
            println("$key - $values")
        }
        // Getting the keys and values of player1_Characters and display it so that user has responsive options to select.

        var isInputValid = true
        // A boolean that I will use on my while loop so that the while loop continue when isValidInput is not equal to false

        var player1Choice = 0
        /* Declaring variable inside the while loop named "player1Choice" which I used inside
         the while loop to get the user input or the user selected number from the option. */

        lateinit var selectedPlayerCharacter: String
        // Declaring a late init variable named "selectedPlayerCharacter" which values will be initialized after

        while (isInputValid) {
            print("\nWhat's your pick? ")
            try {
                player1Choice = userInput.nextLine().toInt()

                // This checks if the player choice is within the option range.
                if (player1Choice !in 1..7) {
                    print("That choice is not valid. Please select a number from ${player1_Characters.keys}\n")
                }

                // This will execute when the player1 selected option is not within the mutableList player1_Characters keys
                else if(player1Choice !in player1_Characters.keys){
                    println("The number that you write is already have been killed")
                }

                else {
                    selectedPlayerCharacter = player1_Characters[player1Choice]!!
                    isInputValid = false // if input is valid then exit this loop
                }

            } catch (e: NumberFormatException) {
                println("That's not a valid number. Please try again.")
            }
        }

        val win = fightWithEnemy(player1Choice, currentEnemyKey, selectedPlayerCharacter, enemyName, player1_Characters)

        // the value of win is true meaning the player defeat the enemy character the if condition will work, if not the else condition works



        if (win) {
            enemy_Characters.remove(currentEnemyKey)
            println("Your enemy has ${magentaColor}(${enemy_Characters.values.joinToString(", ")})${resetColor} on its team")

            if (enemy_Characters.isEmpty()) {
                println("Congratulations, $player1_Name! ${yellowColor}You have defeated all of your enemies and won the game!{$resetColor}")
                break
            }

            currentEnemyKey = enemy_Characters.keys.random()
        }

        else {
            player1_Characters.remove(player1Choice)
            if (player1_Characters.isEmpty()) {
                println("All of your piece lost the battle, better luck next time!")
                break // Exit the loop since the game is over
            }
        }

        if(!enemy_Characters.values.contains("Private") && !enemy_Characters.values.contains("Spy")
            && player1_Characters.values.contains("Spy")){
            println("Congratulations, $player1_Name! You have defeated your enemy by eliminating all of its ${yellowColor}Spies and Privates${resetColor}, " +
                    "while still having your ${greenColor}Spy.${resetColor}")
            break
        }

        else if(!enemy_Characters.values.contains("5 Star General") && !enemy_Characters.values.contains("Spy")
            && player1_Characters.values.contains("5 Star General")){
            println("Congratulations, $player1_Name! ${yellowColor}You win the game by killing all the characters from your enemy team${resetColor}, " +
                    "which is only able to kill your ${greenColor}'5 Star General.${resetColor}")
            break
        }

        else if(!player1_Characters.values.contains("Spy") && enemy_Characters.values.contains("5 Star General")){
            println("$player1_Name, you lost because you lost all your ${magentaColor}spies${resetColor} while your enemy has a higher-ranking character, " +
                    "which is the ${redColor}'5 Star General'${resetColor}.")
            break
        }

        else if(!player1_Characters.values.contains("Spy") && !player1_Characters.values.contains("5 Star General")
            && enemy_Characters.values.contains("3 Star General")){
            println("$player1_Name, you lost because you lost all your ${magentaColor}'Spy' and '5 Star General'${resetColor} " +
                    "while your enemy has a higher-ranking character, which is the ${redColor}'3 Star General'${resetColor}.")
            break
        }

        else if(!player1_Characters.values.contains("Spy") && !player1_Characters.values.contains("5 Star General")
            && !player1_Characters.values.contains("3 Star General") && enemy_Characters.values.contains("Colonel")){
            println("$player1_Name, you lost because you lost all your ${magentaColor}'Spy', '5 Star General', '3 Star General'${resetColor}, " +
                    "while your enemy has a higher-ranking character, which is the ${redColor}'Colonel'${resetColor}.")
            break
        }

        else if(!player1_Characters.values.contains("Spy") && enemy_Characters.values.contains("5 Star General")){
            println("$player1_Name, you lost because you lost all your ${magentaColor}'Spy' ${resetColor} " +
                    "while your enemy has a higher-ranking character, which is the ${redColor}'5 Star General'${resetColor}.")
            break
        }


        else if(!player1_Characters.values.contains("Private") && enemy_Characters.values.contains("Spy")){
            println("You lose all your ${magentaColor}Private${resetColor} that's why you lost the game.\n")
            break
        }

    }
}

fun fightWithEnemy(player1Response: Int, chooseRandomEnemy: Int, yourCharacter: String, enemyCharacter: String, player1_Characters: MutableMap<Int,String>): Boolean {
    // A method named "fightWithEnemy that has

    val resetColor = "\u001B[0m"
    val yellowColor = "\u001B[33m"
    val redColor = "\u001B[31m"

    if (player1_Characters.isEmpty()) {
        return true
    } else {
        return when (player1Response) {
            1 -> if (chooseRandomEnemy in listOf(2, 3, 4, 6)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }

            2 -> if (chooseRandomEnemy in listOf(3, 4, 6)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }

            3 -> if (chooseRandomEnemy in listOf(4, 6)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }

            4 -> if (chooseRandomEnemy in listOf(5, 7)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }

            5 -> if (chooseRandomEnemy in listOf(1, 2, 3)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true

            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }
            6 -> if (chooseRandomEnemy in listOf(5, 7)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }

            7 -> if (chooseRandomEnemy in listOf(1, 2, 3)) {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter won the battle, your enemy is rank as ${redColor}$enemyCharacter${resetColor}")
                println()
                true
            } else {
                println("${yellowColor}Arbiter:${resetColor} Your $yourCharacter ${redColor}lose the battle${resetColor}, please select another fighter")
                println()
                false
            }
            else -> {
                println("")
                false
            }
        }
    }
}

fun instructions(){
    // This is a method for instructions I just call this on my main to display instructions about my game

    var enemy_Characters = mutableListOf("5 Star General", "3 Star General", "Colonel", "Private", "Spy", "Private", "Spy")

    val resetColor = "\u001B[0m"
    val redColor = "\u001B[31m"

    println("\n ${redColor}Instructions:${resetColor} Please read the instructions carefully to understand the game" +
            "\n \n 1. Each player starts with 7 pieces, which are: $enemy_Characters" +
            "\n \n 2. Every turn, you have a hidden opponent that you have to battle by choosing one of the characters on your team." +
            "\n \n 3. Every character has its own rank. You can only defeat your enemy if the rank of your character is higher." +
            "\n \n 3. If the player's piece matches the enemy's piece, the enemy wins that battle, and the rank of your enemy piece remains hidden" +
            "\n \n 4. When one of your character is defeated, you must choose another character from your team to battle the unknown enemy piece. " +
            "\n \n 5. Only when you defeat the enemy character will it show its rank to you." +
            "\n \n 5. Be strategic in choosing your pieces to battle based on the enemy character possible rank." +
            "\n \n 6. The arbiter will judge if the rank of your piece is superior to the enemy or not" +
            "\n \n 7. To win the game, defeat all of your opponent's pieces." +
            "\n \n 8. You can also win if you still have higher-ranking character than your opponent, making it impossible for your opponent to counter. \n")

    println("${redColor}Piece Ranking Power in Combat:${resetColor} " +
            "\n \n 1. 5-Star General defeats any piece except the Spy." +
            "\n \n 2. 3-Star General defeats Colonel and Private." +
            "\n \n 3. Colonel defeats Private." +
            "\n \n 4. Private defeats Spy." +
            "\n \n 5. Spy defeats any piece except the Private \n")

    val yellowColor = "\u001B[33m"
    println("Title: Games of the Generals made by ${yellowColor}Gian Lim${resetColor}\n")
}

