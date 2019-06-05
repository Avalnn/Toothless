package kyle.toothless.general;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import kyle.toothless.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Help extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "help")) {
            event.getChannel().sendTyping().complete();

            EmbedBuilder success = new EmbedBuilder();
            success.setColor(0x85f96d);
            success.setTitle(":gear: Help");
            success.setDescription("Commands for toothless!" 
            + "\n" + 
            "\n**General Commands**" + "\n`" + Constants.PREFIX 
            + "userinfo` - Shows the info of a member." 
            + "\n" +
            "\n**Admin Commands**" + "\n`" + Constants.PREFIX 
            + "clear` - Deletes a number of messages."
            + "\n" +
            "\n**COMING SOON**" 
            + "\nMusic" + "\nLevels"+ "\nEconomy" + "\nReactions");
            event.getChannel().sendMessage(success.build()).queue();

        }
    }
}