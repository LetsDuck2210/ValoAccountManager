package valorant;

import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Optional;

import util.ImageUtil;

public record Account(String riotId, String password, String name, String tagline, String additional,
		Currency currency) {
	private static Map<Account, Image> rankIcons = new HashMap<>();
	public static Map<Account, Integer> rr = new HashMap<>();
	public static Map<Account, Integer> absoluteRR = new HashMap<>();
	private static Map<Account, Set<Optional<Consumer<Image>>>> fetching = new HashMap<>();


	public Account(String riotId, String pw, String name, String tagline, Currency currency) {
		this(riotId, pw, name, tagline, "", currency);
	}

	public void getRank(Consumer<Image> averageAmerican) {
		//we don't check for RR, because when rankIcon is fetched the RR should always be fetched too
		if(rankIcons.containsKey(this)) {
			averageAmerican.accept(rankIcons.get(this));
			return;
		}
		if(fetching.containsKey(this)) {
			fetching.get(this).add(Optional.of(averageAmerican));
			return;
		}
		fetching.put(this, new HashSet<>(Set.of(Optional.ofNullable(averageAmerican))));
		new Thread(() -> {
			try {
				System.out.println("fetching " + name + "#" + tagline);
				var resp = fetchAccountInfo();

				var fromStrRR = "\"ranking_in_tier\":";
				var fromIndRR = resp.indexOf(fromStrRR) + fromStrRR.length();
				var rrStr = resp.substring(
						fromIndRR,
						resp.indexOf('"', fromIndRR + 1) - 1
				);
				int rr;
				try {
					rr = Integer.parseInt(rrStr);
				} catch (NumberFormatException e) {
					System.out.println("couldn't fetch RR: " + e.getMessage());
					rr = -1;
				}

				var fromStrAbsoluteRR = "\"elo\":";
				var fromIndAbsoluteRR = resp.indexOf(fromStrAbsoluteRR) + fromStrAbsoluteRR.length();
				var absoluteRRStr = resp.substring(
						fromIndAbsoluteRR,
						resp.indexOf('"', fromIndAbsoluteRR + 1) - 1
				);
				int absoluteRR;
				try {
					absoluteRR = Integer.parseInt(absoluteRRStr);
				} catch (NumberFormatException e) {
					System.out.println("couldn't fetch absolute RR: " + e.getMessage());
					absoluteRR = -1;
				}

				var fromStr = "\"currenttierpatched\":\"";
				var fromInd = resp.indexOf(fromStr) + fromStr.length();
				var rank = resp.substring(
					fromInd,
					resp.indexOf('"', fromInd + 1)
				);
				var img = ImageUtil.loadFile("assets/rankIcons/" + rank.replaceAll("\s", "_") + ".png")
							.catchErr(e -> {})
							.sync();
				System.out.println(name + "#" + tagline + " fetched!");
				if(img == null)
					img = ImageUtil.loadFile("assets/rankIcons/empty.png")
							.catchErr(e -> {})
							.sync();

				this.rr.put(this, rr);
				this.absoluteRR.put(this, absoluteRR);
				rankIcons.put(this, img);
				for(var recv : fetching.get(this)) {
					if(recv.isPresent())
						recv.get().accept(img);
				}
			} catch (IOException | InterruptedException | URISyntaxException e) {
				System.out.println("Couldn't fetch rank icon: " + e.getMessage());
			} finally {
				fetching.remove(this);
			}
		}).start();
	}


	/* TODO: this could cause some problems: this method is mainly used to get the RR for sorting the accounts list.
	 * TODO: Due to the fact, that the api needs some time to fetch the ranks, the sorting could mess up because the
	 * TODO: value -1 will returned instead of the actual rank.
	 */
	public int getAbsoluteRR() {
		System.out.println(this.name + ": " + absoluteRR.get(this));
		if(absoluteRR.containsKey(this)) return absoluteRR.get(this);
		return -1;
	}

	private String fetchAccountInfo() throws URISyntaxException, IOException, InterruptedException {
		var nameR = name.replace(' ', '_');
		var req = HttpRequest.newBuilder()
				.uri(new URI("https://api.henrikdev.xyz/valorant/v1/mmr/eu/" + nameR + "/" + tagline)).GET()
				.build();

		var client = HttpClient.newHttpClient();
		return client.send(req, BodyHandlers.ofString()).body();
	}

	@Override
	public String toString() {
		return riotId.length() + ":" + riotId + password.length() + ":" + password + name.length() + ":" + name
				+ tagline.length() + ":" + tagline + additional.length() + ":" + additional + currency.toString();
	}

	public static Account fromString(String string) throws IllegalArgumentException {
		var split = string.split(":");
		Account acc;
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
			if (nameLength > ValoConstants.MAX_NAME_LENGTH || tagLength > ValoConstants.MAX_TAG_LENGTH)
				throw new IllegalArgumentException("name or tagline too long");

			acc = new Account(riotId, password, name, tag, additional, currency);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("wrong string format");
		}
		return acc;
	}
}