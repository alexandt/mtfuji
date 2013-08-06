import java.util.TreeSet;
import java.util.Comparator;

public class TokenPrioritizerImpl implements TokenPrioritizer {

	private static int INITIAL_QUEUE_SIZE = 10;
	private static Comparator<Token> comparator = new TokenPriorityComparator();
	private static TreeSet<Token> treeSet = new TreeSet<Token>(comparator);

	public Token nextToken() {
		return treeSet.pollFirst();
	}

	public void addToken(Token theToken) {
		treeSet.add(theToken);
	}
}
