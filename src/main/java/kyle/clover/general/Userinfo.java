package kyle.clover.general;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

import kyle.clover.Main;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Userinfo extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.PREFIX + "userinfo")) {

            List<Role> roles = event.getMember().getRoles();
            System.out.print(roles);

            String[] rolesid = 

            roles.forEach(event.getGuild().getRolesByName(roles[i], true));

        }
    }
}