pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9'
    }

    environment {
        JACOCO_EXEC_FILE = "${WORKSPACE}/target/jacoco.exec"
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

        stage('JaCoCo Coverage Report') {
            steps {
                echo "📈 Generating JaCoCo Coverage Report..."
                bat "mvn jacoco:report"
            }
            post {
                always {
                    echo "📊 Publishing JaCoCo Report..."

                    jacoco(
                        execPattern: '**/target/*.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java',
                        exclusionPattern: '**/test/**,**/BaseTest.class',
                        changeBuildStatus: false,
                        minimumLineCoverage: '50',
                        minimumBranchCoverage: '40',
                        minimumMethodCoverage: '50',
                        minimumClassCoverage: '60',
                        maximumLineCoverage: '90',
                        maximumBranchCoverage: '80',
                        maximumMethodCoverage: '90',
                        maximumClassCoverage: '90'
                    )
                }
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
            // Archive screenshots or other PNGs
            archiveArtifacts artifacts: 'target/**/*.png', allowEmptyArchive: true

            // Archive JaCoCo artifacts
            archiveArtifacts artifacts: 'target/*.exec', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true

            // Publish JaCoCo HTML report to Jenkins
            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'JaCoCo Coverage Report'
            ])
        }

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
    }
}
