package skily_leyu.mistyrain.common.item.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.item.MRItems;

import javax.annotation.Nonnull;

public class MistyMainGroup extends ItemGroup{

    public MistyMainGroup() {
        super("misty_main_group");
    }

    @Override
    @Nonnull
    public ItemStack makeIcon() {
        return new ItemStack(MRItems.snowVelvet);
    }

}
