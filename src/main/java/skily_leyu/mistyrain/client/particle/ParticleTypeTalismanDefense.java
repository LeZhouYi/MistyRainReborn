package skily_leyu.mistyrain.client.particle;

import com.mojang.serialization.Codec;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.particles.ParticleType;

@MethodsReturnNonnullByDefault
public class ParticleTypeTalismanDefense extends ParticleType<ParticleDataTalismanDefense> {
    public ParticleTypeTalismanDefense() {
        super(true
                , ParticleDataTalismanDefense.DESERIALIZER);
    }

    @Override
    public Codec<ParticleDataTalismanDefense> codec() {
        return Codec.unit(new ParticleDataTalismanDefense());
    }
}

