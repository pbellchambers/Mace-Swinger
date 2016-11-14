package uk.co.pbellchambers.maceswinger.core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;
import org.magnos.entity.vals.IntVal;
import uk.co.pbellchambers.maceswinger.Animation;
import uk.co.pbellchambers.maceswinger.Components;
import uk.co.pbellchambers.maceswinger.Rectangle;
import uk.co.pbellchambers.maceswinger.Vector2;
import uk.co.pbellchambers.maceswinger.client.render.AnimationRenderer;
import uk.co.pbellchambers.maceswinger.core.Core;
import uk.co.pbellchambers.maceswinger.map.MapObject;

public class GoblinSpawn implements MapObject
{

	@Override
	public Object spawn(EntityList list, int x, int y, String... params)
	{
		Entity goblin = new Entity();
		goblin.add(Components.animation);
		goblin.add(Core.Components.collider);
		goblin.add(Components.position);
		goblin.add(Core.Components.velocity);
		goblin.add(Core.Components.jumpCooldown);
		
		goblin.add(Core.Controllers.gravity);
		goblin.add(Core.Controllers.goblinAi);
		goblin.add(Core.Controllers.velocity);

		goblin.set(Components.animation, new Animation(new Animation.Frame(params[0], 5)));
		goblin.set(Core.Components.collider, new Rectangle(x * 32 + 3, y * 32 + 1, 17, 48));
		goblin.set(Components.position, new Vector2(x * 32, y * 32));
		goblin.set(Core.Components.jumpCooldown, new IntVal(0));
		goblin.setRenderer(new AnimationRenderer("mob"));

		list.add(goblin);
		return goblin;
	}

	public String toString()
	{
		return "Goblin";
	}
}
