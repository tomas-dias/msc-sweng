package renaming.tool;

import library.Actor;
import library.Address;
import library.Message;
import library.SystemKillMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Customer extends Actor  {

	private Path path;
	private String oldString;
	private String newString;
	private Address serverAddress;
	private int depth = 1;
	private int numFiles;
	
	public Customer(Path path, String oldString, String newString, Address address) {
		this.path = path;
		this.oldString = oldString;
		this.newString = newString;
		serverAddress = address;
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof BootstrapMessage) {
			//System.out.println("Starting Customer");
			try {
				Files.walk(path, depth)
						.filter(p -> p.getNameCount() - path.getNameCount() == depth)
						.forEach(p -> serverAddress.sendMessage(
								new WorkOnProblemMessage(p, oldString, newString, this.getAddress())));
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (m instanceof ResponseMessage) {
			ResponseMessage m2 = (ResponseMessage) m;

			/*
			if(((ResponseMessage) m).isRenamed())
				System.out.println("File " + path.getFileName().toString() + " was renamed");
			*/

			try {
				numFiles = (int) Files.walk(path, depth)
						.filter(p -> p.getNameCount() - path.getNameCount() == depth)
						.map(Path::getFileName)
						.count();

			} catch (IOException e) {
				e.printStackTrace();
			}

			if (numFiles == 0) {
				serverAddress.sendMessage(new SystemKillMessage());
				this.getAddress().sendMessage(new SystemKillMessage());
			}
			else {
				depth++;
				try {
					Files.walk(path, depth)
							.filter(p -> p.getNameCount() - path.getNameCount() == depth)
							.forEach(p -> serverAddress.sendMessage(
									new WorkOnProblemMessage(p, oldString, newString, this.getAddress())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
