package uk.co.pbellchambers.maceswinger.net;

import uk.co.pbellchambers.maceswinger.client.GameClient;
import uk.co.pbellchambers.maceswinger.server.GameServer;

/**
 * Represents messages which get sent between server and client.
 *
 * @since Feb 2, 2014
 */
public abstract class Message {

    /**
     * Code to run on the client when it receives this message.
     *
     * @param c Client in question
     */
    public abstract void runClient(GameClient c);

    /**
     * Code to run on the server when it receives this message.
     *
     * @param s Server in question
     */
    public abstract void runServer(GameServer s);
}
