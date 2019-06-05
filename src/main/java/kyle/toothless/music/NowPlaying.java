package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class NowPlaying extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "nowplaying")) {

            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                EmbedBuilder notplaying = new EmbedBuilder();
                notplaying.setColor(0xf97272);
                notplaying.setTitle(":musical_note: Error");
                notplaying.setDescription("I am currently not playing a song.");
                event.getChannel().sendMessage(notplaying.build()).queue();

            } else {

                AudioTrackInfo info = player.getPlayingTrack().getInfo();

                EmbedBuilder nowplaying = new EmbedBuilder();
                nowplaying.setColor(0x85f96d);
                nowplaying.setTitle(":musical_note: Now Playing");

                nowplaying.appendDescription(String.format(
                    "[%s](%s)\n%s - %s",
                    info.title,
                    info.uri,
                    player.isPaused() ? "\u23F8" : "▶",
                    formatTime(player.getPlayingTrack().getPosition()),
                    formatTime(player.getPlayingTrack().getDuration())

                ));
            }
        }
    }

    private String formatTime(Long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis / TimeUnit.MINUTES.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}