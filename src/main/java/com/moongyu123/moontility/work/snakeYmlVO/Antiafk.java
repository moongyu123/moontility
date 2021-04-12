package com.moongyu123.moontility.work.snakeYmlVO;

public class Antiafk {
	private boolean use;    //사용여부

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	@Override public String toString() {
		String indent = "    ";
		String line = System.getProperty("line.separator");

		return "antiafk: " + line +
				indent + "use: " + use + line
				;
	}
}
