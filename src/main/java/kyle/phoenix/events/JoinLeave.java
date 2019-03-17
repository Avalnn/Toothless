package kyle.phoenix.events;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinLeave extends ListenerAdapter {

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {

		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0xce8de8);
		join.setAuthor(event.getMember().getEffectiveName(), null, event.getMember().getUser().getAvatarUrl());
		join.setDescription(event.getMember().getAsMention() + " has joined!");

		event.getGuild().getTextChannelById("541407727319121930").sendMessage(join.build()).queue();

	}

	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

		EmbedBuilder leave = new EmbedBuilder();
		leave.setColor(0xeda238);
		leave.setAuthor(event.getMember().getEffectiveName(), null, event.getMember().getUser().getAvatarUrl());
		leave.setDescription(event.getMember().getEffectiveName() + " has left, what a loser!");

		event.getGuild().getTextChannelById("541407727319121930").sendMessage(leave.build()).queue();
	}
}
