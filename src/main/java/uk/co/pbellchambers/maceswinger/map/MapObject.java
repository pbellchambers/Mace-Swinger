package uk.co.pbellchambers.maceswinger.map;

import org.magnos.entity.EntityList;

public interface MapObject {

    public Object spawn(EntityList list, int x, int y, String... params);
}
