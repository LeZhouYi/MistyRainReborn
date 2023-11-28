package skily_leyu.mistyrain.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.MistyRain;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MRParticles {

    private MRParticles() {
    }

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MistyRain.MOD_ID);
    public static final RegistryObject<ParticleType<ParticleDataTalismanDefense>> talismanDefense = PARTICLE_TYPES.register("talisman_defense", ParticleTypeTalismanDefense::new);

    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(talismanDefense.get(), ParticleFactoryTalismanDefense::new);
    }
}

