import javax.swing.*;
import java.awt.*;// yes, it does have all the stuff but I'm just going to keep it 
import java.awt.Desktop;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class AppLauncherUI extends JFrame {
	//private List<Process> processes = new ArrayList<>();
	private Map<String, Process> processes = new HashMap<>();
	private void startProcessIfNotRunning(String key, ProcessBuilder pb) throws IOException {
		Process existing = processes.get(key);
		if (existing != null && existing.isAlive()) {
			System.out.println(key + " already running");
			return;
		}
		System.out.println("Starting " + key);
		Process process = pb.start();
		processes.put(key, process);
	};
	private String fetchInfoFromAPI() {
		try {
			URI uri = new URI("http://localhost:5001/");
			java.net.URL url = uri.toURL();
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			int status = conn.getResponseCode();
			if (status != 200) {
				return "For info open one of the web apps";
			}
			java.io.BufferedReader reader = new java.io.BufferedReader( new java.io.InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			String json = response.toString();
			// VERY SIMPLE parsing (no library)
			return formatJsonToText(json);
		} catch (Exception e) {
			return "For info open one of the web apps";
		}
	}
	//
	private String formatJsonToText(String json) {
		return json
			.replace("{", "")
			.replace("}", "")
			.replace("\"", "")
			.replace(",", "\n")
			.replace(":", " => ");
	}

    public AppLauncherUI() {
        setTitle("App Launcher");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
	setSize(1200, 800);
	setLocationRelativeTo(null);
	setVisible(true);
	requestFocus();
	//
	addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Closing app, killing processes...");
			for(Process p : processes.values()) {
				p.destroy();
			}
			// force kill if still alive
			for (Process p : processes.values()) {
				if (p.isAlive()) {
					p.destroyForcibly();
				}
			}
			System.exit(0);
		}
	});
	//
        setLayout(new GridLayout(2, 2)); // split left / right

        // LEFT PANEL (INFO)
        JTextArea info = new JTextArea();
	Timer timer = new Timer(2000, e -> { // every 2 seconds
		String data = fetchInfoFromAPI();
		if(!data.equals("For info open one of the web apps")) {
			info.setText("""
				Here is all the apps

				Information:

			""" + data);
			((Timer) e.getSource()).stop(); // stop once successful
		}
	});
	timer.start();
	//info.setText("Loading info...( may need to open one of the web apps. )");
	SwingUtilities.invokeLater(() -> {
		String data = fetchInfoFromAPI();
		info.setText("""
			Here is all the apps

			Information:

	""" + data);
	});
        /*info.setText("""
Here is all the apps

Information (
if error check if java backend is running on port:
localhost:8080/api/digital-portfolio/contactMe
)

• email => leecash133@gmail.com
• phoneNumber => +1 762 222-3156
• name => Lee Cash
• education => High school Diploma
• linkGithub => https://github.com/LeeFCash
""");*/

        info.setEditable(false);
        info.setBackground(Color.BLACK);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Monospaced", Font.PLAIN, 16));

        add(new JScrollPane(info));

        // RIGHT PANEL (APPS)
        JPanel rightPanel = new JPanel(new GridLayout(1, 2));

        rightPanel.add(createAppPanel("Digital Portfolio web app", "./IMGs/faceBAW.png", "http://localhost:5001/digital_portfolio/home_page"));
        rightPanel.add(createAppPanel("Number Guessing Game web game", "./IMGs/#.png", "http://localhost:5001/numberGuessingGame"));
        rightPanel.add(createAppPanel("SpaceInvaders app game", "./IMGs/S.png", ""));

        add(rightPanel);

        setVisible(true);
    }

    private JPanel createAppPanel(String title, String imagePath, String url) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Load image
        ImageIcon icon = new ImageIcon(imagePath);

        // Resize dynamically when panel changes size
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

		int imgW = w;
        	int imgH = h - 40;

		if (imgW <= 0 || imgH <= 0) return;

                Image scaled = icon.getImage().getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH);
                //Image scaled = icon.getImage().getScaledInstance(imgW, imgH - 40, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            }
        });

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
	//
	panel.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        System.out.println(title + " clicked");

        try {
		ProcessBuilder pb = null;
		if(title.equals("Digital Portfolio web app") || title.equals("Number Guessing Game web game")){
			pb = new ProcessBuilder("python", "app.py");
			pb.directory(new File("./pythonFlaskJavaHub"));
			pb.inheritIO();
		}
		if(title.equals("SpaceInvaders app game")){
			//ProcessBuilder pb = new ProcessBuilder("_JAVA_AWT_WM_NONREPARENTING=1", "java", "app/Main");
			pb = new ProcessBuilder("bash", "-c", "cd SpaceInvaders && javac *.java && java SpaceInvaders");
//			pb = new ProcessBuilder("bash", "-c", "javac *.java");
//			pb = new ProcessBuilder("bash", "-c", "java SpaceInvaders");
			pb.directory(new File("."));
			pb.inheritIO();
		}
            // Use unique key per app
            startProcessIfNotRunning(title, pb);

            if(!url.equals("")){
		    if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                }
            }
	}

        } catch (IOException | URISyntaxException t) {
            t.printStackTrace();
        }
    }
});

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(titleLabel, BorderLayout.SOUTH);
	panel.setVisible(true);
	//panel.toFront();
	panel.requestFocus();

        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppLauncherUI::new);
    }
}
