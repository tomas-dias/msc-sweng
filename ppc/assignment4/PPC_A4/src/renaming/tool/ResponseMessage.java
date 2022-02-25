package renaming.tool;

import library.Message;

public class ResponseMessage extends Message {
	private boolean renamed;

	public ResponseMessage(boolean renamed) {
		this.renamed = renamed;
	}

	public boolean isRenamed() {
		return renamed;
	}
}
