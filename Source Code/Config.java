package Koi.TeamManager;

import java.io.*;
import java.util.*;

//USED TO READ THE CONFIG FILE AND GET THE BOT SET-UP. REMEMBER TO PUT YOUR TOKEN IN THE CONFIG FILE.
public class Config {
	private Scanner read;

	//opens the config file
	public void openFile() {
		try {
			read = new Scanner(new File(TeamManager.configFile));
		} catch(Exception e) {
			System.out.print("Could not find the config file! Make sure it is in the correct location, and has the correct name.\n"
					+ "For reference: the current name of the file should be '" + TeamManager.configFile + "' according to what is specified.");
			System.exit(0);
		}
	}

	//reads the token from the config file
	public String readToken() {
		String token = "none found"; //default to none found, then begin search

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.configFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Token: ")){ //WITH space
					token = line.substring(7);
				}else if(line.startsWith("Token:")) { //WITHOUT space
					token = line.substring(6);
				}
			}
		} catch (Exception e) {
			System.out.print("Could not read the config file for some unknown reason");
		}

		if (token.equalsIgnoreCase("none found")) {
			System.out.print("The config file is missing the 'Token:' line. Please make sure to specify the bot's token.");
			System.exit(0);
		}else if (token.equalsIgnoreCase("") || token.equalsIgnoreCase(" ")) {
			System.out.print("There is nothing in the 'Token:' field! Make sure to enter the token into the config file.");
			System.exit(0);
		}

		return token;
	}

	//gets the bot version # from config
	public String readVersion() {
		String version = "none found"; //default to none found, then begin search

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.configFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Version: ")){ //WITH space
					version = line.substring(9);
				}else if(line.startsWith("Version:")) { //WITHOUT space
					version = line.substring(8);
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		if (version.equalsIgnoreCase("none found")) {
			System.out.print("The config file is missing the 'Version:' line. Please make sure to specify a version number.");
			System.exit(0);
		}else if (version.equalsIgnoreCase("") || version.equalsIgnoreCase(" ")) {
			System.out.print("There is nothing in the 'Version:' field! Make sure to enter the version into the config file.");
			System.exit(0);
		}

		return version;
	}

	//reads the bots prefix from config file
	public String readPrefix() {
		String prefix = "none found"; //default to none found, then begin search

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.configFile))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Prefix: ")){ //WITH space
					prefix = line.substring(8);
				}else if(line.startsWith("Prefix:")) { //WITHOUT space
					prefix = line.substring(7);
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		if (prefix.equalsIgnoreCase("none found")) {
			System.out.print("The config file is missing the 'Version:' line. Please make sure to specify a version number.");
			System.exit(0);
		}else if (prefix.equalsIgnoreCase("") || prefix.equalsIgnoreCase(" ")) {
			System.out.print("There is nothing in the 'Version:' field! Make sure to enter the version into the config file.");
			System.exit(0);
		}

		return prefix;
	}

	//changes the bots prefix using config file
	public void writeNewPrefix(String newPrefix) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.configFile));
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
				if(lines[i].startsWith("Prefix: ")) {
					String oldPrefix = lines[i].substring(8);
					lines[i] = lines[i].replace(oldPrefix, newPrefix);
				}else if(lines[i].startsWith("Prefix:")) {
					String oldPrefix = lines[i].substring(7);
					lines[i] = lines[i].replace(oldPrefix, newPrefix);
				}
			}

			//using the new lines, make the string to write to file
			String finalStr = String.join("\n", lines);

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.configFile);
			fileOut.write(finalStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//closes the file
	public void closeFile() {
		read.close();
	}
}
