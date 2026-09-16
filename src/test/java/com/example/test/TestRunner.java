package com.example.test;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@Suite
@SuiteDisplayName("Complete System Test Suite")
@SelectPackages({
    "com.example.test.Service",
    "com.example.test.Controller",
    "com.example.test"
})
public class TestRunner {
    // This class serves as a test suite runner
    // All test classes in the specified packages will be executed
}
