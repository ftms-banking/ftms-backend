pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    environment {
        // SonarQube
        SONAR_HOST_URL = 'http://sonarqube:9000'

        // MySQL
        MYSQL_HOST = 'mysql'
        MYSQL_PORT = '3306'
        MYSQL_DATABASE = 'ftms_db'
        MYSQL_USER = 'ftms_user'
        MYSQL_PASSWORD = 'ftms_pass'

        // Application
        APP_NAME = 'ftms-backend'
        BUILD_VERSION = "${env.BUILD_NUMBER}"
    }

    options {
        // Keep last 10 builds
        buildDiscarder(logRotator(numToKeepStr: '10'))

        // Timeout after 30 minutes
        timeout(time: 30, unit: 'MINUTES')

        // Timestamps in console
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📥 Checking out code from GitHub..."
                checkout scm

                script {
                    // Get Git commit info
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
                    echo ""
                    echo "Docker Version:"
                    docker --version
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
                    // Publish test results
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

                    // Publish JaCoCo coverage report
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java',
                        exclusionPattern: '**/config/**,**/dto/**,**/entity/**,**/exception/**,**/*Application.class'
                    )
                }
            }
        }

        stage('Code Quality - Checkstyle') {
            steps {
                echo "📝 Running Checkstyle..."
                sh 'mvn checkstyle:check || true'
            }
            post {
                always {
                    recordIssues(
                        enabledForFailure: true,
                        tool: checkStyle(pattern: '**/target/checkstyle-result.xml')
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
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo "🚦 Waiting for Quality Gate result..."
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            echo "⚠️ Quality Gate failed: ${qg.status}"
                            // Don't fail build, just warn
                            unstable("Quality Gate failed")
                        } else {
                            echo "✅ Quality Gate passed!"
                        }
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

                // Archive test reports
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
            // Optional: Send notification
        }

        failure {
            echo "❌ Pipeline failed!"
            // Optional: Send notification
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
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">
    <property name="charset" value="UTF-8"/>
    <property name="severity" value="warning"/>
    <property name="fileExtensions" value="java"/>

    <!-- Excludes all 'module-info.java' files -->
    <module name="BeforeExecutionExclusionFileFilter">
        <property name="fileNamePattern" value="module\-info\.java$"/>
    </module>

    <module name="TreeWalker">
        <!-- Naming Conventions -->
        <module name="TypeName"/>
        <module name="MethodName"/>
        <module name="PackageName"/>
        <module name="ConstantName"/>

        <!-- Imports -->
        <module name="AvoidStarImport"/>
        <module name="UnusedImports"/>

        <!-- Size Violations -->
        <module name="LineLength">
            <property name="max" value="120"/>
        </module>

        <!-- Whitespace -->
        <module name="WhitespaceAfter"/>
        <module name="WhitespaceAround"/>

        <!-- Coding -->
        <module name="EmptyStatement"/>
        <module name="EqualsHashCode"/>
    </module>
</module>
