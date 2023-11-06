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
    private MRItem() {
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MistyRain.MOD_ID);
    public static final RegistryObject<Item> itemWoodenPot = ITEMS.register("mr_wooden_pot",
            () -> new BlockItem(MRBlock.blockWoodenPot.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemSnowVelvet = ITEMS.register("mr_snow_velvet", () -> new BlockItem(MRBlock.blockSnowVelvet.get(), new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemClayPot = ITEMS.register("mr_clay_pot",
            () -> new BlockItem(MRBlock.blockClayPot.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemNarcissus = ITEMS.register("mr_narcissus",
            () -> new BlockItem(MRBlock.blockNarcissus.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemHomingPlum = ITEMS.register("mr_homing_plum",
            () -> new BlockItem(MRBlock.blockHomingPlum.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemGreenWisteria = ITEMS.register("mr_green_wisteria",
            () -> new BlockItem(MRBlock.blockGreenWisteria.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemCandleRush = ITEMS.register("mr_candle_rush",
            ()->new BlockItem(MRBlock.blockCandleRush.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
    public static final RegistryObject<Item> itemBreezeRattan = ITEMS.register("mr_breeze_rattan",
            ()->new BlockItem(MRBlock.blockBreezeRattan.get(),
                    new Item.Properties().tab(MRGroup.mistyMainGroup)));
}