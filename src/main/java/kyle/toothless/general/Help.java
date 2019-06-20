package kyle.toothless.general;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import kyle.toothless.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Help extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "help")) {
            

            EmbedBuilder success = new EmbedBuilder();
            success.setColor(0x85f96d);
            success.setTitle(":gear: Help");
            success.setDescription("Commands for Toothless!" 
            + "\n" + 
            "\n**General Commands**" + 
            "\n`" + Constants.PREFIX + "userinfo` - Shows the info of a member." +
            "\n`" + Constants.PREFIX + "help` - Shows this nerdy menu." 
            + "\n" +
            "\n**Level Commands**" +
            "\n`" + Constants.PREFIX + "level` - Shows your level stats." 
            + "\n" +
            "\n**Music Commands** - **WORK IN PROGRESS**" +
            "\n`" + Constants.PREFIX + "play` - Plays a song of your choice." +
            "\n`" + Constants.PREFIX + "stop` - Stops the song and clears the queue." +
            "\n`" + Constants.PREFIX + "skip` - Skips current song in the queue." +
            "\n`" + Constants.PREFIX + "queue` - Shows the songs in the queue." +
            "\n`" + Constants.PREFIX + "nowplaying` - Shows current song playing." +
            "\n`" + Constants.PREFIX + "connect` - Connects the bot to your channel." +
            "\n`" + Constants.PREFIX + "disconnect` - Disconnects the bot from your channel."
            + "\n" +
            "\n**Admin Commands**" + 
            "\n`" + Constants.PREFIX + "clear` - Deletes a number of messages."
            + "\n" +
            "\n**COMING SOON**" 
            + "\nEconomy" + "\nReactions");
            event.getChannel().sendMessage(success.build()).queue();

        }
    }
}