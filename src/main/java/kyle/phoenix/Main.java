package kyle.phoenix;

import javax.security.auth.login.LoginException;

import kyle.phoenix.admin.*;
import kyle.phoenix.general.*;
import kyle.phoenix.music.*;
import kyle.phoenix.utils.*;
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
		jda.addEventListener(new Music());
		
		// Admin Listeners
		jda.addEventListener(new Clear());
		jda.addEventListener(new Restart());

		// Utils Listeners
		jda.addEventListener(new RoleSelection());
		
		jda.build();

    }
}