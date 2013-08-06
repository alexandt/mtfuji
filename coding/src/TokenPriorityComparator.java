import java.util.Comparator;

/**
 * A class to compare tokens based first on upon priority tokens
 * and secondly on insert order.
 *
 * Assume well formed and validated inputs.  Assume no duplicate
 * tokens will be added.
 */
public class TokenPriorityComparator implements Comparator<Token> {

	@Override
	public int compare(Token first, Token second) {

		// NOTE: skipping null input validation (see requirements)

		// ascending order based upon priority
		int score = first.getPriority() - second.getPriority();

		if (score == 0) {
			// keep FIFO order (i.e. store in order received)
			score++;
		}

		return score;
	}
}
