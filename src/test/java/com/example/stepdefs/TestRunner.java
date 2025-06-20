package com.example.stepdefs;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.stepdefs",
    plugin = { "pretty", "html:target/cucumber-report.html", "json:target/cucumber.json" },
    tags = "@UI or @Backend",
    monochrome = true
)
public class TestRunner {
}
