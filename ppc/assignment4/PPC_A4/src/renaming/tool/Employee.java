package renaming.tool;

import library.Actor;
import library.Address;
import library.Message;

import java.io.File;
import java.io.IOException;


public class Employee extends Actor  {

	public Employee(Address address) {
		super(address);
	}

	@Override
	protected void handleMessage(Message m) {
		//System.out.println("Employee " + this + " received " + m);

		if (m instanceof WorkOnProblemMessage) {
			WorkOnProblemMessage m2 = ((WorkOnProblemMessage) m);

			String filename = m2.getPath().getFileName().toString();
			String oldPath = m2.getPath().toString();

			if(filename.contains(m2.getOldString()))
			{
				//System.out.println("File to be renamed");
				File oldFile = new File(oldPath);
				String newFilename = filename.replace(m2.getOldString(), m2.getNewString());
				String newPath = oldPath.replace(filename, newFilename);
				boolean renamed = false;

				if(oldPath.contains(newFilename))
				{
					oldFile.delete();
					File newFile = new File(newPath);
					try {
						renamed = newFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					renamed = oldFile.renameTo(new File(newPath));
				}
				m2.getReplyTo().sendMessage(new ResponseMessage(renamed));
			}
			else
				m2.getReplyTo().sendMessage(new ResponseMessage(false));

		}
		
	}

}
