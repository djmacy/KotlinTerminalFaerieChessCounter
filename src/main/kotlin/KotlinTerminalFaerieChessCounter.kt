// David Macy
// This is a terminal application that asks the user which pieces it would like to use for the Brybelly faerie chess board game.
// once all the pieces have been selected it will count up how many points the user has left or if they picked too many pieces.
// this was created so people do not need to count up the points themselves but rather have the application do it and let them know if they have points to spare.

fun main() {
    FaerieChessCounter().faerieChessCounter()
}

enum class Pieces(val value: Int, val minPieces: Int, val maxPieces: Int) {
    PAWN(1, 4, 8),
    PEASANT(2, 0, 2),
    SOLDIER(3, 0, 2),
    ROOK(9, 0, 2),
    KNIGHT(4, 0, 2),
    BISHOP(6, 0, 2),
    CATAPULT(3, 0, 1),
    CHAMBERLAIN(6, 0, 1),
    COURTESAN(6, 0, 1),
    HERALD(6, 0, 1),
    INQUISITOR(8, 0, 1),
    LANCER(5, 0, 1),
    PONTIFF(8, 0, 1),
    THIEF(5, 0, 1),
    TOWER(10, 0, 1),
    QUEEN(12, 0, 1),
    JESTER(12, 0, 1),
    KING(0, 0, 1),
    REGENT(15, 0, 1)
}

class FaerieChessCounter {

    /**
     * This method allows for a chess pieces that can be selected more once to have its value assigned. It will also
     * check to see if the user has given a valid input, which is an integer from the min to max value provided. It then
     * returns the number of pieces selected per piece.
     * @param statement - Statement that is asked to the user asking if they want that piece and how many
     * @param min - Min is the minimum number of pieces that may be selected for that piece
     * @param max - Max is the maximum number of pieces that may be selected for that piece
     * @return the number of selected pieces
     */
    private fun checkForValidClassicalInput(statement: String, min: Int, max: Int): Int {
        val errStr = "Invalid input. Please enter a number between $min and $max."
        while (true) {
            print(statement)
            //var can be reassigned. val cannot be reassigned to a new value. Similar to Java final I think.
            val userInput = readLine()
            if (userInput != null && userInput.matches(Regex("\\d+"))) {
                val intValue = userInput.toInt()
                //.. will help check that intValue is: min <= intValue <= max
                if (intValue in min..max) {
                    return intValue
                }
            }
            println(errStr)
        }
    }

    /**
     * This method allows for a chess pieces that can be selected none or once to have its value assigned by the user.
     * It will also check to see if the user has given a valid input, which is y for yes and n for no. It will then
     * return the number of selected pieces 0 or 1.
     * @param statement - Statement that is asked to the user asking if they want that piece
     * @return the number of selected pieces
     */
    private fun checkForValidFaerieInput(statement: String): Int {
        while(true) {
            print(statement)
            when (readLine()?.lowercase()) {
                "y" -> {
                    return 1
                }
                "n" -> {
                    return 0
                }
                else -> {
                    println("Please insert y or n")
                }
            }
        }
    }

    /**
     * This method will calculate how many points the user has after selecting all of their rank I pieces.
     */
    private fun calculateRankIPoints(): Int {
        var rank1 = 0
        var totalPoints = 0
        val soldierLimit = Pieces.SOLDIER.maxPieces
        //The user can only select 8 rank I pieces so it will break when the user selects more than 8
        while (true) {
            val pawn = checkForValidClassicalInput("How many pawns would you like: ", Pieces.PAWN.minPieces, Pieces.PAWN.maxPieces)
            //rank_1 will determine when we have selected the right number of pieces. 8 is the maximum number of rank_1 pieces
            rank1 += pawn
            //This variable will keep track of how many points the user has until the end of the script
            totalPoints += pawn * Pieces.PAWN.value

            var peasant = 0
            var soldier = 0
            if (rank1 == 8) {
                println("You have selected the maximum number of Rank I pieces")
                break
            } else if (rank1 == 4) {
                peasant = 2
                soldier = 2
                totalPoints += (peasant * Pieces.PEASANT.value + soldier * Pieces.SOLDIER.value)
                println("Due to picking 4 pawns you will automatically get 2 peasants and 2 soldiers")
                break
            } else if (rank1 > 4) {
                val peasantMin = maxOf(0, 8 - (rank1 + soldierLimit))
                val peasantLimit = minOf(2, 8 - rank1)
                println("You can select from $peasantMin to $peasantLimit peasants")
                peasant = checkForValidClassicalInput("How many peasants would you like: ", peasantMin, peasantLimit)
                totalPoints += peasant * Pieces.PEASANT.value
                rank1 += peasant
                if (rank1 == 8) {
                    println("You have picked the maximum number of pieces")
                    break
                } else if (rank1 == 6) {
                    println("You automatically get 2 soldiers")
                    soldier = 2
                    totalPoints += soldier * Pieces.SOLDIER.value
                    rank1 += soldier
                } else if (rank1 == 7) {
                    println("You automatically get 1 soldier")
                    soldier = 1
                    totalPoints += soldier * Pieces.SOLDIER.value
                    rank1 += soldier
                }
            }
            println("You selected $pawn Pawns, $peasant Peasants, $soldier Soldiers")
        }
        return totalPoints
    }

