import java.util.TimeZone;

public class TimeZoneIDMaxLength {
	public static void main(String[] args) {
		String[] ids = TimeZone.getAvailableIDs();
		int maxLen = 0;
		int count = ids.length;
		for (int i = 0; i < count; i++) {
			String id = ids[i];
			System.out.println(id);

			int currentLen = id.length();
			if (maxLen < currentLen) {
				maxLen = currentLen;
			}
		}
		System.out.println("count is " + count);
		System.out.println("max length is " + maxLen);
	}
}
