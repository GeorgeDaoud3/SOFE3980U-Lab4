pipeline {
  agent any
  tools {
    maven 'maven' 
  }
  stages {
    stage ('Init') {
      steps {
        sh 'echo "Start of Job"'
        sh 'ls -la'
      }
    }
    stage ('test') {
      steps {
        sh 'mvn clean test -f ./BinaryCalculatorWebapp/pom.xml'
      }
    }
    stage ('build') {
      steps {
        sh 'mvn package -DskipTests -f ./BinaryCalculatorWebapp/pom.xml'
      }
    }
    stage ('Deploy') {
      steps {
        sh 'echo "bye bye"'
      }
    }
  }
}