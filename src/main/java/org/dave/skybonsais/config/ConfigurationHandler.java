package org.dave.skybonsais.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dave.skybonsais.SkyBonsais;
import org.dave.skybonsais.integration.SkyOrchardsDropChances;
import org.dave.skybonsais.util.Logz;

import java.io.File;

public class ConfigurationHandler {
    public static Configuration configuration;
    public static File baseDirectory;
    public static final String CATEGORY_DROPS = "drops";

    public static void init(File configFile) {
        if(configuration != null) {
            return;
        }

        baseDirectory = new File(configFile.getParentFile(), "skybonsais");
        if(!baseDirectory.exists()) {
            baseDirectory.mkdir();
        }

        configuration = new Configuration(new File(baseDirectory, "settings.cfg"), null);
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Logz.info("Loading configuration");

        SkyOrchardsDropChances.stickAmount = configuration.getInt(
                "stickAmount", CATEGORY_DROPS, 3, 0, 64, "How many sticks to drop by default"
        );

        SkyOrchardsDropChances.logAmount = configuration.getInt(
                "logAmount", CATEGORY_DROPS, 1, 0, 64, "How many wood logs to drop by default"
        );

        SkyOrchardsDropChances.leafAmount = configuration.getInt(
                "leafAmount", CATEGORY_DROPS, 1, 0, 64, "How many leaves to drop by default"
        );

        SkyOrchardsDropChances.saplingAmount = configuration.getInt(
                "saplingAmount", CATEGORY_DROPS, 1, 0, 64, "How many saplings to drop by default"
        );

        SkyOrchardsDropChances.resinAmount = configuration.getInt(
                "resinAmount", CATEGORY_DROPS, 2, 0, 64, "How many fruits to drop by default"
        );

        SkyOrchardsDropChances.acornAmount = configuration.getInt(
                "acornAmount", CATEGORY_DROPS, 3, 0, 64, "How many acorns to drop by default"
        );

        SkyOrchardsDropChances.amberAmount = configuration.getInt(
                "amberAmount", CATEGORY_DROPS, 1, 0, 64, "How many ambers to drop by default"
        );

        SkyOrchardsDropChances.stickChance = configuration.getFloat(
                "stickChance", CATEGORY_DROPS, 0.2f, 0.0f, 1.0f, "Default chance for sticks to drop"
        );

        SkyOrchardsDropChances.logChance = configuration.getFloat(
                "logChance", CATEGORY_DROPS, 0.75f, 0.0f, 1.0f, "Default chance for wood logs to drop"
        );

        SkyOrchardsDropChances.leafChance = configuration.getFloat(
                "leafChance", CATEGORY_DROPS, 0.1f, 0.0f, 1.0f, "Default chance for leaves to drop"
        );

        SkyOrchardsDropChances.saplingChance = configuration.getFloat(
                "saplingChance", CATEGORY_DROPS, 0.05f, 0.0f, 1.0f, "Default chance for saplings to drop"
        );

        SkyOrchardsDropChances.resinChance = configuration.getFloat(
                "resinChance", CATEGORY_DROPS, 0.2f, 0.0f, 1.0f, "Default chance for resin to drop"
        );

        SkyOrchardsDropChances.acornChance = configuration.getFloat(
                "acornChance", CATEGORY_DROPS, 0.5f, 0.0f, 1.0f, "Default chance for fruits to drop"
        );

        SkyOrchardsDropChances.amberChance = configuration.getFloat(
                "amberChance", CATEGORY_DROPS, 0.2f, 0.0f, 1.0f, "Default chance for ambers to drop"
        );

        if(configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void saveConfiguration() {
        Logz.info("Saving configuration");
        configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent event) {
        if(!event.getModID().equalsIgnoreCase(SkyBonsais.MODID)) {
            return;
        }

        loadConfiguration();
    }
}
