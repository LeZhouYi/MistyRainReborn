package skily_leyu.mistyrain.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import skily_leyu.mistyrain.client.gui.MRGuis;

public class ItemHerbalsBook extends Item {
    public ItemHerbalsBook(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!world.isClientSide){
            DistExecutor.safeCallWhenOn(Dist.CLIENT,()-> MRGuis::new);
        }
        return super.use(world,playerEntity,hand);
    }
}
