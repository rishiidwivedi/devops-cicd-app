
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(
            numToKeepStr: '10',
            artifactNumToKeepStr: '5'
        ))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'git rev-parse --short HEAD'
            }
        }

        stage('Verify Build Environment') {
            steps {
                sh '''
                    set -eu
                    java -version
                    mvn -version
                    git --version
                '''
            }
        }

        stage('Compile Application') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn -B -Dtest=GreetingServiceTest test'
            }
        }

        stage('Functional API Tests') {
            steps {
                sh 'mvn -B -Dtest=GreetingApiTest test'
            }
        }

        stage('Package Application') {
            steps {
                sh 'mvn -B -DskipTests package'
                sh 'test -s target/devops-cicd-app.jar'
            }
        }
    }

    post {
        always {
            junit(
                testResults: 'target/surefire-reports/TEST-*.xml',
                allowEmptyResults: true
            )
        }

        success {
            archiveArtifacts(
                artifacts: 'target/devops-cicd-app.jar',
                fingerprint: true
            )

            echo 'BUILD SUCCESSFUL: application packaged and tests executed.'
        }

        failure {
            echo 'BUILD FAILED: inspect the stage logs and test reports.'
        }

        cleanup {
            echo 'Pipeline execution finished.'
        }
    }
}
