import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class TokenPrioritizerImpl implements TokenPrioritizer {

	/**
	 * The TimeStampedToken class wraps a token adding a time stamp used for sorting
	 */
	private static class TimeStampedToken implements Comparable<TimeStampedToken> {
		private long time;
		private Token token;

		private TimeStampedToken(long time, Token token) {
			this.time = time;
			this.token = token;
		}

		@Override
		public int compareTo(TimeStampedToken tokenWrapper) {
			if (this.token.getPriority() < tokenWrapper.token.getPriority()) {
				return -1;
			} else if (this.token.getPriority() > tokenWrapper.token.getPriority()) {
				return 1;
			} else {
				if (this.time < tokenWrapper.time) {
					return -1;
				} else if (this.time > tokenWrapper.time) {
					return 1;
				} else {
					throw new RuntimeException("Unable to process non unique tokens");
				}
			}
		}

		/*
		 * print token id
		 */
		@Override
		public String toString() {

			return "(id:" + token.getTokenID() + ", p:" + String.valueOf(token.getPriority()) + ")";
		}
	}

	private final PriorityQueue<TimeStampedToken> queue = new PriorityQueue<TimeStampedToken>();

	public synchronized Token nextToken() {
		TimeStampedToken timeStampedToken = queue.poll();
		return (timeStampedToken != null ) ? timeStampedToken.token : null;
	}

	public synchronized void addToken(Token theToken) {
		queue.offer(new TimeStampedToken(System.nanoTime(), theToken));
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (TimeStampedToken timeStampedToken : queue) {
			stringBuffer.append(timeStampedToken.toString()).append(",");
		}
		return stringBuffer.toString();
	}
}
