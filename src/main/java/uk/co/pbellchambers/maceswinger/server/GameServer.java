
package uk.co.pbellchambers.maceswinger.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;
import org.magnos.entity.Ents;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.render.AnimationRenderer;
import uk.co.pbellchambers.maceswinger.map.MapLoader;
import uk.co.pbellchambers.maceswinger.mods.ModuleLoader;
import uk.co.pbellchambers.maceswinger.net.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

/**
 * Game server's uk.co.pbellchambers.maceswinger.core
 * 
 * @since Feb 4, 2014
 */
public class GameServer
{
	private boolean isRunning;
	private ServerShell shell;
	public static Vector2 playerSpawn = new Vector2();
	public static EntityList entities = new EntityList();
	public static Runnable onPlayerConnect;
	private static boolean modsInitialized = false;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            Server shell to use
	 */
	public GameServer(ServerShell shell)
	{
		if (shell == null)
			throw new IllegalArgumentException("Shell can't be null!");
		isRunning = false;
		this.shell = shell;
	}

	/**
	 * Starts the server.
	 * 
	 * @param port
	 *            Port to start the server on
	 */
	public void run(int port)
	{
		if (!modsInitialized) //Either this way or dispose of the mods when the server is closed.
		{
			ModuleLoader.debugPasteCoreMod();
			//ModuleLoader.initMods();
			modsInitialized = true;
		}

		isRunning = true;
		Server server = new Server();
		KryoReg.reg(server.getKryo());
		try
		{
			server.bind(port);
			shell.serverStarted(port);
		}
		catch (IOException e)
		{
			shell.portInUse(port);
			return;
		}
		MapLoader.loadMap(entities, "map/test2.map");
		server.addListener(new Listener.LagListener(100, 300, new Listener()
		{
			@Override
			public void connected(Connection connection) //Client connects to server
			{
				shell.clientConnected(new Client("Username", connection.getRemoteAddressTCP().getHostName()));

				if (onPlayerConnect != null)
					onPlayerConnect.run();
				System.out.println(entities.size());
				for (int i = 0; i < entities.size(); i++)
				{
					Entity e = entities.at(i);
					connection.sendTCP(new MessageNewEntity());
					for (int j = 0; j < Ents.getComponents().size(); j++)
						if (e.has(Ents.getComponents().getDefinition(j)))
							connection.sendTCP(new MessageSetComponent(e.id, Ents.getComponents().getDefinition(j).id, e.get(Ents.getComponents().getDefinition(j))));
					for (int j = 0; j < Ents.getControllers().size(); j++)
						if (e.has(Ents.getControllers().getDefinition(j)))
							connection.sendTCP(new MessageSetController(e.id, Ents.getControllers().getDefinition(j).id));
					connection.sendTCP(new MessageSetRenderer(e.id, ((AnimationRenderer) e.getRenderer()).internalSpriteRenderer.spriteSheet));
				}
			}

			public void disconnected(Connection connection)
			{
				shell.clientDisconnected(null);
			}

			public void received(Connection connection, Object o) //Server receives message
			{
				if (o.getClass() != FrameworkMessage.keepAlive.getClass() && o instanceof Message) //Message handling
				{
					((Message) o).runServer(GameServer.this);
					shell.message(new Client("Username", "IP"), o.getClass().getName());
				}
			}
		}));
		while (isRunning)
		{
			try
			{
				server.update(0);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		shell.serverStopped();
		server.stop();
	}

//	public static void main(String[] args)
//	{
//		new GameServer(new ServerShell()
//		{
//
//			@Override
//			public void clientDisconnected(Client c)
//			{
//				System.out.println(c.getUsername() + " disconnected");
//			}
//
//			@Override
//			public void clientConnected(Client c)
//			{
//				System.out.println(c.getUsername() + " connected");
//			}
//
//			@Override
//			public void serverStarted(int port)
//			{
//				System.out.println("Server started " + port);
//			}
//
//			@Override
//			public void serverStopped()
//			{
//				System.out.println("Server stopped");
//			}
//
//			@Override
//			public void message(Client c, String message)
//			{
//				System.out.println(c.getUsername() + ": " + message);
//			}
//
//			@Override
//			public void portInUse(int port)
//			{
//				System.out.println("Unable to lock port: " + port + ", is another server running?");
//			}
//		}).run(2650);
//	}

	public void stop()
	{
		this.isRunning = false;
	}

	public boolean isRunning()
	{
		return isRunning;
	}

	public static String getIP()
	{
		try
		{
			URL url = new URL("http://ipecho.net/plain");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String ipString = new String();
			ipString = (in.readLine()).trim();
			if (!(ipString.length() > 0))
			{
				try
				{
					InetAddress ip = InetAddress.getLocalHost();
					System.out.println((ip.getHostAddress()).trim());
					return ((ip.getHostAddress()).trim());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			System.out.println(ipString);
			return ipString;
		}
		catch (Exception e)
		{
			try
			{
				InetAddress ip = InetAddress.getLocalHost();
				System.out.println((ip.getHostAddress()).trim());
				return ((ip.getHostAddress()).trim());
			}
			catch (Exception e2)
			{
				return "whoops";
			}
		}
	}
}

