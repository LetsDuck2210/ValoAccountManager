package valorant;

import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import util.ImageUtil;

public class Account {
	private String riotId;
	private String password;
	private String name;
	private String tagline;
	private String additional;
	private Currency currency;

	public Account(String riotId, String pw, String name, String tagline, String additional, Currency currency) {
		this.riotId = riotId;
		this.password = pw;
		this.name = name;
		this.tagline = tagline;
		this.additional = additional;
		this.currency = currency;
	}

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

	public void setRiotId(String riotId) {
		this.riotId = riotId;
	}

	public void setPassword(String pw) {
		this.password = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getRiotId() {
		return riotId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getTagline() {
		return tagline;
	}

	public String getAdditional() {
		return additional;
	}

	public Currency getCurrency() {
		return currency;
	}

}
