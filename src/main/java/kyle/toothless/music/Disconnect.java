package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import kyle.toothless.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Disconnect extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "disconnect")) {

            AudioManager audioManager = event.getGuild().getAudioManager();

            if (!audioManager.isConnected()) {
                EmbedBuilder musicvc = new EmbedBuilder();
                musicvc.setColor(0xf97272);
                musicvc.setTitle(":musical_note: Error");
                musicvc.setDescription("I'm not connected to a voice channel.");
                event.getChannel().sendMessage(musicvc.build()).queue();
            }

            VoiceChannel voiceChannel = audioManager.getConnectedChannel();

            if (!voiceChannel.getMembers().contains(event.getMember())) {
                EmbedBuilder membervc = new EmbedBuilder();
                membervc.setColor(0xf97272);
                membervc.setTitle(":musical_note: Error");
                membervc.setDescription("You are not in the same voice channel as me to use this.");
                event.getChannel().sendMessage(membervc.build()).queue();
            } else {
                
                audioManager.closeAudioConnection();
                EmbedBuilder disconnected = new EmbedBuilder();
                disconnected.setColor(0x85f96d);
                disconnected.setTitle(":musical_note: Connected");
                disconnected.setDescription("Disconnected from `" + voiceChannel.getName() + "`");
                event.getChannel().sendMessage(disconnected.build()).queue();

            }
        }
    }
}