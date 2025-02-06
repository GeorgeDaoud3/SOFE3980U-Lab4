# Milestone 4: Continuous Integration and Continuous Delivery (Jenkins)
## Objective   
1. Git Familiar with Jenkins.
2. Understand the pipeline syntax used by Jenkins.
3. Configure a continuous integration pipeline for a Jenkins job. 
4. Configure a continuous deployment pipeline for a Jenkins job.
## Repository:   
[https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git](https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git) 

## Introduction and Getting Started 
1. Watch the following video about [Dev-ops](https://www.youtube.com/watch?v=LFDrDnKPOTg). 
2. Install Jenkins Server on GCP, using Helm. Helm is a tool that creates customized applications within Kubernetes.
   1. Watch the following video about [Helm](https://www.youtube.com/watch?v=fy8SHvNZGeE).
   2. If there is no Kubernetes cluster within your GCP project, Create a cluster as illustrated in [MS3](https://github.com/GeorgeDaoud3/SOFE3980U-Lab3#setup-google-kubernetes-engine-gke). To get the cluster information.
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
   6. **(optional)** to remove the **cd-jenkins** from Kubernetes. **<ins>Don’t run it now</ins>**.
      ```cmd
      helm uninstall cd-jenkins
      ```
   7. Retrieve Jenkins external IP,
      ```cmd 
      kubectl get services
      ```

      ![sq4_1](figures/sq4_1.jpg)
    
   8. Access Jenkins server using the following URL http://\<jenkinsIP\>:8080. Where \<jenkinsIP\> is the IP obtained in the previous step. It should look like

      ![sq4_2](figures/sq4_2.jpg)
    
      To fix a connection issue, click Manage Jenkins, select Security, and check Enable proxy compatibility under CSRF Protection. Click the save button. (Note: the connection issue won't be fixed until the configuration is saved. You may need to repeat this step until the configuration is saved) 

   9. To fix a connection issue, click **Manage Jenkins**, select **Security**,and check **Enable proxy compatibility** under **CSRF Protection**. Click the **save** button. (Note: the connection issue won’t be fixed until the configuration is saved. You may need to repeat this step until the configuration is saved)
   
      ![sq4_3](figures/sq4_3.jpg)
   
## Protect Jenkins by Setting a Username and Password
1. Click **Manage Jenkins**, select **System**, set the **Security Realm** to **Jenkins' own user database**, set the **Authentication** to **Logged-in users can do anything**, and uncheck **Allow users to sign up** and **Allow anonymous  read access**. Finally, click the **save** button.
   
   ![pd_1](figures/pd_1.jpg)
   
3. Then, Jenkins forwards you to another page to set the username and password for the admin.
   
   ![pd_2](figures/pd_2.jpg)
   
## Create a Maven project job
1. Create a GitHub account if you don't have one. Create a repository. Copy this [repository](https://github.com/GeorgeDaoud3/SOFE3980U-Lab4.git)'s content  into your repository.
2. Create a GitHub token: Within the GitHub page, login into your account. Choose  **settings**, **Developer settings**, **Personal access token**, and **Token (classic)**. Click **Generate new token**. Select **Generate new token (classic)**. Set the note, check **public_repo**, and click **Generate token**. Finally, copy the token code.
   
   ![sq4_7](figures/sq4_7.jpg)
   
3. Install **Pipeline Maven Integration** Plugin: select **Dashboard**, **Manage Jenkins**, then **Manage Plugins**. Choose **Available Plugins**. Search for **Pipeline Maven Integration**. After checking it, click **Download now and install after restart**. Check **Restart Jenkins when installation is complete and no jobs are running**. Finally, wait until the plugin is installed and Jenkins restarts.

   ![sq4_4](figures/sq4_4.jpg)
   
4. Repeat the previous step to install **Maven Integration** and  **GitHub** plugins
5. Configure GitHub Plugin
   1. Select **Dashboard**, **Manage Jenkins**, then **System**. Scroll down to the **GitHub** section. Click the **Add GitHub Server** button. Name it, **github**. Finally, click **Add**.

      ![sq4_8](figures/sq4_8.jpg)
      
   2. Select the **kind** to be **Secret text**. Type the Github token obtained previously in the **Secret** textbox. Set its **ID** to be **GitHub_token**. Finally, click **ADD**.

      ![sq4_9](figures/sq4_9.jpg)
      
   3. Set the **credential** to **GitHub_token**. Test the connection. Then, click **Save**.

      ![sq4_10](figures/sq4_10.jpg)
      
6. Configure Maven Plugin: select **Dashboard**, **Manage Jenkins**, **Tools**, choose **Add Maven**, name it **maven**, choose **Install automatically**, then click **Save**.

   ![sq4_5](figures/sq4_5.jpg)
   
7. Create a Job: select **Dashboard**. Click **New Item**. Name it **binaryCalculate_mvn**, and choose **Maven Project**. Finally, click **Ok**.

   ![sq4_6](figures/sq4_6.jpg)
   
8. Configure the job: The job will automatically pull the repository for each commit. Then, the maven project is built, and the status is sent back to the repository associate it with the commit.
   1. For **Source Code Management**, select **Git**. Fill in the **Repository URL**. Make sure that the **branch** is blank.

      ![sq4_11](figures/sq4_11.jpg)
      
   2. In the **build triggers** section, check **GitHub hook trigger for GITScm polling** to automatically start the job for each new commit to the repository.

      ![sq4_12](figures/sq4_12.jpg)
      
   3. In the **build** section, set the path of the **pom** file in the repository and the build options.

      ![sq4_13](figures/sq4_13.jpg)
      
   4. In the **Post-build Actions** section, **set GitHub commit status (universal)**, which will associate the GitHub commit with the status of the Jenkins job. Change the **Status result:** to **One of the default messages and statuses**.

      ![sq4_14](figures/sq4_14.jpg)
      
   5. Click **Save**.
      
9. To allow Jenkins to start running after each commit automatically, a **webhook** has to be configured in the GitHub repository.
   1. Within the repository page in the Github, select **Settings**. Select **Webhooks**. Then, click **Add webhook**.

      ![sq4_15](figures/sq4_15.jpg)
      
   2. Set the **Payload URL** to http://\<jenkinsIP\>:8080/github-webhook/ and the content type to JSON. Then, click **Add webhook**.

      ![sq4_16](figures/sq4_16.jpg)
      
10. The job will start building once you commit any changes to the repository (or you can trigger it by editting the readme file).
    
    1. To check the status of each run or to build the job manually, navigate to the job page within Jenkins.

       ![sq4_17](figures/sq4_17.jpg)
      
    2. The commit in the GitHub will also have a checkmark and a link to the Jenkins build report.

       ![sq4_18](figures/sq4_18.jpg)


## Create a Jenkins job using a script.
The Other way is to create a customized job by providing a script (**Jenkinsfile**) that describes the Job as a pipeline of stages that would be executed. This method is more flexible. The Jenkinsfile already exists in the repository at the path /BinaryCalculatorWebapp/. 
1.	Watch this video about [the pipeline syntax]( https://www.youtube.com/watch?v=pzbrVVy6ul4)
2.	Read the file [/BinaryCalculatorWebapp/Jenkinsfile](/BinaryCalculatorWebapp/Jenkinsfile) 
    * It defines a pipeline of tasks
    * The first stage in the pipeline is called **Init**. This will print a welcome string and list the current directory that contains the cloned repository.
    * The second stage, **test**, runs the test cases for the project defined at the path **./BinaryCalculatorWebapp/**.
    * The third stage, **build**, builds the project defined at the path **./BinaryCalculatorWebapp/**.
    * The last stage, **Deploy**, is a dummy stage that only displays a string
3.	Go to Jenkins' **Dashboard**. Click **New Item**. Name it **BinaryCalculator_pipeline**. Choose the type to be **Pipeline**.
4.	In the configuration page,
   1. In the **Triggers** section, check **GitHub hook trigger for GITScm polling**.
   2. In the **Pipeline** section, 
      * Set the **Definition** to **Pipeline script from SCM**.
      * Set the **SCM** to **Git**
      * Set the **Repository URL** to your GitHub link.
      * Set the **branches to build** to **main** or leave it blank.
      * Set the **Script Path** to BinaryCalculatorWebapp/Jenkinsfile
5.	As the **webhook** is already configured, it should work automatically when  after each commit. Commit any change to the repository. Then, check that the job is been executed and check its report.

## Continuous Integration / Continuous Deployment (CICD) using Jenkins

A continuous deployment will be added to the previous Jenkins Job. 

### 1. Create a tunnel to GKE

As Jenkins runs in GKE, we will use a tunnel created with Jenkins to connect to GKE and use Docker containers as Jenkins nodes (workers).
1. In the Jenkins user interface, select **Manage Jenkins**.
2. Click **Clouds**.
3. Click **New cloud**.
4. Type any name under **Cloud name** and then select **Kubernetes** for Type.
5. Click **Create**.
6. In the **Jenkins URL** field, enter the following value:
    ``` txt
    http://cd-jenkins:8080
    ```
7. In the **Jenkins tunnel** field, enter the following value:
    ``` txt
    cd-jenkins-agent:50000
    ```
8. Click **Save**.

### 2. Create a Service Account and Record needs information from GCP
We will start by creating a service account. It permits dealing with the **Artifact Registry** and **GKE** from any device. A JSON key will be generated and downloaded to your computer to allow you to use the service account
1. In the console of GCP, run
    ``` cmd
    cd ~
    
    gcloud iam service-accounts create jenkins-sa

    gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
        --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
        --role "roles/cloudbuild.builds.builder"
   	
    gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
        --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
        --role "roles/container.clusterAdmin"
   	
    gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT \
        --member "serviceAccount:jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com" \
        --role "roles/container.admin"
   	
    gcloud iam service-accounts keys create service_account.json  \
        --iam-account=jenkins-sa@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com  
    ```
    These commands will create a service account, grant it three roles, and generate a JSON key file in the home directory.
2.  Download the **service_account.json** file from the home directory of the GCP console to your computer.
3.  Print the GCP project name, copy it, and save it.
    ``` cmd
    echo $GOOGLE_CLOUD_PROJECT
    ```
4. Navigate to the **Artifact Registry**, copy the full path of the repository created before, and save it.
5.  Navigate to the **Clusters** in **Kubernetes Engine**, copy the cluster Name and location, and save them.

### 3. Save the Important Information in Jenkins
The information obtained in the previous subsection will be saved in Jenkins as secret text and files for security reasons. 
1. In the Jenkins user interface, select **Manage Jenkins**.
2. Click **Credentials**.
3. Click **System**.
4. Click **Global credentials (unrestricted)**.
5. Click **Add credentials**.
6. In the **Kind** field, select **Secret file**, upload the Service account JSON file, set the **ID** field to **serive_account**, and click **Create**.
7. Add another credential with the Kind **Secret Text**. Set the **ID** field to **project_id** and the **Secret** field to the GCP project ID obtained in the previous subsection.
8. Add another credential with the Kind **Secret Text**. Set the **ID** field to **repo_path** and the **Secret** field to the full path of the repository obtained in the previous subsection.
9. Add another credential with the Kind **Secret Text**. Set the **ID** field to **cluster_name** and the **Secret** field to the GKE cluster name obtained in the previous subsection.
10. Add another credential with the Kind **Secret Text**. Set the **ID** field to **cluster_zone** and the **Secret** field to the GKE cluster location obtained in the previous subsection.

### 4. Create Jenkins Job 
1. Read the file [/BinaryCalculatorWebapp/Jenkinsfile_v2](/BinaryCalculatorWebapp/Jenkinsfile_v2)
    * It reads the secret files and text and sets them as environment variables.
    * It creates an agent as a Kubernetes pod that creates a container, namely **gcloud**, from the image **google/cloud-sdk:latest**. It includes a Google Cloud SDK with the gcloud CLI.
    * The first stage, **test**, runs the test cases for the project defined at the path **./BinaryCalculatorWebapp/**.
    * The second stage, **build**, builds the project defined at the path **./BinaryCalculatorWebapp/**.
    * The third stage, **containerize**, initializes the gcloud to be connected to the GCP project using the service account. Then, It builds and pushes the docker image of the **BinaryCalculatorWebapp** project to the Artifact Registry repository. 
    * The fourth stage, **deployment**, initializes the gcloud, then deletes any previous deployment if found and recreates the deployment.
    * The last stage, **service**, initializes the gcloud then creates a load-balancing service for the deployment if it does not exist. Finally, the IP provided to the service will be displayed if the service is running.
2. Select **Dashboard**. Click **New Item**. Name it **BinaryCalculator_cicd**. Choose the type to be **Pipeline**.
3. On the configuration page,
    1. In the Build Triggers section, check **GitHub hook trigger for GITScm polling**.
    2. In the Pipeline section,
        * Set the **Definition** to **Pipeline script from SCM**.
        * Set the **SCM** to **Git**
        * Set the **Repository URL** to your GitHub link.
        * Set the Script Path to **BinaryCalculatorWebapp/Jenkinsfile_v2**
        * Set the branches to build to **main** or leave it blank.
          
As the webhook is already configured, it should work automatically when after each commit. Commit any change to the repository. Then, check that the job is been executed and check its report.

## Discussion:
What do pipeline, node, agent, stage, and steps mean in the context of Jenkins?

## Design:
* Update the Binary Calculator project to the latest version you have implemented. Check that the jobs start running and check their report. 


## Deliverable
* A report containing both the discussion and design parts.
* Your Github link.
* An audible video of about 3 minutes showing the continuous integration part (the two techniques).
* An audible video of about 3 minutes showing the design part.
