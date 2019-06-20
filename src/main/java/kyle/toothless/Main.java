package kyle.toothless;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.security.auth.login.LoginException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import kyle.toothless.admin.*;
import kyle.toothless.config.Config;
import kyle.toothless.general.*;
import kyle.toothless.levels.*;
import kyle.toothless.music.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	public static JDABuilder jda;
	public static Firestore db;
	
	public static void main(String[] args) throws LoginException, IOException {
		// Initialize Firebase database
		InputStream serviceAccount = new FileInputStream("GCP.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredentials(credentials)
			.build();
		FirebaseApp.initializeApp(options);
	    db = FirestoreClient.getFirestore();

		Config config = new Config(new File("botconfig.json"));
		// Config config = new Config(new File("src/main/java/kyle/toothless/botconfig.json"));
		jda = new JDABuilder(AccountType.BOT);
		jda.setToken(config.getString("token"));
		jda.setStatus(OnlineStatus.ONLINE);
		jda.setGame(Game.playing("with fish | " + Constants.PREFIX + "help"));

		// Needed Listeners
		jda.addEventListener(new Restart());

		if (Config.getInstance().getBoolean("loadcommands")) {
			// General Listeners
			jda.addEventListener(new Userinfo());
			jda.addEventListener(new Help());

			// Admin Listeners
			jda.addEventListener(new Clear());
			jda.addEventListener(new RoleSelection());

			// Levels Listeners
			jda.addEventListener(new Level());
		}

		if (Config.getInstance().getBoolean("disabledcommands")) {

		}

		if (Config.getInstance().getBoolean("musiccommands")) {
			// Music Listeners
			jda.addEventListener(new Connect());
			jda.addEventListener(new Disconnect());
			jda.addEventListener(new Play());
			jda.addEventListener(new Stop());
			jda.addEventListener(new Queue());
			jda.addEventListener(new Skip());
			jda.addEventListener(new NowPlaying());
			jda.addEventListener(new Volume());
			jda.addEventListener(new ClearQueue());
			jda.addEventListener(new Pause());
		}

		jda.build();
	}
}