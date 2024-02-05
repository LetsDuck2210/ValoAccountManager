package gui.panels;

import gui.GuiConstants;
import gui.components.Button;
import gui.components.EmptyComponent;
import gui.components.Headline;
import main.ValoAccountManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SortByPanel extends JPanel {
    private static final String[] options = new String[]{"Custom", "Name", "Rank"};
    public SortByPanel() {
        super();
        var layout = new GridBagLayout();
        layout.rowWeights = new double[] { 0, 1, 0 };
        layout.columnWeights = new double[] { 1, 0 };
        var c = new GridBagConstraints();
        setLayout(layout);
        setBackground(GuiConstants.BACKGROUND_COLOR);

        c.gridx = c.gridy = 0;
        c.gridwidth = c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new Headline("Sort Accounts by..."), c);
        c.gridy = 1;
        add(new SortByContentPanel(), c);
    }

    private class SortByContentPanel extends JPanel {
        SortByContentPanel() {
            super();
            setOpaque(true);
            setBackground(GuiConstants.BACKGROUND_COLOR);
            setLayout(new GridLayout(8, 1));

            for(String option : options) {
                var button = new Button(option, a -> ValoAccountManager.sortBy(option));
                add(button);
            }

            for(int i = 0; i < 7 - options.length; i++)
                add(new EmptyComponent());

            var reverse = new Button("Reverse", a -> ValoAccountManager.reverse());
            add(reverse);
        }
    }
}


