public class PrioritizationTest {
	static TokenPrioritizer prioritizer = TokenPrioritizerFactory.getInstance();
	static int ITERATIONS = 10;

	public static void main(String[] args) {
		testBasicOperation();
		testLowestToHighestPriorityInsertOrder();
		testFixedPriorityFIFOOrder();
		testTokenCounts();
	}

	public static boolean testBasicOperation() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName());

		// Some dummy token IDs
		String firstTokenID = "dummy-token-ID-1";
		String secondTokenID = "dummy-token-ID-2";
		String thirdTokenID = "dummy-token-ID-3";
		String fourthTokenID = "dummy-token-ID-4";
		String fifthTokenID = "dummy-token-ID-5";

		// Create a couple of test Tokens
		Token firstToken = new Token(firstTokenID, 3);
		Token secondToken = new Token(secondTokenID, 1);
		Token thirdToken = new Token(thirdTokenID, 2);
		Token fourthToken = new Token(fourthTokenID, 1);
		Token fifthToken = new Token(fifthTokenID, 4);


		// Add the tokens to the prioritizer
		prioritizer.addToken(firstToken);
		prioritizer.addToken(secondToken);
		prioritizer.addToken(thirdToken);
		prioritizer.addToken(fourthToken);
		prioritizer.addToken(fifthToken);

		// Confirm that we get the correct token back from the prioritizer
		Token returnedFirst = prioritizer.nextToken();
		assertTrue(returnedFirst.getTokenID().equals(secondTokenID));

		Token returnedSecond = prioritizer.nextToken();
		assertTrue(returnedSecond.getTokenID().equals(fourthTokenID));

		Token returnedThird = prioritizer.nextToken();
		assertTrue(returnedThird.getTokenID().equals(thirdTokenID));

		Token returnedFourth = prioritizer.nextToken();
		assertTrue(returnedFourth.getTokenID().equals(firstTokenID));

		Token returnedFifth = prioritizer.nextToken();
		assertTrue(returnedFifth.getTokenID().equals(fifthTokenID));

		return true;
	}

	public static boolean testLowestToHighestPriorityInsertOrder() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName());

		// Add tokens in backwards priority order
		for (int i = 0; i < ITERATIONS ; i++) {
			prioritizer.addToken(new Token(Integer.toString(ITERATIONS - i), i));
		}

		// Validate prioritizer priority token order
		for (int i = 0; i < ITERATIONS ; i++) {
			Token token = prioritizer.nextToken();

			// each token should have incrementing priority
			int tokenPriority = token.getPriority();
			assertTrue(i == tokenPriority);

			// each token should have decrementing id
			int tokenId = Integer.parseInt(token.getTokenID());
			assertTrue((ITERATIONS - i) == tokenId);
		}


		return true;
	}

	public static boolean testFixedPriorityFIFOOrder() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName());

		int priority = 1;

		// Add tokens with same priority
		for (int i = 0; i < ITERATIONS ; i++) {
			prioritizer.addToken(new Token(Integer.toString(i), priority));
		}

		// Validate prioritizer FIFO token order
		for (int i = 0; i < ITERATIONS ; i++) {
			Token token = prioritizer.nextToken();

			// each token should have same priority
			int tokenPriority = token.getPriority();
			assertTrue(priority == tokenPriority);

			// each token should have incrementing id
			int tokenId = Integer.parseInt(token.getTokenID());
			assertTrue(i == tokenId);
		}

		return true;
	}

	public static boolean testTokenCounts() {
		System.out.println("Running " + Thread.currentThread().getStackTrace()[1].getMethodName());

		// Add tokens
		for (int i = 0; i < ITERATIONS ; i++) {
			prioritizer.addToken(new Token(Integer.toString(i), i));
		}

		// Validate that correct number of tokens added
		for (int i = 0; i < ITERATIONS ; i++) {
			Token token = prioritizer.nextToken();

			// each token incrementing priority
			int tokenPriority = token.getPriority();
			assertTrue(i == tokenPriority);

			// each token should have incrementing id
			int tokenId = Integer.parseInt(token.getTokenID());
			assertTrue(i == tokenId);
		}

		// Final call should return null
		assertTrue(null == prioritizer.nextToken());

		return true;
	}

	private static void assertTrue(boolean assertion) {
		if (!assertion) {
			throw new RuntimeException("Assertion Failed");
		}
	}
}
