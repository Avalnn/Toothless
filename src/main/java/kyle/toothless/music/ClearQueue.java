package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ClearQueue extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "clearqueue")) {

            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()) {
                EmbedBuilder emptyqueue = new EmbedBuilder();
                emptyqueue.setColor(0xf97272);
                emptyqueue.setTitle(":musical_note: Error");
                emptyqueue.setDescription("The queue is empty.");
                event.getChannel().sendMessage(emptyqueue.build()).queue();

            } else {

                GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

                if (!memberVoiceState.inVoiceChannel()) {
                    EmbedBuilder musicvc = new EmbedBuilder();
                    musicvc.setColor(0xf97272);
                    musicvc.setTitle(":musical_note: Error");
                    musicvc.setDescription("You are not connected to a voice channel.");
                    event.getChannel().sendMessage(musicvc.build()).queue();
                }

                AudioManager audioManager = event.getGuild().getAudioManager();
                VoiceChannel voiceChannel = audioManager.getConnectedChannel();

                if (!voiceChannel.getMembers().contains(event.getMember())) {
                    EmbedBuilder membervc = new EmbedBuilder();
                    membervc.setColor(0xf97272);
                    membervc.setTitle(":musical_note: Error");
                    membervc.setDescription("You are not in the same voice channel as me to use this.");
                    event.getChannel().sendMessage(membervc.build()).queue();

                } else {

                    EmbedBuilder clearedqueue = new EmbedBuilder();
                    clearedqueue.setColor(0x85f96d);
                    clearedqueue.setTitle(":musical_note: Cleared Queue");
                    clearedqueue.setDescription("Cleared `" + queue.size() + "` songs from the queue.");
                    event.getChannel().sendMessage(clearedqueue.build()).queue((message) -> {
                        queue.clear();
                    });
                }
            }

        }
    }
}