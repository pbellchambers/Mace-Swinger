package uk.co.pbellchambers.maceswinger;

import org.magnos.entity.ComponentValueFactory;
import uk.co.pbellchambers.maceswinger.items.Item;



public class ItemVal implements ComponentValueFactory<ItemVal>
{

	public Item item;
	public ItemVal() {}
	
	public ItemVal(Item item) {
		this.item=item;
	}

	@Override
	public ItemVal create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemVal clone(ItemVal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemVal copy(ItemVal from, ItemVal to) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
