package skily_leyu.mistyrain.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.MRBlock;
import skily_leyu.mistyrain.item.itemgroup.MRGroup;

public class MRItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MistyRain.MOD_ID);

    public static RegistryObject<Item> itemSnowVelvet = ITEMS.register("mr_snow_velvet", ()->{
        return new BlockItem(MRBlock.blockSnowVelvet.get(), new Item.Properties().tab(MRGroup.potPlantGroup));
    });
    public static RegistryObject<Item> itemWoodenPot = ITEMS.register("mr_wooden_pot", ()->{
        return new BlockItem(MRBlock.blockWoodenPot.get(), new Item.Properties().tab(MRGroup.potPlantGroup));
    });
}