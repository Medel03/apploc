pipeline {
    agent {
        docker {
            image 'med3301/jenkins-agent:2.3'
            args '--user root -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/jenkins/workspace:/var/lib/jenkins/workspace'
            
        } 
    }

    environment {
        APP_NAME = "gcsp"
        RELEASE = "1.0.0"
        DOCKER_USER = "med3301"
        DOCKER_PASS = "dockerhub-pass"
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
        
    }

    stages {
        stage("Cleanup Workspace") {
            steps {
                cleanWs()  // Clean the workspace before starting the build
            }
        }

        stage("Checkout from SCM") {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/Medel03/cbapp'
            }
        }
        
        stage('Verify Workspace') {
            steps {
                script {
                    // Print current workspace directory
                    sh 'echo "Current Workspace: $(pwd)"'
                    }
                }
            }

        stage("Build Application") {
            steps {
                sh "ls -ltr"
                sh "mvn clean package"
            }
        }

        stage("Test Application") {
            steps {
                sh "mvn test"
            }
        }

        stage("Sonarqube Analysis") {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'jenkins-sonar-token'){
                        sh "mvn sonar:sonar"
                    }
                }
            }
        }

         stage("Quality Gate") {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonar-token'
                }
            }
        }

        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS){
                        docker_image = docker.build "${IMAGE_NAME}"
                    }

                    docker.withRegistry('',DOCKER_PASS){
                        docker_image.push("${IMAGE_TAG}")
			docker_image.push('latest')
                    }
                }
            }
        }





        
        
    }
}
