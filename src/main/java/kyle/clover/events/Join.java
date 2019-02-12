package kyle.clover.events;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {

		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0xce8de8);
		join.setAuthor(event.getMember().getEffectiveName(), null, event.getMember().getUser().getAvatarUrl());
		join.setDescription(event.getMember().getAsMention() + " has joined!");

		event.getGuild().getTextChannelById("541407727319121930").sendMessage(join.build()).queue();

	}
}
