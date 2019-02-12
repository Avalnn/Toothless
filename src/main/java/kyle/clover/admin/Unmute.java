package kyle.clover.admin;

import java.util.concurrent.TimeUnit;

import kyle.clover.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Unmute extends ListenerAdapter {
	Member unmutee;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Main.PREFIX + "unmute")) {
			event.getChannel().sendTyping().complete();

			if (event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
				event.getMessage().delete().queueAfter(6, TimeUnit.SECONDS);

				if (args.length < 2) {
					EmbedBuilder usage = new EmbedBuilder();
					usage.setColor(0xefeb75);
					usage.setTitle(":loud_sound: Specify user");
					usage.setDescription("Usage: `" + Main.PREFIX + "unmute [@user]`");
					event.getChannel().sendMessage(usage.build()).queue((message) -> {
						message.delete().queueAfter(5, TimeUnit.SECONDS);
					});

					// Checks If Mentioned User Has Role
				} else if (event.getMessage().getMentionedMembers().get(0).getRoles()
						.contains(event.getGuild().getRolesByName("no talk", false).get(0)) == true) {

					// Finds Mentioned User And Role
					unmutee = event.getMessage().getMentionedMembers().get(0);
					Role role = event.getGuild().getRolesByName("no talk", false).get(0);

					// Removes Role From Mentioned User
					event.getGuild().getController().removeSingleRoleFromMember(unmutee, role).queue();

					EmbedBuilder success = new EmbedBuilder();
					success.setColor(0x85f96d);
					success.setTitle(":white_check_mark: Successfully Unmuted");
					success.setDescription(unmutee.getAsMention() + " has been unmuted!");
					event.getChannel().sendMessage(success.build()).queue((message) -> {
						message.delete().queueAfter(5, TimeUnit.SECONDS);
					});
				} else {
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xf97272);
					error.setTitle(":red_circle: Error");
					error.setDescription("Member does not have mute role.");
					event.getChannel().sendMessage(error.build()).queue((message) -> {
						message.delete().queueAfter(5, TimeUnit.SECONDS);
					});
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