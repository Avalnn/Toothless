package kyle.phoenix;

import javax.security.auth.login.LoginException;

import kyle.phoenix.admin.*;
import kyle.phoenix.general.*;
import kyle.phoenix.levels.*;
import kyle.phoenix.music.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    public static JDABuilder jda;
	
	public static void main(String[] args) throws LoginException {
		Token token = new Token();
		jda = new JDABuilder(AccountType.BOT).setToken(token.token);
		jda.setStatus(OnlineStatus.ONLINE);
		jda.setGame(Game.playing("with fire | " + Constants.PREFIX + "help"));

		// General Listeners
		jda.addEventListener(new Userinfo());
		jda.addEventListener(new Help());

		// Music Listeners
		jda.addEventListener(new Connect());
		jda.addEventListener(new Disconnect());
		jda.addEventListener(new Play());
		
		// Admin Listeners
		jda.addEventListener(new Clear());
		jda.addEventListener(new Restart());
		jda.addEventListener(new RoleSelection());

		// Levels Listeners
		jda.addEventListener(new Level());
		
		jda.build();

    }
}