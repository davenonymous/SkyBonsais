package org.dave.skybonsais;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.dave.skybonsais.config.ConfigurationHandler;
import org.dave.skybonsais.util.Logz;

@Mod(modid = SkyBonsais.MODID, version = SkyBonsais.VERSION, name = "SkyBonsais", acceptedMinecraftVersions = "[1.12,1.13)")
public class SkyBonsais {
    public static final String MODID = "skybonsais";
    public static final String VERSION = "1.0.1";

    @Mod.Instance(SkyBonsais.MODID)
    public static SkyBonsais instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logz.logger = event.getModLog();

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(ConfigurationHandler.class);
    }
}
