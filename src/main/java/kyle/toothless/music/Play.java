package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import kyle.toothless.Constants;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Play extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "play")) {

            if (args.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setColor(0xefeb75);
                usage.setTitle(":musical_note: Provide Link");
                usage.setDescription("Usage: `" + Constants.PREFIX + "play [link]`");
                event.getChannel().sendMessage(usage.build()).queue((message) -> {
                    message.delete().queueAfter(5, TimeUnit.SECONDS);
                });
            } else {

                if (args.length < 3) {

                    if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {

                        if (!isUrl(args[1]) && !args[1].startsWith("ytsearch:")) {
                            EmbedBuilder link = new EmbedBuilder();
                            link.setColor(0xf97272);
                            link.setTitle(":musical_note: Error");
                            link.setDescription("Please provide a valid youtube or soundcloud link.");
                            event.getChannel().sendMessage(link.build()).queue();

                            return;
                        }

                        PlayerManager manager = PlayerManager.getInstance();

                        manager.loadAndPlay(event.getChannel(), args[1], event);
                    } else {

                        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

                        if (!memberVoiceState.inVoiceChannel()) {
                            EmbedBuilder membervc = new EmbedBuilder();
                            membervc.setColor(0xf97272);
                            membervc.setTitle(":musical_note: Error");
                            membervc.setDescription("You are not connected to a voice channel.");
                            event.getChannel().sendMessage(membervc.build()).queue();

                        } else {

                            event.getGuild().getAudioManager()
                                    .openAudioConnection(event.getMember().getVoiceState().getChannel());

                            if (!isUrl(args[1]) && !args[1].startsWith("ytsearch:")) {
                                EmbedBuilder link = new EmbedBuilder();
                                link.setColor(0xf97272);
                                link.setTitle(":musical_note: Error");
                                link.setDescription("Please provide a valid youtube or soundcloud link.");
                                event.getChannel().sendMessage(link.build()).queue();

                                return;
                            }

                            PlayerManager manager = PlayerManager.getInstance();

                            manager.loadAndPlay(event.getChannel(), args[1], event);
                        }
                    }
                }
            }
        }
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }
}