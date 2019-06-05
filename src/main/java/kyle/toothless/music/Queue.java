package kyle.toothless.music;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import kyle.toothless.Constants;
import kyle.toothless.utils.GuildMusicManager;
import kyle.toothless.utils.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Queue extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "queue")) {
            
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

            int trackCount = Math.min(queue.size(), 10);
            List<AudioTrack> tracks = new ArrayList<>(queue);

            EmbedBuilder bqueue = new EmbedBuilder();
                bqueue.setColor(0x85f96d);
                bqueue.setTitle(":musical_note: Current Queue");

            for (int i = 0; i < trackCount; i++) {
                AudioTrack track = tracks.get(i);
                AudioTrackInfo info = track.getInfo();
                
                bqueue.appendDescription(String.format(
                    "[**%s**](%s)\n" + "\n",
                    info.title,
                    info.uri
                ));
            }

            event.getChannel().sendMessage(bqueue.build()).queue();
        }
    }
    }
}