package skily_leyu.mistyrain.tileentity.tileport;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class ModTileEntity extends TileEntity{

    public ModTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition,-1,getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(getBlockState(),pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    /**
     * Server向Client同步数据
     */
    public final void syncToTrackingClients(){
        World world = this.level;
        if(world!=null&&!world.isClientSide()&&world.isAreaLoaded(worldPosition, 1)){
            SUpdateTileEntityPacket packet = this.getUpdatePacket();
            if(packet!=null){
                ServerChunkProvider chunkProvider = ((ServerWorld)world).getChunkSource();
                chunkProvider.chunkMap.getPlayers(new ChunkPos(worldPosition), false).forEach(e->e.connection.send(packet));
            }
            this.setChanged();
        }
    }

}
