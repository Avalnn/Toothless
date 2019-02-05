package kyle.saishu.admin;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kyle.saishu.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Main.PREFIX + "clear")) {

			if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

				if (args.length < 2) {

					event.getChannel().sendTyping().complete();
					EmbedBuilder usage = new EmbedBuilder();
					usage.setColor(0xefeb75);
					usage.setTitle("Specify amount to delete");
					usage.setDescription("Usage: `" + Main.PREFIX + "clear [# of messages]`");
					event.getChannel().sendMessage(usage.build()).queue((message) -> {
						message.delete().queueAfter(5, TimeUnit.SECONDS);
					});
				} else {
					try {
						List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]))
								.complete();
						event.getChannel().deleteMessages(messages).queue();

						event.getChannel().sendTyping().complete();
						EmbedBuilder success = new EmbedBuilder();
						success.setColor(0x85f96d);
						success.setTitle(":white_check_mark: Successfully deleted");
						success.setDescription("**" + args[1] + "** messages have been removed!");
						event.getChannel().sendMessage(success.build()).queue((message) -> {
							message.delete().queueAfter(5, TimeUnit.SECONDS);
						});

					} catch (IllegalArgumentException e) {
						if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {

							event.getChannel().sendTyping().complete();
							EmbedBuilder error = new EmbedBuilder();
							error.setColor(0xf97272);
							error.setTitle(":red_circle: Error");
							error.setDescription("Between 1-100 messages can be deleted at one time.");
							event.getChannel().sendMessage(error.build()).queue((message) -> {
								message.delete().queueAfter(5, TimeUnit.SECONDS);
							});
							
						} else if (e.toString().startsWith(
								"java.lang.IllegalArgumentException: Must provide at least 2 or at most 100 messages to be deleted.")) {

							event.getChannel().sendTyping().complete();
							EmbedBuilder error = new EmbedBuilder();
							error.setColor(0xf97272);
							error.setTitle(":red_circle: Error");
							error.setDescription("Can only clear more than 2 messages.");
							event.getChannel().sendMessage(error.build()).queue((message) -> {
								message.delete().queueAfter(5, TimeUnit.SECONDS);
							});
						} else {

							event.getChannel().sendTyping().complete();
							EmbedBuilder error = new EmbedBuilder();
							error.setColor(0xf97272);
							error.setTitle(":red_circle: Error");
							error.setDescription("Messages older than 2 weeks cannot be deleted.");
							event.getChannel().sendMessage(error.build()).queue((message) -> {
								message.delete().queueAfter(5, TimeUnit.SECONDS);
							});
						}
					}
				}
			} else {
				event.getChannel().sendTyping().complete();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xf97272);
				error.setTitle(":red_circle: Error");
				error.setDescription("`MANAGE_MESSAGE` permission required to use this.");
				event.getChannel().sendMessage(error.build()).queue((message) -> {
					message.delete().queueAfter(5, TimeUnit.SECONDS);
				});
			}
		}
	}
}
