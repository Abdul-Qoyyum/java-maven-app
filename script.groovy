def buildJar() {
    echo "building the application..."
    sh "mvn package"
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'my-docker-hub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t cloudnqt/demo-app:jma-2.0 .'
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
    sh "mvn test"
}


return this