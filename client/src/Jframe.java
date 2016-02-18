import sign.signlink;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Jframe extends client implements ActionListener {

	private static JMenuItem menuItem;
	private JFrame frame;

	public Jframe(String args[]) {
		super();
		try {
			sign.signlink.startpriv(InetAddress.getByName(server));
			initUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void initUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			frame = new JFrame("Dragonicpk - Do you even pk?");
			ImageIcon img = new ImageIcon(signlink.findcachedir() + "sprites/icon.png");
			frame.setIconImage(img.getImage());
			frame.setLayout(new BorderLayout());
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel gamePanel = new JPanel();

			gamePanel.setLayout(new BorderLayout());
			gamePanel.add(this);
			gamePanel.setPreferredSize(new Dimension(765, 503));

			JMenu fileMenu = new JMenu("File");

			String[] mainButtons = new String[] { "Community", "-", "Exit" };

			for (String name : mainButtons) {
				JMenuItem menuItem = new JMenuItem(name);
				if (name.equalsIgnoreCase("-")) {
					fileMenu.addSeparator();
				} else {
					menuItem.addActionListener(this);
					fileMenu.add(menuItem);
				}
			}

			/*JMenuBar menuBar = new JMenuBar();
			JMenuBar jmenubar = new JMenuBar();

			frame.add(jmenubar);
			menuBar.add(fileMenu);
			frame.getContentPane().add(menuBar, BorderLayout.NORTH);*/
			frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
			frame.pack();

			frame.setVisible(true); // can see the client
			frame.setResizable(false); // resizeable frame
			frame.setLocationRelativeTo(null); // this one line puts it in the middle ofthe screen :l
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + server + "/cache");
		} catch (Exception e) {
			return super.getCodeBase();
		}
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public void loadError(String s) {
		System.out.println("loadError: " + s);
	}

	public String getParameter(String key) {
		return "";
	}

	public static void openWebpage(String url) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(new URL(url).toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public void actionPerformed(ActionEvent evt) {
		String cmd = evt.getActionCommand();
		try {
			if (cmd != null) {
				if (cmd.equalsIgnoreCase("exit")) {
					System.exit(0);
				}
				if (cmd.equalsIgnoreCase("Rune-Server")) {
					openWebpage("http://dragonicpk.com/forum/");
				}
			}
		} catch (Exception e) {
		}
	}
}