package not.hub.bscb;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSCB extends JavaPlugin implements Listener {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String PROXIED_NATIVE_COMMAND_SENDER = "org.bukkit.craftbukkit.v1_12_R1.command.ProxiedNativeCommandSender";

    private boolean log;

    @Override
    public void onEnable() {

        // config start
        getConfig().addDefault("log-to-console", true);
        getConfig().options().copyDefaults(true);
        saveConfig();
        log = getConfig().getBoolean("log-to-console");
        if (log) {
            getLogger().info("Logging to console.");
        }
        // config end

        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onServerCommand(ServerCommandEvent e) {

        if (!e.getSender().getClass().getCanonicalName().equals(PROXIED_NATIVE_COMMAND_SENDER)) {
            return;
        }

        e.setCancelled(true);

        if (log) {
            getLogger().info(
                    "Canceled Server Command: "
                            + '{'
                            + "command=" + e.getCommand() + ','
                            + "sender=" + e.getSender().getName() + ','
                            + "source=" + e.getSender().getClass().getCanonicalName()
                            + '}'
            );
        }

    }

}
