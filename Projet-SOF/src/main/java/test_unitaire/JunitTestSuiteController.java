package test_unitaire;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ App2FormationControllerTest.class,
				FormationControllerTest.class, 
				LoginControlerSingletonTest.class })
public class JunitTestSuiteController {
}
