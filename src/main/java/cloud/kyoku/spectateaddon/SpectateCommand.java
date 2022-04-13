package cloud.kyoku.spectateaddon;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // wait for MBedwars to load
        BedwarsAPI.onReady(() -> {

            // start of command code
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("bw.specplayer")) {
                    if (args.length == 0) {
                        p.sendMessage(ChatColor.RED + "Usage: /spectate <player>");
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (target != null) {
                            Arena arena = GameAPI.get().getArenaByPlayer(target);
                            if (arena != null && target != p) {
                                arena.addSpectator(p);
                                p.sendMessage(ChatColor.GREEN + "You are now spectating " + target.getDisplayName() + ChatColor.GREEN + ".");
                            } else if (target == p){
                                p.sendMessage(ChatColor.RED + "You cannot spectate yourself.");
                            } else {
                                p.sendMessage(ChatColor.RED + "" + target.getDisplayName() + ChatColor.RED + " is not in a game.");
                            }
                        } else if (target.hasPlayedBefore()) {
                            p.sendMessage(ChatColor.RED + target.getDisplayName() + ChatColor.RED + " is currently offline");
                        } else {
                            p.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                }
            } else {
                Bukkit.getLogger().warning("Only players can use that command.");
            }

        });
        return true;
    }
}
