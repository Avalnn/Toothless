package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import kyle.toothless.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Connect extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "connect")) {

            AudioManager audioManager = event.getGuild().getAudioManager();

            if (audioManager.isConnected()) {
                EmbedBuilder musicvc = new EmbedBuilder();
                musicvc.setColor(0xf97272);
                musicvc.setTitle(":musical_note: Error");
                musicvc.setDescription("I'm already connected to a voice channel.");
                event.getChannel().sendMessage(musicvc.build()).queue();
            }

            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                EmbedBuilder membervc = new EmbedBuilder();
                membervc.setColor(0xf97272);
                membervc.setTitle(":musical_note: Error");
                membervc.setDescription("You are not connected to a voice channel.");
                event.getChannel().sendMessage(membervc.build()).queue();
            }

            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                EmbedBuilder vc = new EmbedBuilder();
                vc.setColor(0xf97272);
                vc.setTitle(":musical_note: Error");
                vc.setDescription(
                        "I'm missing the permission `VOICE_CONNECT` to join `" + voiceChannel.getName() + "`");
                event.getChannel().sendMessage(vc.build()).queue();
            } else {

                audioManager.openAudioConnection(voiceChannel);
                EmbedBuilder connected = new EmbedBuilder();
                connected.setColor(0x85f96d);
                connected.setTitle(":musical_note: Connected");
                connected.setDescription("Connected to `" + voiceChannel.getName() + "`");
                event.getChannel().sendMessage(connected.build()).queue();

            }
        }
    }
}