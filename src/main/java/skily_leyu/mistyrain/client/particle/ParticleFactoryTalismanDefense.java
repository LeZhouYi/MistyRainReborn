package skily_leyu.mistyrain.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import org.apache.logging.log4j.Level;
import skily_leyu.mistyrain.common.MistyRain;

public class ParticleFactoryTalismanDefense implements IParticleFactory<ParticleDataTalismanDefense> {
    private final IAnimatedSprite sprites;

    public ParticleFactoryTalismanDefense(IAnimatedSprite sprite) {
        this.sprites = sprite;
    }

    @Override
    public Particle createParticle(ParticleDataTalismanDefense typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        ParticleTalismanDefense particle = new ParticleTalismanDefense(worldIn, x, y, z);
        MistyRain.getLogger().log(Level.DEBUG,"{0},{1},{2}",x,y,z);
        particle.pickSprite(sprites);
        return particle;
    }
}
