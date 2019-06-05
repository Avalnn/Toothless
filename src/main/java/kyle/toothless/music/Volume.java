package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import kyle.toothless.Constants;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Volume extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "volume")) {

            

        }
    }
}