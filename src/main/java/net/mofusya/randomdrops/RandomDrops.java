package net.mofusya.randomdrops;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mofusya.randomdrops.config.ModConfigs;
import org.slf4j.Logger;

@Mod(RandomDrops.MOD_ID)
public class RandomDrops {
    public static final String MOD_ID = "randomdrops";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RandomDrops() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModConfigs.COMMON.load();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        /*
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
         */
    }
}
