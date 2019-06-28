package Koi.TeamManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//USED TO GET INFO, OR READ AND WRITE TO THE TEAM DATA FILE
public class TeamData {
	private Scanner read;

	//open the file
	public void openFile() {
		try {
			read = new Scanner(new File(TeamManager.teamdataFile));
		} catch(Exception e) {
			System.out.print("Could not find the team data file! Make sure it is in the correct location, and has the correct name.\n"
					+ "For reference: the current name of the file should be '" + TeamManager.teamdataFile + "' according to what is specified.");
			System.exit(0);
		}
	}

	//read the teams in the file
	public List<String> readTeams() {
		List<String> teams = new ArrayList<>(); //default to none found, then begin search

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.teamdataFile))) {
			String line = " ";
			while ((line = br.readLine()) != null) {
				if(line.startsWith("Name: ")) {
					String foundTeam = line.substring(6);
					teams.add(foundTeam);
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		return teams;
	}

	//create a team in the file
	public void createTeam(String name, String type, String manager) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// check for if team exists or not
			boolean alreadyExists = false;
			for(int i = 0; i<lines.length; i++) {
				if(lines[i].startsWith("Name: ")) {
					String nameToCheck = lines[i].substring(6);
					if(nameToCheck.equalsIgnoreCase(name)) {
						alreadyExists = true;
						break;
					}
				}
			}

			//set the new team info
			if(alreadyExists == false) {
				inputStr = inputStr + "Name: " + name + "\n" + "Desc." + name + ": \n" + "Type." + name + ": " + type + "\n" + 
						"Manager." + name + ": " + manager + "\n" + "Members." + name + ": " + manager + "\n";
			}

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(inputStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//find the team manager of a team from the file
	public String findTeamManager(String name) {
		String foundID = "none found";

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.teamdataFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Manager." + name)){
					foundID = line.substring(8+name.length()+2);
					break;
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		return foundID;
	}

	//delete a team from the file
	public void deleteTeam(String name) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");
			String finalStr = "";

			// do necessary stuff
			for(int i = 0; i<lines.length; i++) {
				if (lines[i].equals("Name: " + name) || 
						(lines[i].startsWith("Desc." + name + ":") || 
								(lines[i].startsWith("Type." + name + ":") || 
										(lines[i].startsWith("Manager." + name + ":") || 
												(lines[i].startsWith("Members." + name + ":")))))) {
					// skip this line
				}else {
					finalStr = finalStr + lines[i] + "\n";
				}
			}

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//set a description of a team from the file
	public void setDesc(String desc, String name) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// REPLACE THE LINES NECESSARY
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Desc." + name + ":";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					String oldDesc = lines[i].substring(5+name.length()+2);
					if(oldDesc.length() > 0) {
						lines[i] = lines[i].replace(oldDesc, desc);
					}else {
						lines[i] = lines[i] + desc;
					}
				}
			}

			//using the new lines, make the string to write to file
			String finalStr = String.join("\n", lines);

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//get the information of a team from the file
	public String teamInfo(String name) {
		String teamInfo = "none found";

		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// get the necessary info
			for(int i = 0; i<lines.length; i++) {
				if(lines[i].equalsIgnoreCase("Name: " + name)) {
					//lines[i] = name
					//lines[i+1] = desc, etc
					//...
					//lines[i+4] - 4 is last line of set

					teamInfo = lines[i] + "\n" + 
							"Description: " + lines[i+1].substring(5+name.length()+2) + "\n" + 
							"Type: " + lines[i+2].substring(5+name.length()+2) + "\n" + 
							"Manager: " + lines[i+3].substring(8+name.length()+2) + "\n" + 
							"Members: " + lines[i+4].substring(8+name.length()+2);
					break;
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		return teamInfo;
	}

	// check to see if a member is in a team using the file
	public boolean memberCheck(String memberToCheck, String team) {
		boolean isInList = true;

		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// check for if team exists or not
			String memberListStr = "";
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Members."+ team +": ";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					memberListStr = lines[i].substring(8+team.length()+2);
					break;
				}
			}

			if(memberListStr.length() > 0) {
				String[] memberList = memberListStr.split("\\s*,\\s*");
				// need to split by comma into new list
				for(String mem: memberList) {
					if(mem.equalsIgnoreCase(memberToCheck)) {
						isInList = true;
						break;
					}else {
						isInList = false;
					}
				}

			}else {
				isInList = false;
			}

		} catch (Exception e) {
			// there was a problem reading the file
		}


		return isInList;
	}

	//check to see if a team is private using the file
	public boolean privateCheck(String team) {
		boolean isPrivate = true;

		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// check for if team exists or not
			String priPub = "";
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Type."+ team +": ";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					priPub = lines[i].substring(5+team.length()+2);
					break;
				}
			}

			if(priPub.equalsIgnoreCase("private")) {
				isPrivate = true;
			}else {
				isPrivate = false;
			}

		} catch (Exception e) {
			//there was a problem reading the file
		}

		return isPrivate;
	}

	//add a member to a team using info from file (needed for private teams)
	public void addToTeam(String ID, String team) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// REPLACE THE LINES NECESSARY
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Members."+ team +": ";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					String oldMemListStr = lines[i].substring(8+team.length()+2);
					String newMemListStr = oldMemListStr + "," + ID;

					if(oldMemListStr.length() > 0) {
						lines[i] = lines[i].replace(oldMemListStr, newMemListStr);
					}else {
						lines[i] = lines[i] + ID;
					}
				}
			}

			//using the new lines, make the string to write to file
			String finalStr = String.join("\n", lines);

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//remove a member from a team using info from file (needed for managers)
	public void removeFromTeam(String ID, String team) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// REPLACE THE LINES NECESSARY
			String memberListStr = "";
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Members."+ team +": ";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					memberListStr = lines[i].substring(8+team.length()+2);

					String newMemListStr = "Members." + team + ": ";
					if(memberListStr.length() > 0) {
						String[] memberList = memberListStr.split("\\s*,\\s*");
						// need to split by comma into new list
						for(String mem: memberList) {
							if(mem.equalsIgnoreCase(ID)) {
								//don't add this one
							}else {
								newMemListStr = newMemListStr + mem + ",";
							}
						}
						newMemListStr = newMemListStr.substring(0, newMemListStr.length() - 1); // get rid of that annoying comma

						lines[i] = lines[i].replace(lines[i], newMemListStr);
					}

					break;
				}
			}

			//using the new lines, make the string to write to file
			String finalStr = String.join("\n", lines);

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//change a teams privacy using file
	public void changePrivacy(String newType, String team) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.teamdataFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then string to line array
			String inputStr = inputBuffer.toString();
			String[] lines = inputStr.split("[\\r\\n]+");

			// REPLACE THE LINES NECESSARY
			for(int i = 0; i<lines.length; i++) {
				String checkThis = "Type." + team + ":";
				if((lines[i].toLowerCase()).startsWith(checkThis.toLowerCase())) {
					String oldType = lines[i].substring(5+team.length()+2);
					lines[i] = lines[i].replace(oldType, newType);
				}
			}

			//using the new lines, make the string to write to file
			String finalStr = String.join("\n", lines);

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.teamdataFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//close the file
	public void closeFile() {
		read.close();
	}
}
