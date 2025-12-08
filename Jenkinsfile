pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9'
    }

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/hossam-fawzy/demoShop', branch: 'main'
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
        always {
            archiveArtifacts artifacts: 'target/**/*.png', allowEmptyArchive: true
        }
    }
}
