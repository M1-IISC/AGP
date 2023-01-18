package jUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the global integration test suite of unit tests.
 * It includes these test cases : {@link BusinessLogicSTS}, {@link BDeSTS}.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessLogicSTS.class, BDeSTS.class})
public class IntegrationTestSuite {
}
