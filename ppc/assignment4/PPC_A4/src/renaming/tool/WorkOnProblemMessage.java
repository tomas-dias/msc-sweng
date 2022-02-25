package renaming.tool;

import library.Address;
import library.Message;

import java.nio.file.Path;

public class WorkOnProblemMessage extends Message {
	private Path path;
	private String oldString;
	private String newString;
	private Address replyTo;

	public WorkOnProblemMessage(Path path, String oldString, String newString, Address address) {
		this.path = path;
		this.oldString = oldString;
		this.newString = newString;
		this.replyTo = address;
	}

	public Address getReplyTo() {
		return replyTo;
	}

	public Path getPath() {
		return path;
	}

	public String getOldString() {
		return oldString;
	}

	public String getNewString() {
		return newString;
	}
}
