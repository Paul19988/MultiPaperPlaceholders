package uk.co.paulcodes.multipaperplaceholders;

import com.github.puregero.multilib.MultiLib;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import puregero.multipaper.ExternalPlayer;
import puregero.multipaper.MultiPaper;
import puregero.multipaper.config.MultiPaperConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MultiPaperPlaceholders extends PlaceholderExpansion {

    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "MultiPaperPlaceholders";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mpp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PaulCodesUK";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1-SNAPSHOT";
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        List<String> list = new ArrayList<>();
        list.add(plc("global_player_count"));
        list.add(plc("local_player_count"));
        list.add(plc("global_player_list"));
        list.add(plc("local_player_list"));
        list.add(plc("player_server_name"));
        return super.getPlaceholders();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("global_player_count")) {
            return String.valueOf(MultiLib.getAllOnlinePlayers().size());
        }else if(identifier.equalsIgnoreCase("local_player_count")) {
            return String.valueOf(MultiLib.getLocalOnlinePlayers().size());
        }else if(identifier.equalsIgnoreCase("global_player_list")) {
            StringBuilder serverList = new StringBuilder();
            int i = 0;
            for(Player allOnlinePlayer : MultiLib.getAllOnlinePlayers()) {
                if(i == MultiLib.getAllOnlinePlayers().size()-1) {
                    serverList.append(allOnlinePlayer.getName());
                }else{
                    serverList.append(allOnlinePlayer.getName()).append(", ");
                    i++;
                }
            }
            return serverList.toString();
        }else if(identifier.equalsIgnoreCase("local_player_list")) {
            StringBuilder serverList = new StringBuilder();
            int i = 0;
            for(Player localOnlinePlayer : MultiLib.getLocalOnlinePlayers()) {
                if(i == MultiLib.getLocalOnlinePlayers().size()-1) {
                    serverList.append(localOnlinePlayer.getName());
                }else{
                    serverList.append(localOnlinePlayer.getName()).append(", ");
                    i++;
                }
            }
            return serverList.toString();
        }else if(identifier.contains("player_server_name")) {
            if(MultiPaper.isExternalPlayer(player)) {
                try {
                    if (player.getClass().getMethod("getHandle").invoke(player) instanceof ExternalPlayer externalPlayer) {
                        return externalPlayer.externalServerConnection.externalServer.getName();
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }else{
                return MultiPaperConfiguration.get().masterConnection.myName;
            }
        }

        return null;
    }

    private String plc(String str) {
        return "%" + getIdentifier() + "_" + str + "%";
    }
}
