package Phase1;
import java.io.*;

public class Simulator {
    public static char[] IR = new char[4];  // Instruction Register
    public static char[] R = new char[4];   // General Purpose Register
    public static int IC;  // Instruction Counter
    public static int SI;  // Service Interrupt
    public static boolean C;  // Toggle
    public static char[][] M = new char[100][4];  // Memory
    public static BufferedReader input;
    public static FileWriter output;

    public static void main(String[] args) {
        try {
            input = new BufferedReader(new FileReader("O:\\OS\\Phase1\\Phase1\\MyInput.txt"));
            output = new FileWriter("O:\\OS\\Phase1\\Phase1\\MyOutput.txt");
            LOAD();
            PRINTMEMORY();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) input.close();
                if (output != null) output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void INIT() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                M[i][j] = ' ';
            }
        }
        for (int i = 0; i < 4; i++) {
            IR[i] = ' ';
            R[i] = ' ';
        }
    }

    private static void MOS() throws IOException {
        switch (SI) {
            case 1 -> READ();
            case 2 -> WRITE();
            case 3 -> TERMINATE();
        }
    }

    private static void READ() throws IOException {
        String buffer = input.readLine();
        if (buffer == null || buffer.equals("$END")) {
            System.out.println("Out-of-data.");
            return;
        }
        int address = (IR[2] - '0') * 10 + (IR[3] - '0');
        int k = 0;
        for (int i = 0; i < buffer.length() && i < 40; i++) {
            M[address][k++] = buffer.charAt(i);
            if (k == 4) {
                k = 0;
                address++;
            }
            if (address >= 100) {
                System.out.println("Address out of bounds during read.");
                break;
            }
        }
    }

    private static void WRITE() throws IOException {
        int address = (IR[2] - '0') * 10 + (IR[3] - '0');
        if (address >= 100) {
            System.out.println("Address out of bounds during write.");
            return;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (address >= 100) {
                System.out.println("Address out of bounds during write.");
                break;
            }
            for (int j = 0; j < 4; j++) {
                out.append(M[address][j]);
            }
            address++;
        }
        output.write(out.toString() + '\n');
    }

    private static void TERMINATE() throws IOException {
        output.write('\n');
        output.write('\n');
    }

    private static void STARTEXECUTION() throws IOException {
        IC = 0;
        EXECUTEUSERPROGRAM();
    }

    private static void EXECUTEUSERPROGRAM() throws IOException {

        while (true) {
            if (IC >= 100) {
                System.out.println("Instruction Counter out of bounds.");
                break;
            }
            for (int i = 0; i < 4; i++) {
                IR[i] = M[IC][i];
            }
            IC++;
            if (IR[0] == 'L' && IR[1] == 'R') {
                int address = (IR[2] - '0') * 10 + (IR[3] - '0');
                if (address >= 100) {
                    System.out.println("Address out of bounds during load.");
                    continue;
                }
                for (int i = 0; i < 4; i++) {
                    R[i] = M[address][i];
                }
            } else if (IR[0] == 'S' && IR[1] == 'R') {
                int address = (IR[2] - '0') * 10 + (IR[3] - '0');
                if (address >= 100) {
                    System.out.println("Address out of bounds during store.");
                    continue;
                }
                for (int i = 0; i < 4; i++) {
                    M[address][i] = R[i];
                }
            } else if (IR[0] == 'C' && IR[1] == 'R') {
                int address = (IR[2] - '0') * 10 + (IR[3] - '0');
                if (address >= 100) {
                    System.out.println("Address out of bounds during compare.");
                    continue;
                }
                C = true;
                for (int i = 0; i < 4; i++) {
                    if (R[i] != M[address][i]) {
                        C = false;
                        break;
                    }
                }
            } else if (IR[0] == 'B' && IR[1] == 'T') {
                int address = (IR[2] - '0') * 10 + (IR[3] - '0');
                if (address >= 100) {
                    System.out.println("Address out of bounds during branch.");
                    continue;
                }
                if (C) {
                    IC = address;
                }
            } else if (IR[0] == 'G' && IR[1] == 'D') {
                SI = 1;
                MOS();
            } else if (IR[0] == 'P' && IR[1] == 'D') {
                SI = 2;
                MOS();
            } else if (IR[0] == 'H') {
                SI = 3;
                MOS();
                break;
            }
        }
    }

    private static void LOAD() throws IOException {
        String buffer;
        int m = 0;
        while ((buffer = input.readLine()) != null) {
            if (buffer.length() < 4 &&(buffer.length()>0 &&buffer.charAt(0)!='H')) {
                continue;
            }
            String line = buffer.substring(0, Math.min(4, buffer.length()));
            if (line.equals("$AMJ")) {
                m = 0;
                INIT();
            } else if (line.equals("$DTA")) {
                STARTEXECUTION();
            } else if (line.equals("$END")) {
                // Do nothing, just clear buffer for next input line
            } else {
                if (m >= 100) {
                    System.out.println("Out of memory.");
                    break;
                }
                int k = 0;
                for (int i = m; i < m + 10 && i < 100 && k < buffer.length(); i++) {
                    for (int j = 0; j < 4 && k < buffer.length(); j++) {
                        M[i][j] = buffer.charAt(k++);
                    }
                }
                m += 10;
            }
        }
    }

    private static void PRINTMEMORY() {
        System.out.println("Memory :");
        for (int i = 0; i < 100; ++i) {
            System.out.print("M [" + i + "] :");
            for (int j = 0; j < 4; j++) {
                System.out.print(M[i][j]);
            }

            System.out.println();
        }
    }
}