# Automated Regression Testing with CI/CD

Java calculator application demonstrating automated regression testing using Jenkins CI/CD pipeline.

## Project Structure

- `src/main/java/.../Calculator.java` - Simple calculator implementation
- `src/test/java/.../CalculatorTest.java` - JUnit test cases
- `Jenkinsfile` - Jenkins pipeline definition for CI/CD

## Pipeline Stages

1. **Checkout** - Fetches latest code
2. **Build & Test** - Runs unit tests with fail-fast
3. **Package** - Creates JAR (only if tests pass)
4. **Deploy** - Simulated deployment step (main branch only)

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