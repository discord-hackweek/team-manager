package Koi.TeamManager;

import java.util.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class TeamManager {
	public static String configFile = "config.txt"; // name of the config file being used for token, version, etc
	public static JDA jda;
	public static String prefix; //global bot prefix - obtained from config file
	public static String token; // global bot token - obtained from config file
	public static String version; // global bot version - obtained from config file
	public static String verifiedFile = "TeamManager.Verified"; //file used to store all verified user IDs
	public static List<Long> verified; //global list of all verified users on your server
	public static String teamdataFile = "TeamManager.TeamData"; //file used to store all team data
	public static List<String> teams; //global list of all teams on your server
	public static Long owner; //server owner var
	public static String koi_avatar = "https://cdn.discordapp.com/avatars/148481254751207424/a_9ea316afd4dfe18412e0cf477452b2a8.webp?size=128"; //koi's discord profile picture (creator of the bot)
	
	public static void main(String[] args) {
		//first thing first, read through the config file
		Config configFile = new Config();
		configFile.openFile(); //make sure the file can be found
		token = configFile.readToken(); //pull out the token
		version = "Version " + configFile.readVersion(); //pull out the version
		prefix = configFile.readPrefix(); //pull out the prefix
		configFile.closeFile(); //we're done
		
		//next, read through the verified users file
		Verified verifiedFile = new Verified();
		verifiedFile.openFile();
		verified = verifiedFile.readVerified();
		verifiedFile.closeFile();
		
		// read through the current teams
		TeamData teamDataFile = new TeamData();
		teamDataFile.openFile();
		teams = teamDataFile.readTeams();
		teamDataFile.closeFile();
		
		// use the token to attempt a login
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).build();
		} catch(Exception e) {
			System.out.print("Could not find token. Make sure the config file is in the correct location and try again. \n\n");
			System.exit(0); // exit if login is not successful (wrong token? no token provided?)
		}
		
		//begin running the bot!
		jda.getPresence().setStatus(OnlineStatus.ONLINE); //set it to online by default
		jda.setAutoReconnect(true); //if there is a disconnect, make sure to auto-reconnect
		jda.getPresence().setGame(Game.playing(version)); //set the "playing" status to the version pulled from config file
		
		jda.addEventListener(new OnStartup()); // run the startup processes - this is used to help give the server owner the verified permissions
		jda.addEventListener(new Commands()); // run the commands class - this is the meat and potatoes of the bot
	}
}
