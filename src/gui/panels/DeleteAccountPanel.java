package gui.panels;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import gui.components.EmptyComponent;
import gui.components.Headline;
import gui.components.TextLabel;
import gui.components.WarningLabel;
import main.ValoAccountManager;
import valorant.Account;

public class DeleteAccountPanel extends JPanel implements Updatable {
	private static final long serialVersionUID = 7694699347859723050L;
	private boolean confirm = false;
	private TextLabel currentAccountLabel;
	private JButton deleteButton;
	private WarningLabel warning;

	public DeleteAccountPanel() {
		super();
		setLayout(new GridLayout(8, 1));
		setBackground(GuiConstants.BACKGROUND_COLOR);
		
		add(new Headline("Delete Account"));
		add(new EmptyComponent());
		
		boolean accountSelected = !ValoAccountManager.getCurrentAccount().isEmpty();
		
		var text = accountSelected 
				? "Selected Account: " + ValoAccountManager.getCurrentAccount().get().name() + " #" + ValoAccountManager.getCurrentAccount().get().tagline()
				: "No Account selected";
		add(currentAccountLabel = new TextLabel(text));
		
		for (int i = 0; i < 3; i++)
			add(new EmptyComponent());
		add(warning = new WarningLabel("Press again to confirm"), false);
		
		deleteButton = new JButton();
		if(!accountSelected) deleteButton.setEnabled(false);
		deleteButton.setText("delete");
		deleteButton.setFont(GuiConstants.FONT);
		deleteButton.setOpaque(true);
		deleteButton.setBackground(GuiConstants.COMPONENT_COLOR);
		deleteButton.setForeground(GuiConstants.TEXT_COLOR);
		deleteButton.setBorder(BorderFactory.createCompoundBorder(
				new MatteBorder(5, 0, 10, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 0, 10, 5)));
		deleteButton.addActionListener(a -> {
			if(!confirm) {
				confirm = true;
				warning.setVisible(true);
			}
			else {
				ValoAccountManager.removeAccount(ValoAccountManager.getCurrentAccount().get());
				confirm = false;
				warning.setVisible(false);
				currentAccountLabel.setText("No account selected");
			}
		});
		add(deleteButton);
	}

	@Override
	public void update(Account acc) {
		currentAccountLabel.setText("Selected Account: " + ValoAccountManager.getCurrentAccount().get().name() + " #" + ValoAccountManager.getCurrentAccount().get().tagline());
		deleteButton.setEnabled(true);
		revalidate();
		repaint();
	}
}
