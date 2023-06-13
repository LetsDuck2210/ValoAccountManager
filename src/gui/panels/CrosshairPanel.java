package gui.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import gui.GuiConstants;
import gui.components.Button;
import gui.components.CrosshairButton;
import gui.components.Headline;
import gui.components.InputField;
import gui.components.InputFieldWithButton;
import main.ValoAccountManager;
import valorant.crosshair.OfflineConverter;

public class CrosshairPanel extends JPanel {
	private static final long serialVersionUID = -8127024845567123317L;
	private CrosshairMainPanel mainPanel;

	public CrosshairPanel() {
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
		add(new Headline("Crosshairs"), c);
		c.gridy = 1;
		add(mainPanel = new CrosshairMainPanel(), c);
		c.gridy = 2;
		add(new EditPanel(), c);
	}

	public void addCrosshair(String code) {
		mainPanel.addCrosshair(code);
	}

	public void removeCrosshair(String code) {
		mainPanel.removeCrosshair(code);
	}

	private class CrosshairMainPanel extends JPanel {
		private static final long serialVersionUID = 4980204593054051356L;
		private JPanel pane;
		private JScrollPane scroll;
		private FlowLayout paneLayout;

		public CrosshairMainPanel() {
			setOpaque(true);
			setBackground(GuiConstants.BACKGROUND_COLOR);
//			setBackground(Color.red);
			setLayout(new GridLayout(1, 1));
			
			pane = new JPanel();
			pane.setLayout(paneLayout = new FlowLayout(FlowLayout.LEFT));
			pane.setBackground(getBackground());
			pane.setPreferredSize(new Dimension((GuiConstants.CROSSHAIR_SIZE.width + paneLayout.getHgap()) * 3, 0));
			
			scroll = new JScrollPane(pane);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.getVerticalScrollBar().setUnitIncrement(16);
			scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
				public Dimension getPreferredSize(JComponent c) {
					return new Dimension(0, 0);
				}
			});
			scroll.getVerticalScrollBar().setBackground(getBackground());
			scroll.setBorder(null);
			
			add(scroll);
		}

		void addCrosshair(String code) {
			pane.add(new CrosshairButton(code));
			calcPaneSize();
		}

		void removeCrosshair(String code) {
			for (var button : pane.getComponents()) {
				var castedButton = (CrosshairButton) button;
				if (castedButton.getCode().equals(code))
					pane.remove(castedButton);
			}
			calcPaneSize();
		}
		
		void calcPaneSize() {
			var old = pane.getPreferredSize();
			old.height = (int) Math.round((GuiConstants.CROSSHAIR_SIZE.height + paneLayout.getVgap()) * pane.getComponentCount() / 2.5) + paneLayout.getVgap();
			pane.setPreferredSize(old);
		}
	}

	private class EditPanel extends JPanel {
		private static final long serialVersionUID = -4110069119627432820L;
		private InputField inputField;
		private JPanel buttonPanel = new JPanel();
		private GridLayout defaultLayout = new GridLayout(0, 1);
		private Optional<View> extendedShowing = Optional.empty();

		EditPanel() {
			super();
			setOpaque(true);
			setBackground(GuiConstants.BACKGROUND_COLOR);
//			setBackground(Color.green);
			setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 5, 5, GuiConstants.BACKGROUND_COLOR),
					new EmptyBorder(5, 5, 5, 5)));
			
			inputField = new InputFieldWithButton("code: ", "submit", a -> {
				if(!inputField.get().equals(""))
					ValoAccountManager.addCrosshair(inputField.get());
				inputField.clear();
			});

			buttonPanel.setOpaque(true);
			buttonPanel.setBackground(GuiConstants.BACKGROUND_COLOR);
			buttonPanel.setLayout(new GridLayout(1, 2));
			buttonPanel.add(new Button("add", a -> {
				toggleExtended(View.ADD);
			}));
			buttonPanel.add(new Button("delete", a -> {
				toggleExtended(View.DELETE);
			}));
			var minSize = buttonPanel.getMinimumSize();
			minSize.height += 10;
			buttonPanel.setMinimumSize(minSize);
			
			showDefault();
		}
		
		private void toggleExtended(View view) {
			if(extendedShowing.isEmpty()) {
				if(view == View.ADD) showAdd();
				else showDelete();
			} else if (extendedShowing.get() == view) {
				showDefault();
			} else {
				if(view == View.ADD) showAdd();
				else showDelete();
			}
		}

		private void showDefault() {
			extendedShowing = Optional.empty();
			removeAll();
			setLayout(defaultLayout);
			add(buttonPanel);
			refresh();
		}

		private void showAdd() {
			extendedShowing = Optional.of(View.ADD);
			removeAll();
			add(inputField);
			add(buttonPanel);
			refresh();
		}
		
		private void showDelete() {
			var selected = ValoAccountManager.getCurrentCrosshair();
			if(selected.isEmpty())
				return;

			var code = selected.get().getCode();
			var image = new OfflineConverter(new Dimension(40, 40)).convert(code);
			var icon = new ImageIcon(image);
			
			var deleteButton = new JButton(icon);
			extendedShowing = Optional.of(View.DELETE);
			removeAll();
			deleteButton.setText("click to delete");
			deleteButton.setFont(GuiConstants.FONT);
			deleteButton.setOpaque(true);
			deleteButton.setForeground(GuiConstants.TEXT_COLOR_WARNING);
			deleteButton.setBackground(GuiConstants.COMPONENT_COLOR);
			deleteButton.addActionListener(a -> {
				ValoAccountManager.removeCrosshair(code);
				showDefault();
			});
			for(var fl : deleteButton.getFocusListeners())
				deleteButton.removeFocusListener(fl);
			add(deleteButton);
			add(buttonPanel);
			refresh();
		}
		
		private void refresh() {
			revalidate();
			repaint();
		}
		
		private enum View {
			ADD, DELETE
		}
	}

}
