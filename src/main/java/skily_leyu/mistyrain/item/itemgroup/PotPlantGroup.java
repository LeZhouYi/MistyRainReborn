package skily_leyu.mistyrain.item.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.item.MRItem;

public class PotPlantGroup extends ItemGroup{

    public PotPlantGroup() {
        super("pot_plant_group");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MRItem.itemSnowVelvet.get());
    }

}
