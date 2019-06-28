package Koi.TeamManager;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnStartup extends ListenerAdapter{
	//as soon as the bot is run/goes online, get the guild owners ID
	public void onGuildReady(GuildReadyEvent event) {
		Guild guild = event.getGuild();
		Member ownerAsMember = guild.getOwner();
		User ownerAsUser = ownerAsMember.getUser();
		Long ownerID = ownerAsUser.getIdLong();
		TeamManager.owner = ownerID;

		//check to see if owner ID is in the verified file
		Verified newVerified = new Verified();
		newVerified.openFile();
		boolean ownerVerified = newVerified.checkIfUserIsVerified(TeamManager.owner);
		//if not in file, then add to file
		if(ownerVerified == false) {
			TeamManager.verified.add(TeamManager.owner);
			//now write to verified.txt
			newVerified.addUserToVerified(TeamManager.owner);
		}
		newVerified.closeFile();
	}
}
