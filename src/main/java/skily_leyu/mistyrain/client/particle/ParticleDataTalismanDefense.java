package skily_leyu.mistyrain.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.Locale;

public class ParticleDataTalismanDefense implements IParticleData {
    public static final IDeserializer<ParticleDataTalismanDefense> DESERIALIZER = new IDeserializer<ParticleDataTalismanDefense>() {

        @Override
        public ParticleDataTalismanDefense fromCommand(ParticleType<ParticleDataTalismanDefense> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            return new ParticleDataTalismanDefense();
        }

        @Override
        public ParticleDataTalismanDefense fromNetwork(ParticleType<ParticleDataTalismanDefense> particleTypeIn, PacketBuffer buffer) {
            return new ParticleDataTalismanDefense();
        }
    };

    public ParticleDataTalismanDefense() {
        //Need to add some data
    }

    @Override
    public ParticleType<?> getType() {
        return MRParticles.talismanDefense.get();
    }

    @Override
    public void writeToNetwork(PacketBuffer buffer) {
        //Need to write some data
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s",
                this.getType().getRegistryName());
    }

}

