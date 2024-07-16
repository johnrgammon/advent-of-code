package SalesTax;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalesTaxProblem {

    private static final String BULLET = "· ";
    private static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String DATA_INPUT;
    static {
        DATA_INPUT = """
                Input 1:  
                1 book at 12.49  
                1 music CD at 14.99  
                1 chocolate bar at 0.85  
                
                Input 2:  
                1 imported box of chocolates at 10.00  
                1 imported bottle of perfume at 47.50  
                
                Input 3:  
                1 imported bottle of perfume at 27.99 1 
                1 bottle of perfume at 18.99  
                1 packet of headache pills at 9.75  
                1 box of imported chocolates at 11.25
                
                Input 4:
                2 books at 10.00
                2 imported leather gloves at 24.95
                12 pairs of earrings at 14.13""";
    }

    private static class Item {
        private static final List<String> TAX_EXEMPTED_GOODS = List.of("book", "headache pills", "chocolate");
        private static final BigDecimal SALES_TAX_RATE = new BigDecimal("0.10"); // 10%
        private static final BigDecimal IMPORT_DUTY_RATE = new BigDecimal("0.05"); // 5%

        private final int quantity;
        private final String description;
        private final BigDecimal originalPrice;
        private final boolean taxExempt;
        private final boolean imported;
        private BigDecimal salesTax;
        private BigDecimal duty;
        private BigDecimal totalPrice;

        /* If we ever wanted to handle "1 imported bottle of perfume at 27.99 1 " as an error because of the trailing 1:
        // one or more digits + space + characters until at + " at " + a number with 2 (and only 2) decimals + nothing trailing
        private static final Pattern ITEM_PATTERN = Pattern.compile("(\\d+) (.+?) at (\\d+\\.\\d{2})$");
        */

        //one or more digits + space + characters until at + " at " + a number with 2 (and only 2) decimals + ignores what comes after
        private static final Pattern ITEM_PATTERN = Pattern.compile("(\\d+) (.+?) at (\\d+\\.\\d{2})");

        public Item(String rawInput) {
            Matcher matcher = ITEM_PATTERN.matcher(rawInput);
            if (matcher.find()) {
                this.quantity = Integer.parseInt(matcher.group(1));
                this.description = matcher.group(2).trim();
                BigDecimal itemPrice = new BigDecimal(matcher.group(3).trim()).setScale(2, RoundingMode.HALF_UP);
                this.originalPrice = itemPrice.multiply(new BigDecimal(quantity));
                this.taxExempt = TAX_EXEMPTED_GOODS.stream().anyMatch(rawInput::contains);
                this.imported = rawInput.contains("import") || rawInput.contains("imported");
                this.salesTax = BigDecimal.ZERO;
                this.duty = BigDecimal.ZERO;
                applyLevies();
            } else {
                throw new IllegalArgumentException("Invalid input format: " + rawInput);
            }
        }

        private void applyLevies() {
            // assumes the nickel rounding is done to the tax applied after items are multiplied by quantity.
            BigDecimal totalPrice = originalPrice;
            if (!taxExempt) {
                BigDecimal taxAmount = originalPrice.multiply(SALES_TAX_RATE);
                taxAmount = taxAmount.setScale(2, RoundingMode.HALF_UP);
                taxAmount = roundToNearestNickel(taxAmount);
                this.salesTax = taxAmount;
                totalPrice = totalPrice.add(taxAmount);
            }
            if (imported) {
                BigDecimal dutyAmount = originalPrice.multiply(IMPORT_DUTY_RATE);
                dutyAmount = dutyAmount.setScale(2, RoundingMode.HALF_UP);
                dutyAmount = roundToNearestNickel(dutyAmount);
                this.duty = dutyAmount;
                totalPrice = totalPrice.add(dutyAmount);
            }
            this.totalPrice = totalPrice;
        }

        private BigDecimal roundToNearestNickel(BigDecimal amount) {
            BigDecimal nickel = new BigDecimal("0.05");
            return amount.divide(nickel, 0, RoundingMode.UP).multiply(nickel);
        }

        public String getOutputItem() {
            return quantity + " " + description + ": " + totalPrice;
        }

        public BigDecimal getLevies() {
            return salesTax.add(duty);
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }
    }

    private static void processItems(String inputString) {
        List<Item> items = new ArrayList<>();
        boolean continueProcessing = true;

        for (String rawItem : inputString.split("\n")) {
            if (rawItem.startsWith("Input")) {
                System.out.println("\n" + rawItem.replace("Input", "Output"));
            } else {
                try {
                    Item item = new Item(rawItem);
                    System.out.println(BULLET + item.getOutputItem());
                    items.add(item);
                } catch (IllegalArgumentException e) {
                    System.out.println(BULLET + ANSI_RED + "Item not processed: \"" + e.getMessage() + "\"" + ANSI_RESET);
                    continueProcessing = getUserProcessingChoice(e);
                }
            }
        }
        if (continueProcessing) {
            BigDecimal totalLevies = items.stream()
                    .map(Item::getLevies)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = items.stream()
                    .map(Item::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println(BULLET + "Sales taxes: " + totalLevies + " Total: " + total);
        } else {
            System.out.println(ANSI_RED + "... calculation halted as requested." + ANSI_RESET);
        }
    }

    private static boolean getUserProcessingChoice(IllegalArgumentException e) {
        String osName = System.getProperty("os.name");
        boolean isMac = osName.startsWith("Mac OS X") || osName.startsWith("macOS");
        String message = "<html><center><h2>An error has occurred:</h2><p style='color:red'>" + e.getMessage() + "." +
                "</p><p>Do you want to continue processing the remaining items?</p></center></html>";

        if (isMac) {
            String originalAppKitValue = System.getProperty("apple.awt.UIElement");
            System.setProperty("apple.awt.UIElement", "true");
            int option = JOptionPane.showConfirmDialog(null, message, "Error", JOptionPane.YES_NO_OPTION);
            if (originalAppKitValue == null) {
                System.clearProperty("apple.awt.UIElement");
            } else {
                System.setProperty("apple.awt.UIElement", originalAppKitValue);
            }
            return option == JOptionPane.YES_OPTION;
        } else {
            int option = JOptionPane.showConfirmDialog(null, message, "Error", JOptionPane.YES_NO_OPTION);
            return option == JOptionPane.YES_OPTION;
        }
    }

    public static void main() {
        Arrays.stream(DATA_INPUT.split("\n\n")).forEach(SalesTaxProblem::processItems);
    }
}
