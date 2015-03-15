import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto2 {

	// CBC
	public static String key1 = "140b41b22a29beb4061bda66b6747e14";
	public static String text1 = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";
	public static String text2 = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253";

	// CTR
	public static String key2 = "36f18357be4dbd77f050515c73fcf9f2";
	public static String text3 = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
	public static String text4 = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";

	public static void main(String[] args) {

		try {
			System.out.println("CBC");
			System.out.println(decipherCBC(key1, text1));
			System.out.println(decipherCBC(key1, text2));

			System.out.println("CTR");
			System.out.println(decipherCTR(key2, text3));
			System.out.println(decipherCTR(key2, text4));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String decipherCTR(String keyString, String messageString)
			throws Exception {

		byte[] key = hexStringToByteArray(keyString);
		byte[] message = hexStringToByteArray(messageString);
		SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(subArray(message, 0, 16));

		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

		return byteArrayToString(cipher.doFinal(subArray(message, 16,
				message.length)));

	}

	private static String decipherCBC(String keyString, String messageString)
			throws Exception {

		byte[] key = hexStringToByteArray(keyString);
		byte[] message = hexStringToByteArray(messageString);

		SecretKeySpec aesKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(subArray(message, 0, 16));

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

		return byteArrayToString(cipher.doFinal(subArray(message, 16,
				message.length)));
	}

	private static String byteArrayToString(byte[] array) throws Exception {

		return new String(array, "UTF-8");
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	private static byte[] subArray(byte[] message, int start, int end) {

		byte[] result = new byte[end - start];
		int contResult = 0;

		for (int cont = start; cont < end; cont++) {
			result[contResult++] = message[cont];
		}
		return result;
	}
}
