package kyle.clover.general;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import kyle.clover.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Stab extends ListenerAdapter {
    String stabber, stabbed;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.PREFIX + "stab")) {

            event.getChannel().sendTyping().complete();

            if (args.length < 2) {
                event.getMessage().delete().queueAfter(6, TimeUnit.SECONDS);
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xefeb75);
                usage.setTitle(":knife: Specify user");
                usage.setDescription("Usage: `" + Main.PREFIX + "stab [@user]`");
                event.getChannel().sendMessage(usage.build()).queue((message) -> {
                    message.delete().queueAfter(5, TimeUnit.SECONDS);
                });
            } else {
                try {
                    stabbed = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
                    stabber = event.getMember().getEffectiveName();

                    String[] stabgifs = { "https://i.imgur.com/PlgOF4U.gif", "https://i.imgur.com/aDHdaLp.gif",
                            "https://i.imgur.com/CyOdgiv.gif" };
                    Random rand = new Random();
                    int number = rand.nextInt(stabgifs.length);

                    String[] hurtwords = { "that hurt...", "rudeee!", "ouch!" };
                    Random rand2 = new Random();
                    int number2 = rand2.nextInt(hurtwords.length);

                    if (stabbed != stabber) {
                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(0x85f96d);
                        success.setDescription(
                                "**" + stabber + "** stabbed **" + stabbed + "**~ " + hurtwords[number2]);
                        success.setImage(stabgifs[number]);
                        event.getChannel().sendMessage(success.build()).queue();

                    } else if (stabbed == stabber) {
                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(0x85f96d);
                        success.setDescription("**" + stabber + "** stabbed themself...");
                        success.setImage(stabgifs[number]);
                        event.getChannel().sendMessage(success.build()).queue();
                    }
                } catch (Exception e) {
                    if (e.toString().startsWith("java.lang.IndexOutOfBoundsException: Index: 0, Size: 0")) {
                        event.getMessage().delete().queueAfter(6, TimeUnit.SECONDS);
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xf97272);
                        error.setTitle(":red_circle: Error");
                        error.setDescription("You need to `@mention` the user.");
                        event.getChannel().sendMessage(error.build()).queue((message) -> {
                            message.delete().queueAfter(5, TimeUnit.SECONDS);
                        });
                    }
                }
            }
        }
    }
}