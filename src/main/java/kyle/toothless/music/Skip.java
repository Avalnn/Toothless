package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import kyle.toothless.utils.TrackScheduler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Skip extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "skip")) {
            
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                EmbedBuilder membervc = new EmbedBuilder();
                membervc.setColor(0xf97272);
                membervc.setTitle(":musical_note: Error");
                membervc.setDescription("You are not connected to a voice channel.");
                event.getChannel().sendMessage(membervc.build()).queue();
            }

            if (player.getPlayingTrack() == null) {
                EmbedBuilder notplaying = new EmbedBuilder();
                notplaying.setColor(0xf97272);
                notplaying.setTitle(":musical_note: Error");
                notplaying.setDescription("I am currently not playing a song.");
                event.getChannel().sendMessage(notplaying.build()).queue();

            } else {

                scheduler.nextTrack();

                EmbedBuilder skipped = new EmbedBuilder();
                skipped.setColor(0x85f96d);
                skipped.setTitle(":musical_note: Skipping");
                skipped.setDescription("Skipping the current track.");
                event.getChannel().sendMessage(skipped.build()).queue();
            }

        }
    }
}