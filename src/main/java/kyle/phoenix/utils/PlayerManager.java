package kyle.phoenix.utils;

import java.util.HashMap;
import java.util.Map;

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

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler(){

            @Override
            public void trackLoaded(AudioTrack track) {

                EmbedBuilder trackadd = new EmbedBuilder();
                trackadd.setColor(0x85f96d);
                trackadd.setThumbnail(track.getInfo().uri);
                trackadd.setTitle(":musical_note: Added To Queue");
                trackadd.setDescription("[**" + track.getInfo().title + "**](" + trackUrl + ")");
                trackadd.addField("Author", track.getInfo().author, true);
                trackadd.addField("Queued By", "", true);
                trackadd.addField("Song Duration", "W.I.P", true);
                trackadd.addField("Position in queue", "W.I.P", true);
                channel.sendMessage(trackadd.build()).queue();

                play(musicManager, track);
            }
        
            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo()).queue();

                play(musicManager, firstTrack);
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

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}