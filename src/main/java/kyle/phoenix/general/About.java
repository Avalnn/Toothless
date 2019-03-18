package kyle.phoenix.general;

import kyle.phoenix.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class About extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.PREFIX + "about")) {

            event.getChannel().sendTyping().complete();

            EmbedBuilder success = new EmbedBuilder();
            success.setColor(0x85f96d);
            success.setTitle(":newspaper: About Clover");
            success.setDescription("**Website**: [Github](https://github.com/Avalnn/Phoenix)" + "\n"
                    + "**Language**: [Java](https://www.java.com/)" + "\n"
                    + "**Library**: [JDA](https://github.com/DV8FromTheWorld/JDA)" + "\n" + "**Coded By**: "
                    + event.getGuild().getOwner().getEffectiveName() + "#"
                    + event.getGuild().getOwner().getUser().getDiscriminator() + "\n" + "**Credit To**: "
                    + event.getGuild().getMemberById("237768953739476993").getEffectiveName() + "#"
                    + event.getGuild().getMemberById("237768953739476993").getUser().getDiscriminator());
            event.getChannel().sendMessage(success.build()).queue();
        }
    }
}