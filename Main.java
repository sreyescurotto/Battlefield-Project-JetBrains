package battleship;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String[][] p1 = new String[12][12];
        String[][] p1Foggy = new String[12][12];
        int p1Ships = 5;
        newMatrix(p1);
        newMatrix(p1Foggy);
        String[][] p2 = new String[12][12];
        String[][] p2Foggy = new String[12][12];
        int p2Ships = 5;
        newMatrix(p2);
        newMatrix(p2Foggy);
        System.out.println("Player 1, place your ships on the game field");
        for (int i = 6; i > 1; i--) {
            int temp = i - 1;
            if (i == 3 || i == 2) {
                temp = i;
            }
            getMatrix(p1);
            System.out.println("\nEnter the coordinates of the " + shipName(i) + " (" + temp + " cells):\n");
            String[] a = newShip(temp, p1);
            char[] c1 = a[0].toCharArray();
            char[] c2 = a[1].toCharArray();
            int firstNum = (int) c1[1] - '0';
            int secondNum = (int) c2[1] - '0';
            if (c1.length == 3) {
                firstNum = 10;
            }
            if (c2.length == 3) {
                secondNum = 10;
            }
            char firstChar = c1[0];
            char secondChar = c2[0];
            makeShip(p1, firstChar, secondChar, firstNum, secondNum);
        }
        getMatrix(p1);
        System.out.println("Press Enter and pass the move to another player");
        String turn = new Scanner(System.in).nextLine();
        System.out.println("\n".repeat(50));
        System.out.println("Player 2, place your ships on the game field\n");
        for (int i = 6; i > 1; i--) {
            int temp = i - 1;
            if (i == 3 || i == 2) {
                temp = i;
            }
            getMatrix(p2);
            System.out.println("\nEnter the coordinates of the " + shipName(i) + " (" + temp + " cells):\n");
            String[] a = newShip(temp, p2);
            char[] c1 = a[0].toCharArray();
            char[] c2 = a[1].toCharArray();
            int firstNum = (int) c1[1] - '0';
            int secondNum = (int) c2[1] - '0';
            if (c1.length == 3) {
                firstNum = 10;
            }
            if (c2.length == 3) {
                secondNum = 10;
            }
            char firstChar = c1[0];
            char secondChar = c2[0];
            makeShip(p2, firstChar, secondChar, firstNum, secondNum);
        }
        getMatrix(p2);
        System.out.println("Press Enter and pass the move to another player");
        turn = new Scanner(System.in).nextLine();;
        if (turn == null) {
            int all = 0;
        }
        System.out.println("\n".repeat(50));
        while (p1Ships != 0 && p2Ships != 0) {
            getDoubleMatrix(p2Foggy, p1);
            System.out.println("Player 1, it's your turn:");
            int res = shot(p2, p2Foggy, p2Ships);
            if (res == 2) {
                --p2Ships;
            }
            System.out.println("Press Enter and pass the move to another player");
            turn = new Scanner(System.in).nextLine();
            if (turn == null) {
                int all = 0;
            }
            System.out.println("\n".repeat(50));
            getDoubleMatrix(p1Foggy, p2);
            System.out.println("Player 2, it's your turn:");
            res = shot(p1, p1Foggy, p1Ships);
            if (res == 2) {
                --p1Ships;
            }
            System.out.println("Press Enter and pass the move to another player");
            turn = new Scanner(System.in).nextLine();
            if (turn == null) {
                int all = 0;
            }
        }
    }

    static boolean checkAlive(char[] coords, int p1Ships, String[][] matrix) {
        int letter = (int) coords[0] - 'A' + 1;
        int num = (int) coords[1] - '0';
        int i = 1;
        int cells = 0;
        while ("O".equals(matrix[letter][num + i]) || "X".equals(matrix[letter][num + i])) {
            if ("O".equals(matrix[letter][num + i])) {
                return true;
            }
            i++;
            cells++;
        }
        i = 1;
        while ("O".equals(matrix[letter][num - i]) || "X".equals(matrix[letter][num - i])) {
            if ("O".equals(matrix[letter][num - i])) {
                return true;
            }
            i++;
            cells++;
        }
        if (cells != 0) {
            --p1Ships;
            if (p1Ships == 0) {
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
                return false;
            }
            System.out.println("\nYou sank a ship! Specify a new target:\n");
            return false;
        }
        cells = 0;
        while ("O".equals(matrix[letter + i][num]) || "X".equals(matrix[letter + i][num])) {
            if ("O".equals(matrix[letter + i][num])) {
                return true;
            }
            i++;
            cells++;
        }
        i = 0;
        while ("O".equals(matrix[letter - i][num]) || "X".equals(matrix[letter - i][num])) {
            if ("O".equals(matrix[letter - i][num])) {
                return true;
            }
            i++;
            cells++;
        }
        if (cells != 0) {
            --p1Ships;
            if (p1Ships == 0) {
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
                return false;
            }
            System.out.println("\nYou sank a ship! Specify a new target:\n");
            return false;
        }
        return false;
    }
    public static int shot(String[][] a, String[][] foggy, int p1Ships) {
        while(true) {
            try {
                String coords = new Scanner(System.in).nextLine().toUpperCase().trim();
                char[] coordsSep = coords.toCharArray();
                int letter = (int) coordsSep[0] - 'A' + 1;
                int num = (int) coordsSep[1] - '0';
                if (coordsSep.length == 3) {
                    if (coordsSep[1] == '1' && coordsSep[2] == '0') {
                        num = 10;
                    } else {
                        throw new IllegalArgumentException("\nError! Wrong coordinates of a shot. Try again:\n");
                    }
                }
                if (coordsSep.length > 3) {
                    throw new IllegalArgumentException("\nError! Wrong coordinates of a shot. Try again:\n");
                }
                if (coordsSep[0] > 'J' || coordsSep[0] < 'A' || num > 10 || num < 1) {
                    throw new IllegalArgumentException("\nError! Wrong coordinates of a shot. Try again:\n");
                }
                if ("X".equals(a[letter][num]) || "M".equals(a[letter][num])) {
                    System.out.println("\nShot there already, idiot!\n");
                    return 0;
                }
                if ("O".equals(a[letter][num])) {
                    foggy[letter][num] = "X";
                    a[letter][num] = "X";
                    if (!checkAlive(coordsSep, p1Ships, a)) {
                        return 2;
                    }
                    System.out.println("\nYou hit a ship!\n");
                    return 1;
                } else if ("~".equals(a[letter][num])) {
                    foggy[letter][num] = "M";
                    a[letter][num] = "M";
                    System.out.println("\nYou missed!");
                    return 0;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    static void newMatrix(String[][] a) {
        a[0][0] = " ";
        for (int i = 1; i < 11; i++) {
            a[0][i] = Integer.toString(i);
        }
        for (int i = 1; i < 11; i++) {
            a[i][0] = Character.toString('A' + i - 1);
            for (int j = 1; j < 11; j++) {
                a[i][j] = "~";
            }
        }
    }
    public static void getMatrix(String[][] a) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    static String[] newShip(int a, String[][] b) {
        while (true) {
            try {
                String coords = new Scanner(System.in).nextLine().toUpperCase().trim();
                String[] gotit = coords.split("\\s+");
                if (gotit.length != 2) {
                    throw new IllegalArgumentException("\nError! Enter the correct coordinates. Try again:\n");
                }
                char[] c1 = gotit[0].toCharArray();
                char[] c2 = gotit[1].toCharArray();
                int fn = (int) c1[1] - '0';
                int sn = (int) c2[1] - '0';
                if (c1.length == 3) {
                    fn = 10;
                }
                if (c2.length == 3) {
                    sn = 10;
                }
                char fc = c1[0];
                char sc = c2[0];
                if (c1[0] > 'J' || c1[0] < 'A' || fn > 10 || fn < 1) {
                    throw new IllegalArgumentException("\nError! Wrong ship location. Try again:\n");
                }
                if (c2[0] > 'J' || c2[0] < 'A' || sn > 10 || sn < 1) {
                    throw new IllegalArgumentException("\nError! Wrong ship location. Try again:\n");
                }
                if (!((Math.abs(c1[0] - c2[0]) == a - 1 && fn == sn) || (Math.abs(fn - sn) == a - 1 && c1[0] == c2[0]))) {
                    throw new IllegalArgumentException("\nError! Wrong ship length/location. Try again:\n");
                }

                if (fn == sn) {
                    int min = (int) fc < (int) sc ? fc : sc;
                    int max = fc + sc - min;
                    for (int i = 0; i <= a; i++) {
                        if ("O".equals(b[min - 'A' + 1 + i][fn + 1]) || "O".equals(b[min - 'A' + 1 + i][fn - 1])) {
                            throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                        }
                    }
                    if ("O".equals(b[min - 'A' + 1 + 1][fn])  || "O".equals(b[min - 'A' + 1 + 1][fn + 1]) || "O".equals(b[min - 'A' + 1 + 1][fn - 1])) {
                        throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                    }
                    if ("O".equals(b[max - 'A' + 1 - 1][fn])  || "O".equals(b[max - 'A' + 1 - 1][fn - 1]) || "O".equals(b[max - 'A' + 1 - 1][fn + 1])) {
                        throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                    }
                } else if (fc == sc) {
                    int min = Math.min(fn, sn);
                    int max = fn + sn - min;
                    for (int i = 0; i <= Math.abs(fn - sn); i++) {
                        if ("O".equals(b[fc - 'A' + 1 + 1][min + i]) || "O".equals(b[fc - 'A' + 1 - 1][min + i])) {
                            throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                        }
                    }
                    if ("O".equals(b[fc - 'A' + 1][min - 1]) || "O".equals(b[fc - 'A' + 1 - 1][min - 1]) || "O".equals(b[fc - 'A' + 1 + 1][min - 1])) {
                        throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                    }
                    if ("O".equals(b[fc - 'A' + 1][max + 1]) || "O".equals(b[fc - 'A' + 1 - 1][max + 1]) || "O".equals(b[fc - 'A' + 1 + 1][max + 1])) {
                        throw new IllegalArgumentException("\nError! Too close to another ship. Try again:\n");
                    }
                }
                return  gotit;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void getDoubleMatrix(String[][] pFoggy, String[][] p) {
        getMatrix(pFoggy);
        System.out.println("---------------------");
        getMatrix(p);
    }
    public static void makeShip(String[][] a, char fc, char sc, int fn, int sn) {
        if (fn == sn) {
            for (int i = 0; i <= Math.abs(fc - sc); i++) {
                int min = (int) fc < (int) sc ? fc : sc;
                a[min - (int)'A' + i + 1][fn] = "O";
            }
        } else {
            for (int i = 0; i <= Math.abs(fn - sn); i++) {
                int min = Math.min(fn, sn);
                a[(int)fc - (int)'A' + 1][min + i] = "O";
            }
        }
    }
    public static String shipName(int a) {
        switch (a) {
            case 6:
                return "Aircraft Carrier";
            case 5:
                return "Battleship";
            case 4:
                return "Submarine";
            case 3:
                return "Cruiser";
            case 2:
                return "Destroyer";
            default:
                return null;
        }
    }

}

