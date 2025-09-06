pipeline {
    agent any

    tools {
        maven 'Maven'    // Maven tool ka naam jo Jenkins me set kiya hai
        jdk 'JDK21'      // JDK ka naam jo Jenkins me set kiya hai
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://gitlab.com/khushboo2107/selenium-capstone-project.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }
        stage('Publish Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
