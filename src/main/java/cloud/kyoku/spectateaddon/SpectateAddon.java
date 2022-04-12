package cloud.kyoku.spectateaddon;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpectateAddon extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        // register spectate command
        getCommand("spectate").setExecutor(new SpectateCommand());

        // ensure the user is using MBedwars
        final int supportedAPIVersion = 10; // find the correct number in the tab "Table of API Versions"
        final String supportedVersionName = "5.0.9"; // update this accordingly to the number, otherwise the error will be wrong

        try {
            Class apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
            int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

            if (apiVersion < supportedAPIVersion)
                throw new IllegalStateException();
        } catch(Exception e) {
            getLogger().warning("Sorry, your installed version of MBedwars is not supported. Please install at least v" + supportedVersionName);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}