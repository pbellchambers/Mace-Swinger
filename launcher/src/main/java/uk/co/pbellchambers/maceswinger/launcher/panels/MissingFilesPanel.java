package uk.co.pbellchambers.maceswinger.launcher.panels;

import aurelienribon.slidinglayout.SLKeyframe.Callback;
import uk.co.pbellchambers.maceswinger.launcher.MainFrame;
import uk.co.pbellchambers.maceswinger.launcher.MainFrame.View;
import uk.co.pbellchambers.maceswinger.launcher.utils.LauncherUtils;
import uk.co.pbellchambers.maceswinger.launcher.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MissingFilesPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public MissingFilesPanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Resources.foreground));
        setBackground(Resources.background.brighter().brighter());
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        JLabel lblUpdateAvailable = new JLabel("<html><center>Some crucial game files appear to be missing.<br>They will be downloaded now.</center></html>");
        lblUpdateAvailable.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 20));
        lblUpdateAvailable.setForeground(Resources.foreground);
        lblUpdateAvailable.setHorizontalAlignment(SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.NORTH, lblUpdateAvailable, 13, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblUpdateAvailable, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, lblUpdateAvailable, -10, SpringLayout.EAST, this);
        add(lblUpdateAvailable);

        JButton btnOk = new JButton("OK");
        springLayout.putConstraint(SpringLayout.SOUTH, lblUpdateAvailable, -6, SpringLayout.NORTH, btnOk);
        btnOk.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
        springLayout.putConstraint(SpringLayout.WEST, btnOk, -100, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.EAST, btnOk, -10, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -10, SpringLayout.SOUTH, this);
        add(btnOk);

        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                MainFrame.mainFrame.animateBetween(View.MISSINGFILES, View.MENU, new Callback() {
                    public void done() {
                        LauncherUtils.downloadGame();
                    }
                });
            }
        });
    }
}
