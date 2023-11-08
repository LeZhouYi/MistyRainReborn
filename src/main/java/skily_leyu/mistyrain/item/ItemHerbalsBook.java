package skily_leyu.mistyrain.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemHerbalsBook extends Item {
    public ItemHerbalsBook(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(world.isClientSide){

        }
        return ActionResult.pass(playerEntity.getItemInHand(hand));
    }
}
