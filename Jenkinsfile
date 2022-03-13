pipeline {
    agent any

    stages {
        stage ('1 gradle clean') {
            steps {
                     gradlew('clean')
            }
        }
        stage ('2 gradle test') {
            steps {
                     gradlew('test')
            }
            post {
                     always {
                         junit '**/build/test-results/test/TEST-*.xml'
                      }
            }
        }

        stage('3 sonarqube code analysis') {
            steps {
                     gradlew('sonarqube')
            }
        }

        stage('4 docker build image') {
            steps {
                 script {
                     sh 'docker build -t Arzu1996/backend .'
                 }
            }
        }
        stage('5 deploy the image') {
            steps {
                 script {
                     withCredentials([string(credentialsId: 'Docker-Hub-Password', variable: 'dockerhubpwd')]) {
                         sh 'docker login -u Arzu1996 -p ${dockerhubpwd}'
                     }
                     sh 'docker push  Arzu1996/backend'
                 }
            }
        }
        stage('6 deploy on K8s') {
            steps {
                 script {
                     sh 'kubectl rollout restart deployment/app-back-deployment -n phonebook-app'
                     }
                 }
            }
        }
    }
}