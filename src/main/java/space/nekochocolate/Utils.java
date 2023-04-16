package space.nekochocolate;

import java.time.Duration;

import static java.lang.Math.round;

public class Utils
{
	public static String convertTimestamp(long Timestamp)
	{
		Duration obj_Duration = Duration.ofSeconds(Timestamp);
		long days, hours, mins, secs;
		days = obj_Duration.toDays();
		obj_Duration = obj_Duration.minusDays(days);
		hours = obj_Duration.toHours();
		obj_Duration = obj_Duration.minusHours(hours);
		mins = obj_Duration.toMinutes();
		obj_Duration = obj_Duration.minusMinutes(mins);
		secs = obj_Duration.toSeconds();
		return days + " 天 " + hours + " 小时 " + mins + " 分钟 " + secs + " 秒";
	}
	
	public static String createBar(int Percent)
	{
		int barCount = (int) round(Percent / 100.0 * 20);
		StringBuilder bar = new StringBuilder("[");
		for (int i = 1; i <= 20; i++)
		{
			if (i <= barCount) bar.append("-");
			else bar.append(" ");
		}
		return bar + "] " + Percent + "%";
	}
	
	public static String anotherLine()
	{
		return "\n";
	}
}
