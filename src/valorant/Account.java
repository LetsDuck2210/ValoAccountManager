package valorant;

import java.awt.Image;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Consumer;

import util.ImageUtil;

public record Account(String riotId, String password, String name, String tagline, String additional,
		Currency currency) {
	public Account(String riotId, String pw, String name, String tagline, Currency currency) {
		this(riotId, pw, name, tagline, "", currency);
	}

	public void getRankIcon(Consumer<Image> averageAmerican) {
		new Thread(() -> {
			try {
				System.out.println("fetching " + name + "#" + tagline);

				var req = HttpRequest.newBuilder()
						.uri(new URI("https://api.kyroskoh.xyz/valorant/v1/mmr/eu/" + name + "/" + tagline)).GET()
						.build();

				var client = HttpClient.newHttpClient();
				var resp = client.send(req, BodyHandlers.ofString());
				var rank = resp.body().split(" - ")[0].trim().replaceAll(" ", "_");
				var img = ImageUtil.loadFile("assets/rankIcons/" + rank + ".png").catchErr(e -> {
				}).sync();
				System.out.println(name + " fetched!");
				if (img == null) {
					img = ImageUtil.loadFile("assets/rankIcons/empty.png").catchErr(e -> {
					}).sync();
				}
				averageAmerican.accept(img);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public String toString() {
		return riotId.length() + ":" + riotId + password.length() + ":" + password + name.length() + ":" + name
				+ tagline.length() + ":" + tagline + additional.length() + ":" + additional + currency.toString();
//		return STR."\{riotId.length()}: \{riotId}...";
	}

	public static Account fromString(String string) throws IllegalArgumentException {
		var split = string.split(":");
		Account acc = null;
		try {
			int riotIdLength = Integer.parseInt(split[0]);
			String riotId = split[1].substring(0, riotIdLength);

			int passwordLength = Integer.parseInt(split[1].substring(riotIdLength));
			String password = split[2].substring(0, passwordLength);

			int nameLength = Integer.parseInt(split[2].substring(passwordLength));
			String name = split[3].substring(0, nameLength);

			int tagLength = Integer.parseInt(split[3].substring(nameLength));
			String tag = split[4].substring(0, tagLength);

			int additionalLength = Integer.parseInt(split[4].substring(tagLength));
			String additional = split[5].substring(0, additionalLength);

			String currencyString = split[5].substring(additionalLength);
			Currency currency = Currency.fromString(currencyString);

			if (riotIdLength == 0 || passwordLength == 0 || nameLength == 0 || tagLength == 0)
				throw new IllegalArgumentException("wrong string format: values can't be empty");
			if (nameLength > 16 || tagLength > 5)
				throw new IllegalArgumentException("name or tagline too long");

			acc = new Account(riotId, password, name, tag, additional, currency);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("wrong string format");
		}
		return acc;
	}
}