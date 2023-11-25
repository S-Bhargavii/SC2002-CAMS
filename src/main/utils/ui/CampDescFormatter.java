package main.utils.ui;

public class CampDescFormatter {
    public static String formatDescription(String description) {
        int lineWidth = 40;  // Adjust the line width as needed
        StringBuilder formattedDescription = new StringBuilder();

        // Split the description into lines
        String[] lines = description.split("\\s+");

        // Add each line with indentation
        for (String line : lines) {
            if (!formattedDescription.isEmpty()) {
                formattedDescription.append("\n");  // Add a newline if not the first line
                for (int i = 0; i < 25; i++) {
                    formattedDescription.append(" ");  // Add indentation
                }
            }
            formattedDescription.append(line);
        }

        return formattedDescription.toString();
    }
}
