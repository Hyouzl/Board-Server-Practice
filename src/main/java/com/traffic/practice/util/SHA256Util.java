package com.traffic.practice.util;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;

@Log4j2
public class SHA256Util {
    public static final String ENCRYPTION_KEY = "SHA-256";
    public static String encryptSHA256(String str) {

        String SHA = null;

        // MessageDigest는 Java에서 제공하는 암호화 해시 알고리즘을 구현한 클래스
        // 단방향 해시 함수로 해시값을 통해 원본값을 복원할 수 있다.
        // SHA-256 (Secure Hash Algorithm 256 비트)
        // MD5 (Message Digest Algorithm 5, 그러나 보안상의 이유로 더 이상 추천되지 않음), SHA-1 등의 알고리즘을 지원한다.
        MessageDigest sh;

        try {
            // SHA-256 알고리즘을 사용한 MessageDigest 객체를 반환
            sh = MessageDigest.getInstance(ENCRYPTION_KEY);
            // 해시 함수가 바이트 단위로 데이터를 처리하기 때문에 입력 문자열을 바이트 배열로 변환해준다.
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuffer sb = new StringBuffer();

            // 바이트 배열을 16진수로 변환하여 출력
            for (byte byteDatum: byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }

            SHA = sb.toString();

        } catch (Exception e) {
            log.error("encryptSHA256 ERROR : {", e.getMessage(), "}");
            SHA = null;
        }

        return SHA;

    }
}
