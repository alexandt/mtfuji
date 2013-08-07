import java.util.Comparator;
import java.util.PriorityQueue;

public class TokenPrioritizerImpl implements TokenPrioritizer {

	/**
	 * The TimeStampedToken class wraps a token adding a
	 * nanosecond timestamp used for natural ordering
	 */
	private static class TimeStampedToken implements Comparable<TimeStampedToken> {
		private long time;
		private Token token;

		private TimeStampedToken(long time, Token token) {
			this.time = time;
			this.token = token;
		}

		/**
		 * Compare two time stamped tokens.  Tokens are first compared
		 * by priority and secondly by a nanosecond timestamp
		 * @param tokenWrapper
		 * @return
		 */
		@Override
		public int compareTo(TimeStampedToken tokenWrapper) {
			if (this.time < tokenWrapper.time) {
				return -1;
			} else if (this.time > tokenWrapper.time) {
				return 1;
			} else {
				throw new RuntimeException("Unable to compare non unique time stamped tokens");
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || obj.getClass() != this.getClass()) {
				return false;
			}

			TimeStampedToken timeStampedToken = (TimeStampedToken) obj;
			return this.time == timeStampedToken.time;
		}

		/**
		 * Convert to string of token id and priority
		 * @return
		 */
		@Override
		public String toString() {
			return "(id:" + token.getTokenID() + ", p:" + String.valueOf(token.getPriority()) + ")";
		}
	}

	public static class PriorityComparator implements Comparator<TimeStampedToken> {

		@Override
		public int compare(TimeStampedToken first, TimeStampedToken second) {
			if (first.token.getPriority() < second.token.getPriority()) {
				return -1;
			} else if (first.token.getPriority() > second.token.getPriority()) {
				return 1;
			} else {
				return first.compareTo(second);
			}
		}
	}

	private final PriorityComparator priorityComparator = new PriorityComparator();
	private final PriorityQueue<TimeStampedToken> queue = new PriorityQueue<TimeStampedToken>(10, priorityComparator);

	/**
	 * Synchronized function to return the next available token from prioritizer.
	 * If no tokens are available in prioritizer null is returned.
	 * @return
	 */
	public synchronized Token nextToken() {
		TimeStampedToken timeStampedToken = queue.poll();
		return (timeStampedToken != null ) ? timeStampedToken.token : null;
	}

	/**
	 * Synchronized method to add a token to the prioritizer.  Assumes
	 * token is well-formed and validated.  Assumes token is unique.
	 * @param theToken
	 */
	public synchronized void addToken(Token theToken) {
		queue.offer(new TimeStampedToken(System.nanoTime(), theToken));
	}

	/**
	 * Convert prioritized items into strings
	 * @return
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
