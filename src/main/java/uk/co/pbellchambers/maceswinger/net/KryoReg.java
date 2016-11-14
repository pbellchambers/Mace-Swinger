package uk.co.pbellchambers.maceswinger.net;

import com.esotericsoftware.kryo.Kryo;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Animation;
import uk.co.pbellchambers.maceswinger.ItemVal;
import uk.co.pbellchambers.maceswinger.Rectangle;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.render.lighting.Block;
import uk.co.pbellchambers.maceswinger.test.inventory.Inventory;
import uk.co.pbellchambers.maceswinger.test.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Registers serializables
 * 
 * @since Feb 2, 2014
 */
public class KryoReg
{
	/**
	 * Registers all the serializable classes
	 * 
	 * @param k
	 *            Kryo instance to register to
	 */
	public static void reg(Kryo k)
	{
		k.register(IntVal.class);
		k.register(Vector2.class);
		k.register(Rectangle.class);
		k.register(Inventory.class);
		k.register(ItemStack.class);
		k.register(ItemVal.class);
		k.register(Block.class);
		k.register(ArrayList.class);
		k.register(Animation.class);
		k.register(Animation.Frame.class);
		k.register(Animation.Frame[].class);

		k.register(MessageNewEntity.class);
		k.register(MessageSetComponent.class);
		k.register(MessageSetController.class);
		k.register(MessageSetRenderer.class);
	}
}
