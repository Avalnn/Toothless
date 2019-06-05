package kyle.toothless.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl, GuildMessageReceivedEvent event) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler(){

            @Override
            public void trackLoaded(AudioTrack track) {

                EmbedBuilder trackadd = new EmbedBuilder();
                trackadd.setColor(0x85f96d);
                trackadd.setThumbnail("https://img.youtube.com/vi/" + track.getInfo().identifier + "/hqdefault.jpg");
                trackadd.setTitle(":musical_note: Added To Queue");
                trackadd.setDescription("[**" + track.getInfo().title + "**](" + trackUrl + ")");
                trackadd.addField("Author", track.getInfo().author, true);
                trackadd.addField("Queued By", event.getMember().getAsMention(), true);
                trackadd.addField("Song Duration", formatTime(track.getDuration()), true);
                trackadd.addField("Position in queue", "W.I.P", true);
                channel.sendMessage(trackadd.build()).queue();

                play(musicManager, track);
            }
        
            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().remove(0);
                }

                EmbedBuilder addedqueue = new EmbedBuilder();
                addedqueue.setColor(0x85f96d);
                addedqueue.setThumbnail("https://img.youtube.com/vi/" + firstTrack.getInfo().identifier + "/hqdefault.jpg");
                addedqueue.setTitle(":musical_note: Added Playlist To Queue");
                addedqueue.setDescription("[**" + playlist.getName() + "**](" + trackUrl + ")");
                addedqueue.addField("Author", firstTrack.getInfo().author, true);
                addedqueue.addField("Queued By", event.getMember().getAsMention(), true);
                addedqueue.addField("Song Duration", formatTime(firstTrack.getDuration()) , true);
                addedqueue.addField("Playlist Amount", "" + playlist.getTracks().size(), true);
                channel.sendMessage(addedqueue.build()).queue();

                play(musicManager, firstTrack);

                playlist.getTracks().forEach(musicManager.scheduler::queue);
            }
        
            @Override
            public void noMatches() {
                EmbedBuilder nomatches = new EmbedBuilder();
                nomatches.setColor(0xf97272);
                nomatches.setTitle(":musical_note: Error");
                nomatches.setDescription("Nothing found by: " + trackUrl);
                channel.sendMessage(nomatches.build()).queue();
            }
        
            @Override
            public void loadFailed(FriendlyException exception) {
                EmbedBuilder loadfailed = new EmbedBuilder();
                loadfailed.setColor(0xf97272);
                loadfailed.setTitle(":musical_note: Error");
                loadfailed.setDescription("Could not play: " + exception.getMessage());
                channel.sendMessage(loadfailed.build()).queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    private String formatTime(Long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}