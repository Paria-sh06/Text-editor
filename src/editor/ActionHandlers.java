package editor;

import javax.swing.*;

public class ActionHandlers {
    public static void cut(JTextArea textArea) {
        textArea.cut();
    }

    public static void copy(JTextArea textArea) {
        textArea.copy();
    }

    public static void paste(JTextArea textArea) {
        textArea.paste();
    }
}