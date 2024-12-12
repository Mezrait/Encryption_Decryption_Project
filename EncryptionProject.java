import java.util.Arrays;
import java.util.Scanner;
public class EncryptionProject {
    public static void main(String[] args) {
        // Welcome message with visual divider
        System.out.println("\n══════════════════════════════════════════════════════════════════");
        System.out.println("       Welcome! This program encrypts data using various techniques.");
        System.out.println("══════════════════════════════════════════════════════════════════");

        Scanner input = new Scanner(System.in);

        // Prompt the user to continue or exit
        System.out.println("\nPress any key to continue, or press 0 to exit.");
        String userInput = input.nextLine();
        boolean proceed = userInput.equals("0");

        // Check if the user wants to proceed
        if (!proceed) {
            System.out.println("\n══════════════════════════════════════════════════════════════════");
            System.out.println("                Choose an Encryption Method:");
            System.out.println("══════════════════════════════════════════════════════════════════");
            System.out.println("\t1. Vigenère Cipher \n\t2. Rail Fence Cipher \n\t3. Column Transposition Cipher");

            String methodChoice = input.nextLine();
            char method = methodChoice.charAt(0);

            // Validate method choice
            while (method < '1' || method > '3') {
                System.out.println("Invalid choice! Please choose a valid encryption method:");
                methodChoice = input.nextLine();
                method = methodChoice.charAt(0);
            }

            String methodName = switch (method) {
                case '1' -> "Vigenère Cipher";
                case '2' -> "Rail Fence Cipher";
                case '3' -> "Column Transposition Cipher";
                default -> "";
            };

            System.out.println("\nYou selected: " + methodName);
            System.out.println("══════════════════════════════════════════════════════════════════");

            // Information request for the selected encryption method
            System.out.println("Do you want more information about " + methodName + "? (yes/no)");
            String infoChoice = input.nextLine().toLowerCase();
            if (infoChoice.startsWith("yes")) {
                displayMethodInfo(method);
            }

            System.out.println("\n══════════════════════════════════════════════════════════════════");
            System.out.println("               Enter the text you want to encrypt:");
            System.out.println("══════════════════════════════════════════════════════════════════");
            String plainText = input.nextLine();

            switch (method) {
                case '1':  // Vigenere Cipher
                    String key = "";
                    while (key.isEmpty()) {
                        System.out.println("Enter keyword (Alphabetical and must not be empty):");
                        key = input.nextLine().trim(); // Use trim() to remove leading/trailing whitespace
                        if (key.isEmpty()) {
                            System.out.println("Keyword cannot be empty. Please try again."); // Inform the user
                        }
                        else {
                            // Check if the key contains only alphabetical characters
                            boolean isValid = true;
                            for (char c : key.toCharArray()) {
                                if (!Character.isLetter(c)) {
                                    isValid = false; // Found a non-letter character
                                    break; // No need to check further
                                }
                            }
                            if (!isValid) {
                                System.out.println("Keyword must contain only alphabetical characters. Please try again."); // Inform the user
                                key = ""; // Reset key to prompt again
                            }
                        }
                    }
                    System.out.println("\nEncrypting using Vigenère Cipher...");
                    System.out.println("═════════════════════════════════════════════════");
                    String encryptedText = visualizeVigenereEncryption(plainText, key);

                    System.out.println("Encrypted Text: " + encryptedText.replace(" ", ""));
                    System.out.println("\nWould you like to decrypt the text? (yes/no)");
                    if (input.nextLine().toLowerCase().startsWith("y")) {
                        System.out.println("\nDecrypting using Vigenère Cipher...");
                        System.out.println("═════════════════════════════════════════════════");
                        System.out.println("Decrypted Text: " + vigenereDecrypt(encryptedText, key));
                    }
                    break;

                case '2':  // Rail Fence Cipher
                    int depth = 0;
                    boolean validDepth = false;

                    // Prompt for depth input and validate it
                    System.out.println("Enter depth (must be a positive integer):");
                    while (!validDepth) {
                        if (input.hasNextInt()) {
                            depth = input.nextInt();
                            input.nextLine(); // Consume newline
                            if (depth > 0) {
                                validDepth = true; // Valid depth entered
                            } else {
                                System.out.println("Depth must be a positive integer. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a positive integer.");
                            input.nextLine(); // Consume the invalid input
                        }
                    }
                    System.out.println("\nEncrypting using Rail Fence Cipher...");
                    System.out.println("══════════════════════════════════════════════════════════════════");
                    String railEncryptedText = railFenceEncryptWithVisualization(plainText, depth);
                    System.out.println("Encrypted Text: " + railEncryptedText.replace(" ", ""));

                    System.out.println("\nWould you like to decrypt the text? (yes/no)");
                    if (input.nextLine().trim().toLowerCase().startsWith("y")) {
                        System.out.println("\nDecrypting using Rail Fence Cipher...");
                        System.out.println("══════════════════════════════════════════════════════════════════");
                        System.out.println("Decrypted Text: " + railfenceDecrypt(railEncryptedText, depth));
                    }
                    break;

                case '3':  // Column Transposition
                    String column = "";
                    while (column.isEmpty()) {
                        System.out.println("Enter the key to encrypt (must not be empty):");
                        column = input.nextLine().trim(); // Use trim() to remove leading/trailing whitespace

                        if (column.isEmpty()) {
                            System.out.println("Key cannot be empty. Please try again."); // Inform the user
                        }
                    }
                    System.out.println("\nEncrypting using Column Transposition Cipher...");
                    System.out.println("══════════════════════════════════════════════════════════════════");
                    String rowEncryptedText = ColumnTransposeEncrypt(plainText, column);
                    System.out.println("Encrypted Text: " + rowEncryptedText.replace(" ", ""));

                    System.out.println("\nWould you like to decrypt the text? (yes/no)");
                    if (input.nextLine().toLowerCase().startsWith("y")) {
                        System.out.println("\nDecrypting using Column Transposition Cipher...");
                        System.out.println("══════════════════════════════════════════════════════════════════");
                        System.out.println("Decrypted Text: " + ColumnTransposeDecrypt(rowEncryptedText, column));
                    }
                    break;
            }
        } else {
            System.out.println("Exiting the program. Goodbye!");
            System.out.println("══════════════════════════════════════════════════════════════════");
        }
    }

    public static void displayMethodInfo(char method) {
        switch (method) {
            case '1':
                System.out.println("\n══════════════════════════════════════════════════════════════════");
                System.out.println("                    Vigenere Cipher (Polyalphabetic)");
                System.out.println("══════════════════════════════════════════════════════════════════");
                System.out.println("Description   : Encrypts by shifting letters based on a keyword, avoiding frequency patterns.");
                System.out.println("Historical    : Developed in the 16th century by Blaise de Vigenère.");
                System.out.println("Applications  : Military, diplomatic message encryption.");
                System.out.println("Security Note : Vulnerable to frequency analysis if the keyword is short or reused.");
                System.out.println("══════════════════════════════════════════════════════════════════\n");
                break;

            case '2':
                System.out.println("\n══════════════════════════════════════════════════════════════════");
                System.out.println("                     Rail Fence Cipher (Transposition)");
                System.out.println("══════════════════════════════════════════════════════════════════");
                System.out.println("Description   : Arranges text in a zigzag pattern on rails, then reads row by row.");
                System.out.println("Historical    : Common in the 19th century, often taught to students.");
                System.out.println("Applications  : Educational and simple encryption tasks.");
                System.out.println("Security Note : Weak; easily broken with frequency analysis and pattern recognition.");
                System.out.println("══════════════════════════════════════════════════════════════════\n");
                break;

            case '3':
                System.out.println("\n══════════════════════════════════════════════════════════════════");
                System.out.println("                  Row-Column Transposition (Transposition)");
                System.out.println("══════════════════════════════════════════════════════════════════");
                System.out.println("Description   : Arranges text into a grid and reads in a specific column order.");
                System.out.println("Historical    : Used since ancient times, especially in military communications.");
                System.out.println("Applications  : Encrypting messages in constrained environments.");
                System.out.println("Security Note : More secure than basic ciphers, but vulnerable if not combined with other methods.");
                System.out.println("══════════════════════════════════════════════════════════════════\n");
                break;

            default:
                System.out.println("\nOops! It looks like you selected an invalid encryption method.");
                System.out.println("Please choose one of the available cipher methods (1, 2, or 3).");
                System.out.println("══════════════════════════════════════════════════════════════════\n");
                break;
        }
    }


    public static String visualizeVigenereEncryption(String text, String key) {
        // StringBuilder to build the encrypted text
        StringBuilder encrypted = new StringBuilder();
        int keyLen = key.length();  // Length of the encryption key
        int keyIndex = 0;  // Tracks the current position in the key, only advancing on letters in 'text'
        // Print the table headers for visualization, including column borders
        System.out.println("┌───────────┬───────────┬───────────┬────────────┐");
        System.out.printf("│ %-9s │ %-9s │ %-9s │ %-10s │\n", "Text", "Key", "Shift", "Encrypted");
        System.out.println("├───────────┼───────────┼───────────┼────────────┤");
        // Loop through each character in the input text
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);  // Current character in 'text'

            // Encrypt only if character is a letter (ignores spaces and punctuation)
            if (Character.isLetter(ch)) {
                // Get the corresponding character from 'key', wrapping around if needed
                char keyChar = key.charAt(keyIndex % keyLen);
                keyIndex++;  // Move to the next key character only for letters in 'text'
                // Determine the base ('A' for uppercase, 'a' for lowercase) for case sensitivity
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                // Calculate the shift based on the key character, normalized to 0-25
                int shift = Character.toLowerCase(keyChar) - 'a';
                // Encrypt the character by shifting it within the alphabet bounds
                char encryptedChar = (char) ((ch - base + shift) % 26 + base);

                // Print the encryption process for this character in the table row format
                System.out.printf("│ %-9s │ %-9s │ %-9d │ %-10s │\n", ch, keyChar, shift, encryptedChar);
                encrypted.append(encryptedChar);  // Add encrypted character to result
            } else {
                // If the character is not a letter, it remains unchanged
                // Print dashes for "Key" and "Shift" to show no encryption applied
                System.out.printf("│ %-9s │ %-9s │ %-9s │ %-10s │\n", ch, "-", "-", ch);
                encrypted.append(ch);  // Add the unchanged character to result
            }
        }
        // Close the visualization table with a bottom border
        System.out.println("└───────────┴───────────┴───────────┴────────────┘");
        // Return the final encrypted text after processing all characters
        return encrypted.toString();
    }

    public static String railFenceEncryptWithVisualization(String text, int depth) {
        // Return original text if no encryption is needed (depth is 1 or larger than text length)
        if (depth <= 1 || depth >= text.length()) {
            return text;
        }
        // Initialize a grid for the rail fence pattern with empty spaces
        char[][] grid = new char[depth][text.length()];
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < text.length(); j++) {
                grid[i][j] = ' ';  // Space as default for unused grid slots
            }
        }
        // Replace spaces in 'text' with '@' for visibility in the grid (temporary visualization)
        StringBuilder xtext = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                xtext.append("@");
            } else {
                xtext.append(text.charAt(i));
            }
        }

        // Fill the grid in a zigzag pattern (alternating down and up directions)
        int row = 0;
        boolean down = true; // Tracks direction in the zigzag

        for (int col = 0; col < text.length(); col++) {
            grid[row][col] = xtext.charAt(col);  // Place each character in the grid

            // Adjust row position based on current zigzag direction
            if (down) {
                row++;
                if (row == depth) { // Reached the bottom, switch direction to up
                    row -= 2;
                    down = false;
                }
            } else {
                row--;
                if (row < 0) { // Reached the top, switch direction to down
                    row += 2;
                    down = true;
                }
            }
        }

        // Print the grid for visualizing the rail fence structure
        System.out.println("Rail Fence Grid:");

        // Print column headers for better reference in the visualization
        System.out.print("    ");  // Top-left alignment for headers
        for (int j = 0; j < xtext.length(); j++) {
            System.out.printf(" %2d ", j);  // Column numbers
        }
        System.out.println();

        // Print the top border of the grid
        System.out.print("   ┌");
        for (int j = 0; j < xtext.length() - 1; j++) {
            System.out.print("───┬");
        }
        System.out.println("───┐");

        // Print each row of the grid with borders for each cell
        for (int i = 0; i < depth; i++) {
            System.out.printf("%2d │", i);  // Row labels for better reference
            for (int j = 0; j < text.length(); j++) {
                if (grid[i][j] != '@')  // If character is not a placeholder
                    System.out.print(" " + grid[i][j] + " │");  // Display character in grid
                else
                    System.out.print("   │");  // Display empty space for '@' placeholders
            }
            System.out.println();

            // Print row separator or bottom border
            if (i < depth - 1) {
                System.out.print("   ├");
                for (int j = 0; j < xtext.length() - 1; j++) {
                    System.out.print("───┼");
                }
                System.out.println("───┤");
            } else {
                System.out.print("   └");
                for (int j = 0; j < text.length() - 1; j++) {
                    System.out.print("───┴");
                }
                System.out.println("───┘");
            }
        }

        // Extract encrypted text by reading across each rail (row-wise)
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (grid[i][j] == '@') {
                    encrypted.append(" ");  // Convert placeholder back to space
                } else if (grid[i][j] != ' ') {
                    encrypted.append(grid[i][j]);  // Append only filled characters
                }
            }
        }

        return encrypted.toString();  // Return final encrypted message
    }


    public static String ColumnTransposeEncrypt(String text, String key) {
        // Check for empty or null input; return error if text or key is invalid
        if (text == null || text.isEmpty() || key == null || key.isEmpty()) {
            System.out.println("Error: Text and key cannot be empty!");
            return "";
        }

        int keyLength = key.length();
        // Calculate the required number of rows to fit the text with the given key length
        int numRows = (int) Math.ceil((double) text.length() / keyLength);

        // Pad the text with 'X' characters to fill the last row if necessary
        while (text.length() < numRows * keyLength) {
            text += 'X';
        }

        // Sort the key alphabetically to determine the order of columns in the transposed grid
        char[] chars = key.toCharArray();
        Arrays.sort(chars);
        String sortedKey = new String(chars);

        // 1. Display the Original Table for Visualization
        System.out.println("\nOriginal Table:");
        // Print top border of the table
        System.out.print("┌");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┬" : "┐"));
        System.out.println();
        // Print key characters as column headers
        System.out.print("│");
        for (int i = 0; i < keyLength; i++) System.out.printf(" %c │", key.charAt(i));
        System.out.println();
        // Print bottom border for header row
        System.out.print("└");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┴" : "┘"));
        System.out.println();
        // Print top border for the table rows
        System.out.print("┌");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┬" : "┐"));
        System.out.println();
        // Print the grid content row by row
        for (int row = 0; row < numRows; row++) {
            System.out.print("│");
            for (int col = 0; col < keyLength; col++) {
                System.out.printf(" %c │", text.charAt(row * keyLength + col));  // Fill with text character
            }
            System.out.println();
            // Print row separators or bottom border
            if (row < numRows - 1) {
                System.out.print("├");
                for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┼" : "┤"));
                System.out.println();
            } else {
                System.out.print("└");
                for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┴" : "┘"));
                System.out.println();
            }
        }

        // 2. Display the Rearranged Table Based on Sorted Key Order

        System.out.println("\nRearranged Table (Based on Sorted Key):");
        // Print top border for the rearranged table
        System.out.print("┌");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┬" : "┐"));
        System.out.println();

        // Column headers sorted by the key
        System.out.print("│");
        for (int i = 0; i < keyLength; i++) System.out.printf(" %c │", sortedKey.charAt(i));
        System.out.println();

        // Print bottom border of sorted header row
        System.out.print("└");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┴" : "┘"));
        System.out.println();

        // Print top border for rearranged table rows
        System.out.print("┌");
        for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┬" : "┐"));
        System.out.println();

        // Populate and display each row in the rearranged order of columns
        for (int row = 0; row < numRows; row++) {
            System.out.print("│");
            for (int col = 0; col < keyLength; col++) {
                int colIndex = key.indexOf(sortedKey.charAt(col));  // Locate original column in sorted order
                System.out.printf(" %c │", text.charAt(row * keyLength + colIndex));
            }
            System.out.println();

            // Print row separators or bottom border
            if (row < numRows - 1) {
                System.out.print("├");
                for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┼" : "┤"));
                System.out.println();
            } else {
                System.out.print("└");
                for (int i = 0; i < keyLength; i++) System.out.print("───" + (i < keyLength - 1 ? "┴" : "┘"));
                System.out.println();
            }
        }

        // 3. Encrypt by Reading Columns in Sorted Order

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            char currentKeyChar = sortedKey.charAt(i);
            int colIndex = key.indexOf(currentKeyChar);  // Find column index in the original key order
            for (int row = 0; row < numRows; row++) {
                encrypted.append(text.charAt(row * keyLength + colIndex));  // Append each row's character in this column
            }
            // Replace used key character with placeholder to avoid duplicate index lookup
            key = key.substring(0, colIndex) + "_" + key.substring(colIndex + 1);
        }

        // Return the final encrypted text as a single string
        return encrypted.toString();
    }

    public static String vigenereDecrypt(String text, String key) {
        // StringBuilder to store the decrypted result as we go through each character
        StringBuilder decrypted = new StringBuilder();
        int keyLength = key.length();  // Length of the encryption key
        int keyIndex = 0;  // Tracks our position in the key so we loop back if it’s shorter than the text

        // Setting up a table format to show the decryption process
        System.out.println("┌───────────┬───────────┬───────────┬────────────┐");
        System.out.printf("│ %-9s │ %-9s │ %-9s │ %-10s │%n", "Text", "Key", "Shift", "Decrypted");
        System.out.println("├───────────┼───────────┼───────────┼────────────┤");

        // Loop through each character in the encrypted text to decrypt it
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);  // Current character in the text

            // Check if it's a letter since only letters are shifted in the Vigenere cipher
            if (Character.isLetter(ch)) {
                // Choose 'A' or 'a' as the base for shifting based on uppercase or lowercase
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                // Get the corresponding key character, wrapping around if needed
                char keyChar = key.charAt(keyIndex % keyLength);
                // Calculate the shift value by converting the key character to a number (0-25)
                int keyShift = Character.toLowerCase(keyChar) - 'a';

                // Calculate the original character by shifting backwards
                char decryptedChar = (char) (((ch - base - keyShift + 26) % 26) + base);

                // Display each step in the table for clarity
                System.out.printf("│ %-9s │ %-9s │ %-9d │ %-10s │\n", ch, keyChar, keyShift, decryptedChar);
                decrypted.append(decryptedChar);  // Add the decrypted character to the result

                keyIndex++;  // Advance to the next key character for the next letter
            } else {
                // Non-letter characters are not encrypted, so they remain unchanged
                System.out.printf("│ %-9s │ %-9s │ %-9s │ %-10s │%n", ch, "-", "-", ch);
                decrypted.append(ch);
            }
        }

        // Print the closing border of the table for a neat look
        System.out.println("└───────────┴───────────┴───────────┴────────────┘");

        // Return the complete decrypted text as a single string
        return decrypted.toString();
    }

    // Rail Fence Decryption method
    public static String railfenceDecrypt(String text, int depth) {
        // Step 1: Set up a 2D array to represent the rail structure, initialized with placeholders
        char[][] rail = new char[depth][text.length()];

        // Fill the rail array with placeholder characters ('\n') to mark unused positions
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < text.length(); j++) {
                rail[i][j] = '\n';
            }
        }

        // Step 2: Mark positions where the text characters will be placed, following a zig-zag pattern
        int row = 0, col = 0;
        boolean directionDown = false;  // Track if we are moving "down" the rails or "up"

        // Mark each position in the zig-zag pattern with '*'
        for (int i = 0; i < text.length(); i++) {
            rail[row][col++] = '*';

            // Reverse direction at the top or bottom rail
            if (row == 0 || row == depth - 1) {
                directionDown = !directionDown;
            }
            row = directionDown ? row + 1 : row - 1;
        }

        // Step 3: Replace '*' markers with actual characters from the encrypted text
        int textIndex = 0;
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < text.length(); j++) {
                // Fill the marked positions with characters from 'text'
                if (rail[i][j] == '*' && textIndex < text.length()) {
                    rail[i][j] = text.charAt(textIndex++);
                }
            }
        }

        // Step 4: Read characters in the zig-zag pattern to decrypt the text
        StringBuilder decryptedText = new StringBuilder();
        row = 0;
        col = 0;
        directionDown = false;  // Reset direction for reading the zig-zag pattern

        // Display decryption steps for interactive table
        System.out.println("\nInteractive Decryption Steps:");
        System.out.println("┌────────┬─────────┬──────────┬─────────────────────────┐");
        System.out.println("│ Step   │ Row     │ Column   │ Decrypted Text So Far   │");
        System.out.println("├────────┼─────────┼──────────┼─────────────────────────┤");

        // Traverse the array in the zig-zag pattern and construct the decrypted message
        for (int i = 0; i < text.length(); i++) {
            // Append the character at the current position to the decrypted text
            decryptedText.append(rail[row][col++]);

            // Show the current step, row, column, and progress of decrypted text
            System.out.printf("│ %-6d │ %-7d │ %-8d │ %-23s │\n", i + 1, row, col - 1, decryptedText);

            // Reverse direction at the top or bottom rail
            if (row == 0 || row == depth - 1) {
                directionDown = !directionDown;
            }
            row = directionDown ? row + 1 : row - 1;
        }

        // Closing border for the interactive table
        System.out.printf("└────────┴─────────┴──────────┴─────────────────────────┘%n");

        // Return the fully decrypted message
        return decryptedText.toString();
    }


    // Decrypt using Columnar Transposition Cipher without StringBuilder or arrays
    public static String ColumnTransposeDecrypt(String text, String key) {
        int keyLength = key.length();   // Get the length of the key, which determines the number of columns
        int numRows = text.length() / keyLength;   // Calculate the number of rows needed based on text length and key length

        // Sort the characters in the key alphabetically to determine the column order for decryption
        char[] sortedKeyChars = key.toCharArray();   // Convert key into a char array
        Arrays.sort(sortedKeyChars);   // Sort the characters to get the order
        String sortedKey = new String(sortedKeyChars);   // Convert sorted characters back to a string

        // Map each column to its position in the original key by creating a column order array
        int[] columnOrder = new int[keyLength];
        for (int i = 0; i < keyLength; i++) {
            columnOrder[i] = key.indexOf(sortedKey.charAt(i));   // Find where each sorted character originally came from
            // Mark used character positions in the key to handle cases with duplicate characters
            key = key.substring(0, columnOrder[i]) + "_" + key.substring(columnOrder[i] + 1);
        }

        // Initialize a grid (2D char array) where we'll place characters according to the column order
        char[][] grid = new char[numRows][keyLength];
        int index = 0;

        // Populate the grid by filling in each column according to the column order
        for (int col : columnOrder) {
            for (int row = 0; row < numRows; row++) {
                grid[row][col] = text.charAt(index++);   // Place each character in the correct cell based on the column order
            }
        }

        // Read the grid row by row to reconstruct the original message
        StringBuilder decrypted = new StringBuilder();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < keyLength; col++) {
                decrypted.append(grid[row][col]);   // Append each character from each row to form the decrypted text
            }
        }

        // Return the decrypted text, removing any trailing 'X' characters used for padding
        return decrypted.toString().replaceAll("X+$", "");
    }
}



