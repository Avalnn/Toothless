package kyle.phoenix;

import javax.security.auth.login.LoginException;

import kyle.phoenix.admin.*;
import kyle.phoenix.events.*;
import kyle.phoenix.general.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class Main {
    public static JDA jda;
	public static String PREFIX = "~";
	
	public static void main(String[] args) throws LoginException {
		Token token = new Token();
		jda = new JDABuilder(AccountType.BOT).setToken(token.token).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setGame(Game.watching("some nerd"));
	
		// Event Listeners
		jda.addEventListener(new JoinLeave());

		// General Listeners
		jda.addEventListener(new RandomColor());
		jda.addEventListener(new About());
		jda.addEventListener(new Stab());
		jda.addEventListener(new Userinfo());
		
		// Admin Listeners
		jda.addEventListener(new Clear());
		jda.addEventListener(new Reload());
		
	}
}
