package kyle.clover.utils;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class StatusCheck extends ListenerAdapter {
    LocalDateTime ld = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
    String date = ld.format(dtf);

    public EmbedBuilder statusCheck(GuildMessageReceivedEvent event) {

        EmbedBuilder online = new EmbedBuilder();
        online.setColor(0x85f96d);
        online.setThumbnail(event.getMember().getUser().getAvatarUrl());
        online.setTitle(":clipboard: User Info");
        online.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        online.addField("Status", event.getMember().getOnlineStatus().name(), true);
        online.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        online.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        online.addField("Roles", roles, false);
        online.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(online.build()).queue();
        EmbedBuilder away = new EmbedBuilder();
        away.setColor(0x85f96d);
        away.setThumbnail(event.getMember().getUser().getAvatarUrl());
        away.setTitle(":clipboard: User Info");
        away.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        away.addField("Status", event.getMember().getOnlineStatus().name(), true);
        away.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        away.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        away.addField("Roles", roles, false);
        away.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(away.build()).queue();
        EmbedBuilder dnd = new EmbedBuilder();
        dnd.setColor(0x85f96d);
        dnd.setThumbnail(event.getMember().getUser().getAvatarUrl());
        dnd.setTitle(":clipboard: User Info");
        dnd.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        dnd.addField("Status", event.getMember().getOnlineStatus().name(), true);
        dnd.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        dnd.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        dnd.addField("Roles", roles, false);
        dnd.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(dnd.build()).queue();
        EmbedBuilder invisible = new EmbedBuilder();
        invisible.setColor(0x85f96d);
        invisible.setThumbnail(event.getMember().getUser().getAvatarUrl());
        invisible.setTitle(":clipboard: User Info");
        invisible.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        invisible.addField("Status", event.getMember().getOnlineStatus().name(), true);
        invisible.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        invisible.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        invisible.addField("Roles", roles, false);
        invisible.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(invisible.build()).queue();

        String status = event.getMember().getOnlineStatus().toString();
        switch (status) {
        case "online":
            return online;
        case "away":
            return away;
        case "dnd":
            return dnd;
        case "invisible":
            return invisible;
        default:
            return online;
        }
    }
}