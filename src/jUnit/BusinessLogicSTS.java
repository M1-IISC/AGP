package jUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is the global business logic test suite of unit tests.
 * It includes these test cases : {@link BusinessLogicTestCase}, {@link SpringIntegrationTestCase}, {@link StayGenerationCase}.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessLogicTestCase.class, SpringIntegrationTestCase.class, StayGenerationCase.class})
public class BusinessLogicSTS {
}
