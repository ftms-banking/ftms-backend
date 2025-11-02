pipeline {
    agent any
    
    tools {
        maven 'Maven 3.9'
    }

    environment {
        SONAR_HOST = 'http://sonarqube:9000'
        DOCKER_IMAGE = 'ftms-backend'
        DOCKER_REGISTRY = 'localhost:5000'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out code from GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building application...'
                sh './mvnw clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo '🧪 Running unit tests...'
                sh './mvnw test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('SonarQube Analysis') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo '📊 Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') {
                    sh """
                        ./mvnw sonar:sonar \
                          -Dsonar.projectKey=ftms-backend \
                          -Dsonar.projectName=FTMS-Backend \
                          -Dsonar.host.url=\${SONAR_HOST}
                    """
                }
            }
        }

        stage('Quality Gate') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo '🚦 Checking quality gate...'
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }

        stage('Package') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo '📦 Packaging application...'
                sh './mvnw package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            steps {
                echo '🐳 Building Docker image...'
                script {
                    sh """
                        docker build -t \${DOCKER_IMAGE}:latest \
                          -t \${DOCKER_IMAGE}:\${BUILD_NUMBER} \
                          -f docker/Dockerfile .

                        docker tag \${DOCKER_IMAGE}:latest \${DOCKER_REGISTRY}/\${DOCKER_IMAGE}:latest
                        docker tag \${DOCKER_IMAGE}:\${BUILD_NUMBER} \${DOCKER_REGISTRY}/\${DOCKER_IMAGE}:\${BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Push to Registry') {
            when {
                branch 'main'
            }
            steps {
                echo '📤 Pushing to Docker registry...'
                script {
                    sh """
                        docker push \${DOCKER_REGISTRY}/\${DOCKER_IMAGE}:latest
                        docker push \${DOCKER_REGISTRY}/\${DOCKER_IMAGE}:\${BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Deploy to Local') {
            when {
                branch 'main'
            }
            steps {
                echo '🚀 Deploying to local Docker...'
                script {
                    sh """
                        # Stop and remove existing container
                        docker stop ftms-app || true
                        docker rm ftms-app || true

                        # Run new container
                        docker run -d \
                          --name ftms-app \
                          --network ftms-enterprise \
                          -p 8090:8080 \
                          -e SPRING_PROFILES_ACTIVE=dev \
                          -e DB_URL=jdbc:mysql://mysql:3306/ftms_db \
                          -e DB_USERNAME=ftms_user \
                          -e DB_PASSWORD=ftms_pass \
                          \${DOCKER_IMAGE}:latest

                        echo "⏳ Waiting for application to start..."
                        sleep 30
                    """
                }
            }
        }

        stage('Health Check') {
            when {
                branch 'main'
            }
            steps {
                echo '❤️ Checking application health...'
                script {
                    sh """
                        for i in {1..10}; do
                            if docker exec ftms-app curl -f http://localhost:8080/actuator/health; then
                                echo "✅ Application is healthy!"
                                exit 0
                            fi
                            echo "Waiting for application... attempt \$i/10"
                            sleep 5
                        done
                        echo "❌ Application health check failed!"
                        exit 1
                    """
                }
            }
        }

        stage('Integration Tests') {
            when {
                branch 'main'
            }
            steps {
                echo '🔗 Running integration tests...'
                sh './mvnw verify -DskipUnitTests'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
            echo "📊 View SonarQube: http://localhost:9000/dashboard?id=ftms-backend"
            echo "🐳 Application: http://localhost:8090/actuator/health"
            echo "📈 Grafana: http://localhost:3000"
        }
        failure {
            echo '❌ Pipeline failed!'
        }
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }
    }
}
