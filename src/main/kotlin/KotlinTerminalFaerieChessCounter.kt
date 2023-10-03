// David Macy
// This is a terminal application that asks the user which pieces it would like to use for the Brybelly faerie chess board game.
// once all the pieces have been selected it will count up how many points the user has left or if they picked too many pieces.
// this was created so people do not need to count up the points themselves but rather have the application do it and let them know if they have points to spare.

fun main() {
    val chessCounter = FaerieChessCounter()
    chessCounter.main()
}

enum class PieceValue(val value: Int) {
    PAWN(1),
    PEASANT(2),
    SOLDIER(3),
    ROOK(9),
    KNIGHT(4),
    BISHOP(6),
    CATAPULT(3),
    CHAMBERLAIN(6),
    COURTESAN(6),
    HERALD(6),
    INQUISITOR(8),
    LANCER(5),
    PONTIFF(8),
    THIEF(5),
    TOWER(10),
    QUEEN(12),
    JESTER(12),
    KING(0),
    REGENT(15)
}

class FaerieChessCounter {
    //This allows me to use something similar to the input function in python and insert a statement
    private fun checkForValidInput(statement: String): Int {
        while (true) {
            print(statement)
            //var can be reassigned. val cannot be reassigned to a new value. Similar to Java final I think.
            val userInput = readLine()
            if (userInput != null && userInput.matches(Regex("\\d+"))) {
                return userInput.toInt()
            } else {
                println("Invalid input. Please insert the correct value.")
            }
        }
    }

    private fun calculateRankIPoints(minPoints: Int, maxPoints: Int): Int {
        var rank1 = 0
        var totalPoints = 0
        //This is the maximum number of pieces available for each piece.
        val pawnLimit = 8
        val peasantLimit = 2
        val soldierLimit = 2
        val pawnMin = 4
        //The user can only select 8 rank I pieces so it will break when the user selects more than 8
        while (rank1 < 8) {
            val pawn = checkForValidInput("How many pawns would you like: ")
            var peasant = 0
            var soldier = 0

            if (pawn < pawnMin || pawn > pawnLimit) {
                println("Invalid input. Please select between $pawnMin and $pawnLimit pawns.")
                continue
            }
            //This variable will keep track of how many points the user has until the end of the script
            totalPoints += pawn * PieceValue.PAWN.value
            //rank_1 will determine when we have selected the right number of pieces. 8 is the maximum number of rank_1 pieces
            rank1 += pawn

            if (rank1 == 8) {
                println("You have selected the maximum number of Rank I pieces")
                break
            } else if (rank1 == 4) {
                peasant = 2
                soldier = 2
                totalPoints += (peasant * PieceValue.PEASANT.value + soldier * PieceValue.SOLDIER.value)
                println("Due to picking 4 pawns you will automatically get 2 peasants and 2 soldiers")
                break
            } else if (rank1 > 4) {
                val peasantMin = maxOf(0, 8 - (rank1 + soldierLimit))
                val peasantLimit = minOf(2, 8 - rank1)

                println("You can select from $peasantMin to $peasantLimit peasants")
                peasant = checkForValidInput("How many peasants would you like: ")

                if (peasant < peasantMin || peasant > peasantLimit) {
                    println("Invalid input. Please select between $peasantMin and $peasantLimit peasants.")
                    rank1 = 0
                    continue
                }

                totalPoints += peasant * PieceValue.PEASANT.value
                rank1 += peasant

                if (rank1 == 8) {
                    println("You have picked the maximum number of pieces")
                    break
                } else if (rank1 == 6) {
                    println("You automatically get 2 soldiers")
                    soldier = 2
                    totalPoints += soldier * PieceValue.SOLDIER.value
                    rank1 += soldier
                } else if (rank1 == 7) {
                    println("You automatically get 1 soldier")
                    soldier = 1
                    totalPoints += soldier * PieceValue.SOLDIER.value
                    rank1 += soldier
                }
            }
            println("You selected $pawn Pawns, $peasant Peasants, $soldier Soldiers")
        }
        return totalPoints
    }

