package kyle.toothless.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class StatusCheck {
    LocalDateTime ld = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
    String date = ld.format(dtf);

    public EmbedBuilder online(GuildMessageReceivedEvent event) {
        String roles = "";
        roles = event.getMember().getRoles().stream().map((rol) -> ", " + rol.getName()).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(2);

        String game = "";
        try {
            game = event.getMember().getGame().getName();
        } catch (NullPointerException e) {
            game = "Not Playing";
        }

        EmbedBuilder online = new EmbedBuilder();
        online.setColor(0x85f96d);
        online.setThumbnail(event.getMember().getUser().getAvatarUrl());
        online.setTitle(":clipboard: User Info");
        online.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        online.addField("Nickname", event.getMember().getEffectiveName(), true);
        online.addField("Status", event.getGuild().getEmoteById("579666249517105152").getAsMention() + "Online", true);
        online.addField("Playing", game, true);
        online.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        online.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        online.addField("Roles", roles, true);
        online.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(online.build()).queue();
        return online;
    }

    public EmbedBuilder away(GuildMessageReceivedEvent event) {
        String roles = "";
        roles = event.getMember().getRoles().stream().map((rol) -> " , " + rol.getName()).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(3);

        String game = "";
        try {
            game = event.getMember().getGame().getName();
        } catch (NullPointerException e) {
            game = "Not Playing";
        }

        EmbedBuilder away = new EmbedBuilder();
        away.setColor(0x85f96d);
        away.setThumbnail(event.getMember().getUser().getAvatarUrl());
        away.setTitle(":clipboard: User Info");
        away.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        away.addField("Nickname", event.getMember().getEffectiveName(), true);
        away.addField("Status", event.getGuild().getEmoteById("579666249567567872").getAsMention() + "Idle", true);
        away.addField("Playing", game, true);
        away.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        away.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        away.addField("Roles", roles, true);
        away.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(away.build()).queue();
        return away;
    }

    public EmbedBuilder dnd(GuildMessageReceivedEvent event) {
        String roles = "";
        roles = event.getMember().getRoles().stream().map((rol) -> " , " + rol.getName()).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(3);

        String game = "";
        try {
            game = event.getMember().getGame().getName();
        } catch (NullPointerException e) {
            game = "Not Playing";
        }

        EmbedBuilder dnd = new EmbedBuilder();
        dnd.setColor(0x85f96d);
        dnd.setThumbnail(event.getMember().getUser().getAvatarUrl());
        dnd.setTitle(":clipboard: User Info");
        dnd.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        dnd.addField("Nickname", event.getMember().getEffectiveName(), true);
        dnd.addField("Status", event.getGuild().getEmoteById("579666249659973662").getAsMention() + "Do Not Disturb",
                true);
        dnd.addField("Playing", game, true);
        dnd.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        dnd.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        dnd.addField("Roles", roles, true);
        dnd.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(dnd.build()).queue();
        return dnd;
    }

    public EmbedBuilder invisible(GuildMessageReceivedEvent event) {
        String roles = "";
        roles = event.getMember().getRoles().stream().map((rol) -> " , " + rol.getName()).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(3);

        String game = "";
        try {
            game = event.getMember().getGame().getName();
        } catch (NullPointerException e) {
            game = "Not Playing";
        }

        EmbedBuilder invisible = new EmbedBuilder();
        invisible.setColor(0x85f96d);
        invisible.setThumbnail(event.getMember().getUser().getAvatarUrl());
        invisible.setTitle(":clipboard: User Info");
        invisible.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        invisible.addField("Nickname", event.getMember().getEffectiveName(), true);
        invisible.addField("Status", event.getGuild().getEmoteById("579666249626419235").getAsMention() + "Offline",
                true);
        invisible.addField("Playing", game, true);
        invisible.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        invisible.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        invisible.addField("Roles", roles, true);
        invisible.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(invisible.build()).queue();
        return invisible;
    }

    public EmbedBuilder nullstatus(GuildMessageReceivedEvent event) {
        String roles = "";
        roles = event.getMember().getRoles().stream().map((rol) -> " , " + rol.getName()).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(3);

        String game = "";
        try {
            game = event.getMember().getGame().getName();
        } catch (NullPointerException e) {
            game = "Not Playing";
        }

        EmbedBuilder nullstatus = new EmbedBuilder();
        nullstatus.setColor(0x85f96d);
        nullstatus.setThumbnail(event.getMember().getUser().getAvatarUrl());
        nullstatus.setTitle(":clipboard: User Info");
        nullstatus.addField("Username",
                event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
        nullstatus.addField("Nickname", event.getMember().getEffectiveName(), true);
        nullstatus.addField("Status", "Not Found", true);
        nullstatus.addField("Playing", game, true);
        nullstatus.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
        nullstatus.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
        nullstatus.addField("Roles", roles, true);
        nullstatus.setFooter("User ID: " + event.getMember().getUser().getId(), null);
        event.getChannel().sendMessage(nullstatus.build()).queue();
        return nullstatus;
    }
}