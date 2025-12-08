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
            slackSend(
                webhookUrl: SLACK_WEBHOOK,
                message: """
:large_green_circle: *BUILD SUCCESS*
*Project:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}
*Branch:* ${env.GIT_BRANCH}
*Report:* ${env.BUILD_URL}allure/
""")
        }

        failure {
            slackSend(
                webhookUrl: SLACK_WEBHOOK,
                message: """
:red_circle: *BUILD FAILED*
*Project:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}
*Branch:* ${env.GIT_BRANCH}
*Logs:* ${env.BUILD_URL}
""")
        }

        always {
            archiveArtifacts artifacts: 'target/**/*.png', allowEmptyArchive: true
        }
    }
}
