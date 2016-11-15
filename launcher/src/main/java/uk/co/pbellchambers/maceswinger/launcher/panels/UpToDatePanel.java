package uk.co.pbellchambers.maceswinger.launcher.panels;

import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.co.pbellchambers.maceswinger.launcher.MainFrame;
import uk.co.pbellchambers.maceswinger.launcher.MainFrame.View;
import uk.co.pbellchambers.maceswinger.launcher.utils.LauncherUtils;
import uk.co.pbellchambers.maceswinger.launcher.utils.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class UpToDatePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JEditorPane dtrpnResponse;
    private JLabel lblNoUpdate;
    private JLabel lblChangelog;
    private JButton btnOk;
    private JScrollPane scrollPane;

    public UpToDatePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Resources.foreground));
        setBackground(Resources.background.brighter());
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        lblNoUpdate = new JLabel("All up to date!");
        lblNoUpdate.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 20));
        lblNoUpdate.setForeground(Resources.foreground);
        lblNoUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.NORTH, lblNoUpdate, 13, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblNoUpdate, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, lblNoUpdate, -10, SpringLayout.EAST, this);
        add(lblNoUpdate);

        lblChangelog = new JLabel("Here's the game's full changelog:");
        springLayout.putConstraint(SpringLayout.NORTH, lblChangelog, 63, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblChangelog, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, lblChangelog, -10, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, lblNoUpdate, -6, SpringLayout.NORTH, lblChangelog);
        lblChangelog.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 16));
        lblChangelog.setForeground(Resources.foreground);
        lblChangelog.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblChangelog);

        btnOk = new JButton("OK");
        btnOk.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
        springLayout.putConstraint(SpringLayout.WEST, btnOk, -100, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.EAST, btnOk, -10, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -10, SpringLayout.SOUTH, this);
        add(btnOk);

        scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, lblChangelog, -6, SpringLayout.NORTH, scrollPane);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 91, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnOk);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.setBorder(BorderFactory.createLineBorder(Resources.foreground));
        add(scrollPane);

        dtrpnResponse = new JEditorPane("text/html", null);
        ((HTMLDocument) dtrpnResponse.getDocument()).getStyleSheet().addRule("a{color:#" + Integer.toHexString(new Color(132, 170, 187).brighter().getRGB()).substring(2) + "}");
        dtrpnResponse.addHyperlinkListener(Resources.hyperlinkListener);
        dtrpnResponse.putClientProperty("Nimbus.Overrides", Resources.defaults);
        dtrpnResponse.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        dtrpnResponse.setBackground(Resources.background.brighter().brighter());
        dtrpnResponse.setBorder(new EmptyBorder(0, 5, 0, 5));
        dtrpnResponse.setForeground(new Color(132, 170, 187));
        dtrpnResponse.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
        dtrpnResponse.setEditable(false);
        scrollPane.setViewportView(dtrpnResponse);

        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                MainFrame.mainFrame.animateBetween(View.UPTODATE, View.MENU, null);
            }
        });
    }

    public void updateChangelog() {
        dtrpnResponse.setText("Loading changes...");
        try {
            Document d = LauncherUtils.bootstrap.getChangelog();
            NodeList nList = d.getElementsByTagName("build");
            StringBuilder entries = new StringBuilder();
            for (int temp = nList.getLength() - 1; temp >= 0; temp--) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Integer buildNumber = Integer.parseInt(eElement.getAttribute("buildNumber"));
                    String verString = eElement.getElementsByTagName("buildVersionString").item(0).getTextContent();
                    Scanner reader = new Scanner(eElement.getElementsByTagName("changes").item(0).getTextContent());
                    StringBuilder entry = new StringBuilder("v" + verString + " (build " + buildNumber + "):");
                    while (reader.hasNextLine()) {
                        entry.append(" " + reader.nextLine().trim() + "\n");
                    }
                    entries.append(entry.toString() + "\n");
                    reader.close();
                }
            }
            dtrpnResponse.setText(LauncherUtils.htmlify(d.getElementsByTagName("headerText").item(0).getTextContent().trim() + "\n\n" + entries.toString().trim()));
            dtrpnResponse.setCaretPosition(0);
        } catch (Exception e) {
            new ExceptionDisplayDialog(MainFrame.mainFrame, e);
        }
    }
}
