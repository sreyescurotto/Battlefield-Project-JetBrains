package battleship;


import java.util.Locale;
import java.util.Scanner;

public class Main {
    // Write your code here
    public static Scanner scanner = new Scanner(System.in);
    public static String[][] gameField = {
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
            { "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" } };
    public static int count = 0;
    public static int maxTries = 3;

    public static void main(String[] args) {
        showField(gameField);
        airCraftCarrier();
        battleShip();
        subMarine();
        cruiser();
        destroyer();
    }

    private static void destroyer() throws RuntimeException {
        while(true) {
            try {
                System.out.print("Enter the coordinates of the Destroyer (2 cells): ");
                String c = scanner.next().toUpperCase(Locale.ROOT);
                String c2 = scanner.next().toUpperCase(Locale.ROOT);
                String number = c.substring(1);
                String letter = String.valueOf(c.charAt(0));
                String number2 = c2.substring(1);
                String letter2 = String.valueOf(c2.charAt(0));
                int n = findNumber(letter);
                int nn = Integer.parseInt(number);
                int n2 = findNumber(letter2);
                int nn2 = Integer.parseInt(number2);
                int cells = 2;

                    if (n - n2 == cells - 1 || n2 - n == cells - 1) {
                        setValueInField(n, nn, n2, nn2);
                        break;
                    } else if (nn - nn2 == cells - 1 || nn2 - nn == cells - 1) {
                        setValueInField(n, nn, n2, nn2);
                        break;
                    }
            } catch (RuntimeException e) {
                System.out.print("Error! Wrong length of the Destroyer! try again: " + "\n");
                if (++count == maxTries) {throw e;}
            }
        }
    }

    private static void cruiser() throws RuntimeException{
        while (true) {
            try {
                System.out.print("Enter the coordinates of the Cruiser (3 cells): ");
                String c = scanner.next().toUpperCase(Locale.ROOT);
                String c2 = scanner.next().toUpperCase(Locale.ROOT);
                String number = c.substring(1);
                String letter = String.valueOf(c.charAt(0));
                String number2 = c2.substring(1);
                String letter2 = String.valueOf(c2.charAt(0));
                int n = findNumber(letter);
                int nn = Integer.parseInt(number);
                int n2 = findNumber(letter2);
                int nn2 = Integer.parseInt(number2);
                int cells = 3;

                if (n - n2 == cells - 1 || n2 - n == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                } else if (nn - nn2 == cells - 1 || nn2 - nn == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                }
            } catch (RuntimeException e) {
                System.out.print("Error! Wrong length of the cruiser! try again: " + "\n");
                if (++count == maxTries) {
                    throw e;
                }

            }
        }
    }

    private static void subMarine() throws RuntimeException {
        while(true) {
            try {
                System.out.print("Enter the coordinates of the Submarine (3 cells): ");
                String c = scanner.next().toUpperCase(Locale.ROOT);
                String c2 = scanner.next().toUpperCase(Locale.ROOT);
                String number = c.substring(1);
                String letter = String.valueOf(c.charAt(0));
                String number2 = c2.substring(1);
                String letter2 = String.valueOf(c2.charAt(0));
                int n = findNumber(letter);
                int nn = Integer.parseInt(number);
                int n2 = findNumber(letter2);
                int nn2 = Integer.parseInt(number2);
                int cells = 3;

                if (n - n2 == cells - 1 || n2 - n == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                } else if (nn - nn2 == cells - 1 || nn2 - nn == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                }
            } catch (RuntimeException e) {
                if (++count == maxTries) {
                    System.out.print("Error! Wrong length of the Submarine! try again: " + "\n");
                    throw e;
                }
            }
        }
    }

