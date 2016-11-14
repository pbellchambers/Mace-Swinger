package uk.co.pbellchambers.maceswinger.map;

import org.magnos.entity.EntityList;
import uk.co.pbellchambers.maceswinger.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Loads maps from file.
 * 
 * @since Feb 5, 2014
 */
public class MapLoader
{
	static HashMap<String, MapObject> mapObjects = new HashMap<String, MapObject>();

	public static void addMapObject(String name, MapObject object)
	{
		mapObjects.put(name, object);
	}

	private static class MapObjectPlusParams
	{
		MapObject mo;
		String[] params;

		public MapObjectPlusParams(MapObject mo, String[] params)
		{
			this.mo = mo;
			this.params = params;
		}

		public String toString()
		{
			String params = "[";
			for (String param : this.params)
				params += param + ", ";
			params = params.substring(0, params.length() - 2) + "]";
			return this.mo + params;
		}
	}

	public static void loadMap(EntityList entityList, String pathToMap)
	{
		Scanner mapScanner = new Scanner(Resources.get(pathToMap));

		HashMap<Character, MapObjectPlusParams> declares = new HashMap<Character, MapObjectPlusParams>();
		ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();

		loadMapHeader(mapScanner, declares);
		loadMapBody(mapScanner, map);

		for (int i = 0; i < map.size(); i++)
			for (int j = 0; j < map.get(i).size(); j++)
			{
				String c = map.get(i).get(j);
				MapObjectPlusParams mop = declares.get(c.charAt(0));
				if (mop != null) {
					mop.mo.spawn(entityList, j, map.size() - i, mop.params);
				}
			}
		mapScanner.close();
	}

	private static void loadMapBody(Scanner mapScanner, ArrayList<ArrayList<String>> map)
	{
		while (mapScanner.hasNextLine())
		{
			String str = mapScanner.nextLine();
			if (str.equals("}"))
				break;
			map.add(new ArrayList<String>());
			for (int i = 0; i < str.length(); i++)
				map.get(map.size() - 1).add(str.charAt(i) + "");
		}
	}

	private static void loadMapHeader(Scanner mapScanner, HashMap<Character, MapObjectPlusParams> declares)
	{
		while (mapScanner.hasNextLine())
		{
			String line = mapScanner.nextLine();
			if (line.startsWith("{"))
				break;
			else
			{
				String identifier = line.substring(0, 1);
				String type = line.substring(2, line.indexOf("("));
				String paramsText = line.substring(line.indexOf("(") + 1, line.indexOf(")")) + ",";
				ArrayList<String> params = new ArrayList<String>();
				while (!paramsText.equals(""))
				{
					params.add(paramsText.substring(0, paramsText.indexOf(",")));
					paramsText = paramsText.substring(paramsText.indexOf(",") + 1);
				}
				String[] finalParams = new String[params.size()];
				for (String str : params)
					finalParams[params.indexOf(str)] = str;
				declares.put(identifier.charAt(0), new MapObjectPlusParams(mapObjects.get(type), finalParams));
			}
		}
	}
}
