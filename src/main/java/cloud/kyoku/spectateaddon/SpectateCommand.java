package cloud.kyoku.spectateaddon;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        BedwarsAPI.onReady(() -> {

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
                                p.sendMessage(ChatColor.GREEN + "You are now spectating " + ChatColor.YELLOW + "" + target.getDisplayName() + ChatColor.GREEN + "" + ".");
                            } else if (target == p){
                                p.sendMessage(ChatColor.RED + "You cannot spectate yourself.");
                            } else {
                                p.sendMessage(ChatColor.RED + "" + target.getDisplayName() + " is not in a game.");
                            }
                        } else {
                        p.sendMessage(ChatColor.RED + args[0] + " is currently offline.");
                        }
                    }
                } else if (sender instanceof ConsoleCommandSender) {
                    System.out.println("Only players can use this command!");
                }
            }

        });
        return true;
    }
}
