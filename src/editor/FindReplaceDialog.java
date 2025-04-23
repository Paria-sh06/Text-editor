package editor;

import javax.swing.*;
import java.awt.*;

public class FindReplaceDialog extends JDialog {
    private JTextField findField, replaceField;
    private JButton findNextButton, replaceButton, replaceAllButton;
    private JTextArea textArea;
    private int lastMatchIndex = 0;

    public FindReplaceDialog(JFrame parent, JTextArea textArea) {
        super(parent, "Find and Replace", false);
        this.textArea = textArea;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel findLabel = new JLabel("Find:");
        JLabel replaceLabel = new JLabel("Replace with:");
        findField = new JTextField(20);
        replaceField = new JTextField(20);

        findNextButton = new JButton("Find Next");
        replaceButton = new JButton("Replace");
        replaceAllButton = new JButton("Replace All");

        findNextButton.addActionListener(e -> findNext());
        replaceButton.addActionListener(e -> replace());
        replaceAllButton.addActionListener(e -> replaceAll());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(findLabel, gbc);
        gbc.gridx = 1;
        add(findField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(replaceLabel, gbc);
        gbc.gridx = 1;
        add(replaceField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(findNextButton);
        buttonPanel.add(replaceButton);
        buttonPanel.add(replaceAllButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    private void findNext() {
        String text = textArea.getText();
        String find = findField.getText();
        if (find.isEmpty()) return;

        lastMatchIndex = text.indexOf(find, lastMatchIndex + 1);
        if (lastMatchIndex >= 0) {
            textArea.setSelectionStart(lastMatchIndex);
            textArea.setSelectionEnd(lastMatchIndex + find.length());
        } else {
            JOptionPane.showMessageDialog(this, "No more matches.");
            lastMatchIndex = 0;
        }
    }

    private void replace() {
        if (textArea.getSelectedText() != null &&
                textArea.getSelectedText().equals(findField.getText())) {
            textArea.replaceSelection(replaceField.getText());
        }
        findNext();
    }

    private void replaceAll() {
        String find = findField.getText();
        String replace = replaceField.getText();
        if (!find.isEmpty()) {
            textArea.setText(textArea.getText().replace(find, replace));
        }
    }
}