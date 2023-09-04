fun checkForRankII(piece: Int) {
    if (piece > 1 || piece < 0) {
        println("Please select a value between 0 and 1: $piece")
    }
}

fun main() {
    var run = true

    while (run) {
        var chooseDiff = true
        var totalPoints = 0
        var minPoints = 0
        var maxPoints = 0
        var rank1 = 0
        var rank2 = 0
        var rank3 = 0
        var pawn = 0
        var peasant = 0
        var soldier = 0


        println("Hello! Welcome to the Faerie Chess Counter\n\nBegin by choosing the difficulty. " +
                "Insert B for beginner, I for intermediate, and A for advanced")

        //This is where the user will select which difficulty they are playing on. The difficulty will determine how many points
        //they can have when choosing their pieces
        while (chooseDiff) {
            print("\nWhat difficulty are you playing on: ")
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
                else -> println("Please choose B, I, or A")
            }
        }
        //The user can only select 8 rank I pieces so it will break when the user selects more than 8
        while (rank1 < 8) {
            rank1 = 0
            totalPoints = 0
            //This is how many points the each piece is worth
            val pawnValue = 1
            val peasantValue = 2
            val soldierValue = 3
            //This is the maximum number of pieces available for each piece.
            val pawnLimit = 8
            val peasantLimit = 2
            val soldierLimit = 2

            print("How many pawns would you like: ")
            //if string cannot be converted to int then null, and if null its 0
            pawn = readLine()?.toIntOrNull() ?: 0

            if (pawn < 4 || pawn > pawnLimit) {
                println("Invalid input. Please select between 4 and $pawnLimit pawns.")
                continue
            }
            //This variable will keep track of how many points the user has until the end of the script
            totalPoints += pawn * pawnValue
            //rank_1 will determine when we have selected the right number of pieces. 8 is the maximum number of rank_1 pieces
            rank1 += pawn

            if (rank1 == 8) {
                println("You have selected the maximum number of Rank I pieces")
                break
            } else if (rank1 == 4) {
                peasant = 2
                soldier = 2
                totalPoints += (peasant * peasantValue + soldier * soldierValue)
                println("Due to picking 4 pawns, you will automatically get 2 peasants and 2 soldiers")
                break
            } else if (rank1 > 4) {
                val peasantMin = maxOf(0, 8 - (rank1 + soldierLimit))
                val peasantLimit = minOf(2, 8 - rank1)

                println("You can select from $peasantMin to $peasantLimit peasants")
                print("How many peasants would you like: ")
                peasant = readLine()?.toIntOrNull() ?: 0

                if (peasant < peasantMin || peasant > peasantLimit) {
                    println("Invalid input. Please select between $peasantMin and $peasantLimit peasants.")
                    continue
                }

                totalPoints += peasant * peasantValue
                rank1 += peasant
            }

            if (rank1 == 8) {
                println("You have picked the maximum number of pieces")
                break
            } else if (rank1 == 6) {
                println("You automatically get 2 soldiers")
                soldier = 2
                totalPoints += soldier * soldierValue
                rank1 += soldier
            } else if (rank1 == 7) {
                println("You automatically get 1 soldier")
                soldier = 1
                totalPoints += soldier * soldierValue
                rank1 += soldier
            }
        }
        println("You selected $pawn Pawns, $peasant Peasants, $soldier Soldiers")
        println("Total points for Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} points left!\n" +
                "Now let's pick your Rank II pieces. You can only select 6 Rank II pieces.")


        val rank1Points = totalPoints
        var doneSelecting = false
        //User can only select 6 rank_2 pieces and will break when the user has selected more than 6
        while (rank2 < 6 && !doneSelecting) {

            var catapult = 0;
            var courtesan = 0;
            var chamberlain = 0;
            var herald = 0;
            var inquisitor = 0;
            var lancer = 0;
            var pontiff = 0;
            var thief = 0;
            var tower = 0;

            totalPoints = rank1Points
            //This is the value of each rank_II piece
            val rookValue = 9
            val knightValue = 4
            val bishopValue = 6
            val catapultValue = 3
            val chamberlainValue = 6
            val courtesanValue = 6
            val heraldValue = 6
            val inquisitorValue = 6
            val lancerValue = 5
            val pontiffValue = 8
            val thiefValue = 5
            val towerValue = 10

            val classicalLimit = 2
            val newLimit = 1
            //This will make sure that we get to six total pieces selected
            rank2 = 0
            //Again similar with the Rank I pieces we have a limited number of pieces that the user can pick. For rank_II its 6
            print("How many rooks would you like: ")

            val rook = readLine()?.toIntOrNull() ?: 0
            //classical_limit is 2 because in the original game of chess you have 2 rooks, 2 bishops, and 2 knights
            if (rook > classicalLimit || rook < 0) {
                println("Invalid input. Please select between 0 and $classicalLimit rooks.")
                continue
            }

            totalPoints += rook * rookValue
            rank2 += rook

            print("How many knights would you like: ")
            val knight = readLine()?.toIntOrNull() ?: 0

            if (knight > classicalLimit || knight < 0) {
                println("Invalid input. Please select between 0 and $classicalLimit knights.")
                continue
            }

            totalPoints += knight * knightValue
            rank2 += knight

            print("How many bishops would you like: ")
            val bishop = readLine()?.toIntOrNull() ?: 0

            if (bishop > classicalLimit || bishop < 0) {
                println("Invalid input. Please select between 0 and $classicalLimit bishops.")
                continue
            }

            totalPoints += bishop * bishopValue
            rank2 += bishop

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            println("\nFor the rest of these, insert y for yes and n for no")

            print("Would you like a catapult: ")
            var userInput = readLine()

            when (userInput) {
                "y" -> {
                    catapult = 1
                    checkForRankII(catapult)
                    totalPoints += catapult * catapultValue
                    rank2 += catapult
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }



            print("Would you like a courtesan: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    courtesan = 1
                    checkForRankII(courtesan)
                    totalPoints += courtesan * courtesanValue
                    rank2 += courtesan
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            print("Would you like a chamberlain: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    chamberlain = 1
                    checkForRankII(chamberlain)
                    totalPoints += chamberlain * chamberlainValue
                    rank2 += chamberlain
                }
                "n" -> {
                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            print("Would you like a herald: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    herald = 1
                    checkForRankII(herald)
                    totalPoints += herald * heraldValue
                    rank2 += herald
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            print("Would you like a inquisitor: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    inquisitor = 1
                    checkForRankII(inquisitor)
                    totalPoints += inquisitor * inquisitorValue
                    rank2 += inquisitor
                }
                "n" -> {
                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            print("Would you like a lancer: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    lancer = 1
                    checkForRankII(lancer)
                    totalPoints += lancer * lancerValue
                    rank2 += lancer
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            print("Would you like a pontiff: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    pontiff = 1
                    checkForRankII(pontiff)
                    totalPoints += pontiff * pontiffValue
                    rank2 += pontiff
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }
            print("Would you like a thief: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    thief = 1
                    checkForRankII(thief)
                    totalPoints += thief * thiefValue
                    rank2 += thief
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }
            print("Would you like a tower: ")
            userInput = readLine()

            when (userInput) {
                "y" -> {
                    tower = 1
                    checkForRankII(tower)
                    totalPoints += tower * towerValue
                    rank2 += tower
                }
                "n" -> {

                }
                else -> {
                    rank2 = 0
                    println("Please insert a valid value: y or n")
                    println("Remember you can only pick 6 pieces")
                    continue
                }
            }

            if (rank2 == 6) {
                doneSelecting = true
                break
            }

            println("You selected " + rank2 + " pieces. The count will now restart. \n" +
                    "Be sure to select 6 pieces.")
        }

        println("You have selected all of your Rank II pieces")
        println("Total points for Rank II & Rank I pieces: $totalPoints. You have ${maxPoints - totalPoints} " +
                "points left!\nNow let's pick your Rank III pieces. You can only select 2 Rank III pieces.")

        val rankIIIPoints = totalPoints

        while (rank3 < 2) {
            rank3 = 0
            totalPoints = rankIIIPoints

            var queen = 0;
            var king = 0;
            var jester = 0;
            var regent = 0;

            val queenValue = 12
            val kingValue = 0
            val jesterValue = 12
            val regentValue = 15

            println("Enter y for yes and n for no")

            print("Would you like a queen: ")
            var userInput = readLine()

            if (userInput != "y" && userInput != "n") {
                println("Please insert y or n")
                continue
            } else if (userInput == "y") {
                queen = 1
                totalPoints += queen * queenValue
                rank3 += queen
            } else {
                println("Since you did not select a queen, you automatically get a jester")
                jester = 1
                totalPoints += jester * jesterValue
            }

            if (maxPoints - totalPoints <= regentValue) {
                println("You do not have enough points for a regent, so you will have to get a king.")
                val king = 1
                totalPoints += king * kingValue
                rank3 += king
                break
            }

            print("Would you like a king: ")
            userInput = readLine()

            if (userInput != "y" && userInput != "n") {
                println("Please insert y or no")
                continue
            } else if (userInput == "y") {
                king = 1
                totalPoints += king * kingValue
                rank3 += king
                break
            } else {
                println("Since you did not select a king, you automatically get a regent")
                regent = 1
                totalPoints += regent * regentValue
                break
            }
        }

        println("You have selected all of your Rank III pieces")
        println("Total points for Rank III pieces: $totalPoints. You have ${maxPoints - totalPoints} points left!")

        print("Would you like to change the difficulty or pick different pieces? Insert y or n: ")
        val redo = readLine()?.toLowerCase()
        if (redo != "y") {
            println("Thank you. See you next time!")
            break
        }
    }
}
