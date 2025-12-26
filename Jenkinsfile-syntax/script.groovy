def buildJar() {
    def mvnHome = tool name: 'maven-3.9', type: 'maven'
    echo "building the application..."
    sh "${mvnHome}/bin/mvn package"
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'my-docker-hub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t docker push cloudnqt/demo-app:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push cloudnqt/demo-app:jma-2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

def buildApp() {
    buildJar()
    buildImage()
}

def testApp() {
    echo "running tests..."
    def mvnHome = tool name: 'maven-3.9', type: 'maven'
    sh "${mvnHome}/bin/mvn test"
}

return this
