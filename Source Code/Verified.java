package Koi.TeamManager;

import java.io.*;
import java.util.*;

//USED TO READ AND WRITE TO THE VERIFIED USER FILE
public class Verified {
	private Scanner read;

	//open the verified file
	public void openFile() {
		try {
			read = new Scanner(new File(TeamManager.verifiedFile));
		} catch(Exception e) {
			System.out.print("Could not find the verified file! Make sure it is in the correct location, and has the correct name.\n"
					+ "For reference: the current name of the file should be '" + TeamManager.verifiedFile + "' according to what is specified.");
			System.exit(0);
		}
	}

	//read verified people IDs from file
	public List<Long> readVerified() {
		List<Long> verifiedPeople = new ArrayList<>(); //default to none found, then begin search

		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.verifiedFile))) {
			String line = " ";
			while ((line = br.readLine()) != null) {
				long verifiedID = Long.parseLong(line);
				verifiedPeople.add(verifiedID);
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		return verifiedPeople;
	}

	//check if a user is in the verified list using file
	public boolean checkIfUserIsVerified(long userID) {
		boolean verif = false;
		try (BufferedReader br = new BufferedReader(new FileReader(TeamManager.verifiedFile))) {
			String line = " ";
			while ((line = br.readLine()) != null) {
				long verifiedID = Long.parseLong(line);
				if(verifiedID == userID) {
					verif = true;
					break;
				}
			}
		} catch (Exception e) {
			//there was a problem reading the file
		}

		return verif;
	}

	//add a user to the verified file
	public void addUserToVerified(long userID) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.verifiedFile));
			StringBuffer inputBuffer = new StringBuffer();
			String line = "";

			//read the lines of the file into buffer
			while ((line = file.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			file.close();

			//convert buffer to string, then add on the missing owner entry
			String inputStr = inputBuffer.toString();
			String ownerIDStr = Long.toString(userID);
			inputStr = inputStr + ownerIDStr + '\n';

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.verifiedFile);
			fileOut.write(inputStr.getBytes());
			fileOut.close();

		} catch (Exception e) {
			//there was a problem reading the file
		}
	}

	//remove a user from the verified file
	public void removeUserFromVerified(long userID) {
		try {
			// input the file content to the StringBuffer
			BufferedReader file = new BufferedReader(new FileReader(TeamManager.verifiedFile));
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
			String ownerIDStr = Long.toString(userID);
			String finalStr = "";

			// get what is needed
			for(int i = 0; i<lines.length; i++) {
				if (lines[i].equalsIgnoreCase(ownerIDStr)) {
					//ignore
				}else {
					finalStr = finalStr + lines[i] + "\n";
				}
			}

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(TeamManager.verifiedFile);
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
