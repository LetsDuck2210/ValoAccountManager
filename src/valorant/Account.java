package valorant;

import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import util.ImageUtil;

// same functionality, no setters
public record Account(String riotId, String password, String name, String tagline, String additional, Currency currency) {
	public Account(String riotId, String pw, String name, String tagline, Currency currency) {
		this(riotId, pw, name, tagline, "", currency);
	}
	
	public Image getRankIcon() throws IOException, URISyntaxException, InterruptedException {
		System.out.println("fetching " + name + "#" + tagline);

		var req = HttpRequest.newBuilder()
				.uri(new URI("https://api.kyroskoh.xyz/valorant/v1/mmr/eu/" + name + "/" + tagline)).GET().build();

		var client = HttpClient.newHttpClient();
		var resp = client.send(req, BodyHandlers.ofString());
		var rank = resp.body().split(" - ")[0].trim().replaceAll(" ", "_");
		var img = ImageUtil.loadFile("assets/" + rank + ".png").catchErr(e -> {
		}).sync();
		System.out.println(name + " fetched!");
		if (img == null) {
			return ImageUtil.loadFile("assets/empty.png").catchErr(e -> {
			}).sync();
		}

		return img;
	}
}