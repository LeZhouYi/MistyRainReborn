package skily_leyu.mistyrain.client.particle;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public class ParticleTalismanDefense extends SpriteTexturedParticle {
    protected ParticleTalismanDefense(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.lifetime = 1000;
        final float PARTICLE_SCALE_FOR_ONE_METRE = 0.5F;
        this.quadSize = PARTICLE_SCALE_FOR_ONE_METRE; //贴图尺寸
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}