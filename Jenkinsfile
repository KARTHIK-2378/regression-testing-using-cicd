// Jenkins pipeline for automated-regression-ci-cd
// If your Jenkins agents run Windows, replace sh '...' with bat '...'
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
        // Webhook will trigger; this polls as a fallback
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build & Test') {
            steps { bat 'mvn -B -Dmaven.test.failure.ignore=false clean test' }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    // Publish JaCoCo coverage report
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
                unsuccessful { echo 'Tests failed — blocking deployment.' }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                script {
                    echo 'Running static code analysis with SpotBugs...'
                    bat 'mvn -B spotbugs:check'
                }
            }
            post {
                always {
                    // Publish SpotBugs results
                    recordIssues(
                        enabledForFailure: true,
                        tools: [spotBugs(pattern: '**/target/spotbugsXml.xml')]
                    )
                }
            }
        }

        stage('Package') {
            when { expression { currentBuild.resultIsWorseOrEqualTo('UNSTABLE') == false } }
            steps {
                bat 'mvn -B -DskipTests package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when { branch 'main' }
            steps {
                script {
                    echo '✅ All regression tests passed. Deploying application...'
                    // Create deployment directory if it doesn't exist
                    bat 'if not exist "C:\\deployments" mkdir "C:\\deployments"'
                    // Copy the jar file to deployment directory
                    bat 'copy /Y target\\*.jar "C:\\deployments\\"'
                    echo 'Deployment completed to C:\\deployments'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline succeeded - Build, Test, Package, and Deploy completed successfully!'
            // Uncomment and configure email after setting up SMTP in Jenkins
            // emailext(
            //     subject: "✅ SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            //     body: """<p>Pipeline completed successfully!</p>
            //              <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
            //     recipientProviders: [developers(), requestor()],
            //     mimeType: 'text/html'
            // )
        }
        failure {
            echo '❌ Pipeline failed - Check the logs above for details.'
            // Uncomment and configure email after setting up SMTP in Jenkins
            // emailext(
            //     subject: "❌ FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            //     body: """<p>Pipeline failed!</p>
            //              <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
            //     recipientProviders: [developers(), requestor()],
            //     mimeType: 'text/html'
            // )
        }
        always {
            echo "Build #${currentBuild.number} finished with status: ${currentBuild.currentResult}"
            // Clean up workspace to save space
            cleanWs(deleteDirs: true, patterns: [[pattern: 'target/**', type: 'INCLUDE']])
        }
    }
}