    //User can only select 6 rank_2 pieces and will break when the user has selected more than 6
    private fun calculateRankII(totalPoints: Int, maxPoints: Int): Int {
        //classical_limit is 2 because in the original game of chess you have 2 rooks, 2 bishops, and 2 knights
        val classicalLimit = 2
        var rank2 = 0
        while (rank2 < 6) {
            //This will make sure that we get to six total pieces selected
            rank2 = 0
            //restart the total points calculated to what the total was after rank_1 calculation if loop gets reset to
            var rank2StartingPoints = totalPoints

            var rook = checkForValidInput("How many rooks would you like: ")
            if (rook > classicalLimit || rook < 0) {
                rook = 0
                println("Invalid input. Please select between 0 and $classicalLimit rooks.")
                continue
            }
            rank2StartingPoints += rook * PieceValue.ROOK.value
            rank2 += rook

            var knight = checkForValidInput("How many knights would you like: ")
            if (knight > classicalLimit || knight < 0) {
                knight = 0
                println("Invalid input. Please select between 0 and $classicalLimit knights.")
                continue
            }
            rank2StartingPoints += knight * PieceValue.KNIGHT.value
            rank2 += knight

            var bishop = checkForValidInput("How many bishops would you like: ")
            if (bishop > classicalLimit || bishop < 0) {
                bishop = 0
                println("Invalid input. Please select between 0 and $classicalLimit bishops.")
                continue
            }
            rank2StartingPoints += bishop * PieceValue.BISHOP.value
            rank2 += bishop

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            //There is only one of each piece that you can select from after you are done selecting from rooks, knights, and bishops.
            println("\nFor the rest of these insert 'y' for yes and 'n' for no")
            println("Would you like a catapult?")
            val catapult = readLine()?.toLowerCase()
            if (catapult == "y") {
                val catapult = 1
                rank2StartingPoints += catapult * PieceValue.CATAPULT.value
                rank2 += catapult
                //If the user provides bad input we will restart the entire loop
            } else if (catapult != "n" && catapult != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a chamberlain?")
            val chamberlain = readLine()?.toLowerCase()
            if (chamberlain == "y") {
                val chamberlain = 1
                rank2StartingPoints += chamberlain * PieceValue.CHAMBERLAIN.value
                rank2 += chamberlain
            } else if (chamberlain != "n" && chamberlain != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a courtesan?")
            val courtesan = readLine()?.toLowerCase()
            if (courtesan == "y") {
                val courtesan = 1
                rank2StartingPoints += courtesan * PieceValue.COURTESAN.value
                rank2 += courtesan
            } else if (courtesan != "n" && courtesan != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a herald?")
            val herald = readLine()?.toLowerCase()
            if (herald == "y") {
                val herald = 1
                rank2StartingPoints += herald * PieceValue.HERALD.value
                rank2 += herald
            } else if (herald != "n" && herald != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like an inquisitor?")
            val inquisitor = readLine()?.toLowerCase()
            if (inquisitor == "y") {
                val inquisitor = 1
                rank2StartingPoints += inquisitor * PieceValue.INQUISITOR.value
                rank2 += inquisitor
            } else if (inquisitor != "n" && inquisitor != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a lancer?")
            val lancer = readLine()?.toLowerCase()
            if (lancer == "y") {
                val lancer = 1
                rank2StartingPoints += lancer * PieceValue.LANCER.value
                rank2 += lancer
            } else if (lancer != "n" && lancer != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a pontiff?")
            val pontiff = readLine()?.toLowerCase()
            if (pontiff == "y") {
                val pontiff = 1
                rank2StartingPoints += pontiff * PieceValue.PONTIFF.value
                rank2 += pontiff
            } else if (pontiff != "n" && pontiff != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a thief?")
            val thief = readLine()?.toLowerCase()
            if (thief == "y") {
                val thief = 1
                rank2StartingPoints += thief * PieceValue.THIEF.value
                rank2 += thief
            } else if (thief != "n" && thief != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            }
            println("Would you like a tower?")
            val tower = readLine()?.toLowerCase()
            if (tower == "y") {
                val tower = 1
                rank2StartingPoints += tower * PieceValue.TOWER.value
                rank2 += tower
            } else if (tower != "n" && tower != "y") {
                rank2 = 0
                println("Please insert a valid value: 'y' or 'n'")
                continue
            }

            if (rank2 >= 6) {
                return rank2StartingPoints
            } else {
                println("You only selected $rank2 pieces. Be sure to select 6 rank II pieces. The count will now reset!")
                continue
            }
        }

        return totalPoints
    }

    private fun calculateRankIII(totalPoints: Int, maxPoints: Int): Int {
        var rank3 = 0
        var currentPoints = totalPoints
        //Can only select 2 rank_III pieces
        while (rank3 < 2) {
            println("\nFor these insert 'y' for yes and 'n' for no\nWould you like a queen?")

            val queen = readLine()?.toLowerCase()
            if (queen == "y") {
                val queen = 1
                currentPoints += queen * PieceValue.QUEEN.value
            } else if (queen == "n") {
                println("Since you did not select a queen, you automatically get a jester")
                val jester = 1
                currentPoints += jester * PieceValue.JESTER.value
            } else {
                println("Invalid input. Please select 'y' or 'n'")
                continue
            }
            //It will only allow the user to pick if he has enough points for a regent and even then the user can select whether he/she wants it or not
            if (maxPoints - currentPoints <= PieceValue.REGENT.value) {
                println("You do not have enough points for a regent, so you will have to get a king.")
                val king = 1
                currentPoints += king * PieceValue.KING.value
                rank3 += king
                break
            }
            println("Would you like a King?")
            val king = readLine()?.toLowerCase()
            if (king == "y") {
                val king = 1
                currentPoints += king * PieceValue.KING.value
            } else if (king == "n") {
                println("Since you did not select a king, you automatically get a regent")
                val regent = 1
                currentPoints += regent * PieceValue.KING.value
                break
            } else {
                println("Invalid input. Please select 'y' or 'n'")
                continue
            }
        }

        return currentPoints
    }

    //Pair in kotlin allows me to store and return two variables of the same or different type
    private fun chooseDifficulty(): Pair<Int, Int> {
        var chooseDiff = true

        println(
            "Hello! Welcome to the Faerie Chess Counter\n\nBegin by choosing the difficulty. " +
                    "Insert B for beginner, I for intermediate, and A for advanced"
        )

        var minPoints = 0
        var maxPoints = 0
        //This is where the user will select which difficulty they are playing on. The difficulty will determine how many points
        //they can have when choosing their pieces
        while (chooseDiff) {
            val difficulty = readLine()?.toUpperCase()

            when (difficulty) {
                "B" -> {
                    println("Great! You will play with 60 - 65 points")
                    minPoints = 60
                    maxPoints = 65
                    chooseDiff = false
                }

                "I" -> {
                    println("Great! You will play with 65 - 70 points")
                    minPoints = 65
                    maxPoints = 70
                    chooseDiff = false
                }

                "A" -> {
                    println("Great! You will play with 70 - 75 points")
                    minPoints = 70
                    maxPoints = 75
                    chooseDiff = false
                }

                else -> {
                    println("Please choose B, I, or A")
                }
            }
        }

        return Pair(minPoints, maxPoints)
    }
    fun main() {
        var run = true

        while (run) {
            val (minPoints, maxPoints) = chooseDifficulty()
            var totalPoints = calculateRankIPoints(minPoints, maxPoints)

            println(
                "Total points for Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} " +
                        "points left!\nNow let's pick your Rank II pieces. You can only select 6 Rank II pieces."
            )

            totalPoints = calculateRankII(totalPoints, maxPoints)
            println("You have selected all of your Rank II pieces")
            println(
                "Total points for Rank II & Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} " +
                        "points left!\nNow let's pick your Rank III pieces. You can only select 2 Rank III pieces."
            )

            totalPoints = calculateRankIII(totalPoints, maxPoints)
            println("You have selected all of your Rank III pieces")
            println("Total points for Rank III pieces: $totalPoints. You have ${maxPoints - totalPoints} points left!")
            println("Would you like to go again?")
            val redo = readLine()?.toLowerCase()
            if (redo != "y") {
                println("Thank you. See you next time!")
                break
            } else if (redo == "y") {
                run = true
            }
        }
    }
}