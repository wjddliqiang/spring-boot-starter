package com.lqq.bookbar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({Java5Tester.class,Java8Tester.class,JavaAnnotationTest.class,
	com.lqq.bookbar.utils.SuiteTest.class,
	BookbarApplicationTests.class})
public class SuiteTest {}
