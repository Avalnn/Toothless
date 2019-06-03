package kyle.phoenix.utils;

import kyle.phoenix.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RoleSelection extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "rolesannouncement")) {
            event.getMessage().delete().queue();

            EmbedBuilder eb = new EmbedBuilder();

            eb.setColor(0x85f96d);
            eb.setTitle(":notebook_with_decorative_cover: Roles");
            eb.setDescription("Click the reaction emote of the roles you want \n" + "" + "\nOverwatch: "
                    + event.getGuild().getEmoteById("584732756542488581").getAsMention() + "\nRainbow 6 Siege: "
                    + event.getGuild().getEmoteById("584732757088010250").getAsMention() + "\nMinecraft: "
                    + event.getGuild().getEmoteById("584732758232793090").getAsMention() + "\nCSGO: "
                    + event.getGuild().getEmoteById("584732756823506954").getAsMention() + "\nRocket League: "
                    + event.getGuild().getEmoteById("584732760820678676").getAsMention());

            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                message.addReaction(event.getGuild().getEmoteById("584732756542488581")).queue();
                message.addReaction(event.getGuild().getEmoteById("584732757088010250")).queue();
                message.addReaction(event.getGuild().getEmoteById("584732758232793090")).queue();
                message.addReaction(event.getGuild().getEmoteById("584732756823506954")).queue();
                message.addReaction(event.getGuild().getEmoteById("584732760820678676")).queue();
            });

        }

    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getMember().getUser().isBot() || e.getMember().getUser().isFake()) {
            // ignore reactions added by bots
        } else if (e.getGuild().getTextChannelById(e.getChannel().getId()).getMessageById(e.getMessageId()).complete()
                .getAuthor().equals(e.getJDA().getSelfUser())) {

            Member reactionAdder = e.getMember();
            Role role = e.getGuild().getRolesByName(e.getReactionEmote().getName().replace("_", " "), true).get(0);
            e.getGuild().getController().addSingleRoleToMember(reactionAdder, role).queue();
            }
        }

    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        if (e.getMember().getUser().isBot() || e.getMember().getUser().isFake()) {
            // ignore reactions added by bots
        } else if (e.getGuild().getTextChannelById(e.getChannel().getId()).getMessageById(e.getMessageId()).complete()
                .getAuthor().equals(e.getJDA().getSelfUser())) {

            Member reactionAdder = e.getMember();
            Role role = e.getGuild().getRolesByName(e.getReactionEmote().getName().replace("_", " "), true).get(0);
            e.getGuild().getController().removeSingleRoleFromMember(reactionAdder, role).queue();
            };
        }
    }