    private static void battleShip() {
        while (true) {
            try {
            System.out.print("Enter the coordinates of the Battleship (4 cells): ");
            String c = scanner.next().toUpperCase(Locale.ROOT);
            String c2 = scanner.next().toUpperCase(Locale.ROOT);
            String number = c.substring(1);
            String letter = String.valueOf(c.charAt(0));
            String number2 = c2.substring(1);
            String letter2 = String.valueOf(c2.charAt(0));
            int n = findNumber(letter);
            int nn = Integer.parseInt(number);
            int n2 = findNumber(letter2);
            int nn2 = Integer.parseInt(number2);
            int cells = 4;

                if (n - n2 == cells - 1 || n2 - n == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                } else if (nn - nn2 == cells - 1 || nn2 - nn == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                }
            } catch (RuntimeException e) {
                System.out.print("Error! Wrong length of the Battleship! try again: " + "\n");
                if (++count == maxTries) {

                    throw e;}
            }
        }
    }

    private static void airCraftCarrier(){
        while (true) {
            try {
                System.out.print("Enter the coordinates of the Aircraft Carrier (5 cells): ");
                String c = scanner.next().toUpperCase(Locale.ROOT);
                String c2 = scanner.next().toUpperCase(Locale.ROOT);
                String number = c.substring(1);
                String letter = String.valueOf(c.charAt(0));
                String number2 = c2.substring(1);
                String letter2 = String.valueOf(c2.charAt(0));
                int n = findNumber(letter);
                int nn = Integer.parseInt(number);
                int n2 = findNumber(letter2);
                int nn2 = Integer.parseInt(number2);
                int cells = 5;

                if (n - n2 == cells - 1 || n2 - n == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                } else if (nn - nn2 == cells - 1 || nn2 - nn == cells - 1) {
                    setValueInField(n, nn, n2, nn2);
                    break;
                }
            } catch (RuntimeException e) {
                if (++count == maxTries) throw e;
            }
        }
    }

    public static void setValueInField(Integer n, Integer nn, Integer n2, Integer nn2) throws RuntimeException {
        String oo = "O";
            try {
                if (n == n2 && nn > nn2) {
                    for (int i = nn; i >= nn2; i--) {
                        if (gameField[n][i - 1] == oo) {
                            throw new RuntimeException();
                        } else if (gameField[n - 1][i - 1] == oo) {
                            throw new RuntimeException();
                        } else {
                            gameField[n][i - 1] = oo;
                        }
                    }
                    showField(gameField);
                } else if (n == n2 && nn < nn2) {
                    for (int i = nn; i <= nn2; i++) {
                        if (gameField[n][i - 1] == oo) {
                            throw new RuntimeException();
                        } else if (gameField[n - 1][i - 1] == oo) {
                            throw new RuntimeException();
                        } else {
                            gameField[n][i - 1] = oo;
                        }
                    }
                    showField(gameField);

                } else if (nn == nn2 && n < n2) {
                    for (int i = n; i <= n2; i++) {
                        if (gameField[i][nn - 1] == oo) {
                            throw new RuntimeException();

                        } else if (i < 9 && gameField[i + 1][nn - 1] == oo) {
                            throw new RuntimeException();
                        } else {
                            gameField[i][nn - 1] = oo;
                        }
                    }
                    showField(gameField);
                } else if (nn == nn2 && n > n2) {
                    for (int i = n; i >= n2; i--) {
                        if (gameField[i][nn - 1] == oo) {

                            throw new RuntimeException();
                        } else if (i < 9 && gameField[i + 1][nn - 1] == oo) {

                            throw new RuntimeException();
                        } else {
                            gameField[i][nn - 1] = oo;
                        }
                    }
                    showField(gameField);

                } else {
                    throw new RuntimeException();
                }
            } catch (RuntimeException e) {
                System.out.println("Error! You placed it too close to another one. Try again: ");
                throw e;
            }
    }


    public static Integer findNumber(String a) {
        int n = 0;
        if (a.contains("B")) {
            n = 1;
        } else if (a.contains("C")) {
            n = 2;
        } else if (a.contains("D")) {
            n = 3;
        } else if (a.contains("E")) {
            n = 4;
        } else if (a.contains("F")) {
            n = 5;
        } else if (a.contains("G")) {
            n = 6;
        } else if (a.contains("H")) {
            n = 7;
        } else if (a.contains("I")) {
            n = 8;
        } else if (a.contains("J")) {
            n = 9;
        }
        return n;
    }

    public static void showField(String[][] a) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            int q = i + 65;
            System.out.print((char)q + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}

