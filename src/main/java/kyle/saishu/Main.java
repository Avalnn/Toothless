package kyle.saishu;

import javax.security.auth.login.LoginException;

import kyle.saishu.admin.*;
import kyle.saishu.events.*;
import kyle.saishu.general.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class Main {
    public static JDA jda;
	public static String PREFIX = "~";
	
	public static void main(String[] args)  throws LoginException {
		Token token = new Token();
		jda = new JDABuilder(AccountType.BOT).setToken(token.token).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setGame(Game.watching("people dooting."));
	
		// Event Listeners
		jda.addEventListener(new Join());

		// General Listeners
		jda.addEventListener(new RandomColor());
		
		// Admin Listeners
		jda.addEventListener(new Clear());
		jda.addEventListener(new Mute());
		jda.addEventListener(new Unmute());
		
	}
}
