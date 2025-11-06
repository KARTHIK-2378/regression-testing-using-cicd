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
            steps { sh 'mvn -B -Dmaven.test.failure.ignore=false clean test' }
            post {
                always { junit '**/target/surefire-reports/*.xml' }
                unsuccessful { echo 'Tests failed — blocking deployment.' }
            }
        }

        stage('Package') {
            when { expression { currentBuild.resultIsWorseOrEqualTo('UNSTABLE') == false } }
            steps {
                sh 'mvn -B -DskipTests package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when { branch 'main' }
            steps { echo '✅ All regression tests passed. (Deployment step would go here.)' }
        }
    }

    post {
        success { echo 'Pipeline succeeded.' }
        failure { echo 'Pipeline failed.' }
    }
}
