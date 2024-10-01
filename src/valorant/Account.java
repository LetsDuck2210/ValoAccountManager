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

import org.jsoup.Jsoup;

import java.util.Optional;

import util.FileManager;
import util.ImageUtil;
import util.Tuple;

public record Account(String riotId, String password, String name, String tagline, String additional,
		Currency currency) {
	private static Map<Account, Image> rankIcons = new HashMap<>();
	public static Map<Account, Integer> rr = new HashMap<>();
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
				var resp = fetchAccountInfo(name, tagline);
				String rankName = resp.first();
				int rr = resp.second();

				System.out.println(name + "#" + tagline + " fetched: " + rankName);
				var img = ImageUtil.loadFile("assets/rankIcons/" + rankName.replaceAll("\\s", "_") + ".png")
						.catchErr(e -> e.printStackTrace())
						.sync();
				if(img == null)
					img = ImageUtil.loadFile("assets/rankIcons/empty.png")
							.catchErr(e -> {})
							.sync();

				Account.rr.put(this, rr);
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
	public int getRR() {
		if(rr.containsKey(this)) return rr.get(this);
		return -1;
	}

	private Tuple<String, Integer> fetchAccountInfo(String name, String tagline) throws URISyntaxException, IOException, InterruptedException {
		if(name.isBlank() || tagline.isBlank())
			return Tuple.of("Unranked", -1);

		var nameR = name.replace(" ", "%20");
		var doc = Jsoup.connect("https://tracker.gg/valorant/profile/riot/" + nameR + "%23" + tagline)
			.userAgent("Mozilla/5.0 (Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0")
			.followRedirects(true)
			.headers(Map.of(
				"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/png,image/svg+xml,*/*;q=0.8",
				"Accept-Language", "en-US,en;q=0.5",
				"Connection", "keep-alive",
				"DNT", "1",
				"Priority", "u=0, i"
			))
			.get();
		var ranks = doc.select("span.stat__value");
		String rankStr = (ranks.size() > 0 ? 
				ranks.getFirst().text().trim() 
				: "Unranked");
		int level = -1;
		try {
			level = (ranks.size() > 1 ?
					Integer.parseInt(ranks.get(1).text().trim())
					: -1);
		} catch(NumberFormatException e) {
			System.out.println("Failed to parse level to int: " + ranks.get(1).text().trim());
		}

		return Tuple.of(rankStr, level);
	}

	@Override
	public String toString() {
		return riotId.length() + ":" + riotId + password.length() + ":" + password + name.length() + ":" + name
				+ tagline.length() + ":" + tagline + additional.length() + ":" + additional + currency.toString();
//		return STR."\{riotId.length()}: \{riotId}...";
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
