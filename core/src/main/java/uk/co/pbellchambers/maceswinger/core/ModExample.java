package uk.co.pbellchambers.maceswinger.core;

import uk.co.pbellchambers.maceswinger.items.Condition;
import uk.co.pbellchambers.maceswinger.items.Material;
import uk.co.pbellchambers.maceswinger.mods.*;

@ModInfo
public class ModExample extends Mod {

    public static final Material STEEL = new Material(102, "Steel", 40);
    public static final Condition MIGHTYFINE = new Condition(102, "Mighy Fine", 30);
    public static final ModTexture TILES = new ModTexture(TextureType.TILE, "tileset");
    public static final ModTexture poo = new ModTexture(TextureType.TILE, "poo");
    public static final Tile tile = new Tile("poo", poo, 0, 0);

    public void init() {
        registerMaterial(STEEL);
        registerCondition(MIGHTYFINE);
        addTexture(TILES);
        addTexture(poo);
        addTile(tile);
    }

    @Override
    public void info() {
        name = "Example Mod";
        desc = "how lovely";

    }
}