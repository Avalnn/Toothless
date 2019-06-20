package kyle.toothless.admin;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

import kyle.toothless.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetPrefix extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "setprefix")) {

            if (event.getMember().getUser().getId().contains("219528105709142017")) {
                
                
            }
        } else {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(0xf97272);
            error.setTitle(":red_circle: Error");
            error.setDescription(
                    "Only " + event.getGuild().getMemberById("219528105709142017").getAsMention() + " can use this.");
            event.getChannel().sendMessage(error.build()).queue((message) -> {
                message.delete().queueAfter(5, TimeUnit.SECONDS);
            });
        }
    }
}