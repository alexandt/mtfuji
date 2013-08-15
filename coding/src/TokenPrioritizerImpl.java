import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Implementation of TokenPrioritizer interface.  Tokens can be added in any
 * order and can be fetched in priority order.  Priority is determined by
 * token priority value and then timestamp
 */
public class TokenPrioritizerImpl implements TokenPrioritizer {

	/**
	 * Wraps a token adding a nanosecond timestamp used for ordering
	 */
	private static class TimeStampedToken {
		private long time;
		private Token token;

		private TimeStampedToken(long time, Token token) {
			this.time = time;
			this.token = token;
		}

		/**
		 * Convert to string of token id and priority
		 * @return string representation
		 */
		@Override
		public String toString() {
			return "(id:" + token.getTokenID() + ", p:" + String.valueOf(token.getPriority()) + ")";
		}
	}

	/**
	 * Compares token jpg on priority and secondly on
	 * the timestamp of TimeStampedTokens
	 */
	private static class PriorityAndTimeStampComparator implements Comparator<TimeStampedToken> {

		/**
		 * Compares tokens on priority and secondly on
		 * the natural ordering of TimeStampedTokens
		 * @param first token
		 * @param second token
		 * @return -1,0,1 (less, equal, greater)
		 */
		@Override
		public int compare(TimeStampedToken first, TimeStampedToken second) {
			if (first.token.getPriority() < second.token.getPriority()) {
				return -1;
			} else if (first.token.getPriority() > second.token.getPriority()) {
				return 1;
			} else {
				if (first.time < second.time) {
					return -1;
				} else if (first.time > second.time) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	private static int INIT_QUEUE_SIZE = 10;
	private final PriorityBlockingQueue<TimeStampedToken> queue =
			new PriorityBlockingQueue<TimeStampedToken>(INIT_QUEUE_SIZE, new PriorityAndTimeStampComparator());

	/**
	 * Thread safe function to return the highest priority token from prioritizer.
	 * If no tokens are stored in the prioritizer null is returned.
	 * @return highest priority token
	 */
	public Token nextToken() {
		TimeStampedToken timeStampedToken = queue.poll();
		return (timeStampedToken != null ) ? timeStampedToken.token : null;
	}

	/**
	 * Thread safe method to add a token to the prioritizer.  Assumes
	 * token is well-formed and validated.  Assumes token is unique.
	 * @param theToken to prioritize
	 */
	public void addToken(Token theToken) {
		queue.offer(new TimeStampedToken(System.nanoTime(), theToken));
	}

	/**
	 * Convert prioritized items into strings
	 * @return string representation
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (TimeStampedToken timeStampedToken : queue) {
			stringBuffer.append(timeStampedToken.toString()).append(",");
		}
		return stringBuffer.toString();
	}
}
