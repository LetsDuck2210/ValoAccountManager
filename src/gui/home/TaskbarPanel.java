package gui.home;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.components.EmptyComponent;
import gui.panels.Updatable;
import main.ValoAccountManager;

public class TaskbarPanel extends JPanel implements Extendable {
	private static final long serialVersionUID = 5118137174535785959L;
	private GridLayout defaultLayout = new GridLayout(1, 2);
	private GridLayout extendedLayout = new GridLayout(1, 3);
	private TaskbarMainPanel mainPanel = new TaskbarMainPanel();

	public TaskbarPanel() {
		setBackground(GuiConstants.BACKGROUND_COLOR_ALT);
		setOpaque(true);
//		setLayout(new FlowLayout(FlowLayout.LEFT));
		showDefault();

//		var deletePanel = new DeleteAccountPanel();
//		var editPanel = new EditAccountPanel();
//		ValoAccountManager.addUpdateablePanel(deletePanel);
//		ValoAccountManager.addUpdateablePanel(editPanel);
//		add(new TaskbarButton("add", new AddAccountPanel()));
//		add(new TaskbarButton("edit", editPanel));
//		add(new TaskbarButton("delete", deletePanel));
//		add(new TaskbarButton("crosshairs", new CrosshairPanel()));
	}

	@Override
	public void showDefault() {
		removeAll();
		setLayout(defaultLayout);
		add(mainPanel);
		add(new EmptyComponent());
		refresh();
	}

	@Override
	public void showExtended(JPanel panel) {
		removeAll();
		setLayout(extendedLayout);
		add(mainPanel);
		add(new EmptyComponent());
		add(new EmptyComponent());
		refresh();
	}

	private void refresh() {
		revalidate();
		repaint();
	}

	public void addExtendedPanelButton(String text, JPanel panel) {
//		panels.forEach(p -> {
//			if(p instanceof Updatable u) {
//				ValoAccountManager.addUpdateablePanel(u);
//			}
//			mainPanel.add(new TaskbarButton(text, p));
//		});
		mainPanel.add(new TaskbarButton(text, panel));
	}

	private class TaskbarMainPanel extends JPanel {
		private static final long serialVersionUID = 1378643499162019016L;

		public TaskbarMainPanel() {
			setBackground(GuiConstants.BACKGROUND_COLOR_ALT);
			setOpaque(true);
			setLayout(new GridLayout());

//			var deletePanel = new DeleteAccountPanel();
//			var editPanel = new EditAccountPanel();
//			ValoAccountManager.addUpdateablePanel(deletePanel);
//			ValoAccountManager.addUpdateablePanel(editPanel);
//			add(new TaskbarButton("add", new AddAccountPanel()));
//			add(new TaskbarButton("edit", editPanel));
//			add(new TaskbarButton("delete", deletePanel));
//			add(new TaskbarButton("crosshairs", new CrosshairPanel()));
		}

	}

}
