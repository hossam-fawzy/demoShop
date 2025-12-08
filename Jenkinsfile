pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9'
    }

    environment {
        SLACK_WEBHOOK = credentials('slack-webhook')
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
            powershell  """
            Invoke-RestMethod -Uri "$env:SLACK_WEBHOOK" -Method Post -ContentType 'application/json' -Body '{
                "text": ":large_green_circle: *BUILD SUCCESS*  `#${env.BUILD_NUMBER}`  <${env.BUILD_URL}|Open Jenkins>"
            }'
            """
        }

        failure {
            powershell  """
            Invoke-RestMethod -Uri "$env:SLACK_WEBHOOK" -Method Post -ContentType 'application/json' -Body '{
                "text": ":red_circle: *BUILD FAILED*  `#${env.BUILD_NUMBER}`  <${env.BUILD_URL}|Check logs>"
            }'
            """
        }

        always {
            archiveArtifacts artifacts: 'target/**/*.png', allowEmptyArchive: true
        }
    }
}
