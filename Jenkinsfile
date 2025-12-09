pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/hossam-fawzy/demoShop', branch: 'master'
            }
        }

        stage('Build & Test') {
            steps {
                bat "mvn clean test"
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {

        success {
            withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
                powershell """
                \$uri = '${SLACK_WEBHOOK}'
                Invoke-RestMethod -Uri \$uri -Method Post -ContentType 'application/json' -Body '{ "text": ":large_green_circle: BUILD SUCCESS #${env.BUILD_NUMBER} <${env.BUILD_URL}|Open Jenkins>" }'
                """
            }
        }

        failure {
            withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
                powershell """
                \$uri = '${SLACK_WEBHOOK}'
                Invoke-RestMethod -Uri \$uri -Method Post -ContentType 'application/json' -Body '{ "text": ":red_circle: BUILD FAILED #${env.BUILD_NUMBER} <${env.BUILD_URL}|Check logs>" }'
                """
            }
        }

        always {
            archiveArtifacts artifacts: 'target/**/*.png', allowEmptyArchive: true
        }
    }
}
