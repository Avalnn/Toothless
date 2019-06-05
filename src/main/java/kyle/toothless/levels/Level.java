package kyle.toothless.levels;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Level extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

            if (event.getMember().getUser().getId().contains("219528105709142017")) {

            }
        }
    }