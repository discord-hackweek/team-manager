# Team Manager
#### Created by Koi#0001


## What is it?
Team Manager is a JDA bot which is meant to organize various types of teams inside of your discord server WITHOUT relying on discord roles. It is a local-storage solution team management system (which can also be deployed in a cloud setting if needed). 
It is perfect for keeping teams organized. Some teams which would benefit include professional work groups, esports teams, or even friend groupds - the list goes on.


## Commands
The bot's default prefix is `!` but can be changed using the prefix command.

#### Generic Commands
`info` - View the bots info

`commands` - View the bots commands

`id` - View your ID - note that this ID is what will be used to keep track of team membership

`create team public (team name)` - Create a public team

`delete team (team name)` - Deletes your team if you have created one

`description (team name) (new description)` - Changes your team's description

`team list` - View a list of all team names on the server

`team info (team name)` - View a specific team's information

`join (team name)` - Join someone else's public team

`leave (team name)` - Leave someone else's team


#### Team Manager Commands
`add (@user) (private team name)` - Allows the manager of a private team to add new members

`remove (@user) (team name)` - Allows the manager of a team to remove a member from their team


#### Verified User Commands
`sendmsg (#channel) (message)` - Send a message to a channel using the bot

`botstatus (online/idle/offline)` - Sets the bot's current status in the server

`create team private (team name) (@user)` - Creates a private team and assigns a mananger to the team

`set (private/public) (team name)` - Changes an existing team's privacy setting


#### Owner Only Commands
`prefix (new prefix)` - Change the bots prefix

`verified add/remove (@user)` - Adds or removes a user from the verified user list

`verified list` - View the list of verified user IDs 


## Setup
#### "Team Manager.zip"
Setup is easy. Download the 'Team Manager.zip' file, and extract the "Team Manager" folder to anywhere you'd like on your computer. 
Open that folder and you will see 5 files: `config.txt`, `RunTeamManager.bat`, `TeamManager.jar`, `TeamManager.TeamData`, and `TeamManager.Verified`. 

Remember NOT to move any of these files - keep them inside the same folder all together. 

#### "config.txt"
Open the config.txt file. The first line will read: `Token: YourTokenGoesHere!`. Replace YourTokenGoesHere! with your discord bot's token. 

Remember NOT to remove the beginning of the line (`Token: `), as this is utilized by the bot to find your token. 

#### Running the Bot
Once you have successfully added your bot's token to the config file, the bot is ready to run! Double click the `RunTeamManager.bat` file, and a CMD window will pop up. 
If you did everything right, this console window will stay open and the bot will be running. If you close this window, the bot will stop. 

Alternatively, you can just run the `TeamManager.jar` file and this will also run the bot, but will not use a console. Note that this makes the bot harder to stop. 


## Demonstration
[Short YouTube video demonstrating some of the commands](https://www.youtube.com/watch?v=yuTtxtS3sIw)
