pipeline {
    agent any

    stages {
        stage ('Code checkout'){
            steps {

                git	(
                        branch: 'asrivastava/awshcl',
                        url:'https://repo.hclets.com/dna-pib/WSO2.git',
                        credentialsId: 'gitaccessUI'
                )

            }
        }
        stage ("Loading essential file"){
            steps{
                sh 'cp /home/users/amit_srivastava/wsofiles/wso2am-3.0.0.zip WSO2Ansible/files/packs'
                sh 'cp /home/users/amit_srivastava/wsofiles/amazon-corretto-8.242.08.1-linux-x64.tar.gz WSO2Ansible/files/lib'


            }
        }
        stage ('Running WSO2 Configuration Job') {
            steps {

                ansiColor('xterm') {
                    ansiblePlaybook(
                            installation: 'ansible',
                            playbook: 'WSO2Ansible/site.yml',
                            inventory: 'WSO2Ansible/dev',
                            credentialsId: 'sshAnsible',
                            disableHostKeyChecking: true,
                            colorized: true
                    )
                }
            }
        }


    }
}
