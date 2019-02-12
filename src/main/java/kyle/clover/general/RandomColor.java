package kyle.clover.general;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kyle.clover.Main;
import kyle.clover.Token;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RandomColor extends ListenerAdapter {

    // Coded By @Mykyta https://github.com/nkomarn

    Random random = new Random();
    int nextInt = random.nextInt(0xffffff + 1);
    String colorCode = String.format("#%06x", nextInt);

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.PREFIX + "rcolor")) {

            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);

            event.getChannel().sendTyping().complete();
            Random random = new Random();
            int nextInt = random.nextInt(0xffffff + 1);

            try {
                BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
                Graphics graphics = img.getGraphics();
                graphics.setColor(Color.decode(String.valueOf(nextInt)));
                graphics.fillRect(0, 0, 500, 500);
                graphics.dispose();
                File file = new File("image.png");
                ImageIO.write(img, "png", file);
 
                Token imgcid = new Token();
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.imgur.com/3/image").openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Client-ID " + imgcid.imgcid);
                conn.setReadTimeout(100000);
                conn.connect();

                // Convert to base64
                byte[] b = new byte[(int) file.length()];
                FileInputStream fs = new FileInputStream(file);
                fs.read(b);
                fs.close();
                String base64 = URLEncoder.encode(DatatypeConverter.printBase64Binary(b), "UTF-8");

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write("image=" + base64);
                writer.flush();
                writer.close();

                // Get response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder str = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(str.toString());
                String data = json.get("data").toString();
                JSONObject dataJson = (JSONObject) parser.parse(data);
                reader.close();

                // Convert to hex
                String colorCode = String.format("#%06x", nextInt);

                EmbedBuilder success = new EmbedBuilder();
                success.setColor(0x85f96d);
                success.setTitle(":white_check_mark: Successfully Generated");
                success.addField("Color code", colorCode, false);
                success.setThumbnail(dataJson.get("link").toString());
                event.getChannel().sendMessage(success.build()).queue((message) -> {
                    message.delete().queueAfter(14, TimeUnit.SECONDS);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}