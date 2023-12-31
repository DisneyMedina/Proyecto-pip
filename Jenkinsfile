pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk "17.0.9"
    }
    environment {
        NEXUS_USERNAME = 'admin'
        NEXUS_PASSWORD = 'admin'
        registryServer = '192.168.1.34:5000'
        imageName = readMavenPom().getArtifactId()
        imageVersion = readMavenPom().getVersion()
        imageTag = 'cibertec/pipv1/${imageName}:${imageVersion}'
        dockerhub_PSW = credentials('nexus-docker-registry').password
        dockerhub_USR = credentials('nexus-docker-registry').username

    }
    stages {
        stage('Initialize') {
            steps {
                script {
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                }
            }
        }
        stage('Clean') {
            steps {
                script {
                    withMaven(maven: 'Maven', mavenSettingsConfig: '2ea57b6f-d6f0-42bc-9770-f24d4170a439') {
                        sh 'mvn -B -U -DskipTests clean install'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    withMaven(maven: 'Maven', mavenSettingsConfig: '2ea57b6f-d6f0-42bc-9770-f24d4170a439') {
                        sh 'mvn -DskipTests deploy'
                    }
                }
            }
        }
        stage('Pushing to Image Registry') {
            steps {
                script {
                    sh 'docker tag $imageTag $registryServer/$imageTag'
                    sh "echo '$dockerhub_PSW' | docker login -u '$dockerhub_USR' --password-stdin $registryServer"
                    sh 'docker push $registryServer/$imageTag'
                }
            }
        }
    }
}
