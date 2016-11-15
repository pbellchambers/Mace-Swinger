package uk.co.pbellchambers.maceswinger.launcher.utils;

import com.moomoohk.Mootilities.OSUtils.OSUtils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 20, 2013
 */
public class Resources {

    public static Color background = new Color(0x1b2028);
	public static Color foreground = new Color(0xd7b818);
    public static Font PTSans;
    public static URL logo, footer, settingsLogo, shield;
    public static String truststore, saveDir, gamePath;
    public static File versionFile;
    public static UIDefaults defaults = new UIDefaults();
    public static HyperlinkListener hyperlinkListener = new HyperlinkListener() {
        public void hyperlinkUpdate(HyperlinkEvent arg0) {
			if (arg0.getEventType() == EventType.ACTIVATED) {
				OSUtils.browse(arg0.getURL().toExternalForm());
			}
        }
    };

    static {
        switch (OSUtils.getCurrentOS()) {
            case OTHER:
                saveDir = OSUtils.getDynamicStorageLocation() + "Mace Swinger/";
                break;
            case MACOSX:
                saveDir = OSUtils.getDynamicStorageLocation() + "Mace Swinger/";
                break;
            case UNIX:
                saveDir = OSUtils.getDynamicStorageLocation() + ".maceswinger/";
                break;
            case WINDOWS:
                saveDir = OSUtils.getDynamicStorageLocation() + "Mace Swinger/";
                break;
        }
        defaults.put("EditorPane[Enabled].backgroundPainter", background.brighter().brighter());
        try {
            System.out.println("Save directory: " + saveDir);
            System.out.println("Loading fonts...");
            loadFonts();
            System.out.println("Done");
            System.out.println("Loading images...");
            loadImages();
            System.out.println("Done");
            System.out.print("Setting truststore... ");
            truststore = saveDir + "cert/truststore.jks";
            System.setProperty("javax.net.ssl.keyStore", truststore);
            System.setProperty("javax.net.ssl.trustStore", truststore);
            System.out.println("Done\n");
            gamePath = "Mace-Swinger.jar";
            versionFile = new File(saveDir + "game/version");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void loadFonts() throws FontFormatException, IOException {
        System.out.print("- PT Sans.ttf...");
        PTSans = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/font/PT Sans.ttf"));
        System.out.println(" Done");
        System.out.print("Registering fonts...");
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(PTSans);
        System.out.println(" Done");
    }

    private static void loadImages() {
        System.out.print("- logo.png...");
        logo = Resources.class.getResource("/image/logo.png");
        System.out.println(" Done");
        System.out.print("- footer.png...");
        footer = Resources.class.getResource("/image/footer.png");
        System.out.println(" Done");
        System.out.print("- settings.png...");
        settingsLogo = Resources.class.getResource("/image/settings.png");
        System.out.println(" Done");
        System.out.print("- shield.png...");
        shield = Resources.class.getResource("/icon/shield.png");
        System.out.println(" Done");
    }
}
