package kyle.clover.admin;

import java.util.concurrent.TimeUnit;

import kyle.clover.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter {
	Member mutee;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Main.PREFIX + "mute")) {
			event.getChannel().sendTyping().complete();

			if (event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
				event.getMessage().delete().queueAfter(6, TimeUnit.SECONDS);

				if (args.length < 2) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setColor(0xefeb75);
					usage.setTitle(":mute: Specify user & reason");
					usage.setDescription("Usage: `" + Main.PREFIX + "mute [@user]`");
					event.getChannel().sendMessage(usage.build()).queue((message) -> {
						message.delete().queueAfter(5, TimeUnit.SECONDS);
					});
				} else {
					// Finds Mentioned User And Role
					mutee = event.getMessage().getMentionedMembers().get(0);
					Role role = event.getGuild().getRolesByName("no talk", false).get(0);

					// Adds Role To Mentioned User
					event.getGuild().getController().addSingleRoleToMember(mutee, role).queue();

					// Reason For Mute
					if (args.length > 2) {
						String reason = " ";
						for (int i = 2; args.length > i; i++) {
							reason = reason + args[i] + " ";
						}

						EmbedBuilder success = new EmbedBuilder();
						success.setColor(0x85f96d);
						success.setTitle(":white_check_mark: Successfully Muted");
						success.setDescription(mutee.getAsMention() + " has been muted!");
						event.getChannel().sendMessage(success.build()).queue((message) -> {
							message.delete().queueAfter(5, TimeUnit.SECONDS);
						});

						EmbedBuilder log = new EmbedBuilder();
						log.setColor(0xe2855a);
						log.setTitle(":mute: Mute Log");
						log.addField("Muted User", mutee.getAsMention(), false);
						log.addField("Reason", reason, false);
						log.addField("Muted By", "" + event.getMember().getAsMention(), false);
						event.getGuild().getTextChannelById("542890254416216064").sendMessage(log.build()).queue();
					}
				}
			} else {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xf97272);
				error.setTitle(":red_circle: Error");
				error.setDescription("`MUTE_MEMBERS` permission required to use this.");
				event.getChannel().sendMessage(error.build()).queue((message) -> {
					message.delete().queueAfter(5, TimeUnit.SECONDS);
				});
			}
		}
	}
}