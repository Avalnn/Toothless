package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Pause extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "pause")) {

            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            AudioPlayer player = musicManager.player;

            if (player.isPaused()) {
                EmbedBuilder ispaused = new EmbedBuilder();
                ispaused.setColor(0xf97272);
                ispaused.setTitle(":musical_note: Error");
                ispaused.setDescription("The queue is empty.");
                event.getChannel().sendMessage(ispaused.build()).queue();
            } else {

                player.setPaused(true);

                EmbedBuilder paused = new EmbedBuilder();
                paused.setColor(0x85f96d);
                paused.setTitle(":musical_note: Paused Song");
                paused.setDescription(".");
                event.getChannel().sendMessage(paused.build()).queue();

            }
        }
    }
}