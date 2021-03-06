package uk.co.pbellchambers.maceswinger.mods;

import com.moomoohk.Mootilities.FileUtils.FileUtils;
import com.moomoohk.Mootilities.OSUtils.OSUtils;
import uk.co.pbellchambers.maceswinger.Resources;
import uk.co.pbellchambers.maceswinger.client.GameClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class ModuleLoader {

    private static URLClassLoader loader;
	public static ArrayList<Mod> mods = new ArrayList<Mod>();
    public static ArrayList<ModTexture> tileTextures = new ArrayList<ModTexture>();
    public static ArrayList<ModTexture> maceTextures = new ArrayList<ModTexture>();
    public static ArrayList<ModTexture> mobTextures = new ArrayList<ModTexture>();

    public static void debugPasteCoreMod() {
        File coreDirBuild = new File("build/classes/main/uk/co/pbellchambers/maceswinger/core");
        File coreDir = new File("modules/core");
        File resDir = new File("modules/res");
        File newlocdir = new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger/modules/core");
        File newresdir = new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger/modules/res");
        try {
            FileUtils.copyFolder(coreDir, newlocdir);
            FileUtils.copyFolder(coreDirBuild, newlocdir);
            FileUtils.copyFolder(resDir, newresdir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<File> getFilesInFolder(File folder) {
        ArrayList<File> files = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                files.addAll(getFilesInFolder(fileEntry));
            } else {
                if (fileEntry.isFile()) {
                    files.add(fileEntry);
                }
            }
        }
        return files;
    }

    public static void initMods() {
        try {
            loader = new URLClassLoader(new URL[]{new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules").toURI().toURL()});
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        File file = new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        for (String directory : directories) {
            for (File f : getFilesInFolder(new File(file.toString() + File.separator + directory))) {
                String c = "uk.co.pbellchambers.maceswinger." + f.toString().substring((OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules").length() + 1).replace(File.separatorChar, '.');
				if (!c.endsWith(".class")) {
					continue;
				}
				if (c.contains("$")) {
					continue;
				}
                c = c.substring(0, c.length() - 6);
                Object o = null;
                try {
                    o = loader.loadClass(c).getConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (o != null && o.getClass().isAnnotationPresent(ModInfo.class)) {
                    Mod info = (Mod) o;
                    info.info();
                    info.init();
                    mods.add(info);
                }
            }
        }
        combineTextures();
    }

    private static void combineTextures() {

        for (ModTexture tex : Mod.textures) {
            switch (tex.type) {

                case TILE:
                    tileTextures.add(tex);
                    break;
                case MACE:
                    maceTextures.add(tex);
                    break;
                case MOB:
                    mobTextures.add(tex);
                    break;

            }
        }

        stitchTextures(TextureType.TILE, "image/vmobs.png");
        stitchTextures(TextureType.TILE, "image/vtileset.png");
        stitchTextures(TextureType.TILE, "image/vtileset.png");

    }

    static String resFolder = OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules" + File.separator + "res" + File.separator;

    private static void stitchTextures(TextureType type, String path) {

        int nheight = 0;
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        try {
            BufferedImage vanillaImage = ImageIO
                    .read(Resources.get(path));
            int vwidth = vanillaImage.getWidth();
            int vheight = vanillaImage.getHeight();

            for (ModTexture tex : tileTextures) {
                BufferedImage toAddImage = ImageIO
                        .read(new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules" + File.separator + "res" + File.separator + tex.name + ".png"));
                int nwidth = toAddImage.getWidth();
                nheight += toAddImage.getHeight();
                if (toAddImage.getHeight() % 32 != 0) {
                    System.err.println("Mod texture not correct height! Not multiple of 32");
                    GameClient.exit(1);
                }
                images.add(toAddImage);
            }

            BufferedImage combined = new BufferedImage(vwidth, nheight + vheight, BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            g.drawImage(vanillaImage, 0, 0, null);
            int length = 0;
            for (BufferedImage im : images) {
                g.drawImage(im, 0, vheight + length, null);
                length += im.getHeight();
            }
            System.out.println(nheight + vheight);
            switch (type) {
                case TILE:
                    ImageIO.write(combined, "PNG", new File(resFolder + "tileset.png"));
                    break;
                case MOB:
                    ImageIO.write(combined, "PNG", new File(resFolder + "mob.png"));
                    break;
                case MACE:
                    ImageIO.write(combined, "PNG", new File(resFolder + "maces.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
