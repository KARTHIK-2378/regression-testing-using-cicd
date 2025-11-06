# Automated Regression Testing with CI/CD

[![Build Status](http://localhost:8080/buildStatus/icon?job=regression-testing-using-cicd)](http://localhost:8080/job/regression-testing-using-cicd/)
[![Coverage](http://localhost:8080/job/regression-testing-using-cicd/lastBuild/jacoco/badge/icon)](http://localhost:8080/job/regression-testing-using-cicd/lastBuild/jacoco/)

Java calculator application demonstrating automated regression testing using Jenkins CI/CD pipeline with code coverage and static analysis.

## Features

- ✅ Automated regression testing with JUnit 5
- 📊 Code coverage reporting with JaCoCo
- 🔍 Static code analysis with SpotBugs
- 🚀 Continuous deployment to local directory
- 📧 Email notifications (optional)
- 🔄 GitHub webhook integration

## Project Structure

- `src/main/java/.../Calculator.java` - Simple calculator implementation
- `src/test/java/.../CalculatorTest.java` - JUnit test cases
- `Jenkinsfile` - Jenkins pipeline definition for CI/CD
- `pom.xml` - Maven build configuration with plugins

## Pipeline Stages

1. **Checkout** - Fetches latest code from GitHub
2. **Build & Test** - Runs unit tests with fail-fast & generates coverage
3. **Code Quality Analysis** - SpotBugs static analysis
4. **Package** - Creates JAR (only if tests pass)
5. **Deploy** - Copies artifact to C:\deployments (main branch only)

## Setup Requirements

- Java 17 or later
- Maven 3.x
- Jenkins (with Java/Maven tools configured)

## Local Development

```bash
# Run tests
mvn test

# Package (creates JAR in target/)
mvn package
```

## Jenkins Setup

1. Install Jenkins LTS
2. Configure Tools:
   - JDK17 installation
   - Maven3 installation
3. Create Pipeline:
   - New Item → Pipeline
   - Configure Git SCM
   - Set branch to `main`
   - Set script path to `Jenkinsfile`

## Test Failure Behavior

The pipeline is configured to block deployment if any regression tests fail. This ensures code quality and prevents broken changes from reaching production.