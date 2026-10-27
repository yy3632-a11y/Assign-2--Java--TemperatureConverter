import java.util.Scanner;

public class TemperatureConverter {

    public static double convertCelsius(double celsius, String targetScale) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException("Temperature is below absolute zero.");
        }

        if (targetScale.equalsIgnoreCase("F")) {
            return (celsius * 9.0 / 5.0) + 32;
        } else if (targetScale.equalsIgnoreCase("K")) {
            return celsius + 273.15;
        } else if (targetScale.equalsIgnoreCase("C")) {
            return celsius;
        }

        throw new IllegalArgumentException("Invalid target scale.");
    }
    public static double convertFahrenheit(double fahrenheit, String targetScale) {
        if (fahrenheit < -459.67) {
            throw new IllegalArgumentException("Temperature is below absolute zero.");
        }

        if (targetScale.equalsIgnoreCase("C")) {
            return (fahrenheit - 32) * 5.0 / 9.0;
        } else if (targetScale.equalsIgnoreCase("K")) {
            return (fahrenheit - 32) * 5.0 / 9.0 + 273.15;
        } else if (targetScale.equalsIgnoreCase("F")) {
            return fahrenheit;
        }

        throw new IllegalArgumentException("Invalid target scale.");
    }

    public static double convertKelvin(double kelvin, String targetScale) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("Kelvin cannot be negative.");
        }

        if (targetScale.equalsIgnoreCase("C")) {
            return kelvin - 273.15;
        } else if (targetScale.equalsIgnoreCase("F")) {
            return (kelvin - 273.15) * 9.0 / 5.0 + 32;
        } else if (targetScale.equalsIgnoreCase("K")) {
            return kelvin;
        }

        throw new IllegalArgumentException("Invalid target scale.");
    }

    public static double convertMultiple(double temperature,
                                         String sourceScale,
                                         String targetScale) {

        if (Double.isNaN(temperature) || Double.isInfinite(temperature)) {
            throw new IllegalArgumentException("Temperature must be a finite number.");
        }

        if (sourceScale.equalsIgnoreCase("C")) {
            return convertCelsius(temperature, targetScale);
        } else if (sourceScale.equalsIgnoreCase("F")) {
            return convertFahrenheit(temperature, targetScale);
        } else if (sourceScale.equalsIgnoreCase("K")) {
            return convertKelvin(temperature, targetScale);
        }

        throw new IllegalArgumentException("Invalid source scale.");
    }

    public static double[] convertMultiple(double[] temperatures,
                                           String sourceScale,
                                           String targetScale) {

        double[] results = new double[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            results[i] = convertMultiple(
                temperatures[i], sourceScale, targetScale
            );
        }

        return results;
    }

    public static void demonstratePassByValue(double value) {
        value = value + 10;
        System.out.printf("Inside method: %.2f%n", value);
    }

    public static void displayConversion(double temperature, String sourceScale) {

        if (sourceScale.equalsIgnoreCase("C")) {
            double fahrenheit = convertCelsius(temperature, "F");
            double kelvin = convertCelsius(temperature, "K");

            System.out.printf(
                "%.2f°C is equivalent to %.2f°F and %.2fK%n",
                temperature, fahrenheit, kelvin
            );

        } else if (sourceScale.equalsIgnoreCase("F")) {
            double celsius = convertFahrenheit(temperature, "C");
            double kelvin = convertFahrenheit(temperature, "K");

            System.out.printf(
                "%.2f°F is equivalent to %.2f°C and %.2fK%n",
                temperature, celsius, kelvin
            );

        } else if (sourceScale.equalsIgnoreCase("K")) {
            double celsius = convertKelvin(temperature, "C");
            double fahrenheit = convertKelvin(temperature, "F");

            System.out.printf(
                "%.2fK is equivalent to %.2f°C and %.2f°F%n",
                temperature, celsius, fahrenheit
            );
        }
    }
    public static String readScale(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String scale = scanner.nextLine().trim().toUpperCase();

            if (scale.equals("C") || scale.equals("F") || scale.equals("K")) {
                return scale;
            }

            System.out.println("Invalid scale. Please enter C, F, or K.");
        }
    }

    public static double readTemperature(Scanner scanner, String sourceScale) {
        while (true) {
            System.out.print("Enter temperature: ");

            try {
                double temperature = Double.parseDouble(
                    scanner.nextLine().trim()
                );

                // Validate against absolute zero.
                convertMultiple(temperature, sourceScale, "C");

                return temperature;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Temperature Conversion Examples ===");

        displayConversion(25, "C");
        displayConversion(68, "F");
        displayConversion(300, "K");
        System.out.println("\n=== Multiple Temperature Conversion ===");

        double[] temperatures = {0, 25, 100};
        double[] results = convertMultiple(temperatures, "C", "F");

        for (int i = 0; i < temperatures.length; i++) {
            System.out.printf(
                "%.2f°C = %.2f°F%n",
                temperatures[i], results[i]
            );
        }

        System.out.println("\n=== Pass-by-Value Demonstration ===");

        double originalValue = 25;
        System.out.printf("Before method: %.2f%n", originalValue);

        demonstratePassByValue(originalValue);

        System.out.printf("After method: %.2f%n", originalValue);

        System.out.println("\n=== Your Temperature Conversion ===");

        String sourceScale = readScale(
            scanner, "Enter source scale (C, F, or K): "
        );

        double userTemperature = readTemperature(scanner, sourceScale);

        System.out.println("\nConversion Result:");
        displayConversion(userTemperature, sourceScale);

        scanner.close();
    }
}
