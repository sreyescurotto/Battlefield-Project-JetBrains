package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BattleField battleField = new BattleField();
        BattleField battleField2 = new BattleField();// create new field
        battleField.drawField(2);                                                  // output new field for the first time
        try (Scanner scanner = new Scanner(System.in)) {
            battleField.setNewShips(scanner);
            System.out.println("\r\nPress Enter and pass the move to another player!");
            scanner.nextLine();
            System.out.println("\r\n...");
            System.out.println("\r\nPlayer 2, place your ships to the game field!");
            battleField2.drawField(2);
            battleField2.setNewShips(scanner);
            System.out.println("\r\nThe game starts!");
            battleField.drawField(2);
            System.out.print("Take a shot!\r\n\r\n> ");
            boolean shipsLeft = true;
            while (shipsLeft) {
                shipsLeft = battleField.shoot(scanner);
            }
        }
    }

}

class BattleField {

    public char[][] field;                                                           // battlefield has a 2D array field
    public String[] playerShips = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};      // ships
    public int[] shipSizes = {5, 4, 3, 3, 2};        // ships cells at the start, indexes must correspond to playerShips
    static int shipCellsLeft;


    public BattleField() {
        /* constructor creates new battlefield and sets all fields with fog of war
        array stores only fields of battlefield
        headers of the battlefield and separators are drawn only when displayed
        also sets all ship fields as not hit
        also collects number of ship cells left in order to check cells left when hit */

        field = new char[10][10];
        for (char[] chars : field) {
            Arrays.fill(chars, BFConstants.fogOfWar);
        }

        for (int cellsLeft : shipSizes) {
            shipCellsLeft += cellsLeft;
        }
    }

    public void drawField(int mode) {
        /* represents all array fields as 2D battlefield with battlefield headers and separators
        mode 1 = represents array masking ship indexes by a ship sign (array fields contain indexes of ships)
        mode 2 = represents array hiding all non hit or missed fields
        other modes (0) = represent array as it is without hiding any values (for development) */

        char separator = ' ';

        System.out.println();                                                      // new lane at the start of the field

        for (int i = 0; i <= field.length; i++) {
            for (int j = 0; j <= field[0].length; j++) {
                if (i == 0) {
                    if (j == 0) {                                        // for first row in first column set two spaces
                        System.out.print(separator + "" + separator);
                    } else if (j < field[0].length) {   // for first row and every column except from first and last one
                        System.out.print(j + "" + separator);         // set number of the column plus space as a header
                    } else {          // for first row and last column set number of the column as a header of the field
                        System.out.print(j);
                    }
                } else if (j == 0) {                         // for first column for every row except from the first one
                    System.out.print(getCharForNumber(i, 64) + "" + separator);         // set a letter as a header
                } else if (j < field[0].length) {   // for every row and column ex. for first row, first and last column
                    if (field[i - 1][j - 1] == BFConstants.fogOfWar ||
                            field[i - 1][j - 1] == BFConstants.miss ||
                            field[i - 1][j - 1] == BFConstants.hit)
                        System.out.print(field[i - 1][j - 1] + "" + separator);// set corresponding content of the array
                    else if (mode == 2) {
                        System.out.print(BFConstants.fogOfWar + "" + separator);
                    } else if (mode == 1) {
                        System.out.print(BFConstants.ship + "" + separator);
                    } else {
                        System.out.print(field[i - 1][j - 1] + "" + separator);// set corresponding content of the array
                    }
                } else { // for last column in every row except the first one set the corresponding content of the array
                    if (field[i - 1][j - 1] == BFConstants.fogOfWar ||
                            field[i - 1][j - 1] == BFConstants.miss ||
                            field[i - 1][j - 1] == BFConstants.hit)
                        System.out.print(field[i - 1][j - 1]);                 // set corresponding content of the array
                    else if (mode == 2) {
                        System.out.print(BFConstants.fogOfWar);
                    } else if (mode == 1) {
                        System.out.print(BFConstants.ship);
                    } else {
                        System.out.print(field[i - 1][j - 1]);                 // set corresponding content of the array
                    }
                }
            }
            System.out.println();                                                     // new line at the end of each row
        }

        System.out.println();                                                        // new line at the end of the field
    }

    private char getCharForNumber(int i, int shift) {
        // gets a char from unicode table with a given number, shifted by a given value

        return (char) (i + shift);
    }

    private int getNumberForChar(char i, int shift) {
        // gets number of a char from unicode table with a given char, shifted by a given value

        return i + shift;
    }

    public void setNewShips(Scanner scanner) {

        for (int i = 0; i < shipSizes.length; i++) {
            int[] coordinates = new int[0];
            boolean needsCheck = true;
            System.out.printf("Enter the coordinates of the %s (%d cells):\r\n\r\n> ",
                    playerShips[i], shipSizes[i]);
            while (needsCheck) {                                          // until the input is verified - ask once more
                String firstCoordinate = scanner.next().toUpperCase();
                String secondCoordinate = scanner.next().toUpperCase();
                try {
                    coordinates = getCoordinates(parseCoordinate(firstCoordinate), parseCoordinate(secondCoordinate));
                    needsCheck = isCoordinatesWrong(coordinates, shipSizes[i], playerShips[i]);
                } catch (Exception e) {
                    System.out.print("\r\nError! Wrong coordinates! Try again:\r\n\n> ");
                }
            }

            placeNewShip(coordinates, getCharForNumber(i, 48));
            System.out.println();
            drawField(1);
        }
    }

    public int[] parseCoordinate(String Coordinate) {
        /* parses only one entered string entity to a two integer array
        containing index(!) of row and column for a coordinate
        first - index of row (only first char!), second - index of column */

        int[] result = new int[2];
        result[0] = Coordinate.charAt(0) - 65;
        result[1] = Integer.parseInt(Coordinate.substring(1)) - 1;
        return result;
    }

    public int[] getCoordinates(int[] firstCoordinate, int[] secondCoordinate) {
        /* sets the following ship coordinate order:
        first - min row, second - min column, third - max row, forth - max column
        if both dimensions differ and coordinates mix - it' ok, there is a check for corresponding dimensions further
        order from top min to bottom max is important for bufferZone check further */

        int[] result = new int[4];
        result[0] = Math.min(firstCoordinate[0], secondCoordinate[0]);
        result[1] = Math.min(firstCoordinate[1], secondCoordinate[1]);
        result[2] = Math.max(firstCoordinate[0], secondCoordinate[0]);
        result[3] = Math.max(firstCoordinate[1], secondCoordinate[1]);
        return result;
    }

    public boolean isCoordinatesWrong(int[] coordinates, int length, String battleship) {
        /* checks if the ship could be placed on the battlefield
        if so - returns false to get out of the outer loop */

        if (isCoordinateNonexistent(coordinates)) {
            return true;
        } else if (coordinates[0] != coordinates[2] &&                 // no common dimension means ship lays diagonally
                coordinates[1] != coordinates[3]) {
            System.out.print("\r\nError! Wrong ship location! Try again:\r\n\r\n> ");
            return true;
        } else if (Math.abs(coordinates[0] - coordinates[2]) != length - 1 &&      // check for not common dimension dif
                Math.abs(coordinates[1] - coordinates[3]) != length - 1) {                       // gives length minus 1
            System.out.printf("\r\nError! Wrong length of the %s! Try again:\r\n\r\n> ", battleship);
            return true;
        } else {

            int[] bufferZone = {                     // set array of four coordinates to check if there are ships around
                    Math.max(coordinates[0] - 1, 0),               // check top left row minus 1 not to be out of bounds
                    Math.max(coordinates[1] - 1, 0),            // check top left column minus 1 not to be out of bounds
                    Math.min(coordinates[2] + 1, field.length - 1),                  // same for bottom right row plus 1
                    Math.min(coordinates[3] + 1, field[0].length - 1),            // same for bottom right column plus 1
            };

            for (int i = bufferZone[0]; i <= bufferZone[2]; i++) {            // check if there are ships in buffer zone
                for (int j = bufferZone[1]; j <= bufferZone[3]; j++) {
                    if (field[i][j] != BFConstants.fogOfWar) {
                        System.out.print("\r\nError! You placed it too close to another one. Try again:\r\n\r\n> ");
                        return true;
                    }
                }
            }
        }

        return false;                                                             // if no return yet - everything is ok
    }

    public boolean isCoordinateNonexistent(int[] coordinates) {
        /* checks pairs of coordinates if they exist on the field -
        as coordinates contain indexes, check if 0 <= index <= array length minus 1 */

        for (int k = 0; k < coordinates.length / 2; k++) {
            if (coordinates[k] < 0 ||
                    coordinates[k] > field.length - 1 ||
                    coordinates[k + 1] < 0 ||
                    coordinates[k + 1] > field[0].length - 1) {
                System.out.print("\r\nError! Wrong coordinates! Try again:\r\n\n> ");
                return true;
            }
        }

        return false;                                                             // if no return yet - everything is ok
    }

    public void placeNewShip(int[] coordinates, char shipIndex) {
        /* places a ship on the battlefield without any checks - they should be done already
        places the index of the ship in the corresponding field in order to find out what ship was hit latter */

        if (coordinates[0] == coordinates[2]) {
            for (int i = coordinates[1]; i <= coordinates[3]; i++) {
                field[coordinates[0]][i] = shipIndex;
            }
        } else {
            for (int i = coordinates[0]; i <= coordinates[2]; i++) {
                field[i][coordinates[1]] = shipIndex;
            }
        }
    }

    public boolean shoot(Scanner scanner) {
        /* asks user for one coordinate until it is verified
        then represents the shot in the array and draw the field */

        int[] coordinate = new int[0];
        boolean shipsLeft;
        boolean needsCheck = true;
        while (needsCheck) {
            String onlyCoordinate = scanner.next().toUpperCase();
            try {
                coordinate = parseCoordinate(onlyCoordinate);
                needsCheck = isCoordinateNonexistent(coordinate);
            } catch (Exception e) {
                System.out.print("\r\nError! Wrong coordinates! Try again:\r\n\n> ");
            }
        }
        shipsLeft = markShot(coordinate);
        return shipsLeft;
    }

    public boolean markShot(int[] coordinate) {
        /* represents the shot coordinate in the field array
        if coordinate contains fog of war or miss - marks field as miss and show field
        if coordinate contains hit - the same as miss, but doesn't rewrite the field
        if coordinate contains ship index - marks field as hit and reduce the ship cells left using its index in cell */

        if (field[coordinate[0]][coordinate[1]] == BFConstants.fogOfWar ||
                field[coordinate[0]][coordinate[1]] == BFConstants.miss) {
            field[coordinate[0]][coordinate[1]] = BFConstants.miss;
            drawField(2);
            System.out.print("You missed! Try again:\r\n\n> ");
        } else if (field[coordinate[0]][coordinate[1]] == BFConstants.hit) {
            drawField(2);
            System.out.print("You missed! Try again:\r\n\n> ");
        } else {
            shipSizes[getNumberForChar(field[coordinate[0]][coordinate[1]], -48)] -= 1;
            shipCellsLeft -= 1;
            if (shipCellsLeft == 0) {
                field[coordinate[0]][coordinate[1]] = BFConstants.hit;
                drawField(2);
                System.out.println("You sank the last ship. You won. Congratulations!");
                return false;
            } else if (shipSizes[getNumberForChar(field[coordinate[0]][coordinate[1]], -48)] == 0) {
                field[coordinate[0]][coordinate[1]] = BFConstants.hit;
                drawField(2);
                System.out.print("You sank a ship! Specify a new target:\r\n\n> ");
            } else {
                field[coordinate[0]][coordinate[1]] = BFConstants.hit;
                drawField(2);
                System.out.print("You hit a ship! Try again:\r\n\n> ");
            }
        }

        return true;
    }

}

class BFConstants {
    /* class for storing constants */

    static final char fogOfWar = '~';
    static final char ship = 'O';
    static final char hit = 'X';
    static final char miss = 'M';

}