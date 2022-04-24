package com.ssafy.board.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	// 해쉬코드로 전환
	public String getHash(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());
		return byteToHex(md.digest());
	}

	// 16진수로 전환
	private String byteToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes)
			builder.append(String.format("%02x", b));
		return builder.toString();
	}
}
