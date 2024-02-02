package gui.panels;

import gui.GuiConstants;
import gui.components.Button;
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
    private String chosen = options[0];
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
            //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            var group = new ButtonGroup();
            for(String option : options) {
                var radioButton = new JRadioButton(option);
                group.add(radioButton);
                radioButton.addActionListener(a -> {
                    setSort(option);
                });
                radioButton.setOpaque(true);
                radioButton.setBackground(GuiConstants.COMPONENT_COLOR);
                radioButton.setForeground(GuiConstants.TEXT_COLOR);
                radioButton.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
                        new EmptyBorder(5, 5, 0, 5)));
                add(radioButton);
            }
            var submit = new Button("submit", a -> {
                System.out.println("changing oder to: " + chosen);
                ValoAccountManager.sortBy(chosen);
            });
            add(submit);
        }

        private void setSort(String option) {
            if(option == null || Arrays.stream(options).noneMatch(option::equals)) throw new IllegalArgumentException();
            chosen = option;
        }
    }
}


