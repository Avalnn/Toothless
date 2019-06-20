package kyle.toothless.levels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import kyle.toothless.Constants;
import kyle.toothless.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Level extends ListenerAdapter {

    Firestore db = Main.db;
    public static ArrayList<String> messageLog = new ArrayList<>();

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put("messages", 0);
        data.put("level", 0);
        db.collection("Users").document(event.getMember().getUser().getId()).set(data);
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        db.collection("Users").document(event.getMember().getUser().getId()).delete();
    }

    // static int added = 0;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        /*
         * // Fuck kyles dog if
         * (event.getMessage().getContentRaw().equalsIgnoreCase("supersecretdogfucker"))
         * { int total = event.getGuild().getMembers().size();
         * event.getGuild().getMembers().forEach(m -> { added++; Map<String, Integer>
         * data = new HashMap<String, Integer>(); data.put("messages", 0);
         * data.put("level", 0);
         * db.collection("Users").document(m.getUser().getId()).set(data);
         * System.out.println("Added " + m.getEffectiveName() + "(" + added + "/" +
         * total + ")"); }); }
         */

        if (event.getMember().getUser().isBot())
            return; // Avoid bots using levels
        if (messageLog.contains(event.getAuthor().getId()))
            return; // If in log, don't do counting code
        else {
            messageLog.add(event.getMember().getUser().getId());

            // Increment message count
            DocumentReference ref = db.collection("Users").document(event.getMember().getUser().getId());
            ref.update("messages", FieldValue.increment(1));

            // Check if level up is necessary
            try {
                DocumentSnapshot snapshot = db.collection("Users").document(event.getMember().getUser().getId()).get().get();
                int messages = (int) ((long) snapshot.getData().get("messages"));
                int level = (int) ((long) snapshot.getData().get("level"));
                if (messages == Math.pow(2, level + 1)) {
                    db.collection("Users").document(event.getMember().getUser().getId()).update("level",
                            FieldValue.increment(1)); // Increase level

                    EmbedBuilder levelup = new EmbedBuilder();
                    levelup.setColor(0x85f96d);
                    levelup.setThumbnail(event.getMember().getUser().getEffectiveAvatarUrl());
                    levelup.setTitle(":tada: Leveled Up!");
                    levelup.setDescription(event.getMember().getAsMention() + " is now **Level "
                            + String.valueOf(level + 1) + "**" + "\n\n**" + (int) (Math.pow(2, level + 2) - messages)
                            + "** messages needed to" + "\nrankup to level **" + (level + 2) + "**");
                    event.getChannel().sendMessage(levelup.build()).queue((message) -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ForkJoinPool.commonPool().execute(() -> {
                // Create timeout for 30 seconds
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // Remove from message log after 30 sec
                        Level.messageLog.remove(event.getMember().getUser().getId());
                    }
                }, 30000);
            });
        }

        if (args[0].equalsIgnoreCase(Constants.PREFIX + "level")) {

            try {
                DocumentSnapshot snapshot = db.collection("Users").document(event.getMember().getUser().getId()).get().get();
                int messages = (int) ((long) snapshot.getData().get("messages"));
                int level = (int) ((long) snapshot.getData().get("level"));

                EmbedBuilder levelstats = new EmbedBuilder();
                levelstats.setColor(0x85f96d);
                levelstats.setThumbnail(event.getMember().getUser().getEffectiveAvatarUrl());
                levelstats.setTitle(":sparkles: Level Stats");
                levelstats.setDescription(event.getMember().getAsMention() + " is **Level " + String.valueOf(level)
                        + "**" + "\n\n**" + String.valueOf(messages) + "** Total Messages");
                event.getChannel().sendMessage(levelstats.build()).queue();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}