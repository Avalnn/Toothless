package kyle.phoenix.admin;

import java.util.concurrent.TimeUnit;

import kyle.phoenix.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Restart extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "restart")) {
            event.getMessage().delete().queue();

            if (event.getMember().getUser().getId().contains("219528105709142017")) {

                if (args.length < 2) {

                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(0x85f96d);
                    success.setTitle(":bomb: Restarting");
                    success.setDescription("Phoenix is rebooting.. 10s remaining");
                    event.getChannel().sendMessage(success.build()).queue((message) -> {
                        message.delete().queueAfter(9, TimeUnit.SECONDS);
                    });

                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        @Override
                        public void run() {
                            event.getJDA().shutdown();
                            System.exit(0);
                        }
                    }, 10000);
                }

            } else {
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xf97272);
                error.setTitle(":red_circle: Error");
                error.setDescription("Only " + event.getGuild().getMemberById("219528105709142017").getAsMention() + " can use this.");
                event.getChannel().sendMessage(error.build()).queue((message) -> {
                    message.delete().queueAfter(5, TimeUnit.SECONDS);
                });
            }
        }
    }
}