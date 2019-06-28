package Koi.TeamManager;

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.util.*;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commands extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) { // begin by looking for a message

		// PRE-COMMAND REQUIREMENTS
		String[] args = event.getMessage().getContentRaw().split(" "); //create a list of the args in the message
		String fullContent = event.getMessage().getContentRaw(); // gather the message content as a whole

		Member senderAsMember = event.getMember(); // get the sender info (as member of guild)
		User senderAsUser = senderAsMember.getUser(); // get the sender info (as generic discord user)
		long senderID = senderAsUser.getIdLong(); // get the sender's discord ID as long - this will be used to check against a verified ID list

		// COMMAND 0: "help" - this uses both the current prefix and "!" just to make sure the person can still see the message
		if(fullContent.equalsIgnoreCase("!teammanager help") || (fullContent.equalsIgnoreCase(TeamManager.prefix + "teammanager help") ||
				(fullContent.equalsIgnoreCase("!help") || (fullContent.equalsIgnoreCase(TeamManager.prefix + "help") ||
						(fullContent.equalsIgnoreCase("!teammanager") || (fullContent.equalsIgnoreCase(TeamManager.prefix + "teammanager"))))))) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Team Manager");
			info.setDescription("Created for Discord's first annual Hack Week!\n"
					+ "The bot prefix is currently set to `" + TeamManager.prefix + "`\n"
					+ "To see a list of commands, type `" + TeamManager.prefix + "commands`");
			info.setFooter("Created by Koi", TeamManager.koi_avatar);
			info.setColor(0x7289DA);

			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}

		// COMMAND 1: "info" - Basic info about how the bot came to be
		if(args[0].equalsIgnoreCase(TeamManager.prefix + "info")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Team Manager Info");
			info.setDescription("Created for Discord's first annual Hack Week!\n\n"
					+ "All 5 days were utilized, with the bot taking a grand total of about 18 hours to "
					+ "complete.\n\n"
					+ "This was created by the individual Koi#0001 in order to see if it was \n"
					+ "possible to create a Local Team Management System without the use of roles.\n\n"
					+ "This is " + TeamManager.version + " of the bot, and may contain some hidden\n"
					+ "bugs, however many of the edge cases have been ironed out.\n\n"
					+ "I hope you can make use of, and enjoy my bot!");
			info.setFooter("Created by Koi", TeamManager.koi_avatar);
			info.setColor(0x7289DA);

			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}

		// COMMAND 2: "commands" - displays the FULL list of commands for all permission levels
		if(args[0].equalsIgnoreCase(TeamManager.prefix + "commands")) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Team Manager Commands");
			info.setDescription("List of user commands:\n"
					+ "`" + TeamManager.prefix + "info` - View the bots info.\n"
					+ "`" + TeamManager.prefix + "commands` - View the bots commands.\n"
					+ "`" + TeamManager.prefix + "id` - View your ID - note that this ID is what will be used to keep track of team membership.\n"
					+ "`" + TeamManager.prefix + "create team public (team name)` - Create a public team.\n"
					+ "`" + TeamManager.prefix + "delete team (team name)` - Deletes your team if you have created one.\n"
					+ "`" + TeamManager.prefix + "description (team name) (new description)` - Changes your team's description.\n"
					+ "`" + TeamManager.prefix + "team list` - View a list of all team names on the server.\n"
					+ "`" + TeamManager.prefix + "team info (team name)` - View a specific team's information.\n"
					+ "`" + TeamManager.prefix + "join (team name)` - Join someone else's public team.\n"
					+ "`" + TeamManager.prefix + "leave (team name)` - Leave someone else's team.\n\n"

					+ "List of team manager commands:\n"
					+ "`" + TeamManager.prefix + "add (@user) (private team name)` - Allows the manager of a private team to add new members.\n"
					+ "`" + TeamManager.prefix + "remove (@user) (team name)` - Allows the manager of a team to remove a member from their team.\n\n"

					+ "List of verified user commands:\n"
					+ "`" + TeamManager.prefix + "sendmsg (#channel) (message)` - Send a message to a channel using the bot.\n"
					+ "`" + TeamManager.prefix + "botstatus (online/idle/offline)` - Sets the bot's current status in the server.\n"
					+ "`" + TeamManager.prefix + "create team private (team name) (@user)` - Creates a private team and assigns a mananger to the team.\n"
					+ "`" + TeamManager.prefix + "set (private/public) (team name)` - Changes an existing team's privacy setting.\n\n"

					+ "List of server owner only commands:\n"
					+ "`" + TeamManager.prefix + "prefix (new prefix)` - Change the bots prefix.\n"
					+ "`" + TeamManager.prefix + "verified add/remove (@user)` - Adds or removes a user from the verified user list.\n"
					+ "`" + TeamManager.prefix + "verified list` - View the list of verified user IDs.\n" );

			info.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
			info.setColor(0x7289DA);

			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		}

		// COMMAND 3: "ID" - simple command to help the user find their ID (this is very important for team membership purposes)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "ID")) {
				String mention = "<@" + senderID + ">";
				event.getChannel().sendMessage(mention + ": Your ID is `" + senderID + "`. Your ID is what gets used for team member identification.").queue();
			}
		}catch(Exception e) {
			// no need to do anything here, there is almost no conceivable way this can error
		}

		// COMMAND 4: "prefix" - changes the bot prefix (server owner only)
		if(senderAsMember.isOwner()) {
			try {
				if(args[0].equalsIgnoreCase(TeamManager.prefix + "prefix")) {
					String newPrefix = args[1];

					if(newPrefix != " " && newPrefix != "" && newPrefix != null && newPrefix != "\t") {
						TeamManager.prefix = newPrefix;

						//write the new prefix to config file
						Config configFile = new Config();
						configFile.openFile();
						configFile.writeNewPrefix(newPrefix);
						configFile.closeFile();
					}
				}
			}catch(Exception e) {
				//usage statement
				Usage usage = new Usage();
				EmbedBuilder embed = usage.prefix_usage();
				event.getChannel().sendMessage(embed.build()).queue();
				embed.clear();
			}
		}

		// COMMAND 5: "verified add", "verified remove", and "verified list" - verified people have more control of the team management system (server owner only)
		if(senderAsMember.isOwner()) {
			try {
				if(args[0].equalsIgnoreCase(TeamManager.prefix + "verified")) {
					String type = args[1];
					if (type.equalsIgnoreCase("list")) {
						String gatheredIDs = "";
						String currentID = "";
						for (Long ID: TeamManager.verified) {           
							currentID = Long.toString(ID);
							gatheredIDs = gatheredIDs + currentID + "\n";
						}
						event.getChannel().sendMessage("List of current verified user IDs:\n```\n" + gatheredIDs + "\n```").queue();
					}else {
						String checkIDStr = "";
						if(args[2].startsWith("<@")) {
							checkIDStr = args[2];
							checkIDStr = checkIDStr.replace("<@", "");
							checkIDStr = checkIDStr.replace(">", "");
						}else {
							checkIDStr = args[2];
						}
						if (checkIDStr.length() == 18) {
							long checkID = Long.parseLong(checkIDStr);
							Verified changeVerified = new Verified();
							if(type.equalsIgnoreCase("add")) {
								changeVerified.openFile();
								boolean userVerified = changeVerified.checkIfUserIsVerified(checkID);
								if(userVerified == false) {
									TeamManager.verified.add(checkID);
									changeVerified.addUserToVerified(checkID);
									event.getChannel().sendMessage("User added to verified list successfully!").queue();
								}else {
									event.getChannel().sendMessage("The user you are trying to add is already in the verified list.").queue();
								}
							}else if(type.equalsIgnoreCase("remove")) {
								changeVerified.openFile();
								boolean userVerified = changeVerified.checkIfUserIsVerified(checkID);
								if(userVerified == true) {
									TeamManager.verified.remove(checkID);
									changeVerified.removeUserFromVerified(checkID);
									event.getChannel().sendMessage("User removed from verified list successfully.").queue();
								}else {
									event.getChannel().sendMessage("The user you are trying to remove is not in the verified list.").queue();
								}
							}
							changeVerified.closeFile();
						}else {
							//usage statement
							Usage usage = new Usage();
							EmbedBuilder embed = usage.verifiedAddRemoveList_usage();
							event.getChannel().sendMessage(embed.build()).queue();
							embed.clear();
						}
					}
				}

			} catch(Exception e) {
				//usage statement
				Usage usage = new Usage();
				EmbedBuilder embed = usage.verifiedAddRemoveList_usage();
				event.getChannel().sendMessage(embed.build()).queue();
				embed.clear();
			}
		}

		// COMMAND 6: "sendmsg" - allows the user to send a message to a text channel of choice using the bot (verified only)
		if(TeamManager.verified.contains(senderID)) {
			try {
				if(args[0].equalsIgnoreCase(TeamManager.prefix + "sendmsg")) {
					String channelName = args[1];
					TextChannel sendHere = null;
					String messageToSend = "";
					List<TextChannel> channelList = event.getGuild().getTextChannels();
					for(int i = 0; i<channelList.size(); i++) {
						String currentlyChecking = channelList.get(i).getAsMention();
						if(currentlyChecking.equalsIgnoreCase(channelName)){
							sendHere = channelList.get(i);
							break;
						}
					}

					for(int j = 2; j<args.length; j++) {
						messageToSend = messageToSend + args[j] + " ";
					}

					if (sendHere != null) {
						sendHere.sendMessage(messageToSend).queue();
					}else {
						//usage statement
						Usage usage = new Usage();
						EmbedBuilder embed = usage.sendmsg_usage();
						event.getChannel().sendMessage(embed.build()).queue();
						embed.clear();
					}
				}
			} catch(Exception e) {
				//usage statement
				Usage usage = new Usage();
				EmbedBuilder embed = usage.sendmsg_usage();
				event.getChannel().sendMessage(embed.build()).queue();
				embed.clear();
			}
		}

		// COMMAND 7: "botstatus" - Change how the bot's status is displayed (verified only)
		if(TeamManager.verified.contains(senderID)) {
			try {
				if(args[0].equalsIgnoreCase(TeamManager.prefix + "botstatus")) {
					String status = args[1];

					if(status.equalsIgnoreCase("online")) {
						TeamManager.jda.getPresence().setStatus(OnlineStatus.ONLINE);
					}else if(status.equalsIgnoreCase("idle")) {
						TeamManager.jda.getPresence().setStatus(OnlineStatus.IDLE);
					}else if(status.equalsIgnoreCase("offline")) {
						TeamManager.jda.getPresence().setStatus(OnlineStatus.OFFLINE);
					}
				}
			} catch(Exception e) {
				//this could be a usage statement, but seeing as this is 'botstatus' it does not need one
			}
		}

		// COMMAND 8: "create team public/private" - creates a public or private team (public = anyone, private = verified only)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "create")) {
				String team = args[1];
				String teamType = args[2];
				String teamName = args[3];
				TeamData teamData = new TeamData();

				if(team.equalsIgnoreCase("team") && (teamType.equalsIgnoreCase("public"))) {
					String managerID = Long.toString(senderID);

					teamData.createTeam(teamName, teamType, managerID);
					TeamManager.teams.add(teamName);
					event.getChannel().sendMessage("Created team successfully!").queue();
				}

				if(team.equalsIgnoreCase("team") && (teamType.equalsIgnoreCase("private") && (TeamManager.verified.contains(senderID)))) {
					String managerID = "";
					if(args[4].startsWith("<@")) {
						managerID = args[4];
						managerID = managerID.replace("<@", "");
						managerID = managerID.replace(">", "");
					}else {
						managerID = args[4];
					}
					long manager = Long.parseLong(managerID);

					boolean personExistsInServer = false;
					List<Member> memberList = event.getGuild().getMembers();
					for (Member member : memberList) {
						if(member.getUser().getIdLong() == manager){
							personExistsInServer = true;
						}
					}

					if(personExistsInServer) {
						teamData.createTeam(teamName, teamType, managerID);
						TeamManager.teams.add(teamName);
						event.getChannel().sendMessage("Created team successfully!").queue();
					}else {
						event.getChannel().sendMessage("The user you are trying to set as the manager is not in this server").queue();
					}
				}


			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.createTeam_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 9: "delete (teamname)" - team manager can delete their team (verified can also use but can delete any team)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "delete")) {
				String team = args[1];
				String teamName = args[2];
				TeamData teamData = new TeamData();

				if(team.equalsIgnoreCase("team")) {
					String senderIDStr = Long.toString(senderID);
					String teamManager = teamData.findTeamManager(teamName);

					if (teamManager.equalsIgnoreCase("none found")) {
						event.getChannel().sendMessage("Couldn't find that team's manager. Make sure you spelled the team name right.").queue();
					} else if(senderIDStr.equalsIgnoreCase(teamManager) || TeamManager.verified.contains(senderID)) {
						teamData.deleteTeam(teamName);
						TeamManager.teams.remove(teamName);
						event.getChannel().sendMessage("Team deleted successfully.").queue();
					}else {
						event.getChannel().sendMessage("You are not allowed to delete a team you don't own.").queue();
					}
				}
			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.deleteTeam_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 10: "description (teamname)" - sets the team description (team manager or verified only)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "description")) {
				String teamName = args[1];
				TeamData teamData = new TeamData();

				String senderIDStr = Long.toString(senderID);
				String teamManager = teamData.findTeamManager(teamName);

				if (teamManager.equalsIgnoreCase("none found")) {
					event.getChannel().sendMessage("Couldn't find that team's manager. Make sure you spelled the team name right.").queue();
				} else if(senderIDStr.equalsIgnoreCase(teamManager) || TeamManager.verified.contains(senderID)) {
					// set the desc
					String newDesc = "";

					for(int j = 2; j<args.length; j++) {
						newDesc = newDesc + args[j] + " ";
					}

					teamData.setDesc(newDesc, teamName);
					event.getChannel().sendMessage("The new description has been set.").queue();
				}
			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.description_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 11: "team list" and "team info (teamname) - list the teams or view a specific team's info
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "team")) {
				String list_info = args[1];
				String msg = "";

				if(list_info.equalsIgnoreCase("list")) {
					for (String team: TeamManager.teams) {           
						msg = msg + team + "\n";
					}

					if(msg != "") {
						event.getChannel().sendMessage("List of team names on the server:\n```\n" + msg + "\n```").queue();
					}else {
						event.getChannel().sendMessage("This server currently has no teams.").queue();
					}
				}

				if(list_info.equalsIgnoreCase("info")) {
					String team = args[2];
					TeamData teamData = new TeamData();

					for(String teamName: TeamManager.teams) {
						if(team.equalsIgnoreCase(teamName)) {
							//print the name, desc, pub/pri, manager id, list of member ids
							String teamInfo = teamData.teamInfo(team);
							if(teamInfo.equalsIgnoreCase("none found")) {
								// couldn't find, just ignore
							}else {
								event.getChannel().sendMessage("Info for " + team + ":\n```\n" + teamInfo + "\n```").queue();
							}
						}
					}
				}

			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.teamListInfo_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 12: "join (teamname)" and "leave (teamname)" - allows the user to join or leave a team
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "join")) {
				String teamToJoin = args[1];
				String senderIDStr = Long.toString(senderID);
				TeamData teamData = new TeamData();

				boolean teamExists = false;
				for(String teamName: TeamManager.teams) {
					if(teamName.equalsIgnoreCase(teamToJoin)) {
						teamExists = true;
						break;
					}
				}

				if(teamExists == true) {
					boolean alreadyMember = teamData.memberCheck(senderIDStr, teamToJoin);

					if(alreadyMember==false) {
						//check to see if the team is private
						boolean isPrivate = teamData.privateCheck(teamToJoin);

						if(isPrivate==false) {
							//team is public, therefore it is joinable
							teamData.addToTeam(senderIDStr, teamToJoin);
							event.getChannel().sendMessage("Joined the team successfully.").queue();
						}else {
							event.getChannel().sendMessage("The team you are trying to join is private - you need permission from the team's manager.").queue();
						}

					}else {
						event.getChannel().sendMessage("You are already in the member list for this team.").queue();
					}
				}else {
					event.getChannel().sendMessage("Couldn't find the team you are trying to join.").queue();
				}

			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.joinLeave_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "leave")) {
				String teamToLeave = args[1];
				String senderIDStr = Long.toString(senderID);
				TeamData teamData = new TeamData();

				boolean teamExists = false;
				for(String teamName: TeamManager.teams) {
					if(teamName.equalsIgnoreCase(teamToLeave)) {
						teamExists = true;
						break;
					}
				}

				if(teamExists == true) {
					boolean alreadyMember = teamData.memberCheck(senderIDStr, teamToLeave);

					if(alreadyMember==true) { // user is in the team they are trying to leave
						String managerID = teamData.findTeamManager(teamToLeave);
						if(managerID.equalsIgnoreCase(senderIDStr)) {
							event.getChannel().sendMessage("You are the manager of this team. You cannot leave a team you manage.\nIf you wish to delete this team, use the delete command instead.").queue();
						}else {
							teamData.removeFromTeam(senderIDStr, teamToLeave);
							event.getChannel().sendMessage("Left the team successfully.").queue();
						}
					}
				}else {
					event.getChannel().sendMessage("Couldn't find the team you are trying to leave.").queue();
				}

			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.joinLeave_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 13: "add (user) (private team)" - team manager can add people to their private team (also usable by verified to add people to public teams)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "add")) {
				String person = "";
				if(args[1].startsWith("<@")) {
					person = args[1];
					person = person.replace("<@", "");
					person = person.replace(">", "");
				}else {
					person = args[1];
				}
				String team = args[2];
				String senderIDStr = Long.toString(senderID);
				TeamData teamData = new TeamData();
				String managerID = teamData.findTeamManager(team);


				boolean teamExists = false;
				for(String teamName: TeamManager.teams) {
					if(teamName.equalsIgnoreCase(team)) {
						teamExists = true;
						break;
					}
				}

				if(teamExists == true) {
					if(managerID.equalsIgnoreCase(senderIDStr) || (TeamManager.verified.contains(senderID))) {
						boolean alreadyMember = teamData.memberCheck(person, team);

						if(alreadyMember==false) {
							//check to see if the team is private
							boolean isPrivate = teamData.privateCheck(team);

							if(isPrivate==true) {
								//team is private, therefore the person can be added
								teamData.addToTeam(person, team);
								event.getChannel().sendMessage("The user has been added to the team successfully.").queue();
							}else {
								if(TeamManager.verified.contains(senderID)) {
									teamData.addToTeam(person, team); // verified people can add users to public teams too
									event.getChannel().sendMessage("The user has been added to the team successfully.").queue();
								}else {
									event.getChannel().sendMessage("This team is public - you cannot add people to it.\nYou need people to use the join command on their own.").queue();
								}
							}
						}
					}
				}else {
					event.getChannel().sendMessage("Couldn't find the private team you are trying to add a member to.").queue();
				}
			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.addUser_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 14: "remove (user) (team)" - for team manager, similar to add but for removal (can also be used by verified people on public teams)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "remove")) {
				String person = "";
				if(args[1].startsWith("<@")) {
					person = args[1];
					person = person.replace("<@", "");
					person = person.replace(">", "");
				}else {
					person = args[1];
				}
				String team = args[2];
				String senderIDStr = Long.toString(senderID);
				TeamData teamData = new TeamData();
				String managerID = teamData.findTeamManager(team);

				boolean teamExists = false;
				for(String teamName: TeamManager.teams) {
					if(teamName.equalsIgnoreCase(team)) {
						teamExists = true;
						break;
					}
				}

				if(teamExists == true) {
					if(managerID.equalsIgnoreCase(senderIDStr) || (TeamManager.verified.contains(senderID))) {
						if(person.equalsIgnoreCase(managerID)) {
							event.getChannel().sendMessage("You can't remove the team manager from the team.").queue();
						}else {
							boolean alreadyMember = teamData.memberCheck(person, team);

							if(alreadyMember==true) {
								teamData.removeFromTeam(person, team);
								event.getChannel().sendMessage("The user has been removed from the team successfully.").queue();
							}
						}
					}
				}else {
					event.getChannel().sendMessage("Couldn't find the team you are trying to remove a member from.").queue();
				}
			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.removeUser_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

		// COMMAND 15: "set private/public (teamname)" - sets the teams privacy (verified only)
		try {
			if(args[0].equalsIgnoreCase(TeamManager.prefix + "set")) {
				String priPub = args[1];
				String team = args[2];
				TeamData teamData = new TeamData();

				boolean teamExists = false;
				for(String teamName: TeamManager.teams) {
					if(teamName.equalsIgnoreCase(team)) {
						teamExists = true;
						break;
					}
				}

				if(teamExists == true) {
					if(priPub.equalsIgnoreCase("private") || (priPub.equalsIgnoreCase("public"))) {
						priPub = priPub.toLowerCase();
						if(TeamManager.verified.contains(senderID)) {
							boolean isPrivate = teamData.privateCheck(team);
							if(isPrivate==true && priPub.equalsIgnoreCase("public")) {
								//set this team to public
								teamData.changePrivacy(priPub, team);
								event.getChannel().sendMessage("The specified team is now public.").queue();
							}else if(isPrivate==true && priPub.equalsIgnoreCase("private")) {
								event.getChannel().sendMessage("This team is already private.").queue();
							}

							if(isPrivate==false && priPub.equalsIgnoreCase("private")) {
								//set this team to private
								teamData.changePrivacy(priPub, team);
								event.getChannel().sendMessage("The specified team is now private.").queue();
							}else if(isPrivate==false && priPub.equalsIgnoreCase("public")) {
								event.getChannel().sendMessage("This team is already public.").queue();
							}	
						}
					}else {
						//usage statement
						Usage usage = new Usage();
						EmbedBuilder embed = usage.setPriPub_usage();
						event.getChannel().sendMessage(embed.build()).queue();
						embed.clear();
					}
				}else {
					event.getChannel().sendMessage("Couldn't find the team you are trying to change the privacy setting of.").queue();
				}
			}
		}catch(Exception e) {
			//usage statement
			Usage usage = new Usage();
			EmbedBuilder embed = usage.setPriPub_usage();
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
		}

	}
}
