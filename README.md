# Milestone 4: Continuous Integration and Continuous Delivery (Jenkins)
## Objective
## Repository: 
[https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git](https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git) 

## Introduction and Getting started
1. Watch the following video about [Dev-ops](https://www.youtube.com/watch?v=LFDrDnKPOTg).
2. Install Jenkins Server on GCP, using Helm. Helm is a tool to create a customized applications within Kubernetes.
   1. Watch the following video about [Helm](https://www.youtube.com/watch?v=fy8SHvNZGeE).
   2. If there is no Kubernetes cluster within your GCP project, Create a cluster (https://github.com/GeorgeDaoud3/SOFE3980U-Lab3#setup-google-kubernetes-engine-gke). To get the cluster information.
	 ```cmd
   kubectl cluster-info
   ```
   3. Pull the Jenkins Helm chart from a repository.
	 ```cmd
   helm repo add jenkinsci https://charts.jenkins.io
   helm repo update
   ```
   4. Clone the repository for the Helm values.
      ```cmd
      cd ~
      git clone https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git
      ```
   5. Use the Helm CLI to deploy the chart **cd-jenkins** with the configuration set from the repository **jenkins/values.yaml** onto Kubernetes with the name **cd-jenkins**.
      ```cmd
      helm install cd-jenkins -f ~/SOFE3980U-Lab4/jenkins/values.yaml jenkinsci/jenkins --wait
      ```
   6. **(optional)** to remove the **cd-jenkins** from Kubernetes. **<u>Don’t run it now<\u>**.
      ```cmd
      helm uninstall cd-jenkins
      ```
   7. Retrieve Jenkins external IP,
      ```cmd
      kubectl get services
      ```
      ![sq4_1](figures/sq4_1.jpg)
   8. Access Jenkins server using the following URL http://<jenkinsIP>:8080. Where <jenkinsIP> is the IP obtained in the previous step. It should look like
      ![sq4_2](figures/sq4_2.jpg)
   9. To fix a connection issue, select **Manage Jenkins**, **configure Global Security**, under **CSRF Protection**, check **Enable proxy compatibility**. Click **save** button. (Note the connection issue won’t be fixed until the configuration is saved. You may need to repeat this step until the configuration is saved)
      ![sq4_3](figures/sq4_3.jpg)
