package skily_leyu.mistyrain.item.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.item.MRItem;

public class MistyMainGroup extends ItemGroup{

    public MistyMainGroup() {
        super("misty_main_group");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MRItem.itemWoodenPot.get());
    }

}
