package kyle.toothless.general;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import kyle.toothless.Constants;
import kyle.toothless.utils.StatusCheck;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Userinfo extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "userinfo")) {

            StatusCheck statuscheck = new StatusCheck();
            String status = event.getMember().getOnlineStatus().toString();
            if (status == "ONLINE") {
                statuscheck.online(event);
            } else if (status == "IDLE") {
                statuscheck.away(event);
            } else if (status == "DO_NOT_DISTURB") {
                statuscheck.dnd(event);
            } else if (status == "OFFLINE") {
                statuscheck.invisible(event);
            } else {
                statuscheck.nullstatus(event);
            }
        }
    }
}