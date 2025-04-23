package editor;

import javax.swing.*;
import java.io.*;

public class FileManager {

    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                textEditor.currentFile = file;

            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor,
                        "Error reading file: " + e.getMessage(),
                        "File Read Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());

                    textEditor.currentFile = file;

                    JOptionPane.showMessageDialog(textEditor,
                            "File saved successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(textEditor,
                            "Error saving file: " + e.getMessage(),
                            "File Save Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(textEditor.currentFile))) {
                writer.write(textArea.getText());

                JOptionPane.showMessageDialog(textEditor,
                        "File saved successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor,
                        "Error saving file: " + e.getMessage(),
                        "File Save Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        int result = JOptionPane.showConfirmDialog(textEditor,
                "Do you want to save changes before creating a new file?",
                "Save Changes",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }

        if (result == JOptionPane.YES_OPTION) {
            saveFile(textEditor, textArea);
        }

        textArea.setText("");
        textEditor.currentFile = null;
    }
}
