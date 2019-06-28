package Koi.TeamManager;

import net.dv8tion.jda.core.EmbedBuilder;

// THIS IS USED SOLELY TO MAKE USAGE COMMENTS FOR THE THE VARIOUS COMMANDS
public class Usage {
	public EmbedBuilder sendmsg_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "sendmsg usage");
		usage.setDescription("To use " + TeamManager.prefix + "sendmsg: `" + TeamManager.prefix + "sendmsg (#channelname) (message)`\n"
				+ "This command requires the user to be in the verified users list.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder verifiedAddRemoveList_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "verified add/remove/list usage");
		usage.setDescription("To use " + TeamManager.prefix + "verified add/remove: `" + TeamManager.prefix + "verified add (@user)` or `" + TeamManager.prefix + "verified remove (@user)`.\n"
				+ "To use " + TeamManager.prefix + "verified list: `" + TeamManager.prefix + "verified list`\n"
				+ "This command can only be used by the owner of the server.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder prefix_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "prefix usage");
		usage.setDescription("To use " + TeamManager.prefix + "prefix: `" + TeamManager.prefix + "prefix (new prefix)`\n"
				+ "This command can only be used by the owner of the server.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder createTeam_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "create team usage");
		usage.setDescription("To use " + TeamManager.prefix + "create team: `" + TeamManager.prefix + "create team public (team name)`.\n"
				+ "Verified users can also use `" + TeamManager.prefix + "create team private (team name) (@user)` where @user is the private team's manager.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder deleteTeam_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "delete team usage");
		usage.setDescription("To use " + TeamManager.prefix + "delete team: `" + TeamManager.prefix + "delete team (team name)`.\n"
				+ "Only team managers can use this command on their own teams - not regular members.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder description_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "description usage");
		usage.setDescription("To use " + TeamManager.prefix + "description: `" + TeamManager.prefix + "description (team name) (new description)`.\n"
				+ "Only team managers can use this command on their own teams - not regular members.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder teamListInfo_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "team list/info usage");
		usage.setDescription("To use " + TeamManager.prefix + "list/info: `" + TeamManager.prefix + "team list` and `" + TeamManager.prefix + "team info (team name)`.\n");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder joinLeave_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "join/leave usage");
		usage.setDescription("To use " + TeamManager.prefix + "join/leave: `" + TeamManager.prefix + "join (public team name)` and `" + TeamManager.prefix + "leave (team name)`.\n");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder addUser_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "add user usage");
		usage.setDescription("To use " + TeamManager.prefix + "add user: `" + TeamManager.prefix + "add (@user) (private team name)`.\n"
				+ "This command is for private team managers only.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder removeUser_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "remove user usage");
		usage.setDescription("To use " + TeamManager.prefix + "remove user: `" + TeamManager.prefix + "user (@user) (team name)`.\n"
				+ "This command is for team managers only.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}

	public EmbedBuilder setPriPub_usage() {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setTitle(TeamManager.prefix + "set private/public usage");
		usage.setDescription("To use " + TeamManager.prefix + "set private/public: `" + TeamManager.prefix + "set private (public team name)` and `" + TeamManager.prefix + "set public (private team name)`.\n"
				+ "This command is for verified users only.");
		usage.setFooter("Team Manager created by Koi", TeamManager.koi_avatar);
		usage.setColor(0x7289DA);
		return usage;
	}
}
