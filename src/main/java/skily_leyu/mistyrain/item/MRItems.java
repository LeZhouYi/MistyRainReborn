package skily_leyu.mistyrain.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.item.itemgroup.MRGroup;

public class MRItems {
    private MRItems() {
    }

    private static Item.Properties defaultBuilder(){
        return new Item.Properties().tab(MRGroup.mistyMainGroup);
    }

    //pot
    public static final Item woodenPot = new BlockItem(MRBlocks.woodenPot, defaultBuilder());
    public static final Item clayPot = new BlockItem(MRBlocks.clayPot,defaultBuilder());

    //plant
    public static final Item snowVelvet = new BlockItem(MRBlocks.snowVelvet, defaultBuilder());

    public static final Item narcissus = new BlockItem(MRBlocks.narcissus,defaultBuilder());
    public static final Item homingPlum = new BlockItem(MRBlocks.homingPlum,defaultBuilder());
    public static final Item greenWisteria = new BlockItem(MRBlocks.greenWisteria,defaultBuilder());
    public static final Item candleRush = new BlockItem(MRBlocks.candleRush,defaultBuilder());
    public static final Item breezeRattan = new BlockItem(MRBlocks.breezeRattan,defaultBuilder());

    public static final Item herbalsBook = new ItemHerbalsBook(defaultBuilder());

    public static void registerItems(RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();
        register(r,"mr_wooden_pot",woodenPot);
        register(r,"mr_clay_pot",clayPot);
        register(r,"mr_snow_velvet",snowVelvet);
        register(r,"mr_narcissus",narcissus);
        register(r,"mr_homing_plum",homingPlum);
        register(r,"mr_green_wisteria",greenWisteria);
        register(r,"mr_candle_rush",candleRush);
        register(r,"mr_breeze_rattan",breezeRattan);
        register(r,"mr_herbals_book",herbalsBook);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(new ResourceLocation(MistyRain.MOD_ID,name)));
    }
}