    /**
     * This method will calculate how many points the user has after selecting all of their rank II pieces.
     * @param totalPoints - Total Points from selecting the rank I pieces
     */
    private fun calculateRankII(totalPoints: Int): Int {

        val rankIIPieces = listOf<Pieces>(
            Pieces.ROOK, Pieces.KNIGHT, Pieces.BISHOP, Pieces.CATAPULT,
            Pieces.CHAMBERLAIN, Pieces.COURTESAN, Pieces.HERALD,
            Pieces.INQUISITOR, Pieces.LANCER, Pieces.PONTIFF, Pieces.THIEF,
            Pieces.TOWER
        )
        while (true) {
            var rank2 = 0
            var rank2StartingPoints = totalPoints

            for (piece in rankIIPieces) {
                var selectedPieces: Int
                if (piece.maxPieces == 2) {
                    selectedPieces = checkForValidClassicalInput(
                        "How many $piece" + "s would you like: ", piece.minPieces, piece.maxPieces
                    )
                } else if (piece.maxPieces == 1) {
                    println("\nFor the rest of these insert 'y' for yes and 'n' for no")
                    selectedPieces = checkForValidFaerieInput("Would you like a ${piece.name}?")
                } else {
                    selectedPieces = 0
                }
                rank2StartingPoints += selectedPieces * (piece.value)
                rank2 += selectedPieces
                if (rank2 == 6) {
                    break
                }
            }
            if (rank2 == 6) {
                return rank2StartingPoints
            } else {
                println("You only selected $rank2 pieces. Be sure to select 6 rank II pieces. The count will now reset!")
            }
        }
    }
    
    /**
     * This method will calculate how many points the user has after selecting all of their rank III pieces.
     */
    private fun calculateRankIII(totalPoints: Int, maxPoints: Int): Int {
        var rank3 = 0
        var currentPoints = totalPoints
        //Can only select 2 rank_III pieces
        while (true) {
            println("\nFor these insert 'y' for yes and 'n' for no\nWould you like a queen?")
            val queen = readLine()?.lowercase()
            if (queen == "y") {
                val queen = 1
                currentPoints += queen * Pieces.QUEEN.value
            } else if (queen == "n") {
                println("Since you did not select a queen, you automatically get a jester")
                val jester = 1
                currentPoints += jester * Pieces.JESTER.value
            } else {
                println("Invalid input. Please select 'y' or 'n'")
                continue
            }
            //It will only allow the user to pick if he has enough points for a regent and even then the user can select whether he/she wants it or not
            if (maxPoints - currentPoints <= Pieces.REGENT.value) {
                println("You do not have enough points for a regent, so you will have to get a king.")
                val king = 1
                currentPoints += king * Pieces.KING.value
                rank3 += king
                break
            }
            println("Would you like a King?")
            val king = readLine()?.lowercase()
            if (king == "y") {
                val king = 1
                currentPoints += king * Pieces.KING.value
            } else if (king == "n") {
                println("Since you did not select a king, you automatically get a regent")
                val regent = 1
                currentPoints += regent * Pieces.REGENT.value
                break
            } else {
                println("Invalid input. Please select 'y' or 'n'")
                continue
            }
        }

        return currentPoints
    }

    /**
     * This method will allow the user to pick their difficulty which sets the points they can use to select their
     * pieces. Beginner allows the user to have 65 pts, Intermediate 70, and Advanced 75.
     */
    private fun chooseDifficulty(): Int {
        var chooseDiff = true

        println(
            "Hello! Welcome to the Faerie Chess Counter\n\nBegin by choosing the difficulty. " +
                    "Insert B for beginner, I for intermediate, and A for advanced"
        )

        var maxPoints = 0
        //This is where the user will select which difficulty they are playing on. The difficulty will determine how many points
        //they can have when choosing their pieces
        while (chooseDiff) {
            val difficulty = readLine()?.uppercase()

            when (difficulty) {
                "B" -> {
                    println("Great! You will play with 60 - 65 points")
                    maxPoints = 65
                    chooseDiff = false
                }

                "I" -> {
                    println("Great! You will play with 65 - 70 points")
                    maxPoints = 70
                    chooseDiff = false
                }

                "A" -> {
                    println("Great! You will play with 70 - 75 points")
                    maxPoints = 75
                    chooseDiff = false
                }

                else -> {
                    println("Please choose B, I, or A")
                }
            }
        }

        return maxPoints
    }

    /**
     * This method runs the logic of the entire program. It starts by asking the difficulty and assigning the number of
     * points they have to work with. After that it will run the methods that ask the user what pieces they want.
     */
    fun faerieChessCounter() {
        while (true) {
            val maxPoints = chooseDifficulty()
            var totalPoints = calculateRankIPoints()

            println(
                "Total points for Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} " +
                        "points left!\nNow let's pick your Rank II pieces. You can only select 6 Rank II pieces."
            )

            totalPoints = calculateRankII(totalPoints)
            println("You have selected all of your Rank II pieces")
            println(
                "Total points for Rank II & Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} " +
                        "points left!\nNow let's pick your Rank III pieces. You can only select 2 Rank III pieces."
            )

            totalPoints = calculateRankIII(totalPoints, maxPoints)
            println("You have selected all of your Rank III pieces")
            println("Total points for Rank III pieces: $totalPoints. You have ${maxPoints - totalPoints} points left!")
            println("Would you like to go again?")
            val redo = readLine()?.lowercase()
            if (redo != "y") {
                println("Thank you. See you next time!")
                break
            }
        }
    }
}