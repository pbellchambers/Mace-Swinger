package uk.co.pbellchambers.maceswinger.test.inventory;

import org.magnos.entity.ComponentValueFactory;

import java.util.ArrayList;

public class Inventory implements ComponentValueFactory<Inventory> {

    private ArrayList<ItemStack> stacks;

    public Inventory() {
        this.stacks = new ArrayList<ItemStack>();
    }

    public void addItem(Item i) {
        ItemStack temp = null;
        for (ItemStack stack : stacks) {
            if (stack.getBaseItem().equals(i) && !stack.isFull()) {
                temp = stack;
            }
        }
        if (temp != null) {
            temp.addItem(i);
        } else {
            ItemStack temp2 = new ItemStack(i);
            temp2.addItem(i);
            stacks.add(temp2);
        }
    }

    public void removeItem(Item i) {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                stack.removeItem(i);
            }
        }
    }

    public ArrayList<ItemStack> getStacks() {
        return this.stacks;
    }

    public int getSize() {
        return this.stacks.size();
    }

    @Override
    public Inventory create() {
        return new Inventory();
    }

    @Override
    public Inventory clone(Inventory value) {
        return copy(value, new Inventory());
    }

    @Override
    public Inventory copy(Inventory from, Inventory to) {
        to.stacks = from.stacks;
        return to;
    }
}