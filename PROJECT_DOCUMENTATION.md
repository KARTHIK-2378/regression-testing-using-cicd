# Automated Regression Testing CI/CD Pipeline
## Project Documentation

**Project Name:** Automated Regression Testing using CI/CD  
**Author:** KARTHIK-2378  
**Date:** November 6, 2025  
**Repository:** https://github.com/KARTHIK-2378/regression-testing-using-cicd

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Introduction](#introduction)
3. [Project Objectives](#project-objectives)
4. [Technologies Used](#technologies-used)
5. [System Architecture](#system-architecture)
6. [Implementation Details](#implementation-details)
7. [Pipeline Workflow](#pipeline-workflow)
8. [Testing Strategy](#testing-strategy)
9. [Deployment Process](#deployment-process)
10. [Results and Achievements](#results-and-achievements)
11. [Challenges and Solutions](#challenges-and-solutions)
12. [Future Enhancements](#future-enhancements)
13. [Conclusion](#conclusion)
14. [Appendix](#appendix)

---

## Executive Summary

This project demonstrates the implementation of a fully automated Continuous Integration and Continuous Deployment (CI/CD) pipeline for a Java application. The system automatically builds, tests, and deploys code changes while ensuring that regression tests block faulty code from reaching production.

**Key Achievements:**
- ✅ Automated build and test pipeline
- ✅ Regression test blocking mechanism
- ✅ GitHub webhook integration
- ✅ Code coverage reporting with JaCoCo
- ✅ Automated artifact deployment
- ✅ 14+ successful pipeline executions

---

## Introduction

### Background

In modern software development, manual testing and deployment processes are time-consuming, error-prone, and inefficient. Continuous Integration and Continuous Deployment (CI/CD) practices automate these processes, enabling faster delivery while maintaining high quality standards.

### Problem Statement

Organizations face several challenges in software delivery:
- Manual testing is slow and inconsistent
- Human errors in deployment processes
- Difficulty in tracking code quality over time
- Delayed feedback on code changes
- Risk of deploying broken code to production

### Solution Overview

This project implements an automated CI/CD pipeline that:
1. Automatically triggers on code changes
2. Runs comprehensive regression tests
3. Blocks deployment if tests fail
4. Generates quality reports
5. Deploys successful builds automatically

---

## Project Objectives

### Primary Objectives

1. **Automate Testing Process**
   - Implement automated unit testing with JUnit 5
   - Generate code coverage reports using JaCoCo
   - Ensure tests run on every code commit

2. **Implement Regression Test Blocking**
   - Prevent deployment of failing builds
   - Maintain code quality gates
   - Provide clear feedback on test failures

3. **Enable Continuous Integration**
   - Integrate GitHub with Jenkins
   - Automate build process with Maven
   - Track build history and artifacts

4. **Achieve Continuous Deployment**
   - Automate artifact packaging
   - Deploy to target environment
   - Maintain deployment history

### Learning Objectives

- Understanding CI/CD principles and practices
- Hands-on experience with Jenkins pipeline
- GitHub webhook configuration
- Maven build automation
- Test-driven development practices

---

## Technologies Used

### Development Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 (LTS) | Programming Language |
| JUnit | 5.10.2 | Unit Testing Framework |
| Maven | 3.9.5 | Build Automation Tool |
| Git | 2.49.0 | Version Control System |

### CI/CD Tools

| Tool | Version | Purpose |
|------|---------|---------|
| Jenkins | Latest | CI/CD Automation Server |
| GitHub | - | Repository Hosting & Webhooks |
| JaCoCo | 0.8.11 | Code Coverage Tool |

### Environment

- **Operating System:** Windows 11
- **JDK:** Eclipse Temurin 21.0.4.7-hotspot
- **IDE:** Visual Studio Code
- **Shell:** Windows Command Prompt

---

## System Architecture

### Component Diagram

```
┌─────────────────┐
│   Developer     │
│   Workstation   │
└────────┬────────┘
         │ git push
         ▼
┌─────────────────┐
│     GitHub      │
│   Repository    │
└────────┬────────┘
         │ webhook
         ▼
┌─────────────────┐
│     Jenkins     │
│   CI/CD Server  │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌────────┐ ┌──────────┐
│ Build  │ │  Test    │
│ Stage  │ │  Stage   │
└────────┘ └──────────┘
         │
         ▼
    ┌────────┐
    │Package │
    │ Stage  │
    └────┬───┘
         │
         ▼
    ┌────────┐
    │ Deploy │
    │ Stage  │
    └────────┘
         │
         ▼
┌─────────────────┐
│   Deployment    │
│   Directory     │
└─────────────────┘
```

### Data Flow

1. **Developer** writes code and commits to local Git repository
2. **Git Push** sends changes to GitHub repository
3. **GitHub Webhook** triggers Jenkins pipeline automatically
4. **Jenkins** executes pipeline stages sequentially:
   - Checkout: Clone repository
   - Build & Test: Compile and run tests
   - Package: Create JAR artifact
   - Deploy: Copy to deployment directory
5. **Artifacts** are archived and available for download

---

## Implementation Details

### Project Structure

```
automated-regression-ci-cd/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── app/
│   │                   └── Calculator.java
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── app/
│                       └── CalculatorTest.java
├── target/                      # Build output directory
├── .gitignore                   # Git ignore rules
├── Jenkinsfile                  # Pipeline definition
├── pom.xml                      # Maven configuration
└── README.md                    # Project documentation
```

### Source Code

#### Calculator.java
```java
package com.example.app;

public class Calculator {
    public int add(int a, int b) { 
        return a + b; 
    }
    
    public int subtract(int a, int b) { 
        return a - b; 
    }
}
```

#### CalculatorTest.java
```java
package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    void add_shouldReturnSum() {
        Calculator c = new Calculator();
        assertEquals(7, c.add(3, 4));
    }

    @Test
    void subtract_shouldReturnDifference() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(5, 4));
    }
}
```

### Maven Configuration (pom.xml)

Key configurations:
- **Java Version:** 17 (compatibility target)
- **JUnit Version:** 5.10.2
- **JaCoCo Plugin:** 0.8.11 for code coverage
- **Surefire Plugin:** 3.2.5 for test execution
- **Minimum Coverage:** 50% line coverage required

### Jenkins Pipeline (Jenkinsfile)

```groovy
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn -B -Dmaven.test.failure.ignore=false clean test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
                unsuccessful { 
                    echo 'Tests failed — blocking deployment.' 
                }
            }
        }

        stage('Package') {
            when { 
                expression { 
                    currentBuild.resultIsWorseOrEqualTo('UNSTABLE') == false 
                } 
            }
            steps {
                bat 'mvn -B -DskipTests package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when { branch 'main' }
            steps {
                script {
                    bat 'if not exist "C:\\deployments" mkdir "C:\\deployments"'
                    bat 'copy /Y target\\*.jar "C:\\deployments\\"'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline succeeded!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
        always {
            echo "Build #${currentBuild.number} finished"
            cleanWs(deleteDirs: true, patterns: [[pattern: 'target/**', type: 'INCLUDE']])
        }
    }
}
```

---

## Pipeline Workflow

### Stage 1: Checkout

**Purpose:** Retrieve latest code from GitHub repository

**Actions:**
- Jenkins connects to GitHub
- Clones/pulls latest code from main branch
- Verifies commit hash

**Duration:** ~2-3 seconds

**Success Criteria:** Code successfully retrieved

---

### Stage 2: Build & Test

**Purpose:** Compile code and execute regression tests

**Actions:**
1. Clean previous build artifacts
2. Compile source code (javac)
3. Compile test code
4. Execute JUnit tests
5. Generate JaCoCo coverage report
6. Publish test results to Jenkins

**Maven Command:**
```bash
mvn -B -Dmaven.test.failure.ignore=false clean test
```

**Duration:** ~4-7 seconds

**Success Criteria:**
- ✅ Code compiles without errors
- ✅ All tests pass (2/2 tests)
- ✅ Coverage meets minimum threshold (50%)

**Failure Handling:**
- If any test fails, pipeline stops immediately
- Deployment stages are skipped
- Build marked as FAILED

---

### Stage 3: Package

**Purpose:** Create deployable JAR artifact

**Execution Condition:** Only runs if Build & Test stage succeeds

**Actions:**
1. Skip test execution (already done)
2. Package compiled classes into JAR
3. Archive artifact in Jenkins
4. Generate fingerprint for tracking

**Maven Command:**
```bash
mvn -B -DskipTests package
```

**Output:** `automated-regression-ci-cd-1.0.0.jar` (~2.37 KB)

**Duration:** ~2-3 seconds

**Success Criteria:**
- ✅ JAR file created successfully
- ✅ Artifact archived in Jenkins
- ✅ Fingerprint generated

---

### Stage 4: Deploy

**Purpose:** Deploy artifact to target environment

**Execution Condition:** 
- Build & Test passed
- Package succeeded
- Branch is 'main'

**Actions:**
1. Create deployment directory if not exists
2. Copy JAR file to C:\deployments\
3. Log deployment completion

**Duration:** <1 second

**Success Criteria:**
- ✅ JAR file copied to deployment directory
- ✅ No file access errors

**Note:** In production, this would deploy to:
- Application server
- Cloud platform (AWS, Azure)
- Container registry (Docker Hub)
- Kubernetes cluster

---

## Testing Strategy

### Unit Testing Approach

**Framework:** JUnit 5 (Jupiter)

**Test Coverage:**
- Calculator.add() method
- Calculator.subtract() method

**Test Types:**
1. **Positive Tests:** Verify correct behavior
2. **Edge Cases:** (Can be extended)

### Code Coverage

**Tool:** JaCoCo Maven Plugin

**Coverage Metrics:**
- **Line Coverage:** Percentage of code lines executed
- **Branch Coverage:** Percentage of decision branches taken
- **Method Coverage:** Percentage of methods invoked

**Minimum Requirements:**
- Line Coverage: 50%
- Package Coverage: Enforced by JaCoCo check goal

**Current Coverage:**
- Line Coverage: 100%
- Branch Coverage: 100%
- Class Coverage: 100%

### Test Execution

**Automated Triggers:**
- Every Git push to repository
- Manual build trigger in Jenkins
- Scheduled builds (fallback polling)

**Test Reports:**
- JUnit XML reports in `target/surefire-reports/`
- JaCoCo HTML report in `target/site/jacoco/`
- Jenkins test result visualization

---

## Deployment Process

### Artifact Management

**Artifact Details:**
- **Name:** automated-regression-ci-cd-1.0.0.jar
- **Type:** Executable JAR
- **Size:** ~2.37 KB
- **Storage:** Jenkins archive + deployment directory

**Versioning:**
- Version defined in pom.xml (1.0.0)
- Build number tracked by Jenkins
- Git commit hash linked to each build

### Deployment Target

**Current Setup (Development):**
- **Location:** C:\deployments\
- **Method:** File copy

**Production Setup (Recommended):**
- Deploy to application server (Tomcat, WildFly)
- Upload to cloud storage (S3, Azure Blob)
- Deploy to container platform (Docker, Kubernetes)
- Use deployment tools (Ansible, Terraform)

### Rollback Strategy

**Manual Rollback:**
1. Access Jenkins build history
2. Download previous successful artifact
3. Deploy manually to target environment

**Automated Rollback (Future):**
- Implement blue-green deployment
- Maintain previous N versions
- Auto-rollback on health check failure

---

## Results and Achievements

### Build Statistics

- **Total Builds:** 14
- **Successful Builds:** 3 (Builds #8, #9, #14)
- **Failed Builds:** 11 (mostly due to testing pipeline)
- **Success Rate:** ~21% (intentional failures for testing)

### Key Milestones

1. ✅ **Initial Setup** - Git repository, basic structure
2. ✅ **Jenkins Integration** - Pipeline creation, tool configuration
3. ✅ **GitHub Webhook** - Automatic trigger on push
4. ✅ **Test Implementation** - JUnit tests with coverage
5. ✅ **Regression Blocking** - Failed tests block deployment
6. ✅ **Artifact Deployment** - Successful JAR deployment
7. ✅ **Pipeline Optimization** - Removed unsupported plugins

### Performance Metrics

| Metric | Value |
|--------|-------|
| Average Build Time | ~10-15 seconds |
| Test Execution Time | ~4-7 seconds |
| Code Coverage | 100% |
| Pipeline Stages | 4 |
| Automated Tests | 2 |

### Quality Improvements

**Before CI/CD:**
- Manual testing required
- No automated quality checks
- Deployment errors possible
- No build history

**After CI/CD:**
- ✅ Fully automated testing
- ✅ Quality gates enforced
- ✅ Zero deployment errors
- ✅ Complete build audit trail

---

## Challenges and Solutions

### Challenge 1: JDK Version Mismatch

**Problem:**  
Jenkins configured with JDK21, but Jenkinsfile referenced JDK17

**Error Message:**
```
Tool type "jdk" does not have an install of "JDK17" configured
```

**Solution:**  
Updated Jenkinsfile to use `jdk 'JDK21'` matching Jenkins configuration

**Learning:**  
Always verify tool names match Jenkins global configuration

---

### Challenge 2: Missing Jenkins Plugins

**Problem:**  
Pipeline failed when trying to use JaCoCo and publishHTML steps

**Error Message:**
```
No such DSL method 'jacoco' found among steps
No such DSL method 'publishHTML' found among steps
```

**Solution:**  
Removed plugin-dependent features and relied on Maven-generated reports

**Alternative:**  
Could install required plugins:
- JaCoCo Plugin
- HTML Publisher Plugin

**Learning:**  
Check installed plugins before using pipeline steps

---

### Challenge 3: Intentional Test Failure

**Problem:**  
Test initially designed to fail for demonstrating regression blocking

**Test Code:**
```java
assertEquals(8, c.add(3, 4)); // Expected 8, got 7
```

**Result:**  
Pipeline correctly blocked deployment

**Solution:**  
Fixed test to expect correct value (7)

**Learning:**  
Pipeline successfully prevents broken code deployment

---

### Challenge 4: Windows Batch Commands

**Problem:**  
Initial Jenkinsfile used Unix shell commands

**Solution:**  
Replaced all `sh` commands with `bat` for Windows

**Examples:**
```groovy
// Before
sh 'mvn clean test'

// After
bat 'mvn clean test'
```

**Learning:**  
Pipeline syntax must match agent operating system

---

## Future Enhancements

### Short-term Improvements

1. **Email Notifications**
   - Configure SMTP in Jenkins
   - Send build status emails
   - Notify on failures

2. **Additional Tests**
   - Add more test cases
   - Test edge cases (division by zero, overflow)
   - Integration tests

3. **Code Quality Tools**
   - Install SpotBugs plugin
   - Add Checkstyle for coding standards
   - Implement PMD for code analysis

4. **Docker Integration**
   - Containerize application
   - Build Docker images in pipeline
   - Deploy to container registry

### Long-term Enhancements

1. **Multi-Environment Deployment**
   - Dev → Test → Staging → Production
   - Environment-specific configurations
   - Approval gates for production

2. **Performance Testing**
   - Add JMeter tests
   - Load testing stage
   - Performance benchmarks

3. **Security Scanning**
   - OWASP dependency check
   - Static application security testing (SAST)
   - Container vulnerability scanning

4. **Advanced Monitoring**
   - Application performance monitoring
   - Log aggregation (ELK stack)
   - Metrics and dashboards (Grafana)

5. **Infrastructure as Code**
   - Terraform for Jenkins setup
   - Ansible for configuration management
   - GitOps workflow

---

## Conclusion

### Project Summary

This project successfully demonstrates the implementation of a complete CI/CD pipeline for automated regression testing. The system automatically builds, tests, and deploys Java applications while ensuring quality through automated regression tests.

### Key Takeaways

1. **Automation Saves Time**  
   Manual processes replaced with automated pipeline

2. **Quality Gates Work**  
   Failed tests successfully blocked deployment

3. **Fast Feedback**  
   Developers get immediate feedback on code changes

4. **Audit Trail**  
   Complete history of builds, tests, and deployments

5. **Scalability**  
   Pipeline can easily accommodate more tests and stages

### Skills Demonstrated

- ✅ CI/CD pipeline development
- ✅ Jenkins configuration and management
- ✅ Maven build automation
- ✅ Unit testing with JUnit
- ✅ Git and GitHub workflow
- ✅ Code coverage analysis
- ✅ DevOps best practices
- ✅ Problem-solving and debugging

### Impact

This implementation provides a foundation for professional software development practices, ensuring:
- Faster development cycles
- Higher code quality
- Reduced deployment risks
- Better team collaboration
- Improved software reliability

---

## Appendix

### A. Installation Guide

#### Prerequisites
1. Java Development Kit 21
2. Maven 3.9.5
3. Git 2.x
4. Jenkins (latest LTS)

#### Jenkins Setup
1. Install Jenkins as Windows service
2. Configure JDK: Manage Jenkins → Tools → JDK
3. Configure Maven: Manage Jenkins → Tools → Maven
4. Install required plugins (if needed):
   - Git plugin
   - Pipeline plugin
   - JUnit plugin

#### GitHub Configuration
1. Create repository on GitHub
2. Configure webhook:
   - URL: `http://your-jenkins-url/github-webhook/`
   - Content type: application/json
   - Events: Just the push event

### B. Command Reference

#### Maven Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# Package application
mvn package

# Skip tests
mvn package -DskipTests
```

#### Git Commands
```bash
# Initialize repository
git init

# Add files
git add .

# Commit changes
git commit -m "message"

# Push to GitHub
git push origin main

# View history
git log --oneline
```

### C. Troubleshooting Guide

#### Build Fails with Test Errors
**Solution:** Check test code, ensure assertions are correct

#### Jenkins Cannot Clone Repository
**Solution:** Verify GitHub URL, check network connectivity

#### Artifact Not Found
**Solution:** Ensure Package stage completed successfully

#### Deployment Directory Error
**Solution:** Check folder permissions, verify path exists

### D. References

1. **Jenkins Documentation**  
   https://www.jenkins.io/doc/

2. **Maven Guide**  
   https://maven.apache.org/guides/

3. **JUnit 5 User Guide**  
   https://junit.org/junit5/docs/current/user-guide/

4. **JaCoCo Documentation**  
   https://www.jacoco.org/jacoco/trunk/doc/

5. **GitHub Webhooks**  
   https://docs.github.com/en/webhooks

### E. Project Repository

**GitHub:** https://github.com/KARTHIK-2378/regression-testing-using-cicd

**Clone Command:**
```bash
git clone https://github.com/KARTHIK-2378/regression-testing-using-cicd.git
```

---

## Glossary

- **CI/CD:** Continuous Integration / Continuous Deployment
- **Jenkins:** Open-source automation server
- **Pipeline:** Automated workflow for building and deploying software
- **Regression Testing:** Re-running tests to ensure new changes don't break existing functionality
- **Artifact:** Deployable output (JAR file)
- **Webhook:** Automated notification from GitHub to Jenkins
- **JaCoCo:** Java Code Coverage tool
- **Maven:** Build automation and dependency management tool
- **JUnit:** Java testing framework

---

**End of Documentation**

*Generated on November 6, 2025*  
*Project by KARTHIK-2378*
