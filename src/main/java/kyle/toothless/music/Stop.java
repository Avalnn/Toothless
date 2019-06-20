package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Stop extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "stop")) {

            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                EmbedBuilder membervc = new EmbedBuilder();
                membervc.setColor(0xf97272);
                membervc.setTitle(":musical_note: Error");
                membervc.setDescription("You are not connected to a voice channel.");
                event.getChannel().sendMessage(membervc.build()).queue();

            } else {

                AudioManager audioManager = event.getGuild().getAudioManager();
                VoiceChannel voiceChannel = audioManager.getConnectedChannel();

                if (!voiceChannel.getMembers().contains(event.getMember())) {
                    EmbedBuilder membervc = new EmbedBuilder();
                    membervc.setColor(0xf97272);
                    membervc.setTitle(":musical_note: Error");
                    membervc.setDescription("You are not in the same voice channel as me to use this.");
                    event.getChannel().sendMessage(membervc.build()).queue();

                } else {

                    musicManager.scheduler.getQueue().clear();
                    musicManager.player.stopTrack();
                    musicManager.player.setPaused(false);
                    audioManager.closeAudioConnection();

                    EmbedBuilder stop = new EmbedBuilder();
                    stop.setColor(0x85f96d);
                    stop.setTitle(":musical_note: Stopped Music");
                    stop.setDescription("Stopped the music and cleared the queue.");
                    event.getChannel().sendMessage(stop.build()).queue();
                }
            }
        }
    }
}