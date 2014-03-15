package com.maceswinger.mods;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import com.moomoohk.Mootilities.FileUtils.FileUtils;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class ModuleLoader
{
	private static URLClassLoader loader;
	public static ArrayList<Mod> mods = new ArrayList<Mod>();
	public static void debugPasteCoreMod()
	{
		File coredir = new File(System.getProperty("user.dir") + "/../Core/bin/core");
		File newlocdir = new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger/modules/core");
		try
		{
			FileUtils.copyFolder(coredir, newlocdir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static ArrayList<File> getFilesInFolder(File folder)
	{
		ArrayList<File> files = new ArrayList<File>();
		for (final File fileEntry : folder.listFiles())
		{
			if (fileEntry.isDirectory())
			{
				files.addAll(getFilesInFolder(fileEntry));
			}
			else
			{
				if (fileEntry.isFile())
				{
					files.add(fileEntry);
				}
			}
		}
		return files;
	}

	public static void initMods()
	{
		try
		{
			loader = new URLClassLoader(new URL[] { new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules").toURL() }); //TODO: Fix this deprecated usage
		}
		catch (MalformedURLException e1)
		{
			e1.printStackTrace();
		}
		File file = new File(OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules");
		String[] directories = file.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir, name).isDirectory();
			}
		});
		for (int i = 0; i < directories.length; i++)
		{
			for (File f : getFilesInFolder(new File(file.toString() + File.separator + directories[i])))
			{
				String c = f.toString().substring((OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "modules").length() + 1).replace(File.separatorChar, '.');
				if (!c.endsWith(".class"))
					continue;
				if (c.contains("$"))
					continue;
				c = c.substring(0, c.length() - 6);
				Object o = null;
				try
				{
					o = loader.loadClass(c).getConstructor().newInstance();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (o != null && o.getClass().isAnnotationPresent(ModInfo.class))
				{
					Mod info = (Mod) o;
					info.info();
					info.init();
					mods.add(info);
				}
			}
		}
	}
}
