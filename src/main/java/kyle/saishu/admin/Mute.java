package kyle.saishu.admin;

import java.util.concurrent.TimeUnit;

import kyle.saishu.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter {
	String reason = "";
	Member mutee;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Main.PREFIX + "mute")) {
			
            mutee = event.getMessage().getMentionedMembers().get(0);
            
            for (int i = 2; args.length > i; i++) {
                reason = reason + args[i] + " ";
            }

			if (args.length < 2) {

				event.getChannel().sendTyping().complete();
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xefeb75);
				usage.setTitle("Specify user & reason");
				usage.setDescription("Usage: `" + Main.PREFIX + "@user [reason]`");
				event.getChannel().sendMessage(usage.build()).queue((message) -> {
					message.delete().queueAfter(5, TimeUnit.SECONDS);
				});
			}
		}
	}
}
