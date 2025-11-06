// Jenkins pipeline for automated-regression-ci-cd
// If your Jenkins agents run Windows, replace sh '...' with bat '...'
pipeline {
    agent any

    tools {
        jdk 'JDK17'
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
                always { junit '**/target/surefire-reports/*.xml' }
                unsuccessful { echo 'Tests failed — blocking deployment.' }
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
        }
        failure {
            echo '❌ Pipeline failed - Check the logs above for details.'
        }
        always {
            echo "Build #${currentBuild.number} finished with status: ${currentBuild.currentResult}"
        }
    }
}
