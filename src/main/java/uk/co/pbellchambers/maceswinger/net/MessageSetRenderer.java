package uk.co.pbellchambers.maceswinger.net;

import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.client.render.AnimationRenderer;
import uk.co.pbellchambers.maceswinger.server.GameServer;

public class MessageSetRenderer extends Message {

    int eid;
    String spritesheet;

    public MessageSetRenderer() {
    }

    public MessageSetRenderer(int eid, String spritesheet) {
        this.eid = eid;
        this.spritesheet = spritesheet;
    }

    @Override
    public void runClient(GameClient c) {
        System.out.println(eid + " " + spritesheet);
        c.entities.at(eid).setRenderer(new AnimationRenderer(spritesheet));
    }

    @Override
    public void runServer(GameServer s) {
    }
}
