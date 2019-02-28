package kyle.clover.general;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kyle.clover.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Userinfo extends ListenerAdapter {
    LocalDateTime ld = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
    String date = ld.format(dtf);

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.PREFIX + "userinfo")) {

            // Grabs users roles
            String roles = "";
            roles = event.getMember().getRoles().stream().map((rol) -> " , " + rol.getName()).reduce(roles,
                    String::concat);
            if (roles.isEmpty())
                roles = "None";
            else
                roles = roles.substring(3);

            EmbedBuilder success = new EmbedBuilder();
            success.setColor(0x85f96d);
            success.setThumbnail(event.getMember().getUser().getAvatarUrl());
            success.setTitle(":clipboard: User Info");
            success.addField("Username",
                    event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), true);
            success.addField("Status", event.getMember().getOnlineStatus().name(), true);
            success.addField("Joined Guild", event.getMember().getJoinDate().format(dtf), true);
            success.addField("Joined Discord", event.getMember().getUser().getCreationTime().format(dtf), true);
            success.addField("Roles", roles, false);
            success.setFooter("User ID: " + event.getMember().getUser().getId(), null);
            event.getChannel().sendMessage(success.build()).queue();
        }
    }
}