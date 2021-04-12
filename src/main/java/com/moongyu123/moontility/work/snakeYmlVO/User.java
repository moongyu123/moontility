package com.moongyu123.moontility.work.snakeYmlVO;

/**
 * org.yaml.snakeyaml 모듈변환용 java객체( scala class를 사용하지 못하여 java로 생성)
 */
public class User {
	private Antiafk antiafk;
	private Clipboard clipboard;

	public Antiafk getAntiafk() {
		return antiafk;
	}

	public void setAntiafk(Antiafk antiafk) {
		this.antiafk = antiafk;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	@Override public String toString() {
		String indent = "  ";
		String line = System.getProperty("line.separator");

		return "user: " + line +
				indent + antiafk + line +
				indent + clipboard + line;
	}
}