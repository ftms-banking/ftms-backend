pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    environment {
        SONAR_HOST_URL = 'http://sonarqube:9000'
        MYSQL_HOST = 'mysql'
        MYSQL_PORT = '3306'
        MYSQL_DATABASE = 'ftms_db'
        MYSQL_USER = 'ftms_user'
        MYSQL_PASSWORD = 'ftms_pass'
        APP_NAME = 'ftms-backend'
        BUILD_VERSION = "${env.BUILD_NUMBER}"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📥 Checking out code from GitHub..."
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = sh(
                        script: "git rev-parse --short HEAD",
                        returnStdout: true
                    ).trim()
                    env.GIT_BRANCH = sh(
                        script: "git rev-parse --abbrev-ref HEAD",
                        returnStdout: true
                    ).trim()
                }
                echo "Branch: ${env.GIT_BRANCH}"
                echo "Commit: ${env.GIT_COMMIT_SHORT}"
            }
        }

        stage('Environment Info') {
            steps {
                echo "🔍 Build Environment Information"
                sh '''
                    echo "Java Version:"
                    java -version
                    echo ""
                    echo "Maven Version:"
                    mvn -version
                '''
            }
        }

        stage('Clean') {
            steps {
                echo "🧹 Cleaning previous builds..."
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                echo "🔨 Compiling source code..."
                sh 'mvn compile -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                echo "🧪 Running unit tests..."
                sh 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java',
                        exclusionPattern: '**/config/**,**/dto/**,**/entity/**,**/exception/**,**/*Application.class'
                    )
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "🔍 Running SonarQube analysis..."
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                          -Dsonar.projectKey=ftms-backend \
                          -Dsonar.projectName="FTMS Backend" \
                          -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    echo "🚦 Waiting for Quality Gate result..."

                    def maxAttempts = 3
                    def attempt = 0
                    def qgPassed = false
                    def lastError = null

                    while (attempt < maxAttempts && !qgPassed) {
                        try {
                            echo "Attempt ${attempt + 1}/${maxAttempts} to check Quality Gate..."

                            timeout(time: 2, unit: 'MINUTES') {
                                def qg = waitForQualityGate()
                                echo "Quality Gate status: ${qg.status}"

                                if (qg.status == 'OK') {
                                    echo "✅ Quality Gate passed!"
                                    qgPassed = true
                                    break
                                } else if (qg.status == 'ERROR' || qg.status == 'WARN') {
                                    echo "⚠️ Quality Gate failed with status: ${qg.status}"
                                    unstable("Quality Gate status: ${qg.status}")
                                    qgPassed = true  // Mark as handled
                                    break
                                }
                            }

                        } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
                            lastError = e
                            echo "⏱️ Timeout on attempt ${attempt + 1}: ${e.message}"
                            attempt++

                            if (attempt < maxAttempts) {
                                echo "Waiting 15 seconds before retry..."
                                sleep(time: 15, unit: 'SECONDS')
                            }

                        } catch (Exception e) {
                            lastError = e
                            echo "⚠️ Error on attempt ${attempt + 1}: ${e.message}"
                            attempt++

                            if (attempt < maxAttempts) {
                                echo "Waiting 15 seconds before retry..."
                                sleep(time: 15, unit: 'SECONDS')
                            }
                        }
                    }

                    if (!qgPassed) {
                        echo "❌ Quality Gate check failed after ${maxAttempts} attempts"
                        echo "Last error: ${lastError?.message}"
                        echo "⚠️ Continuing pipeline despite Quality Gate timeout..."
                        unstable("Quality Gate could not be confirmed")
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo "📦 Packaging application..."
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo "💾 Archiving build artifacts..."
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                archiveArtifacts artifacts: '**/target/surefire-reports/**', allowEmptyArchive: true
            }
        }

        stage('Build Info') {
            steps {
                script {
                    echo """
                    ========================================
                    📊 BUILD SUMMARY
                    ========================================
                    Project:     ${APP_NAME}
                    Version:     ${BUILD_VERSION}
                    Branch:      ${GIT_BRANCH}
                    Commit:      ${GIT_COMMIT_SHORT}
                    Build:       #${BUILD_NUMBER}
                    ========================================
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
        unstable {
            echo "⚠️ Pipeline completed with warnings!"
        }
        always {
            echo "🧹 Cleaning up workspace..."
            cleanWs(
                deleteDirs: true,
                disableDeferredWipeout: true,
                patterns: [
                    [pattern: 'target/**', type: 'INCLUDE'],
                    [pattern: '.git/**', type: 'EXCLUDE']
                ]
            )
        }
    }
}
