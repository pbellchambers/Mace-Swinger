package uk.co.pbellchambers.maceswinger.net;

import org.magnos.entity.Entity;
import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.server.GameServer;

public class MessageNewEntity extends Message {

    public MessageNewEntity() {
    }

    @Override
    public void runClient(GameClient c) {
        Entity e = new Entity();
        c.entities.add(e);
    }

    @Override
    public void runServer(GameServer s) {
    }
}
