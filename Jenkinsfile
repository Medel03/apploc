pipeline {
    agent any /*{
        docker {
            image 'med3301/jenkins-agent:2.3'
            args '--user root -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/jenkins/workspace:/var/lib/jenkins/workspace'
            
        } 
    }*/

    environment {
        APP_NAME = "location-app"
        RELEASE = "1.0.0"
        DOCKER_USER = "med3301"
        DOCKER_PASS = "dockerhub-pass"
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
	HELM_VALUES_FILE = 'helm/location-app-chart/values.yaml'
        
    }

    stages {
        stage("Cleanup Workspace") {
            steps {
                cleanWs()  // Clean the workspace before starting the build
            }
        }

        stage("Checkout from SCM") {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'git@github.com:Medel03/apploc.git'
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

	    

	stage('Update tag in Helm Chart') {
            steps {
                script {
                    // Update the Docker image tag in the Helm chart's values.yaml file
                    sh """
		    sed -i 's/tag: .*/tag: "${IMAGE_TAG}"/' ${HELM_VALUES_FILE}
		    """
                }
            }
        }

        stage('Commit and Push Changes') {
            steps {
                script {
                    // Commit the changes to values.yaml and push to the Git repository
                    sh """
                    git config --global user.email "med.elh3301@gmail.com"
		    git config --global user.name "Med EL HARCH"
                    git add ${HELM_VALUES_FILE}
                    git commit -m "Update tag in Helm chart to ${IMAGE_TAG}"
                    git push origin HEAD:main
                    """
                }
            }
        }





        
        
    }
}
