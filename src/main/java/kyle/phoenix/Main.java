package kyle.phoenix;

import javax.security.auth.login.LoginException;

import kyle.phoenix.admin.Clear;
import kyle.phoenix.admin.Reload;
import kyle.phoenix.events.JoinLeave;
import kyle.phoenix.general.About;
import kyle.phoenix.general.RandomColor;
import kyle.phoenix.general.Userinfo;
import kyle.phoenix.music.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    public static JDABuilder jda;
	public static String PREFIX = "~";
	
	public static void main(String[] args) throws LoginException {
		Token token = new Token();
		jda = new JDABuilder(AccountType.BOT).setToken(token.token);
		jda.setStatus(OnlineStatus.ONLINE);
		jda.setGame(Game.playing("with toe tucks"));
	
		// Event Listeners
		jda.addEventListener(new JoinLeave());

		// General Listeners
		jda.addEventListener(new RandomColor());
		jda.addEventListener(new About());
		jda.addEventListener(new Userinfo());

		// Music Listeners
		jda.addEventListener(new Music());
		
		// Admin Listeners
		jda.addEventListener(new Clear());
		jda.addEventListener(new Reload());
		
		jda.build();

    }
}