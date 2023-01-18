package jUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the global business logic test suite of unit tests.
 * It includes these test cases : {@link BDeTestCase}.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BDeTestCase.class})
public class BDeSTS {
}